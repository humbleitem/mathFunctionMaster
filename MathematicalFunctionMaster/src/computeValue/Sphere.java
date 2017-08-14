package computeValue;

public class Sphere implements FunctionComputeValue{

	//scope [-infinite , infinite]
	
	
	@Override
	public double computeValue(double[] ans) {
		
		double sum = 0;
		int len = ans.length;
		for(int i =0;i<len;i++){
			
			sum = sum + Math.pow(ans[i], 2);
			
		}
		
		return sum;
	}

	
	
}
