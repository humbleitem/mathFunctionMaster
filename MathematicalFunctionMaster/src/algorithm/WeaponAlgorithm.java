package algorithm;

import java.util.ArrayList;

public interface WeaponAlgorithm {
	
	void findAnswer();
	void setPopulation(ArrayList<Instance> population);
	void setStopCondition(int time);
	void setGlobalBestParticle(Instance instance);
	ArrayList<Instance> getPopulation();
	double getIndividualPos();
	Instance getBestLocalAnswer();

}
