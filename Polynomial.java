public class Polynomial {
	// fields
	int coefficientNum;
	double[] polynomial;
	
	// constructor with no arguments
	public Polynomial() {
		coefficientNum = 0;
		double[] x = {0};
		polynomial = x;
	}
	// constructor with array argument
	public Polynomial(double[] coeff) {
		coefficientNum = coeff.length;
		double[] x = new double[coefficientNum];
		for (int i=0; i<coefficientNum; i++) {
			x[i] = coeff[i];
		}
		polynomial = x;
	}
	
	// method add (assuming coeff and calling object are the same length)
	public Polynomial add(Polynomial coeff) {
		Polynomial addedPolynomial;
		int length;
		if (coeff.polynomial.length >= polynomial.length) {
			length = coeff.polynomial.length;
		} else {
			length = polynomial.length;
		}

		double[] arr = new double[length];
		
		for (int i=0; i<length; i++) {
			double sum1;
			double sum2;
			if (i >= coeff.polynomial.length) {
				sum2 = 0.0;
				sum1 = polynomial[i];
			} else if (i >= polynomial.length) {
				sum2 = coeff.polynomial[i];
				sum1 = 0.0;
			} else {
				sum2 = coeff.polynomial[i];
				sum1 = polynomial[i];
			}
			arr[i] = sum1 + sum2;
		}
		addedPolynomial = new Polynomial(arr);
		return addedPolynomial;
	}
	
	// method evaluate
	public double evaluate(double x) {
		double sum = 0;
		for (int i=0; i<coefficientNum; i++) {
			sum += polynomial[i] * Math.pow(x, i);
		}
		return sum;
	}
	
	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);
	}
}