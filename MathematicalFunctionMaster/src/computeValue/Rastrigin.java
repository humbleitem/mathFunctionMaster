package computeValue;

public class Rastrigin implements FunctionComputeValue {

	// scope [-5.12 , 5.12]
	
	public Rastrigin() {
		
		
	}
	
	
	
	@Override
	public double computeValue(double[] ans) {

		double sum = 0;
		int NumN = ans.length;
		
		//calculate value
		sum = NumN * 10;
		
		for (int i = 0; i < NumN; i++) {
			sum = sum + Math.pow(ans[i], 2);
			sum = sum - 10 * Math.cos(2*Math.PI*ans[i]);
		}

		return sum;
	}

}
