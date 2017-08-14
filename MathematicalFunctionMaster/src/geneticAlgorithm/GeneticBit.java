package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeFactory;
import computeValue.ComputeValue;
import computeValue.FunctionComputeValue;

public class GeneticBit implements WeaponAlgorithm {

	private FunctionComputeValue FCV;
	private ArrayList<ChromOneD> ChromList;
	private ChromOneD bestChrom;
	private Attribute AT;

	private int populationNum;
	private int terminateCondition;
	private double crossoverRate = 0.7;
	private double mutationRate = 0.1;
	private int bitLength = 15;
	private int TN;
	private int numX;
	private double startVariable;
	private double endVariable;

	public GeneticBit(Attribute AT, int TN) {

		this.AT = AT;
		this.TN = TN;

		ComputeFactory computeFactory = new ComputeFactory(AT);
		FCV = computeFactory.getFunctionCompute();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();

		bestChrom = new ChromOneD(numX);
		bestChrom.setValue(Double.MAX_VALUE);
		ChromList = new ArrayList<ChromOneD>();

	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {

			if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(bestChrom.getBestValue());

				}
			}

			selection();

			crossover();

			mutation();

		}

		// System.out.println(bestChrom.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {
		
		ChromList.clear();
		// System.out.println(ChromList.size());
		for (Instance instance: population) {
			
			ChromOneD chrom = new ChromOneD(numX);
			chrom.setAns(instance.getBestPos());
			chrom.setValue(instance.getBestValue());
			
			ChromList.add(chrom);
			
		}
		compareBest();

	}

	@Override
	public void setStopCondition(int time) {
		// TODO Auto-generated method stub
		terminateCondition = time;
	}

	@Override
	public ArrayList<Instance> getPopulation() {

		ArrayList<Instance> population = new ArrayList<Instance>();
		for (ChromOneD chrom : ChromList) {
			population.add(chrom);
		}

		return population;
	}

	public void selection() {

		double totalValue = 0;

		compareBest();

		for (ChromOneD chrom : ChromList) {
			totalValue += (1 / chrom.getBestValue());
		}
		// use roulette wheel selection
		for (int i = 0; i < populationNum; i++) {
			double rand = Math.random();
			for (int j = 0; j < ChromList.size(); j++) {
				rand -= (1 / ChromList.get(j).getBestValue()) / totalValue;
				if (rand < 0) {
					// add chromosome behind original chromosome
					ChromList.add(ChromList.get(j));
					break;
				}
			}
		}

		// remove original chromosome
		int tmp = ChromList.size();
		for (int i = 0; i < tmp - populationNum; i++) {
			ChromList.remove(0);
		}

	}

	public void crossover() {
		int finishChrom = 0;
		int parentOne;
		int parentTwo;
		int randPosition;
		int completeCrossover = 0;
		double deicdeCrossover;

		while (finishChrom != populationNum) {
			// find two parent not repeat
			parentOne = (int) (Math.random() * populationNum);
			parentTwo = (int) (Math.random() * populationNum);
			randPosition = (int) (Math.random() * numX);
			while (parentOne == parentTwo) {
				parentTwo = (int) (Math.random() * populationNum);
			}

			double[] arr1 = new double[numX];
			double[] arr2 = new double[numX];
			for (int i = 0; i < numX; i++) {
				// get two parents
				arr1[i] = ChromList.get(parentOne).getBestPos()[i];
				arr2[i] = ChromList.get(parentTwo).getBestPos()[i];
			}

			deicdeCrossover = Math.random();

			// use crossover to generate two child
			if (deicdeCrossover <= crossoverRate) {
				completeCrossover = 1;
				// int changePos = (int) (Math.random() * numX);
				for (int changePos = 0; changePos < numX; changePos++) {

					randPosition = (int) (Math.random() * bitLength);
					while (randPosition == 0) {
						randPosition = (int) (Math.random() * bitLength);
					}

					char[] tmp1 = valueToBit(arr1[changePos]);
					char[] tmp2 = valueToBit(arr2[changePos]);

					for (int j = 0; j < bitLength; j++) {
						if (j >= randPosition) {
							char tmp = tmp1[j];
							tmp1[j] = tmp2[j];
							tmp2[j] = tmp;
						}
					}

					arr1[changePos] = bitToDouble(tmp1);
					arr2[changePos] = bitToDouble(tmp2);
				} // for

			}

			if (completeCrossover == 1) {
				// add child if child is odd
				if (populationNum - finishChrom == 1) {

					ChromOneD chrom = new ChromOneD(numX);
					chrom.setAns(arr1);
					chrom.setValue(FCV.computeValue(arr1));
					ChromList.add(chrom);
					finishChrom += 1;
					// add child if child is even
				} else {
					ChromOneD chrom = new ChromOneD(numX);
					chrom.setAns(arr1);
					chrom.setValue(FCV.computeValue(arr1));
					ChromList.add(chrom);

					ChromOneD chrom2 = new ChromOneD(numX);
					chrom2.setAns(arr2);
					chrom2.setValue(FCV.computeValue(arr2));
					ChromList.add(chrom2);

					finishChrom += 2;

				}
			}
		}

		compareBest();

	}

	public void mutation() {

		double decideMutation;
		double[] tmp = new double[numX];

		for (ChromOneD chrom : ChromList) {
			for (int i = 0; i < numX; i++) {
				tmp[i] = chrom.getBestPos()[i];
			}

			for (int i = 0; i < numX; i++) {
				decideMutation = Math.random();
				if (decideMutation <= mutationRate) {
					// mutate attack target
					double mutationNumber = (Math.random() * (endVariable - startVariable) - endVariable);
					tmp[i] = mutationNumber;
				}
			}
			chrom.setAns(tmp);
			chrom.setValue(FCV.computeValue(tmp));
		}
	}

	public char[] valueToBit(double input) {

		int in = (int) Math.round((input + AT.getEndVariable()) * 1000);
		String bitString = null;

		char[] bitChar = new char[bitLength];
		char initial = '0';

		Arrays.fill(bitChar, initial);

		bitString = Integer.toString(in, 2);
		int StringLen = bitString.length();

		for (int i = 0; i < bitLength; i++) {

			if (StringLen - 1 - i >= 0) {
				bitChar[bitLength - i - 1] = bitString.charAt(StringLen - 1 - i);
			}
		}

		return bitChar;
	}

	public double bitToDouble(char[] bit) {

		double num = 0;

		String tmp = String.valueOf(bit);

		double returnNum = Integer.valueOf(tmp, 2);

		// if not do this move will generate small num

		returnNum = (returnNum / 1000 - AT.getEndVariable()) * 1000;

		returnNum = Math.round(returnNum);

		returnNum = returnNum / 1000;

		return returnNum;
	}

	public void compareBest() {

		for (ChromOneD chrom : ChromList) {
			if (chrom.getBestValue() < bestChrom.getBestValue()) {
				bestChrom.setValue(chrom.getBestValue());
				bestChrom.setAns(chrom.getBestPos());
			}
		}

	}

	@Override
	public double getIndividualPos() {

		return bestChrom.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return bestChrom;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub
		
	}

}
