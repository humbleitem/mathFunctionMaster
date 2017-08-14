package computeValue;

import java.util.ArrayList;

import algorithm.Attribute;
import geneticAlgorithm.ChromOneD;

public class ComputeComposePos {


	private Attribute AT;
	private FunctionComputeValue FCV;
	private int numX;
	private double startVariable;
	private double endVariable;

	public ComputeComposePos(Attribute AT) {

		this.AT = AT;
		
		ComputeFactory computeFactory = new ComputeFactory(AT);	
		FCV = computeFactory.getFunctionCompute();
		numX = AT.getNumX();
		startVariable = AT.getStartVariable();
		endVariable = AT.getEndVariable();
	
	}
	
	public void computeComposeValue(double[] pos){
		
		System.out.println(FCV.computeValue(pos));
		
	}

}
