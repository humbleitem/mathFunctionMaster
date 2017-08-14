package randomAlgorithm;

import java.util.ArrayList;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeFactory;
import computeValue.ComputeValue;
import computeValue.FunctionComputeValue;
import geneticAlgorithm.ChromOneD;

public class RandomAlgorithm implements WeaponAlgorithm {

	private RandomInstance randombest;
	private Attribute AT;
	private FunctionComputeValue FCV;

	private ArrayList<RandomInstance> randomList;
	private int populationNum;
	private int terminateCondition;
	private int TN;
	private int numX;
	private double startVariable;
	private double endVariable;

	public RandomAlgorithm(Attribute AT, int TN) {

		this.AT = AT;
		this.TN = TN;

		ComputeFactory computeFactory = new ComputeFactory(AT);
		FCV = computeFactory.getFunctionCompute();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();

		randombest = new RandomInstance(numX);
		randombest.setValue(Double.MAX_VALUE);
		randomList = new ArrayList<RandomInstance>();

	}

	@Override
	public void findAnswer() {
		// TODO Auto-generated method stub
		for (int i = 0; i <= terminateCondition; i++) {
			if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(randombest.getBestValue());
				}
			}

			randomListUpdate();

			compareBest();

		}
		// System.out.println(randombest.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {

		randomList.clear();

		for (Instance instance : population) {

			RandomInstance randomInstance = new RandomInstance(numX);
			randomInstance.setRandomPos(instance.getBestPos());
			randomInstance.setValue(instance.getBestValue());

			randomList.add(randomInstance);

		}
		compareBest();
	}

	@Override
	public void setStopCondition(int time) {

		terminateCondition = time;
	}

	@Override
	public ArrayList<Instance> getPopulation() {
		// TODO Auto-generated method stub

		ArrayList<Instance> population = new ArrayList<Instance>();
		for (RandomInstance random : randomList) {
			population.add(random);
		}

		return population;
	}

	public void compareBest() {

		for (RandomInstance random : randomList) {

			if (random.getBestValue() < randombest.getBestValue()) {

				randombest.setRandomPos(random.getBestPos());
				randombest.setValue(random.getBestValue());

			}

		}

	}

	public void randomListUpdate() {

		for (RandomInstance random : randomList) {

			for (int i = 0; i < numX; i++) {

				random.getBestPos()[i] = Math.random() * (endVariable - startVariable) - endVariable;
			}
			random.setValue(FCV.computeValue(random.getBestPos()));
		}

	}

	@Override
	public double getIndividualPos() {

		return randombest.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {

		return randombest;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub
		
	}

}
