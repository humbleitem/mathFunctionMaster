package algorithm;

import artificialAlogorithm.ABC;
import geneticAlgorithm.GeneticBit;
import geneticAlgorithm.GeneticDuplicate;
import geneticAlgorithm.GeneticNotDuplicate;
import geneticAlgorithm.GeneticOneD;
import particleAlogrithm.PSO;
import randomAlgorithm.RandomAlgorithm;

public class AlgorithmFactory {
	
	private Attribute AT;
	private int TN;

	public AlgorithmFactory(Attribute AT,int TN) {
		// TODO Auto-generated constructor stub
		this.AT = AT;
		this.TN = TN;


	}

	public WeaponAlgorithm getAlgorithm() {

		switch (AT.getAlgorithm()) {

		case "genetic":
			return new GeneticOneD(AT, TN);
		case "geneticDuplicate":
			return new GeneticDuplicate(AT, TN);
		case "geneticNotDuplicate":
			return new GeneticNotDuplicate(AT, TN);		
		case "geneticbit":
			return new GeneticBit(AT, TN);
		case "particleswarm":
			return new PSO(AT, TN);
		case "artificialbee":
			return new ABC(AT, TN);
		//	return new ArtificialBee(AT, TN);
		case "randomalgorithm":
			return new RandomAlgorithm(AT, TN);			
		case "default":
			System.out.println("not correct algorithm");
			break;
		}

		return null;

	}

}
