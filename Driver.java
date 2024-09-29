import java.io.*;

public class Driver {
	public static void main(String[] args) {
		try {
			//test no-argument
			Polynomial p = new Polynomial();
			System.out.println("Polynomial p: " + p);
			System.out.println("p evaluated at x=3: " + p.evaluate(3));

			//Test poly creation w/coef & expo
			double[] coef1 = {6, -2, 5};
			int[] expo1 = {0, 1, 3};
			Polynomial p1 = new Polynomial(coef1, expo1);
			System.out.println("Polynomial p1: " + p1);

			double[] coef2 = {0, -2, -9};
			int[] expo2 = {1, 2, 4};
			Polynomial p2 = new Polynomial(coef2, expo2);
			System.out.println("Polynomial p2: " + p2);

			//Test add
			Polynomial sum = p1.add(p2);
			System.out.println("Sum of p1 & p2: " + sum);

			//Test multiply
			Polynomial product = p1.multiply(p2);
			System.out.println("Product of p1 & p2: " + product);

			//Test evaluation
			System.out.println("p1 evaluated at x=0.1: " + p1.evaluate(0.1));

			//Test root
			if (sum.hasRoot(1))
			{
				System.out.println("It is a root of the sum polynomial.");
			}
			else
			{
				System.out.println("It is not a root of the sum polynomial.");
			}

			//Test reading from file
			File inputFile = new File("polynomial.txt");
			if (inputFile.exists()) {
				Polynomial p3 = new Polynomial(inputFile);
				System.out.println("Polynomial p3 from file: " + p3);
			}
			else
			{	System.out.println("File 'polynomial.txt' does not exist.");

				//Test saving to a file
				String outputFileName = "output_polynomial.txt";
				p1.saveToFile(outputFileName);
				System.out.println("Polynomial p1 saved to " + outputFileName);
			}
		}		catch (IOException e) {
			System.err.println("An error occurred: " + e.getMessage());
		}
	}
}