package computeValue;

public class Zakharov implements FunctionComputeValue{

	@Override
	public double computeValue(double[] ans) {
		
		double sum = 0;
		int len = ans.length;
		double ansOne = 0;
		double ansTwo = 0;
		
		for(int i =0;i<len;i++){
			
			ansOne = ansOne + Math.pow(ans[i], 2);
			ansTwo = ansTwo + (0.5*(i+1)*ans[i]);
					
		}
		sum = ansOne + Math.pow(ansTwo, 2) + Math.pow(ansTwo, 4);
		
		
		return sum;
	}

}
