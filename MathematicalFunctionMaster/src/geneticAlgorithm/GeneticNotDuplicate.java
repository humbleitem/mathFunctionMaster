package geneticAlgorithm;



import java.util.ArrayList;
import java.util.Collections;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeFactory;
import computeValue.ComputeValue;
import computeValue.FunctionComputeValue;

public class GeneticNotDuplicate implements WeaponAlgorithm {

	private FunctionComputeValue FCV;
	private ArrayList<ChromOneD> parentChromList;
	private ArrayList<ChromOneD> childChromList;
	private ChromOneD bestChrom;
	private Attribute AT;

	private int populationNum;
	private int terminateCondition;
	private double crossoverRate = 0.7;
	private double mutationRate = 0.1;
	private int TN;
	private int numX;
	private double startVariable;
	private double endVariable;
	private int reProduceNumber;

	public GeneticNotDuplicate(Attribute AT, int TN) {

		this.AT = AT;
		this.TN = TN;

		ComputeFactory computeFactory = new ComputeFactory(AT);
		FCV = computeFactory.getFunctionCompute();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();

		bestChrom = new ChromOneD(numX);
		bestChrom.setValue(Double.MAX_VALUE);
		parentChromList = new ArrayList<ChromOneD>();
		childChromList = new ArrayList<ChromOneD>();
		populationNum = AT.getPopulation();

	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {

			if (AT.getExchangeNum() == 1) {

				if (i % 100 == 0) {
					System.out.println(bestChrom.getBestValue());
				}
			}

			reproduce();

			crossover();

			mutation();

			selection();

		}

		// System.out.println(bestChrom.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {
		
		parentChromList.clear();
		reProduceNumber = populationNum / 10;
		
		for (Instance instance: population) {
			
			ChromOneD chrom = new ChromOneD(numX);
			chrom.setAns(instance.getBestPos());
			chrom.setValue(instance.getBestValue());

			parentChromList.add(chrom);
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
		for (ChromOneD chrom : parentChromList) {
			population.add(chrom);
		}

		return population;
	}

	public void reproduce() {

		// clear childChromList
		childChromList.clear();

		int leng = parentChromList.size();
		for (int i = 0; i < leng - 1; i++) {
			for (int j = 0; j < leng - i - 1; j++) {
				if (parentChromList.get(j).getBestValue() > parentChromList.get(j + 1).getBestValue()) {
					Collections.swap(parentChromList, j, j + 1);
				}
			}
		}

		for (int i = 0; i < reProduceNumber; i++) {

			ChromOneD chromOneD = new ChromOneD(numX);
			double[] tmp = new double[numX];
			for (int j = 0; j < numX; j++) {

				tmp[j] = parentChromList.get(i).getBestPos()[j];

			}
			chromOneD.setAns(tmp);
			chromOneD.setValue(parentChromList.get(i).getBestValue());
			childChromList.add(chromOneD);
		}

	}
	

	public void crossover() {
		int parentLen = parentChromList.size();

		int runTime = populationNum / 2;
		for (int i = 0; i < runTime; i++) {

			// decide crossover
			double deicdeCrossover = Math.random();
			if (deicdeCrossover <= crossoverRate) {

				int parentOne;
				int parentTwo;
				int randPosition;

				// find two parent
				parentOne = (int) (Math.random() * parentLen);
				parentTwo = (int) (Math.random() * parentLen);
				randPosition = (int) (Math.random() * numX);

				// find no repeat parent
				while (parentOne == parentTwo) {
					parentTwo = (int) (Math.random() * parentLen);
				}
				// find position not o
				while (randPosition == 0) {
					randPosition = (int) (Math.random() * numX);
				}

				double[] arr1 = new double[numX];
				double[] arr2 = new double[numX];
				for (int j = 0; j < numX; j++) {
					// get two parents
					arr1[j] = parentChromList.get(parentOne).getBestPos()[j];
					arr2[j] = parentChromList.get(parentTwo).getBestPos()[j];
				}

				// use crossover to generate two child
				for (int j = 0; j < numX; j++) {
					if (j >= randPosition) {
						double tmp = arr1[j];
						arr1[j] = arr2[j];
						arr2[j] = tmp;
					}
				}
				compareSame(arr1);
				compareSame(arr2);
			} // end if
		} // end for
	}

	public void mutation() {


		for (ChromOneD chrom : parentChromList) {

			double decideMutation = Math.random();
			if (decideMutation <= mutationRate) {

				double[] mutArr = new double[numX];
				int randomPosition = (int) (Math.random() * numX);
				double mutationNumber = (Math.random() * (endVariable - startVariable) - endVariable);
				// avoid not mutation
				while (mutArr[randomPosition] == mutationNumber) {
					mutationNumber = (Math.random() * (endVariable - startVariable) - endVariable);
				}

				for (int j = 0; j < numX; j++) {
					mutArr[j] = chrom.getBestPos()[j];
				}

				// mutate attack target
				mutArr[randomPosition] = mutationNumber;

				compareSame(mutArr);

			} // end if
		} // end for
		
	}
	

	public void selection() {

		double totalValue = 0;
		parentChromList.clear();

		for (ChromOneD chrom : childChromList) {
			totalValue += (1 / chrom.getBestValue());
		}

		// use roulette wheel selection
		for (int i = 0; i < populationNum; i++) {
			double rand = Math.random();
			for (int j = 0; j < childChromList.size(); j++) {
				rand -= (1 / childChromList.get(j).getBestValue()) / totalValue;
				if (rand < 0) {
					// add chromosome behind original chromosome
					parentChromList.add(childChromList.get(j));

					break;
				}
			}
		}

		compareBest();

	}

	public void compareBest() {

		for (ChromOneD chrom : parentChromList) {
			if (chrom.getBestValue() < bestChrom.getBestValue()) {
				bestChrom.setValue(chrom.getBestValue());
				bestChrom.setAns(chrom.getBestPos());
			}
		}

	}
	
	public void compareSame(double[] arr) {

		boolean same = false;
		for (ChromOneD chromOneD : childChromList) {

			if (FCV.computeValue(arr) == chromOneD.getBestValue()) {

				same = true;
			}

		}
		if (!same) {
			// add in chromList
			ChromOneD chrom = new ChromOneD(numX);
			chrom.setAns(arr);
			chrom.setValue(FCV.computeValue(arr));
			childChromList.add(chrom);
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
