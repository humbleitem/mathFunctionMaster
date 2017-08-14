package genPopulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import algorithm.Attribute;
import algorithm.Instance;
import artificialAlogorithm.Nectar;
import computeValue.ComputeFactory;
import computeValue.FunctionComputeValue;
import geneticAlgorithm.ChromOneD;
import particleAlogrithm.Particle;

public class populationManager {

	private BufferedReader br;
	private Attribute AT;
	private FunctionComputeValue FCV;
	private int populationNum;
	private ArrayList<Instance> arrayPopulation;
	private int numX;
	private double startVariable;
	private double endVariable;
	private double initialVelocityEdge = 1;
	private int limitNumber = 1400;

	public populationManager(Attribute AT) {

		this.AT = AT;

		arrayPopulation = new ArrayList<Instance>();
		populationNum = AT.getPopulation();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();
		ComputeFactory computeFactory = new ComputeFactory(AT);
		FCV = computeFactory.getFunctionCompute();

	}

	public ArrayList<Instance> randomPopulation() {

		arrayPopulation.clear();
		double ans[] = new double[numX];
		ArrayList<Double> compareValue = new ArrayList<Double>();
		for (int i = 0; i < populationNum; i++) {
			for (int j = 0; j < numX; j++) {
				ans[j] = Math.random() * (endVariable - startVariable) - endVariable;

			}
			double costValue = FCV.computeValue(ans);
			Instance instance = getInstane(ans, costValue);

			if (compareValue.size() == 0) {
				compareValue.add(costValue);
				arrayPopulation.add(instance);

			} else {
				boolean same = false;
				// compareSame
				for (double value : compareValue) {

					if (Double.compare(value, costValue) == 0) {
						same = true;
						break;
					}

				}
				if (same) {
					i = i - 1;
				} else {
					compareValue.add(costValue);
					arrayPopulation.add(instance);
				}
			}

		}

		return arrayPopulation;
	}

	public ArrayList<Instance> filePopulation() {

		arrayPopulation.clear();

		double ans[] = new double[numX];

		try {
			 br = new BufferedReader(new FileReader(System.getProperty("user.dir") +
					 "\\population\\population_"
					 + AT.getFunction() + "_" + AT.getNumX() + "_" + populationNum + ".txt"));

			for (int i = 0; i < populationNum; i++) {
				String[] str = br.readLine().split(" ");
				for (int j = 0; j < numX; j++) {
					ans[j] = Double.parseDouble(str[j]);
				}
				double value = FCV.computeValue(ans);
				Instance instance = getInstane(ans, value);
				arrayPopulation.add(instance);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayPopulation;

	}

	public void storePopulation(ArrayList<Instance> population) {

		arrayPopulation.clear();
		for (Instance instance : population) {
			arrayPopulation.add(instance);
		}
		int leng = arrayPopulation.size();
		// sort population
		for (int i = 0; i < leng - 1; i++) {
			for (int j = 0; j < leng - i - 1; j++) {
				if (arrayPopulation.get(j).getBestValue() > arrayPopulation.get(j + 1).getBestValue()) {
					Collections.swap(arrayPopulation, j, j + 1);
				}
			}
		}

	}

	public void addPopulation(Instance instance) {
		if (!AT.getDuplicate()) {

			boolean same = false;
			
			for (Instance instance2 : arrayPopulation) {

				if (instance2.getBestValue() == instance.getBestValue()) {
					same = true;

					break;
				}

			}
			if (!same) {

				minusPopulation();
				arrayPopulation.add(0, instance);
			}
		} else {

			minusPopulation();
			arrayPopulation.add(0, instance);

		}
	}

	public void minusPopulation() {
		arrayPopulation.remove(arrayPopulation.size() - 1);
	}

	public ArrayList<Instance> getPopulation() {

		
		
		return arrayPopulation;

	}

	public Instance getInstane(double[] pos, double value) {

		switch (AT.getAlgorithm()) {

		case "geneticDuplicate":

			ChromOneD chromOneD = new ChromOneD(numX);
			chromOneD.setAns(pos);
			chromOneD.setValue(value);
			return chromOneD;
		case "particleswarm":
			Particle particle = new Particle(numX);
			particle.setParticlePos(pos);
			particle.setValue(value);
			particle.setParticleBestPos(pos);
			particle.setParticleBestValue(value);
			particle.initialVelocity(initialVelocityEdge);
			return particle;
		case "artificialbee":
			Nectar nectar = new Nectar(numX,limitNumber);
			nectar.setBestPos(pos);
			nectar.setBestValue(value);
			nectar.setFrequency(limitNumber);
			return nectar;
		}
		return null;

	}

}
