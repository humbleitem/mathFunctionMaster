package computeValue;

public class Griewank implements FunctionComputeValue{

	@Override
	public double computeValue(double[] ans) {
		
		double sum1 = 0;
		double sum2 = 1;
		double sum = 0;
		int len = ans.length;

		for (int i = 0; i < len; i++) {

			sum1 = sum1 + (Math.pow(ans[i], 2) / 4000);
			sum2 = sum2 * Math.cos(Math.pow(ans[i], 2) / Math.sqrt(i+1));
		}

		sum = sum1 - sum2 + 1;

		return sum;
	}

}
