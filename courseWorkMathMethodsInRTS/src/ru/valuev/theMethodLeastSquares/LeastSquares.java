package ru.valuev.theMethodLeastSquares;

import ru.valuev.systemOfLinearEquations.gaus.Gaussian;

public class LeastSquares
{

	public static void main(String[] args)
	{
		// double[] x = { 5.348, 5.156, 5.096, 5.011, 4.986, 4.761 };
		//
		// double[] y = { 11.95, 13.45, 15.34, 15.89, 20.93, 21.02 };

		// double[] x = { 8, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60 };
		// double[] y = { 33, 31, 27, 25, 23, 21, 20, 19, 16, 14, 13, 12 };
		// double xk = 38;

		// double[] x = { 8, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60 };
		// double[] y = { 11.6, 11.6, 11.9, 11.1, 11.1, 12.6, 11.09, 12.4,
		// 11.65, 12.0, 11.0, 11.1 };
		// double xk = 65;

		// 11.55
		// 10 -1.56
		// 11 -2.48
		// 12 -3.75
		// 13 -4.81
		// 14 -5.16
		// 15 -6.35
		// 16 -7.16
		// 17 10.5

		// double[] x = {10, 11, 12, 13, 14, 15, 16, 17};
		// double[] y = {-1.56, -2.48, -3.75, -4.81, -5.16, -6.35, -7.16, 10.5};
		// double xk = 11.55;

		// double[] y = { -0.01, -0.09, -0.17, -0.56, -0.78, -0.91, -1.10,
		// -1.38,
		// -1.43, -1.64, -1.87, -1.95, -2.01, -2.23, -2.48, -2.53, -2.64,
		// -2.78, -2.83, -2.91 };
		//
		// double[] x = new double[y.length];
		// for (int i = 0; i < x.length; i++) {
		// x[i] = 0.1 * (i + 1);
		// }
		//
		// double xk = 0.35;

		double[] x = { 11, 12, 13, 14 };
		double[] y = { -2.48, -3.75, -4.81, -5.16 };
		double xk = 11.55;

		int m = 3;

		LeastSquares ls = new LeastSquares(m, x, y);
		
		//double[] a = ls.getRootA();
		
		double[] pol = ls.getPolynomial();
		for (double el : pol)
		{
			System.out.println(el);
		}
		
//		double[][] coef = ls.getC();
//		
//		for (double[] elem : coef)
//		{
//			for (double el : elem)
//				System.out.print(el + " ");
//			System.out.println();
//		}
		// System.out.println(ls.getSumResidualSquare());

		// System.out.println(ls.solveInterpolation(xk));
		// double[] resA = ls.getRootA();
		// double[] root = ls.getRootA();
		// for (double el : root)
		// System.out.println(el);

	}

	private int degree;
	private int amountPoint;
	private double[] x;
	private double[] y;

	private double[][] c; // coefficients
	private double[] d; // free members
	private double[] a;

	private double[] polynomial;
	private double[] residual;
	private double sumResidualSquare;
	private double variance;

	public LeastSquares(double[] x, double[] y)
	{
		this(1, x, y);
	}
	
	public LeastSquares(int degree, double[] x, double[] y)
	{
		this.degree = degree;
		this.amountPoint = x.length;
		this.x = x;
		this.y = y;

		resetFields();
	}

	public void setDegree(int degree)
	{
		this.degree = degree;

		resetFields();
	}

	public double solveInterpolation(double xk)
	{
		double p = getRootA()[degree];

		for (int j = degree - 1; j >= 0; j--)
		{
			p = getRootA()[j] + p * xk;
		}

		return p;
	}

	private boolean isComputePolynomialByHorner = false;

	public double[] getPolynomial()
	{
		if (!isComputePolynomialByHorner)
			computePolynomialByHorner();
		return polynomial.clone();
	}

	public double[] getResidual()
	{
		if (!isComputePolynomialByHorner)
			computePolynomialByHorner();
		return residual.clone();
	}

	public double getSumResidualSquare()
	{
		if (!isComputePolynomialByHorner)
			computePolynomialByHorner();
		return sumResidualSquare;
	}

	public double getVariance()
	{
		if (!isComputePolynomialByHorner)
			computePolynomialByHorner();
		return variance;
	}

	private void computePolynomialByHorner()
	{
		sumResidualSquare = 0;
		polynomial = new double[amountPoint];
		residual = new double[amountPoint];
		variance = 0;

		for (int i = 0; i < amountPoint; i++)
		{
			polynomial[i] = getRootA()[degree];

			for (int j = degree - 1; j >= 0; j--)
			{
				polynomial[i] = getRootA()[j] + x[i] * polynomial[i];
			}

			residual[i] = polynomial[i] - y[i];
			sumResidualSquare += Math.pow(residual[i], 2);
		}

		variance = sumResidualSquare / (amountPoint + degree);

		isComputePolynomialByHorner = true;
	}

	private boolean isFindRootA = false;

	public double[] getRootA()
	{
		if (!isFindRootA)
			findRootA();
		return a.clone();
	}

	private void findRootA()
	{
		a = new double[degree + 1];

		Gaussian g = new Gaussian();
		a = g.calculate(getC(), getD());

		isFindRootA = true;
	}

	private boolean isCalculateD = false;

	private double[] getD()
	{
		if (!isCalculateD)
			calculateD();
		return d;
	}

	private void calculateD()
	{
		d = new double[degree + 1];

		for (int k = 0; k <= degree; k++)
		{
			double s = 0;

			for (int i = 0; i < amountPoint; i++)
			{
				s += y[i] * Math.pow(x[i], k);
			}

			d[k] = s;
		}

		isCalculateD = true;
	}

	private boolean isCalculateC = false;

	private double[][] getC()
	{
		if (!isCalculateC)
			calculateC();
		return c;
	}

	private void calculateC()
	{
		c = new double[degree + 1][degree + 1];

		for (int k = 0; k <= degree; k++)
		{
			for (int j = k; j <= degree; j++)
			{
				double s = 0;

				for (int i = 0; i < amountPoint; i++)
				{
					s += Math.pow(x[i], k + j);
				}

				c[k][j] = s;
				if (j != k)
					c[j][k] = s;
			}
		}

		isCalculateC = true;
	}

	private void resetFields()
	{
		this.isCalculateC = false;
		this.isCalculateD = false;
		this.isComputePolynomialByHorner = false;
		this.isFindRootA = false;
	}

}
