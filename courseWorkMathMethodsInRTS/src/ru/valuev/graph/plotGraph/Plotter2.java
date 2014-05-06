package ru.valuev.graph.plotGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

public class Plotter2
{
	public static final double STEP = 0.001;

	private Graphics2D graph;
	private int displayWidth;
	private int displayHeight;

	private double xStart;
	private double yMin;
	private double xFinal;
	private double yMax;

	private final int DISPLACEMENT_WIDTH = 40;
	private final int DISPLACEMENT_HEIGHT = 20;

	private final int AMOUNT_CELLS = 10;

	private int widthOutput;
	private int heightOutput;

	private double scaleX;
	private double scaleY;

	public final int SIZE = 6;

	public Plotter2(int width, int height)
	{
		this.displayWidth = width;
		this.displayHeight = height;

		intializeCordiante();

		signPoints = new ArrayList<MyPoint>();
	}

	private void intializeCordiante()
	{
		xStart = -2.5;
		yMin = -2.5;
		xFinal = 2.5;
		yMax = 2.5;
	}

	public void setDisplayWidth(int width)
	{
		this.displayWidth = width;
	}

	public void setDisplayHeight(int height)
	{
		this.displayHeight = height;
	}

	public void setBorders(double xStart, double yMin, double xFinal, double yMax)
	{
		this.xStart = xStart;
		this.yMin = yMin;
		this.xFinal = xFinal;
		this.yMax = yMax;
	}

	// public void drawPoints(Graphics g, /* Point[] points, */double[]
	// connectingXPoints, double[] connectingYPoints/*
	// * ,
	// * Point
	// * interPoint
	// */) // int
	// // xRight)
	// {
	// this.graph = (Graphics2D) g;
	// resetFields();
	//
	// clearDisplay();
	// drawGrid();
	//
	// drawAxes();
	//
	// // if (points != null)
	// // for (Point p : points)
	// // {
	// // drawPoint(p);
	// // }
	//
	// if (connectingXPoints != null && connectingYPoints != null)
	// drawLineByPoints(connectingXPoints, connectingYPoints);
	//
	// // drawMovingPoint(xRight, functions.get(1));
	//
	// signCoordinates();
	// }

	// private void drawGraphic(Function f)
	// {
	// graph.setColor(new Color(255, 0, 0));
	// graph.setStroke(new BasicStroke(2.0f));
	//
	// int xLeft = DISPLACEMENT_WIDTH;
	// int yLeft = (int) Math.round(getHeightOutput() + yMin * getScaleY() -
	// f.fun(xStart) * getScaleY());
	// // graph.drawOval(xLeft, yLeft, 1, 1);
	// graph.drawLine(xLeft, yLeft, xLeft, yLeft);
	//
	// double stepByX = 0;
	// for (double x = xStart; x <= xFinal; x += STEP)
	// {
	// stepByX += STEP;
	//
	// int xRight = DISPLACEMENT_WIDTH + (int) Math.round(stepByX *
	// getScaleX());
	// int yRight = getHeightOutput() + (int) Math.round(yMin * getScaleY() -
	// f.fun(x) * getScaleY());
	//
	// if (yRight <= getHeightOutput())
	// graph.drawLine(xLeft, yLeft, xRight, yRight);
	// xLeft = xRight;
	// yLeft = yRight;
	//
	// // if (yRight <= getHeightOutput())
	// // graph.drawOval(xRight - 1, yRight - 2, 2, 2);
	//
	// if (yRight <= getHeightOutput())
	// graph.drawLine(xRight, yRight, xRight, yRight);
	//
	// }
	//
	// }

	private void drawPoint(Point p)
	{
		int zeroByOX = DISPLACEMENT_WIDTH - (int) Math.round(xStart * getScaleX());
		int outX = zeroByOX + (int) Math.round(p.x * getScaleX());

		int zeroByOY = getHeightOutput() + (int) Math.round(yMin * getScaleY());
		int outY = zeroByOY - (int) Math.round(p.y * getScaleY());
		// int zeroByOY = -(int) Math.round(yMin * getScaleY());
		// int outY = zeroByOY - (int) Math.round(p.y * getScaleY());

		graph.setColor(new Color(0, 0, 255));
		graph.setStroke(new BasicStroke(4.0f));
		if (outX >= DISPLACEMENT_WIDTH && outY <= getHeightOutput())
			graph.drawLine(outX, outY, outX, outY);
	}

	// private void drawMovingPoint(int xRight, Function f)
	// {
	// double stepByX = (xRight - DISPLACEMENT_WIDTH) / getScaleX();
	//
	// movingX = xStart + stepByX;
	// movingY = f.fun(movingX);
	//
	// int zeroByOY = getHeightOutput() + (int) Math.round(yMin * getScaleY());
	// int yRight = zeroByOY - (int) Math.round(movingY * getScaleY());
	//
	// graph.setColor(Color.BLACK);
	// graph.setStroke(new BasicStroke(6.0f));
	//
	// if (xRight >= DISPLACEMENT_WIDTH && yRight <= getHeightOutput())
	// graph.drawLine(xRight, yRight, xRight, yRight);
	// }

	private List<MyPoint> signPoints;

	private void addSignPoints(Point[] p, double[] x, double[] y)
	{
		for (int i = 0; i < p.length; i++)
			signPoints.add(new MyPoint(p[i], x[i], y[i]));
	}

	public void drawPointCoordinates(int xMouse, int yMouse)
	{
		if (signPoints == null)
			return;
		for (int i = 0; i < signPoints.size(); i++)
		{
			int leftX = signPoints.get(i).getPoint().x - SIZE / 2;
			int leftY = signPoints.get(i).getPoint().y - SIZE / 2;
			
			if ((xMouse >= leftX && xMouse <= leftX + SIZE) &&
				(yMouse >= leftY && yMouse <= leftY + SIZE))
			{
				graph.setColor(Color.GREEN);
				graph.fillRect(leftX, leftY - 25, 80, 15);
				
				graph.setColor(Color.BLACK);
				graph.drawRect(leftX, leftY - 25, 80, 15);
				
				graph.setFont(new Font(Font.SERIF, 0, 10));
				
				double x = signPoints.get(i).getX();
				double y = signPoints.get(i).getY();
				graph.drawString(String.format("%.4f %.4f", x, y), leftX + 5, leftY - 25 + 12);
			}
		}
	}

	class MyPoint
	{
		private Point point;
		private double x;
		private double y;

		public Point getPoint()
		{
			return point;
		}

		public double getX()
		{
			return x;
		}

		public double getY()
		{
			return y;
		}

		MyPoint(Point p, double x, double y)
		{
			this.point = p;
			this.x = x;
			this.y = y;
		}
	}

	public void drawPoint(Graphics g, double x, double y)
	{
		resetFields();

		this.graph = (Graphics2D) g;

		graph.setColor(Color.BLUE);

		Stroke oldStroke = graph.getStroke();
		graph.setStroke(new BasicStroke(2.0f));

		int zeroByOX = DISPLACEMENT_WIDTH - (int) Math.round(xStart * getScaleX());
		int zeroByOY = getHeightOutput() + (int) Math.round(yMin * getScaleY());

		int rightX = zeroByOX + (int) Math.round(x * getScaleX());
		int rightY = zeroByOY - (int) Math.round(y * getScaleY());

		// if ((rightX - SIZE / 2) >= DISPLACEMENT_WIDTH && (rightY - SIZE / 2)
		// <= getHeightOutput())
		graph.fillOval(rightX - SIZE / 2, rightY - SIZE / 2, SIZE, SIZE);

		Point[] p = new Point[] { new Point(rightX, rightY) };
		drawLines(p, Color.lightGray);
		addSignPoints(p, new double[] { x }, new double[] { y });

		drawBordersAndCoordinates();
	}

	public void drawPoints(Graphics g, double[] x, double[] y)
	{
		resetFields();

		Color oldColor = graph.getColor();
		graph.setColor(Color.RED);

		Stroke oldStroke = graph.getStroke();
		graph.setStroke(new BasicStroke(2.0f));

		Point[] points = new Point[x.length];

		int zeroByOX = DISPLACEMENT_WIDTH - (int) Math.round(xStart * getScaleX());
		int zeroByOY = getHeightOutput() + (int) Math.round(yMin * getScaleY());

		for (int i = 0; i < x.length; i++)
		{
			int rightX = zeroByOX + (int) Math.round(x[i] * getScaleX());
			int rightY = zeroByOY - (int) Math.round(y[i] * getScaleY());

			// if ((rightX - SIZE / 2) >= DISPLACEMENT_WIDTH && (rightY - SIZE /
			// 2) <= getHeightOutput())
			graph.drawOval(rightX - SIZE / 2, rightY - SIZE / 2, SIZE, SIZE);

			points[i] = new Point(rightX, rightY);
		}

		drawLines(points, Color.GRAY);
		
		addSignPoints(points, x, y);

		drawBordersAndCoordinates();

		graph.setColor(oldColor);

	}

	private void drawLines(Point[] points, Color color)
	{
		graph.setColor(color);

		Stroke oldStroke = graph.getStroke();
		graph.setStroke(new BasicStroke(1.5f));

		int zeroByOY = getHeightOutput() + (int) Math.round(yMin * getScaleY());

		if (zeroByOY >= getHeightOutput())
			zeroByOY = getHeightOutput();

		for (Point p : points)
			// if (p.x >= DISPLACEMENT_WIDTH && (p.y - SIZE / 2) <=
			// getHeightOutput())
			if (p.y - zeroByOY > 0)
				graph.drawLine(p.x, zeroByOY, p.x, p.y - SIZE / 2);
			else
				graph.drawLine(p.x, zeroByOY, p.x, p.y + SIZE / 2);

		// drawBordersAndCoordinates();

		graph.setStroke(oldStroke);
	}

	public void drawPointsWithLines(Graphics g, double[] x, double[] y)
	{
		resetFields();

		Stroke oldStroke = graph.getStroke();
		graph.setStroke(new BasicStroke(2.0f));

		int[] xPoints = new int[x.length];
		int[] yPoints = new int[y.length];

		int zeroByOX = DISPLACEMENT_WIDTH - (int) Math.round(xStart * getScaleX());
		int zeroByOY = getHeightOutput() + (int) Math.round(yMin * getScaleY());

		int leftX = zeroByOX + (int) Math.round(x[0] * getScaleX());
		int leftY = zeroByOY - (int) Math.round(y[0] * getScaleY());

		xPoints[0] = leftX;
		yPoints[0] = leftY;

		for (int i = 1; i < x.length; i++)
		{
			int rightX = zeroByOX + (int) Math.round(x[i] * getScaleX());
			int rightY = zeroByOY - (int) Math.round(y[i] * getScaleY());

			// if (rightX >= DISPLACEMENT_WIDTH && rightY <= getHeightOutput())
			// {
			// if (leftX >= DISPLACEMENT_WIDTH && leftY <= getHeightOutput())
			graph.drawLine(leftX, leftY, rightX, rightY);

			xPoints[i] = rightX;
			yPoints[i] = rightY;
			// }

			leftX = rightX;
			leftY = rightY;
		}

		for (int i = 0; i < xPoints.length; i++)
		{
			drawCrossPoint(xPoints[i], yPoints[i]);
		}
		
		Point[] points = new Point[xPoints.length];
		for (int i = 0; i < xPoints.length; i++)
			points[i] = new Point(xPoints[i], yPoints[i]);	
		addSignPoints(points, x, y);

		drawBordersAndCoordinates();

		graph.setStroke(oldStroke);
	}

	private void drawCrossPoint(int x, int y)
	{
		Color oldColor = graph.getColor();
		graph.setColor(new Color(30, 150, 10));

		Stroke oldStroke = graph.getStroke();
		graph.setStroke(new BasicStroke(1.5f));

		int size = SIZE / 2;

		int leftX = x - size;
		int leftY = y - size;
		int rightX = x + size;
		int rightY = y + size;

		// if ((leftX >= DISPLACEMENT_WIDTH && leftY <= getHeightOutput()) &&
		// (rightX >= DISPLACEMENT_WIDTH && rightY <= getHeightOutput()))
		// {

		graph.drawLine(x - size, y - size, x + size, y + size); // left -
																// right
																// corners
		graph.drawLine(x - size, y + size, x + size, y - size); // right -
																// left
																// corners
		// graph.drawLine(x - size, y, x + size, y); // middle
		// }

		drawBordersAndCoordinates();

		graph.setColor(oldColor);
		graph.setStroke(oldStroke);
	}

	public void prepareImage(Graphics g)
	{
		this.graph = (Graphics2D) g;
		resetFields();

		clearDisplay();
		drawGrid();
		drawAxes();

		signPoints.clear();
		// signCoordinates();
	}

	private void drawBordersAndCoordinates()
	{
		graph.setColor(new Color(245, 245, 245));

		graph.fillRect(0, 0, DISPLACEMENT_WIDTH, displayHeight);
		graph.fillRect(0, getHeightOutput(), displayWidth, getHeightOutput());

		signCoordinates();
	}

	private double movingX = 0.0;
	private double movingY = 0.0;

	public double getMovingX()
	{
		return movingX;
	}

	public double getMovingY()
	{
		return movingY;
	}

	// Maybe its method need to do abstract??
	private void signCoordinates()
	{
		Color oldColor = graph.getColor();
		graph.setColor(Color.BLUE);
		graph.setFont(new Font(Font.SANS_SERIF, 0, 10));
		double stepByX = (xFinal - xStart) / AMOUNT_CELLS;
		double x = xStart;
		for (int i = 0; i <= getWidthOutput(); i += (int) getWidthOutput() / AMOUNT_CELLS)
		{
			graph.drawString(String.format("%.2f", x), i + 30, getHeightOutput() + DISPLACEMENT_HEIGHT - 5);
			x += stepByX;
		}

		double stepByY = (yMax - yMin) / AMOUNT_CELLS;
		double y = yMax;
		for (int i = 0; i <= getHeightOutput(); i += (int) getHeightOutput() / AMOUNT_CELLS)
		{
			graph.drawString(String.format("%8.2f", y), 0, i + 5);
			y -= stepByY;
		}

		graph.setColor(oldColor);
	}

	private void drawAxes()
	{
		graph.setColor(new Color(11, 3, 130));
		graph.setStroke(new BasicStroke(3.0f));
		// Save and create fonts
		Font oldFont = graph.getFont();
		Font newFont = new Font("TimesRoman", 0, 14);
		int offset = 5;

		// Draw axis OY
		int outXStart = DISPLACEMENT_WIDTH - (int) Math.round(xStart * getScaleX());
		int outYStart = 0;
		int outXEnd = DISPLACEMENT_WIDTH - (int) Math.round(xStart * getScaleX());
		int outYEnd = getHeightOutput();

		if (outXStart >= DISPLACEMENT_WIDTH)
		{
			graph.drawLine(outXStart, outYStart, outXEnd, outYEnd);

			// Draw arrow OY
			graph.fillPolygon(new int[] { outXStart - offset, outXStart, outXStart + offset }, new int[] { outYStart + 2 * offset, outYStart,
					outYStart + 2 * offset }, 3);

			// Sign axis OY
			graph.setFont(newFont);
			graph.drawString("y", outXStart - 2 * offset, outYStart + 2 * offset);
		}

		int coordinatesZeroX = outXStart - 2 * offset;
		int coordinatesZeroY = -1;

		// -----------------------------------------------------------------
		// Draw axis OX
		outXStart = DISPLACEMENT_WIDTH;
		outYStart = getHeightOutput() + (int) Math.round(yMin * getScaleY());
		outXEnd = getWidthOutput() + DISPLACEMENT_WIDTH;
		outYEnd = getHeightOutput() + (int) Math.round(yMin * getScaleY());

		if (outYStart <= getHeightOutput())
		{
			graph.drawLine(DISPLACEMENT_WIDTH, outYStart, getWidthOutput() + DISPLACEMENT_WIDTH, outYEnd);

			// Draw arrow OX
			offset = 5;
			graph.fillPolygon(new int[] { outXEnd - 2 * offset, outXEnd, outXEnd - 2 * offset }, new int[] { outYEnd - offset, outYEnd, outYEnd + offset }, 3);

			// Sign axis OX

			graph.drawString("x", outXEnd - 2 * offset, outYEnd + 3 * offset - 2);
		}

		coordinatesZeroY = outYEnd + 3 * offset;

		// Sign 0
		if (coordinatesZeroX >= DISPLACEMENT_WIDTH && coordinatesZeroY <= getHeightOutput())
			graph.drawString("0", coordinatesZeroX, coordinatesZeroY);

	}

	private void drawGrid()
	{
		graph.setColor(new Color(200, 200, 200));

		// Draw a line parallel to the c OY
		for (int i = 0; i <= getWidthOutput(); i += (int) Math.round(getWidthOutput() / AMOUNT_CELLS))
		{
			graph.drawLine(i + DISPLACEMENT_WIDTH, 0, i + DISPLACEMENT_WIDTH, getHeightOutput());
		}

		// for (int i = widthOutput; i >= 0; i -= Math.round(widthOutput /
		// AMOUNT_CELLS)) {
		// graph.drawLine(i + DISPLACEMENT_WIDTH, getHeightOutput(), i +
		// DISPLACEMENT_WIDTH, 0);
		// }

		// // Draw a line parallel to the axis OX
		// for (int i = 0; i <= getHeightOutput(); i += (int)
		// Math.round(getHeightOutput() / AMOUNT_CELLS)) {
		// graph.drawLine(DISPLACEMENT_WIDTH, i, widthOutput +
		// DISPLACEMENT_WIDTH, i);
		// }
		//
		for (int i = getHeightOutput(); i >= 0; i -= (int) Math.round(getHeightOutput() / AMOUNT_CELLS))
		{
			graph.drawLine(getWidthOutput() + DISPLACEMENT_WIDTH, i, DISPLACEMENT_WIDTH, i);
		}

		// graph.setColor(new Color(245, 245, 245));
		// graph.fillRect(DISPLACEMENT_WIDTH + 2, 2, getWidthOutput() +
		// DISPLACEMENT_WIDTH - 2, getHeightOutput());
	}

	private boolean isDetermineScale = false;

	public double getScaleX()
	{
		if (!isDetermineScale)
			determineScale();
		return scaleX;
	}

	public double getScaleY()
	{
		if (!isDetermineScale)
			determineScale();
		return scaleY;
	}

	private void determineScale()
	{
		scaleX = getWidthOutput() / Math.abs(xFinal - xStart);
		scaleY = getHeightOutput() / Math.abs(yMax - yMin);

		isDetermineScale = true;
	}

	private boolean isDetermineOutputRegion = false;

	private int getWidthOutput()
	{
		if (!isDetermineOutputRegion)
			determineOutputRegion();
		return widthOutput;
	}

	private int getHeightOutput()
	{
		if (!isDetermineOutputRegion)
			determineOutputRegion();
		return heightOutput;
	}

	private void determineOutputRegion()
	{
		widthOutput = displayWidth - DISPLACEMENT_WIDTH;
		heightOutput = displayHeight - DISPLACEMENT_HEIGHT;
	}

	private void clearDisplay()
	{
		graph.setColor(new Color(245, 245, 245));
		graph.fillRect(0, 0, this.displayWidth, this.displayHeight);
	}

	private void resetFields()
	{
		this.isDetermineScale = false;
	}

}
