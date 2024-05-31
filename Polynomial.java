import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;

public class Polynomial {
	// fields
	double precedence;
	double[] coeff;
	double[] exp;
	
	// constructor with no arguments
	public Polynomial() {
		precedence = 0;
		double[] x = {0};
		double[] y = {0};
		this.coeff = x;
		this.exp = y;
	}
	// constructor with array argument
	public Polynomial(double[] coeff, double[] exp) {
		int length = 0;
		for (int i=0; i<coeff.length; i++) {
			if (coeff[i] != 0) {
				length++;
			}
		}


		double[] x = new double[length];
		double[] y = new double[length];

		// remove any zero values from passed coeff and exp arrays
		int j = 0;
		for (int i=0; i<coeff.length; i++) {
			if (coeff[i] != 0) {
				x[j] = coeff[i];
				y[j] = exp[i];
				j++;
			}
		}

		this.coeff = x;
		this.exp = y;

		// set precedence
		precedence = 0;
		if (this.exp.length != 0) {
			precedence = exp[0];
			for (int i=0; i<this.exp.length; i++) {
				if (this.exp[i] > precedence) {
					precedence = exp[i];
				}
			}
		}
	}

	public Polynomial(File f) throws FileNotFoundException{
		Scanner input = new Scanner(f);
		String line = input.nextLine();
		input.close();

		double[] x = new double[line.length()];
		double[] y = new double[line.length()];
		int index = 0;

		for (int i=0; i<x.length; i++) {
			x[i] = 0;
			y[i] = 0;
		}
		
		// System.out.println(line);
		String[] pos = line.split("\\+");

		for (int i=0; i<pos.length; i++) {
			// System.out.println(pos[i]);
			String[] neg = pos[i].split("-");
			// System.out.println("neg 0" + neg[0]);
			// System.out.println("neg 1" + neg[1]);


			String[] val = neg[0].split("x");

			// System.out.println("neg 0" + neg[0]);
			// System.out.println("neg 1" + neg[1]);
			

			//System.out.println(neg.length);

			if (val.length > 1) {
				y[index] = Double.parseDouble(val[0]);
				if (val[1] == "") {
					x[index] = 1;
				} else {
					x[index] = Double.parseDouble(val[1]);
				}
			} else {
				y[index] =  Double.parseDouble(val[0]);
				x[index] = 0;
			}
			index++;



			for (int j=1; j<neg.length; j++) {
				// System.out.println("neg:" + neg[j] + " index: " + j);
				String[] val2 = neg[j].split("x");
				//System.out.println(val2[0]);
				if (val2.length > 1) {
					// System.out.println(val2[0]);
					// System.out.println(val2[1]);
					y[index] =  -1.0 * Double.parseDouble(val2[0]);
					x[index] =  Double.parseDouble(val2[1]);
					
				} else {
					y[index] =  Double.parseDouble(val2[0]);
					x[index] = 0;
				}
				index++;
			}
			
		}
		
		int length = 0;
		for (int i=0; i<y.length; i++) {
			if (y[i] != 0) {
				length++;
			}
		}


		double[] a = new double[length];
		double[] b = new double[length];

		// remove any zero values from passed coeff and exp arrays
		int j = 0;
		for (int i=0; i<y.length; i++) {
			if (y[i] != 0) {
				a[j] = y[i];
				b[j] = x[i];
				j++;
			}
		}

		this.coeff = a;
		this.exp = b;

		// set precedence
		precedence = 0;
		if (this.exp.length != 0) {
			precedence = exp[0];
			for (int i=0; i<this.exp.length; i++) {
				if (this.exp[i] > precedence) {
					precedence = exp[i];
				}
			}
		}


	}

	public void saveToFile(String s) throws FileNotFoundException {
		PrintStream out = new PrintStream(new File(s));
		out.print(coeff[0]);
		if (exp[0] != 0) {
			out.print("x" + exp[0]);
		}


		for (int i=1; i<this.coeff.length; i++) {
			if (coeff[i] >= 0) {
				out.print("+" + coeff[i]);
			} else {
				out.print(coeff[i]);
			}
			if (exp[i] != 0) {
				out.print("x" + exp[i]);
			}
		}
		out.close();
		
	}
	
	private int exists(double[] arr, double n) {
		for (int i=0; i<arr.length; i++) {
			if (arr[i] == n) {
				return i;
			}
		}
		return -1;
	}

	// add x and y
	public Polynomial add(Polynomial x, Polynomial y) {
		if (x == null) {
			return y;
		} else if (y == null) {
			return x;
		} else if (x == null && y == null) {
			return null;
		}

		int maxLength = x.coeff.length + y.coeff.length;

		double[] addExp = new double[maxLength];
		double[] addCoeff = new double[maxLength];

		// copy this data
		for (int i=0; i<x.coeff.length; i++) {
			addExp[i] = x.exp[i];
			addCoeff[i] = x.coeff[i];
		}
		int count = x.coeff.length;

		// add object data
		for (int i=0; i<y.coeff.length; i++) {
			int index = exists(addExp, y.exp[i]);

			if (index != -1) {
				addCoeff[index] += y.coeff[i];
			} else {
				addCoeff[count] += y.coeff[i];
				addExp[count] = y.exp[i];
			}
			count++;
		}

		// create new polynomial with new data and return
		Polynomial addPolynomial = new Polynomial(addCoeff, addExp);
		return addPolynomial;
	}

	// add this and object
	public Polynomial add(Polynomial obj) {
		if (obj == null) {
			return this;
		}

		int maxLength = this.coeff.length + obj.coeff.length;

		double[] addExp = new double[maxLength];
		double[] addCoeff = new double[maxLength];

		// copy this data
		for (int i=0; i<this.coeff.length; i++) {
			addExp[i] = this.exp[i];
			addCoeff[i] = this.coeff[i];
		}
		int count = this.coeff.length;

		// add object data
		for (int i=0; i<obj.coeff.length; i++) {
			int index = exists(addExp, obj.exp[i]);

			if (index != -1) {
				addCoeff[index] += obj.coeff[i];
			} else {
				addCoeff[count] += obj.coeff[i];
				addExp[count] = obj.exp[i];
			}
			count++;
		}

		// create new polynomial with new data and return
		Polynomial addPolynomial = new Polynomial(addCoeff, addExp);
		return addPolynomial;
	}


	public Polynomial multiply(Polynomial obj) {
		if (obj == null) {
			return this;
		}

		Polynomial multPolynomial = new Polynomial();

		for (int i=0; i<this.coeff.length; i++) {
			double[] multCoeff = new double[obj.coeff.length];
			double[] multExp = new double[obj.coeff.length];

			for (int j=0; j<obj.coeff.length; j++) {
				multCoeff[j] = this.coeff[i] * obj.coeff[j];
				multExp[j] = this.exp[i] + obj.exp[j];
			}
			
			Polynomial temp = add(multPolynomial, new Polynomial(multCoeff, multExp));
			multPolynomial = temp;
		}

		return multPolynomial;
	}
	
	
	// method evaluate
	public double evaluate(double x) {
		double sum = 0;
		for (int i=0; i<coeff.length; i++) {
			sum += coeff[i] * Math.pow(x, exp[i]);
		}
		return sum;
	}
	
	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);
	}

	public void print() {
		System.out.print("{");
		for (int i=0; i<this.coeff.length; i++) {
			if (i == this.coeff.length - 1) {
				System.out.print(this.coeff[i]);
			} else {
				System.out.print(this.coeff[i] + ", ");
			}
        }
        System.out.println("}");

		System.out.print("{");
        for (int i=0; i<this.coeff.length; i++) {
            if (i == this.coeff.length - 1) {
				System.out.print(this.exp[i]);
			} else {
				System.out.print(this.exp[i] + ", ");
			}
        }
        System.out.println("}");
	}
	
}