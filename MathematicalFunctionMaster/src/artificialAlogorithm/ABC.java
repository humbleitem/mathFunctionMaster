package artificialAlogorithm;

import java.util.ArrayList;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeFactory;
import computeValue.FunctionComputeValue;

public class ABC implements WeaponAlgorithm {

	private ArrayList<Nectar> nectarList;
	private Nectar bestNectar;
	private Attribute AT;
	private FunctionComputeValue FCV;

	private int terminateCondition;
	private int freNumber = 1400;

	private int TN;
	private int numX;
	private int empoloyedBeeNumber;
	private int onlookerBeeNumber;
	private double startVariable;
	private double endVariable;

	public ABC(Attribute AT, int TN) {

		this.AT = AT;
		this.TN = TN;
		ComputeFactory computeFactory = new ComputeFactory(AT);
		FCV = computeFactory.getFunctionCompute();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();

		bestNectar = new Nectar(numX,freNumber);
		bestNectar.setBestValue(Double.MAX_VALUE);
		nectarList = new ArrayList<Nectar>();
		empoloyedBeeNumber = AT.getPopulation();
		onlookerBeeNumber = AT.getPopulation();

	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {
			if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(bestNectar.getBestValue());
				}
			}
			

			employedBee();

			onLookerBee();

			scoutBee();

			CompareBestNectar();
		}
		// System.out.println(bestBee.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {
		
		nectarList.clear();

		for (Instance instance :population) {

			Nectar nectar = new Nectar(numX,freNumber);
			nectar.setBestPos(instance.getBestPos());
			nectar.setBestValue(instance.getBestValue());
			nectar.setFrequency(instance.getLimNumber());
			nectarList.add(nectar);
		}
		CompareBestNectar();

	}

	@Override
	public void setStopCondition(int time) {

		terminateCondition = time;
	}

	@Override
	public ArrayList<Instance> getPopulation() {

		ArrayList<Instance> population = new ArrayList<Instance>();
		for (Nectar nectar : nectarList) {
			population.add(nectar);
		}
		return population;
	}

	public void employedBee() {

		int count = 0;
		for (Nectar nectar : nectarList) {
			changePos(nectar, count);
			count++;
		}
	}

	public void onLookerBee() {

		double sumValue = 0;
		for (Nectar nectar : nectarList) {
			sumValue += 1 / nectar.getBestValue();
		}
		// chose position to dispatch onlookerbee
		for (int i = 0; i < onlookerBeeNumber; i++) {
			double roulette = Math.random();
			int count = 0;
			for (Nectar nectar : nectarList) {
				roulette -= (1 / nectar.getBestValue()) / sumValue;
				count++;
				if (roulette < 0) {
					changePos(nectar, count);
					break;
				}
			}
		}
	}

	public void scoutBee() {

		for (Nectar nectar : nectarList) {
			nectar.minusFrequency();
			// find which bee frequency is lower zero , and change bee
			if (nectar.getFrequency() <= 0) {
				double[] tmp = new double[numX];
				for (int j = 0; j < numX; j++) {
					tmp[j] = Math.random() * (endVariable - startVariable) - endVariable;
				}

				nectar.setBestPos(tmp);
				nectar.setBestValue(FCV.computeValue(tmp));
				nectar.setFrequency(freNumber);

			}
		}

	}

	// change position and if value is lower than before replace it
	public void changePos(Nectar nectar, int count) {

		double[] tmpPos = new double[numX];

		int randNectar = (int) (Math.random() * empoloyedBeeNumber);
		while (randNectar == count) {

			randNectar = (int) (Math.random() * empoloyedBeeNumber);
		}
		int randomDim = (int) (Math.random() * numX);

		for (int i = 0; i < numX; i++) {

			tmpPos[i] = nectar.getBestPos()[i];

		}

		// for (int i = 0; i < numX; i++) {
		// tmpPos[i] = nectar.getBestPos()[i]
		// + (Math.random() * 2 - 1) * (nectar.getBestPos()[i]
		// - nectarList.get(randNectar).getBestPos()[i]);
		//
		//
		// if (tmpPos[i] < startVariable)
		// tmpPos[i] = startVariable;
		//
		// if (tmpPos[i] > endVariable)
		// tmpPos[i] = endVariable;
		// }

		tmpPos[randomDim] = nectar.getBestPos()[randomDim] + (Math.random() * 2 - 1)
				* (nectar.getBestPos()[randomDim] - nectarList.get(randNectar).getBestPos()[randomDim]);

		if (tmpPos[randomDim] < startVariable)
			tmpPos[randomDim] = startVariable;

		if (tmpPos[randomDim] > endVariable)
			tmpPos[randomDim] = endVariable;

		double tmpValue = FCV.computeValue(tmpPos);
		// System.out.println(tmpValue);
		if (nectar.getBestValue() > tmpValue) {
			
			// not same
			// if (!compareSame(tmpValue)) {
			nectar.setBestPos(tmpPos);
			nectar.setBestValue(tmpValue);
			nectar.initialFequency();
			// }
		}

	}

	// find which value is lower
	public void CompareBestNectar() {

		for (Nectar nectar : nectarList) {

			if (nectar.getBestValue() < bestNectar.getBestValue()) {

				bestNectar.setBestValue(nectar.getBestValue());
				bestNectar.setBestPos(nectar.getBestPos());
				bestNectar.setFrequency(nectar.getFrequency());
			}

		}

	}

	public boolean compareSame(double nectarValue) {

		boolean same = false;
		for (Nectar nectar2 : nectarList) {

			if (nectar2.getBestValue() == nectarValue) {
				same = true;
				break;
			}
		}
		if (same) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		return bestNectar.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return bestNectar;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub
		
	}

}
