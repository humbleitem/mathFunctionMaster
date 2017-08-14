package computeValue;

public class Ackley implements FunctionComputeValue {

	@Override
	public double computeValue(double[] ans) {

		double sum1 = 0.0;
		double sum2 = 0.0;
		int len = ans.length;

		for (int i = 0; i < len; i++) {
			sum1 += Math.pow(ans[i], 2);
			sum2 += (Math.cos(2 * Math.PI * ans[i]));
		}

		return -20.0*Math.exp(-0.2*Math.sqrt(sum1 / ((double )len))) + 20
                - Math.exp(sum2 /((double )len)) + Math.exp(1.0);
	}

}
