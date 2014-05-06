package ru.valuev.graph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import ru.valuev.graph.plotGraph.Plotter;
import ru.valuev.graph.plotGraph.Plotter2;

public class PlotPanel extends JPanel
{
	public final static int GRAPHIC_WIDTH = 421;
	public final static int GRAPHIC_HEIGHT = 240;

	private int currentX;
	private int currentY;

	private double xStart;
	private double yMin;
	private double xFinal;
	private double yMax;

	private double scale = 1;

	private double xStartScale;
	private double yMinScale;
	private double xFinalScale;
	private double yMaxScale;

	private double resize = 2.5;

	private Point[] points;
	private int movingPointX = 0;
	// private JTextField txtMovingPointX;
	// private JTextField txtMovingPointY;

	private Plotter2 plotter;
	private double[] connectingXPoints;
	private double[] connectingYPoints;

	public PlotPanel()
	{
		setSize(new Dimension(GRAPHIC_WIDTH, GRAPHIC_HEIGHT));

		intializeCordiante();

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				requestFocusInWindow();
			}
		});

		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				panelKeyPressed(e);
			}
		});

		addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				double factor = (e.getWheelRotation() == 1) ? 2d : (1d / 2d);

				if (scale < 16d)
				{
					boolean isIncrease = (factor == (1d / 2d));

					if (isIncrease)
					{
						resize *= factor;

						yMax -= resize;
						yMin += resize;

						xFinal -= resize;
						xStart += resize;

					} else
					{
						if (resize < 40)
						{
							yMax += resize;
							yMin -= resize;

							xFinal += resize;
							xStart -= resize;

							resize *= factor;
						}
					}
					
					repaint();

					scale *= factor;
				}

				if (scale >= 16d && factor == 1 / 2d)				
					scale *= factor;
				
			}

		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				panelMouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				currentX = e.getX();
				currentY = e.getY();

				// JOptionPane.showMessageDialog(null, "Mouse move");

				movingPointX = currentX;
				repaint();
			}
		});

		// txtMovingPointX = new JTextField();
		// txtMovingPointX.setBounds(45, 127, 103, 20);
		// getContentPane().add(txtMovingPointX);
		// txtMovingPointX.setColumns(10);
		//
		// txtMovingPointY = new JTextField();
		// txtMovingPointY.setBounds(45, 158, 103, 20);
		// getContentPane().add(txtMovingPointY);
		// txtMovingPointY.setColumns(10);
		//
		// JLabel lblX = new JLabel("x =");
		// lblX.setBounds(10, 130, 46, 14);
		// getContentPane().add(lblX);
		//
		// JLabel lblY = new JLabel("y = ");
		// lblY.setBounds(10, 161, 46, 14);
		// getContentPane().add(lblY);

		points = new Point[] { new Point(0, 0) };
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		plotter = new Plotter2(GRAPHIC_WIDTH, GRAPHIC_HEIGHT);
		
		plotter.setBorders(10, -10, 20, 1);
		
		//plotter.drawPoints(g, connectingXPoints, connectingYPoints);

		// txtMovingPointX.setText(String.format("%.4f", p.getMovingX()));
		// txtMovingPointY.setText(String.format("%.4f", p.getMovingY()));
	}

	private void intializeCordiante()
	{
		xStart = -2.5;
		yMin = -2.5;
		xFinal = 2.5;
		yMax = 2.5;
	}
	
	//private boolean isSetConnetingPoints = false;
	
	
	public void setConnectingPoints(double[] xPoints, double[] yPoints)
	{
		this.connectingXPoints = xPoints;
		this.connectingYPoints = yPoints;
	}
	

	private Point[] getPoints()
	{
		return points;
	}

	private void panelKeyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			xStart += scale / 35;
			xFinal += scale / 35;
			break;
		case KeyEvent.VK_RIGHT:
			xStart -= scale / 35;
			xFinal -= scale / 35;
			break;
		case KeyEvent.VK_UP:
			yMin -= scale / 35;
			yMax -= scale / 35;
			break;
		case KeyEvent.VK_DOWN:
			yMin += scale / 35;
			yMax += scale / 35;
			break;
		}

		repaint();
	}

	private void panelMouseDragged(MouseEvent e)
	{
		double x = currentX - e.getX();
		double y = e.getY() - currentY;

		currentX = e.getX();
		currentY = e.getY();

		x *= (scale / 70);
		y *= (scale / 70);

		xStart += x;
		yMin += y;
		xFinal += x;
		yMax += y;

		// outputCoordinate(xStart, yMin, xFinal, yMax);

		repaint();
	}

}
