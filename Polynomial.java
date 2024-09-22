public class Polynomial {
	private double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[]{0};
	}

	public Polynomial(double[] coefficients) {
		this.coefficients = new double[coefficients.length];
		System.arraycopy(coefficients, 0, this.coefficients, 0, coefficients.length);
	}

	//Method to add 2 polynomials
	public Polynomial add(Polynomial poly) {
		int maxL = Math.max(this.coefficients.length, poly.coefficients.length);
		double[] resultCoefficients = new double[maxL];
		
		for (int i = 0; i < maxL; i++) {
			double originalC = i < this.coefficients.length ? this.coefficients[i] : 0;
			double newC = i < poly.coefficients.length ? poly.coefficients[i] : 0;
			resultCoefficients[i] = originalC + newC;
		}
		return new Polynomial(resultCoefficients);
	}

	//Method to evaluate poly at x
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, i);
			}
		return result;
	}

	//Method to check if x is root of poly
	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
}