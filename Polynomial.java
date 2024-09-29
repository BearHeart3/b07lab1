import java.io.*;
import java.util.*;

public class Polynomial {
	private double[] coefficients;
	private int[] exponents;

	public Polynomial() {
		coefficients = new double[] {0};
		exponents = new int[] {0};
	}

	//has to take coef & expo
	public Polynomial(double[] coefficients, int[] exponents) {
		List<Double> coefList = new ArrayList<>();
		List<Integer> expoList = new ArrayList<>();
		for (int i = 1; i < coefficients.length; i++) {
			if (coefficients[i] != 0) {
				coefList.add(coefficients[i]);
				expoList.add(exponents[i]);
			}
		}
		this.coefficients = coefList.stream().mapToDouble(Double::doubleValue).toArray();
		this.exponents = expoList.stream().mapToInt(Integer::intValue).toArray();
	}

	//Constructor that intializes poly from a file
	public Polynomial(File file) throws IOException {
		String polynomialStr = new Scanner(file).nextLine();
		parsePolynomial(polynomialStr);
	}

	private void parsePolynomial(String polynomialStr) {
		List<Double> coefList = new ArrayList<>();
		List<Integer> expoList = new ArrayList<>();

		String[] terms = polynomialStr.split("(?=[+-])");
		for (String term: terms) {
			term = term.trim();
			if (term.isEmpty()) continue;
			int expo = 0;
			double coef = 1;

			if (term.contains("x")) {
				String[] parts = term.split("x");
				coef = parts[0].isEmpty() || parts[0].equals("+") ? 1 : parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
				if (parts.length > 1 && parts[1].startsWith("^")) {
					expo = Integer.parseInt(parts[1].substring(1));
				}
				else {
					expo = 1;
				}
			}
			else {
				coef = Double.parseDouble(term);
			}
			coefList.add(coef);
			expoList.add(expo);
		}

		//remove duplicate expo by combining coefs
		this.coefficients = new double[expoList.size()];
		this.exponents = new int[expoList.size()];
		for (int i = 0; i < expoList.size(); i++) {
			double currentCoef = coefList.get(i);
			int currentExpo = expoList.get(i);
			int index = indexofExponent(currentExpo);
			if (index != -1)
			{
				this.coefficients[index] += currentCoef;
			}
			else {
				this.coefficients[i] = currentCoef;
				this.exponents[i] = currentExpo;
			}
		}
	}


	private int indexofExponent(int expo) {
		for (int i = 0; i < exponents.length; i++) {
			if (exponents[i] == expo)
				return i;
		}
		return -1;
	}

//Method to add 2 polynomials
	public Polynomial add(Polynomial poly) {
		List<Double> coefList = new ArrayList<>();
		List<Integer> expoList = new ArrayList<>();

		for (int i = 1; i < this.exponents.length; i++) {
			coefList.add(this.coefficients[i]);
			expoList.add(this.exponents[i]);
		}
		for (int i = 1; i < poly.exponents.length; i++) {
			int index = indexofExponent(poly.exponents[i]);
			if (index != -1) {
				coefList.set(index, coefList.get(index) + poly.coefficients[i]);
			}
			else {
				coefList.add(poly.coefficients[i]);
				expoList.add(poly.exponents[i]);
			}
		}

		return new Polynomial(coefList.stream().mapToDouble(Double::doubleValue).toArray(), expoList.stream().mapToInt(Integer::intValue).toArray());
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

//Method to multiply 2 poly
	public Polynomial multiply(Polynomial poly) {
		List<Double> coefList = new ArrayList<>();
		List<Integer> expoList = new ArrayList<>();

		for (int i = 1; i < this.exponents.length; i++) {
			for (int j = 1; j < poly.exponents.length; j++) {
				double newCoef = this.coefficients[i] * poly.coefficients[j];
				int newExpo = this.exponents[i] + poly.exponents[j];

				int index = indexofExponent(newExpo);
				if (index != -1)
				{
					coefList.set(index, coefList.get(index) + newCoef);
				}
				else {
					coefList.add(newCoef);
					expoList.add(newExpo);
				}
			}
		}
		return new Polynomial(coefList.stream().mapToDouble(Double::doubleValue).toArray(), expoList.stream().mapToInt(Integer::intValue).toArray());
	}

//Method to save poly to file
	public void saveToFile(String fileName) throws IOException {
		try (FileWriter writer = new FileWriter(fileName)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < coefficients.length; i++) {
				if (i > 0 && coefficients[i] > 0)
				{
					sb.append("+");
				}
				sb.append(coefficients[i]);
				if (exponents[i] > 0) {
					sb.append("x");
					if (exponents[i] > 1)
					{
						sb.append("^").append(exponents[i]);
					}
				}
			}
			writer.write(sb.toString());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < coefficients.length; i++) {
			if (i > 0 && coefficients[i] > 0)
			{
				sb.append("+");
			}
			sb.append(coefficients[i]);
			if (exponents[i] > 0) {
				sb.append("x");
				if (exponents[i] > 1) {
					sb.append("^").append(exponents[i]);
				}
			}
		}
		return sb.toString();
	}
}