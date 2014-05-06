package ru.valuev.graph;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.valuev.graph.plotGraph.Plotter2;
import ru.valuev.theMethodLeastSquares.LeastSquares;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FormTest extends JFrame
{
	JButton bntDraw;
	JPanel panelGraphic;

	boolean isFirstRun = true;

	public FormTest()
	{
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentMoved(ComponentEvent e)
			{
				panelGraphic.repaint();
			}

			@Override
			public void componentResized(ComponentEvent e)
			{
				plotter.setDisplayWidth(panelGraphic.getWidth());
				plotter.setDisplayHeight(panelGraphic.getHeight());
				panelGraphic.repaint();
			}
		});
		setSize(new Dimension(631, 312));
		Container c = this.getContentPane();

		plotter = new Plotter2(GRAPHIC_WIDTH, GRAPHIC_HEIGHT);

		panelGraphic = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				panelPaintComponent(g);
			}
		};
		panelGraphic.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				panelGraphicMouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				panelGraphicMouseMoved(e);
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(null);

		// panel.setConnectingPoints(new double[] { 11, 12, 13, 14 }, new
		// double[] { -2.48, -3.75, -4.81, -5.16 });

		bntDraw = new JButton("Draw");
		bntDraw.setBounds(36, 11, 90, 30);
		panel.add(bntDraw);

		txtXstart = new JTextField();
		txtXstart.setBounds(36, 66, 86, 20);
		panel.add(txtXstart);
		txtXstart.setText("7");
		txtXstart.setColumns(10);

		txtXfinal = new JTextField();
		txtXfinal.setBounds(36, 128, 86, 20);
		panel.add(txtXfinal);
		txtXfinal.setText("65");
		txtXfinal.setColumns(10);

		txtYmin = new JTextField();
		txtYmin.setBounds(36, 97, 86, 20);
		panel.add(txtYmin);
		txtYmin.setText("-1");
		txtYmin.setColumns(10);

		txtYmax = new JTextField();
		txtYmax.setBounds(36, 159, 86, 20);
		panel.add(txtYmax);
		txtYmax.setText("40");
		txtYmax.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(panelGraphic, GroupLayout.DEFAULT_SIZE, GRAPHIC_WIDTH, Short.MAX_VALUE).addGap(35)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(23)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panelGraphic, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, GRAPHIC_HEIGHT, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		getContentPane().setLayout(groupLayout);

		bntDraw.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnDrawActionPerformed(e);
			}
		});

		initializeMinMaxBorders();
	}

	public final static int GRAPHIC_WIDTH = 421;
	public final static int GRAPHIC_HEIGHT = 240;

	private Plotter2 plotter;
	private boolean isDraw = false;
	private JTextField txtXstart;
	private JTextField txtXfinal;
	private JTextField txtYmin;
	private JTextField txtYmax;

	private boolean isChangeBorders = false;

	private LeastSquares leastSquares;

	// Moving Graphic
	// --------------------------------------------------------------
	private int currentX = 0;
	private int currentY = 0;

	private double scale = 1.0;

	private double xStart;
	private double yMin;
	private double xFinal;
	private double yMax;

	private void panelGraphicMouseDragged(MouseEvent e)
	{
		double x = currentX - e.getX();
		double y = e.getY() - currentY;

		currentX = e.getX();
		currentY = e.getY();

		// x *= (scale / 70);
		// y *= (scale / 70);

		x *= (scale / 30);
		y *= (scale / 30);

		xStart += x;
		yMin += y;
		xFinal += x;
		yMax += y;

		setBorders(xStart, yMin, xFinal, yMax);
		updateForm(xStart, yMin, xFinal, yMax);

		isDraw = true;

		panelGraphic.repaint();
	}

	private void panelGraphicMouseMoved(MouseEvent e)
	{
		currentX = e.getX();
		currentY = e.getY();

	}

	private void updateForm(double xStart2, double yMin2, double xFinal2, double yMax2)
	{
		txtXstart.setText(String.format("%.3f", xStart2));
		txtYmin.setText(String.format("%.3f", yMin2));
		txtXfinal.setText(String.format("%.3f", xFinal2));
		txtYmax.setText(String.format("%.3f", yMax2));

	}

	// --------------------------------------------------------------

	private void panelPaintComponent(Graphics g)
	{
		if (isChangeBorders)
		{
			// double xStart =
			// Double.parseDouble(txtXstart.getText().replace(',', '.'));
			// double yMin = Double.parseDouble(txtYmin.getText().replace(',',
			// '.'));
			//
			// double xFinal =
			// Double.parseDouble(txtXfinal.getText().replace(',', '.'));
			// double yMax = Double.parseDouble(txtYmax.getText().replace(',',
			// '.'));
			// if (!isInitializeMinMaxBorders)
			// initializeMinMaxBorders();

			plotter.setBorders(xStart, yMin, xFinal, yMax);

			isChangeBorders = false;
		}

		if (isDraw)
		{
			double[] x = { 11, 12, 13, 14 };
			double[] y = { -2.48, -3.75, -4.81, -5.16 };
			plotter.prepareImage(g);

			// double[] x = { 8, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60 };
			// double[] y = { 33, 31, 27, 25, 23, 21, 20, 19, 16, 14, 13, 12 };

			//
			// plotter.setBorders(xStart, yMin, xFinal, yMax);

			plotter.drawPoints(g, x, y);

			LeastSquares ls = new LeastSquares(x, y);

			double[] newY = ls.getPolynomial();
			plotter.drawPointsWithLines(g, x, newY);

			double xk = 38;
			double interpolation = ls.solveInterpolation(xk);
			plotter.drawPoint(g, xk, interpolation);

			// double[] pol = {-2.684999999999996, -3.594999999999998, -4.505,
			// -5.415000000000004};
			// plotter.drawLineByPoints(g, x, pol);

		}
	}

	private void btnDrawActionPerformed(ActionEvent e)
	{
		isChangeBorders = true;
		isDraw = true;
		panelGraphic.repaint();
	}

	private void setBorders(double x0, double y0, double x1, double y1)
	{
		xStart = x0;
		yMin = y0;
		xFinal = x1;
		yMax = y1;

		isChangeBorders = true;
	}

	private boolean isInitializeMinMaxBorders = false;

	private void initializeMinMaxBorders()
	{
		// double maxX = 60;
		// double maxY = 33;

		double maxX = 14;
		double maxY = -2.48;
		double minY = -5.16;

		// double x0 = minX + (minX / 2) * (minX > 0 ? -1 : 1);
		// double y0 = minY + (minY / 2) * (minY > 0 ? -1 : 1);
		// double x1 = maxX + (maxX / 2) * (maxX > 0 ? 1 : -1);
		// double y1 = maxY + (maxY / 2) * (maxY > 0 ? 1 : -1);

		// setBorders(x0, y0, x1, y1);
		// setBorders(-2.5, -2.5, 2.5, 2.5);

		setBorders(-1.5, minY, maxX, 0);

		isInitializeMinMaxBorders = true;
	}

	private void getMaxX()
	{
		// double max = x[0];
	}
}
