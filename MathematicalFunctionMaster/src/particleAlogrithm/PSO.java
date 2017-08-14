package particleAlogrithm;

import java.util.ArrayList;

import org.w3c.dom.Attr;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeFactory;
import computeValue.ComputeValue;
import computeValue.FunctionComputeValue;

public class PSO implements WeaponAlgorithm {

	private ArrayList<Particle> particleList;
	private Particle globalBestparticle;
	private Attribute AT;
	private FunctionComputeValue FCV;

	private int terminateCondition;
	private int particleNumber;
	private double MaxVelocity = 0.1;
	private double c1 = 1;
	private double c2 = 1;
	private double weight = 1;
	private int TN;
	private int numX;
	private double startVariable;
	private double endVariable;

	public PSO(Attribute AT, int TN) {

		this.AT = AT;
		this.TN = TN;
		ComputeFactory computeFactory = new ComputeFactory(AT);
		FCV = computeFactory.getFunctionCompute();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();

		globalBestparticle = new Particle(numX);
		globalBestparticle.setParticleBestValue(Double.MAX_VALUE);
		particleList = new ArrayList<Particle>();
	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {
			if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(globalBestparticle.getBestValue());
				}
			}

			adjustBestValue();

			calculatePos();

			compareBest();

			// System.out.println("test: "+ globalBestparticle.getBestValue());

		}

		// System.out.println(globalBestparticle.getBestValue());

	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {
		// TODO Auto-generated method stub
		particleList.clear();

		for (Instance instance : population) {

			Particle particle = new Particle(numX);

			particle.setParticlePos(instance.getParticlePos());
			particle.setValue(instance.getParticleValue());
			particle.setParticleBestPos(instance.getBestPos());
			particle.setParticleBestValue(instance.getBestValue());
			particle.setVelocity(instance.getVelocity());
			particleList.add(particle);

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
		// TODO Auto-generated method stub
		ArrayList<Instance> population = new ArrayList<Instance>();
		for (Particle particle : particleList) {
			population.add(particle);
		}

		return population;
	}

	public void adjustBestValue() {

		for (Particle particle : particleList) {
			// update local best value
			if (particle.getParticleValue() < particle.getBestValue()) {
				particle.setParticleBestValue(particle.getParticleValue());
				particle.setParticleBestPos(particle.getParticlePos());

			}
		}
		compareBest();
	}

	public void calculatePos() {

		// calculate velocity
		for (Particle particle : particleList) {
			double[] tmpV = new double[numX];
			double[] tmpP = new double[numX];

			// for (int i = 0; i < numX; i++) {
			//
			// tmpV[i] = weight * particle.getVelocity()[i]
			// + c1 * Math.random() * (particle.getBestPos()[i] -
			// particle.getParticlePos()[i])
			// + c2 * Math.random() * (globalBestparticle.getBestPos()[i] -
			// particle.getParticlePos()[i]);
			//
			// // control velocity in [initialVelocityEdge ~
			// // -initialVelocityEdge]
			// if (tmpV[i] > MaxVelocity)
			// tmpV[i] = MaxVelocity;
			// if (tmpV[i] < -MaxVelocity)
			// tmpV[i] = -MaxVelocity;
			//
			// tmpP[i] = particle.getParticlePos()[i] + tmpV[i];
			//
			// if (tmpP[i] > endVariable)
			// tmpP[i] = endVariable;
			// if (tmpP[i] < startVariable)
			// tmpP[i] = startVariable;
			//
			// }
			for (int i = 0; i < numX; i++) {

				tmpV[i] = particle.getVelocity()[i];
				tmpP[i] = particle.getParticlePos()[i];
			}
			int randomChange = (int) (Math.random() * numX);

			tmpV[randomChange] = weight * particle.getVelocity()[randomChange]
					+ c1 * Math.random()
							* (particle.getBestPos()[randomChange] - particle.getParticlePos()[randomChange])
					+ c2 * Math.random()
							* (globalBestparticle.getBestPos()[randomChange] - particle.getParticlePos()[randomChange]);

			// control velocity in [initialVelocityEdge ~
			// -initialVelocityEdge]
			if (tmpV[randomChange] > MaxVelocity)
				tmpV[randomChange] = MaxVelocity;
			if (tmpV[randomChange] < -MaxVelocity)
				tmpV[randomChange] = -MaxVelocity;

			tmpP[randomChange] = particle.getParticlePos()[randomChange] + tmpV[randomChange];

			if (tmpP[randomChange] > endVariable)
				tmpP[randomChange] = endVariable;
			if (tmpP[randomChange] < startVariable)
				tmpP[randomChange] = startVariable;

			particle.setVelocity(tmpV);
			particle.setParticlePos(tmpP);
			particle.setValue(FCV.computeValue(tmpP));
		}

	}

	public void compareBest() {

		for (Particle particle : particleList) {

			if (particle.getParticleValue() < globalBestparticle.getBestValue()) {

				globalBestparticle.setParticleBestValue(particle.getParticleValue());
				globalBestparticle.setParticleBestPos(particle.getParticlePos());

			}
		}
	}

	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		return globalBestparticle.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub

		return globalBestparticle;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {

		if (globalBestparticle.getBestValue() > instance.getBestValue()) {

			globalBestparticle.setParticleBestValue(instance.getBestValue());
			globalBestparticle.setParticleBestPos(instance.getBestPos());
		}

	}

}
