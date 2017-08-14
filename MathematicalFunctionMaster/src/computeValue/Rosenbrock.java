package computeValue;

public class Rosenbrock implements FunctionComputeValue{

	
	
	@Override
	public double computeValue(double[] ans) {
		
		
		double sum = 0;
		double tmp = 0;
		double ansNow = 0;
		double ansNext = 0;
		int len = ans.length;
		
		for(int i =0;i<len-1;i++){
			
			ansNow = ans[i];
			ansNext = ans[i+1];
			tmp = 100*(Math.pow((ansNext- Math.pow(ansNow, 2 )),2))+Math.pow((ansNow-1),2);
			sum = sum + tmp;			
		}
			
		return sum;
	}

	
	
}
