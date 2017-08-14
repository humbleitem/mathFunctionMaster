package computeValue;

public class Schwefel implements FunctionComputeValue {

	@Override
	public double computeValue(double[] ans) {

		double sum = 0;
		int len = ans.length;
		sum = 418.9829 * len;

		for (int i = 0; i < len; i++) {

			sum = sum - (ans[i] * (Math.sin(Math.sqrt(Math.abs(ans[i])))));

		}

		return sum;
	}

}
