package ru.valuev.systemOfLinearEquations.gaus;

public class Gaussian {

//	private static double[][] a = null;
//	private static double[] b = null;
	
	public static void main(String[] args) {
		int[][] a = { 
				{ 3, 2, 1, 1 }, 
				{ 1, -1, 4, -1 }, 
				{ -2, -2, -3, 1 },
				{ 1, 5, -1, 2 }, 
			};

		int[] b = { 
				-2,
				-1,
				9,
				4
			};
		
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++)
				System.out.print(" a[" + i + "][" + j + "] = " + a[i][j]);
			System.out.println();
		}
		for (int i = 0; i < b.length; i++) {
			System.out.println("b[" + i + "] = " + b[i]);
		}

		Gaussian g = new Gaussian();
		double[] res = g.calculate(a, b);
		
		for (double r : res) {
			System.out.println(r);
		}
		
		System.out.println(); 
	
		System.out.println(g.checkResults());

	}

	
	private double[][] _coefficients;
	private double[] _freeMembers;
	private int n;
	private double[] x;

	private double[][] _savedCoefficients;
	private double[] _savedFreeMembers;

	public double[] calculate(double[][] coefficients, double[] freeMembers) {
		this._coefficients = this._savedCoefficients = coefficients;
		this._freeMembers = this._savedFreeMembers = freeMembers;
		this.n = _freeMembers.length - 1;
		this.x = new double[n+1];

		if (calculateOfAllStepDirectMove()) {
			// MyException
		}

		return x;
	}
	
	public double[] calculate(int[][] coefficients, int[] freeMembers) {
		double[][] c = new double[coefficients.length][coefficients[0].length];
		double[] f = new double[freeMembers.length];
		
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				c[i][j] = coefficients[i][j];
			}
			
			f[i] = freeMembers[i];
		}
		
		double[] res = calculate(c, f);
		return res;
	}

	private boolean calculateOfAllStepDirectMove() {
		for (int row = 0; row < n; row++) {
			if (0 == _coefficients[row][row]
					&& !searchLeadingNonZeroElement(row)) {
				return false;
			}

			stepDirectMove(row);
		}

		backStep();

		return true;
	}

	private boolean searchLeadingNonZeroElement(int row) {

		for (int k1 = row + 1; k1 <= n; k1++) {
			if (0 == _coefficients[k1][row]) {
				continue;
			} else {
				int kn = k1;

				for (int j = 0; j <= n; j++) {
					double t = _coefficients[row][j];
					_coefficients[row][j] = _coefficients[kn][j];
					_coefficients[kn][j] = t;
				}
				double t = _freeMembers[row];
				_freeMembers[row] = _freeMembers[kn];
				_freeMembers[kn] = t;

				return true;
			}
		}

		return false;
	}

	private void stepDirectMove(int row) {
		for (int i = row + 1; i <= n; i++) {
			double factor = -_coefficients[i][row] / _coefficients[row][row];

			_freeMembers[i] += _freeMembers[row] * factor;

			for (int j = row; j <= n; j++) {
				_coefficients[i][j] += _coefficients[row][j] * factor;
			}
		}
	}

	private void backStep() {
		x[n] = _freeMembers[n] / _coefficients[n][n];

		for (int i = n - 1; i >= 0; i--) {
			double tmp = _freeMembers[i];
			for (int j = i + 1; j <= n; j++)
				tmp -= _coefficients[i][j] * x[j];
			x[i] = tmp / _coefficients[i][i];
		}
	}

	public String checkResults() {
		String res = "";

		double[] f = new double[n+1];
		for (int i = 0; i <= n; i++) {
			f[i] = -_savedFreeMembers[i];
			for (int j = 0; j <= n; j++)
				f[i] += _savedCoefficients[i][j] * x[j];

			res += "f[" + i + "] = " + f[i] + "\n";
		}

		return res;
	}

}

