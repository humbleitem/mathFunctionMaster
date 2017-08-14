package computeValue;

import algorithm.Attribute;

public class ComputeFactory {
	
	private Attribute AT;
	
	public ComputeFactory(Attribute AT) {
		
		this.AT = AT;
		
		
	}
	
	public FunctionComputeValue getFunctionCompute(){
		
		switch(AT.getFunction()){
		
		case "rastrigin":
			
			return new Rastrigin();
		case "sphere":
			return new Sphere();
		case "ackley":
			return new Ackley();
		case "zakharov":
			return new Zakharov();
		case "rosenbrock":
			return new Rosenbrock();
		case "griewank":
			return new Griewank();
		case "schwefel":
			return new Schwefel();
		
		}
		
		
		
		
		return null;
	}
	
	
	

}
