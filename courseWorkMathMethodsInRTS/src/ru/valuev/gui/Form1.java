package ru.valuev.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ru.valuev.graph.plotGraph.Plotter2;
import ru.valuev.gui.io.DataFilter;
import ru.valuev.theMethodLeastSquares.LeastSquares;

public class Form1 extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableXY;
	private JTextField textPolynomial;
	private JTable tableInterpolation;
	private JTextField textXk;
	private JTextField textDegree;
	private JTextField textDispersion;
	private JTextField textSumOfSquares;
	private JTextField textYInterpolation;
	private JTabbedPane tabbedPane;
	private JButton btnFind;
	private JPanel panelGraphic;

	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmExit;

	private final int GRAPHIC_WIDTH = 421;
	private final int GRAPHIC_HEIGHT = 240;

	private JFileChooser fileChooser;

	public Form1()
	{
		initialize();

		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				plotter.setDisplayWidth(panelGraphic.getWidth());
				plotter.setDisplayHeight(panelGraphic.getHeight());
				panelGraphic.repaint();
			}
		});
		setSize(new Dimension(730, 470));
		setTitle("Course Work Mathemathics Methods in RTS");

		panelGraphic = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				panelGraphicPaintComponent(g);
			}
		};
		panelGraphic.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				panelGraphicKeyPressed(e);
			}
		});
		panelGraphic.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				panelGraphicMouseClicked(e);
			}
		});
		panelGraphic.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				panelGraphicMouseWheelMoved(e);
			}
		});
		panelGraphic.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				panelGraphicMouseDragged(e);
			}

			public void mouseMoved(MouseEvent e)
			{
				panelGraphicMouseMoved(e);
			}
		});

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				tabbedPaneStateChanged(e);
			}
		});

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setName("");
		tabbedPane.addTab("Your points", null, layeredPane, null);

		JScrollPane scrollPane = new JScrollPane();

		tableXY = new JTable();
		tableXY.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u2116", "X", "Y" }));
		tableXY.getColumnModel().getColumn(0).setPreferredWidth(30);
		scrollPane.setViewportView(tableXY);

		JLabel label = new JLabel("Input your data here:");

		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonAddActionPerformed(e);
			}
		});

		JButton buttonRemove = new JButton("-");
		buttonRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonRemoveActionPerformed(e);
			}
		});

		JButton buttonEdit = new JButton("...");
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_layeredPane
								.createSequentialGroup()
								.addGap(10)
								.addGroup(
										gl_layeredPane
												.createParallelGroup(Alignment.LEADING)
												.addComponent(label, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
												.addGroup(
														gl_layeredPane
																.createSequentialGroup()
																.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
																.addGap(31)
																.addGroup(
																		gl_layeredPane
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(buttonAdd)
																				.addComponent(buttonRemove, GroupLayout.PREFERRED_SIZE, 41,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(buttonEdit, GroupLayout.PREFERRED_SIZE, 41,
																						GroupLayout.PREFERRED_SIZE)))).addGap(10)));
		gl_layeredPane.setVerticalGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_layeredPane
						.createSequentialGroup()
						.addGap(11)
						.addComponent(label)
						.addGap(11)
						.addGroup(
								gl_layeredPane
										.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
										.addGroup(
												gl_layeredPane.createSequentialGroup().addGap(3).addComponent(buttonAdd).addGap(11).addComponent(buttonRemove)
														.addGap(11).addComponent(buttonEdit))).addGap(11)));
		layeredPane.setLayout(gl_layeredPane);

		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Interpolation or prediction", null, layeredPane_1, null);

		JScrollPane scrollPane_1 = new JScrollPane();

		tableInterpolation = new JTable();
		tableInterpolation.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u2116", "Ei", "Pi" }));
		tableInterpolation.getColumnModel().getColumn(0).setPreferredWidth(30);
		scrollPane_1.setViewportView(tableInterpolation);

		JLabel lblWhatFind = new JLabel("What find:");

		textXk = new JTextField();
		textXk.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				// JOptionPane.showMessageDialog(null, "textXk");
				checkValueXkAndDegree();
			}
		});
		textXk.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				checkValueXkAndDegree();
			}
		});
		textXk.setColumns(10);

		JLabel lblInterpolationDegree = new JLabel("Interpolation degree:");

		JLabel lblXk = new JLabel("xk = ");

		textDegree = new JTextField();
		textDegree.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				checkValueXkAndDegree();
			}
		});
		textDegree.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				checkValueXkAndDegree();
			}
		});
		textDegree.setColumns(10);

		JLabel lblM = new JLabel("m = ");

		JLabel lblResults = new JLabel("Results:");

		btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnFindActionPerformed(e);
			}
		});

		JLabel lblYinterpolation = new JLabel("y(Interpolation):");

		textYInterpolation = new JTextField();
		textYInterpolation.setColumns(10);
		GroupLayout gl_layeredPane_1 = new GroupLayout(layeredPane_1);
		gl_layeredPane_1.setHorizontalGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING).addGroup(
				gl_layeredPane_1
						.createSequentialGroup()
						.addGap(10)
						.addGroup(
								gl_layeredPane_1
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_layeredPane_1.createSequentialGroup()
														.addComponent(lblResults, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE).addGap(88)
														.addComponent(lblWhatFind, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_layeredPane_1
														.createSequentialGroup()
														.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
														.addGap(10)
														.addGroup(
																gl_layeredPane_1
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(
																				gl_layeredPane_1
																						.createSequentialGroup()
																						.addGap(4)
																						.addGroup(
																								gl_layeredPane_1
																										.createParallelGroup(Alignment.LEADING)
																										.addComponent(lblXk, GroupLayout.PREFERRED_SIZE, 39,
																												GroupLayout.PREFERRED_SIZE)
																										.addGroup(
																												gl_layeredPane_1
																														.createSequentialGroup()
																														.addGap(29)
																														.addComponent(textXk,
																																GroupLayout.PREFERRED_SIZE, 70,
																																GroupLayout.PREFERRED_SIZE))))
																		.addComponent(lblInterpolationDegree, GroupLayout.PREFERRED_SIZE, 113,
																				GroupLayout.PREFERRED_SIZE)
																		.addGroup(
																				gl_layeredPane_1
																						.createSequentialGroup()
																						.addGap(4)
																						.addGroup(
																								gl_layeredPane_1
																										.createParallelGroup(Alignment.LEADING)
																										.addComponent(lblM, GroupLayout.PREFERRED_SIZE, 46,
																												GroupLayout.PREFERRED_SIZE)
																										.addGroup(
																												gl_layeredPane_1
																														.createSequentialGroup()
																														.addGap(29)
																														.addComponent(textDegree,
																																GroupLayout.PREFERRED_SIZE, 32,
																																GroupLayout.PREFERRED_SIZE))))
																		.addComponent(btnFind, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblYinterpolation, GroupLayout.PREFERRED_SIZE, 103,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(textYInterpolation, GroupLayout.PREFERRED_SIZE, 103,
																				GroupLayout.PREFERRED_SIZE))))));
		gl_layeredPane_1.setVerticalGroup(gl_layeredPane_1.createParallelGroup(Alignment.LEADING).addGroup(
				gl_layeredPane_1
						.createSequentialGroup()
						.addGap(11)
						.addGroup(
								gl_layeredPane_1.createParallelGroup(Alignment.LEADING).addComponent(lblResults)
										.addGroup(gl_layeredPane_1.createSequentialGroup().addGap(7).addComponent(lblWhatFind)))
						.addGap(4)
						.addGroup(
								gl_layeredPane_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
										.addGroup(
												gl_layeredPane_1
														.createSequentialGroup()
														.addGap(7)
														.addGroup(
																gl_layeredPane_1
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_layeredPane_1.createSequentialGroup().addGap(3).addComponent(lblXk))
																		.addComponent(textXk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGap(9)
														.addComponent(lblInterpolationDegree)
														.addGap(11)
														.addGroup(
																gl_layeredPane_1
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_layeredPane_1.createSequentialGroup().addGap(3).addComponent(lblM))
																		.addComponent(textDegree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGap(15)
														.addComponent(btnFind)
														.addGap(18)
														.addComponent(lblYinterpolation)
														.addGap(11)
														.addComponent(textYInterpolation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))).addGap(13)));
		layeredPane_1.setLayout(gl_layeredPane_1);

		JLabel lblGraphic = new JLabel("Graphic:");

		JPanel panel_1 = new JPanel();

		JLabel lblYourPolynomial = new JLabel("Your polynomial:");

		textPolynomial = new JTextField();
		textPolynomial.setText("P1(x) = ");
		textPolynomial.setColumns(10);

		JLabel lblDispersion = new JLabel("Dispersion:");

		textDispersion = new JTextField();
		textDispersion.setColumns(10);

		JLabel lblSum = new JLabel("Sum of squared residuals:");

		textSumOfSquares = new JTextField();
		textSumOfSquares.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(10)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
														.addGap(10)
														.addGroup(
																groupLayout.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblGraphic, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
																		.addComponent(panelGraphic, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)))
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)).addGap(11)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(11)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
										.addGroup(
												groupLayout.createSequentialGroup().addComponent(lblGraphic).addGap(10)
														.addComponent(panelGraphic, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))).addGap(11)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE).addGap(8)));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addGap(10)
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblYourPolynomial, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
										.addComponent(textPolynomial, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
										.addGroup(
												gl_panel_1
														.createSequentialGroup()
														.addComponent(lblDispersion, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addGap(12)
														.addComponent(textDispersion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addGap(22)
														.addComponent(lblSum, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
														.addGap(10)
														.addComponent(textSumOfSquares, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))).addGap(10)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addComponent(lblYourPolynomial)
						.addGap(11)
						.addComponent(textPolynomial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addGroup(
								gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup().addGap(3).addComponent(lblDispersion))
										.addComponent(textDispersion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel_1.createSequentialGroup().addGap(3).addComponent(lblSum))
										.addComponent(textSumOfSquares, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))));
		panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clearForm();
			}
		});
		mnFile.add(mntmNew);

		mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clearForm();
				openOrSaveActionPerformed(e);
			}
		});
		mnFile.add(mntmOpen);

		mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				openOrSaveActionPerformed(e);
			}
		});
		mnFile.add(mntmSaveAs);
		mntmSaveAs.setEnabled(false);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mntmAboutAddActionListener(e);
			}
		});
		mnHelp.add(mntmAbout);

		JMenuItem menuItem = new JMenuItem("");
		mnHelp.add(menuItem);
	}

	// My Code
	// -------------------------------------------------------------------------------------------------

	private int rowIndexTableXY = 0;

	private LeastSquares leastSquares;
	private double[] x;
	private double[] y;
	private double xk;
	private int degree;
	

	private void mntmAboutAddActionListener(ActionEvent e)
	{
		JOptionPane.showMessageDialog(this, "Курсовая работа\n"
				+ "на тему: "
				+ "\"Нахождение интерполяции с использованием\n"
				+ "Метода Наименьших Квадратов\"\n\n"			
				+ "студента: группы КТ-5a11\n"
				+ "Валуйского Руслана", 
				"About", JOptionPane.PLAIN_MESSAGE, new ImageIcon("tiger.gif"));
	}

	private void openOrSaveActionPerformed(ActionEvent e)
	{
		if (fileChooser == null)
			fileChooser = new JFileChooser();

		fileChooser.setCurrentDirectory(new File(".").getAbsoluteFile());

		fileChooser.addChoosableFileFilter(new DataFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (e.getSource() == mntmOpen)
		{
			int res = fileChooser.showOpenDialog(Form1.this);
			if (res == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				readData(file);
			}

		} else if (e.getSource() == mntmSaveAs)
		{
			int res = fileChooser.showSaveDialog(Form1.this);
			if (res == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();

				saveData(file);
			}
		}
	}

	private void readData(File file)
	{
		Scanner sc = null;

		try
		{
			sc = new Scanner(file);

			double newXk = sc.nextDouble();
			setXk(newXk);

			int newDegree = sc.nextInt();
			setDegree(newDegree);

			int length = sc.nextInt();

			double[] newX = new double[length];
			double[] newY = new double[length];

			for (int i = 0; i < length; i++)
				newX[i] = sc.nextDouble();

			for (int i = 0; i < length; i++)
				newY[i] = sc.nextDouble();

			updatePoints(newX, newY);

			updateForm(newXk, newDegree, newX, newY);

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			if (sc != null)
				sc.close();
		}
	}

	private void saveData(File file)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(Double.toString(xk).replace('.', ',') + " ").append(degree + " ");
		sb.append(x.length + " ");

		for (double el : x)
			sb.append(Double.toString(el).replace('.', ',') + " ");

		for (double el : y)
			sb.append(Double.toString(el).replace('.', ',') + " ");

		PrintWriter printWriter = null;
		try
		{
			printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			printWriter.print(sb.toString());
			printWriter.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (printWriter != null)
				printWriter.close();
		}

	}

	private void btnFindActionPerformed(ActionEvent e)
	{
		find();

	}

	// Graphic
	// =======================================================================================
	private Plotter2 plotter;
	private boolean isDraw = false;
	private boolean isBordersChange = false;

	private double xStart = -1;
	private double yMin = -1;
	private double xFinal = 79;
	private double yMax = 39;

	private void initialize()
	{
		plotter = new Plotter2(GRAPHIC_WIDTH, GRAPHIC_HEIGHT);
	}

	private void panelGraphicPaintComponent(Graphics g)
	{
		if (isClearImage)
		{
			g.setColor(new Color(245, 245, 245));
			g.fillRect(0, 0, panelGraphic.getWidth(), panelGraphic.getHeight());
			isClearImage = false;
			return;
		}

		if (isBordersChange)
		{
			plotter.setBorders(xStart, yMin, xFinal, yMax);

			isBordersChange = false;
		}

		if (isDraw)
		{
			// plotter.drawPoints(g, new double[] { 11, 12, 13, 14 }, new
			// double[] { -2.48, -3.75, -4.81, -5.16 });

			// plotter.drawLineByPoints(g, new double[] { 11, 12, 13, 14 }, new
			// double[] { -2.48, -3.75, -4.81, -5.16 });

			// intializeBorders();

			plotter.prepareImage(g);

			plotter.drawPoints(g, x, y);
			plotter.drawPointsWithLines(g, x, leastSquares.getPolynomial());
			plotter.drawPoint(g, xk, leastSquares.solveInterpolation(xk));

			plotter.drawPointCoordinates(currentX, currentY);

			for (double el : leastSquares.getPolynomial())
				System.out.println(el);

		}
	}

	// Dragged and Scaled graphic
	// --------------------------------------------------------
	private void panelGraphicMouseClicked(MouseEvent e)
	{
		panelGraphic.requestFocusInWindow();
	}

	private int sdsf = 0;

	private void panelGraphicKeyPressed(KeyEvent e)
	{
		double scaleX = 1d / plotter.getScaleX();
		double scaleY = 1d / plotter.getScaleY();

		switch (e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			xStart += scaleX;
			xFinal += scaleX;
			break;
		case KeyEvent.VK_RIGHT:
			xStart -= scaleX;
			xFinal -= scaleX;
			break;
		case KeyEvent.VK_UP:
			yMin -= scaleY;
			yMax -= scaleY;
			break;
		case KeyEvent.VK_DOWN:
			yMin += scaleY;
			yMax += scaleY;
			break;
		}

		isBordersChange = true;

		panelGraphic.repaint();
	}

	private double resize = 40.0;

	private void panelGraphicMouseWheelMoved(MouseWheelEvent e)
	{
		double factor = (e.getWheelRotation() == 1) ? 2d : (1d / 2d);
		boolean isIncrease = (factor == (1d / 2d));

		if (isIncrease)
		{
			resize *= factor;

			yMax -= resize / 2;
			yMin += resize / 2;
			xFinal -= resize;
			xStart += resize;
		} else
		{
			yMax += resize / 2;
			yMin -= resize / 2;

			xFinal += resize;
			xStart -= resize;

			resize *= factor;
		}

		isBordersChange = true;

		panelGraphic.repaint();
	}

	private int currentX;
	private int currentY;

	private void panelGraphicMouseMoved(MouseEvent e)
	{
		currentX = e.getX();
		currentY = e.getY();

		panelGraphic.repaint();
	}

	private void panelGraphicMouseDragged(MouseEvent e)
	{
		double scaleX = plotter.getScaleX();
		double scaleY = plotter.getScaleY();

		double x = (currentX - e.getX()) / scaleX;
		double y = (e.getY() - currentY) / scaleY;

		currentX = e.getX();
		currentY = e.getY();

		xStart += x;
		yMin += y;
		xFinal += x;
		yMax += y;

		isBordersChange = true;

		panelGraphic.repaint();
	}

	// --------------------------------------------------------

	// =======================================================================================

	private void find()
	{
		leastSquares = getInstanceLeastSquares();

		leastSquares.setDegree(degree);

		outputPolynomial(leastSquares.getRootA());
		textYInterpolation.setText(String.format("%.8f", leastSquares.solveInterpolation(xk)));
		textDispersion.setText(String.format("%.8f", leastSquares.getVariance()));
		textSumOfSquares.setText(String.format("%.8f", leastSquares.getSumResidualSquare()));

		fillTableInterpolation(leastSquares.getResidual(), leastSquares.getPolynomial());

		isDraw = true;
		isBordersChange = true;
		panelGraphic.repaint();
	}

	private void fillTableInterpolation(double[] residual, double[] polynomial)
	{
		clearTableInterpolation();

		int countRows = polynomial.length;

		DefaultTableModel model = (DefaultTableModel) tableInterpolation.getModel();

		for (int i = 0; i < countRows; i++)
		{
			model.insertRow(i, new Object[] { i + 1, residual[i], polynomial[i] });
		}

	}

	private void outputPolynomial(double[] rootA)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("P%d(x) = ", degree));

		boolean isEnd = false;

		for (int i = 0; i < rootA.length; i++)
		{
			isEnd = (degree - i) == 0;

			if (i != 0)
			{
				if (!isEnd)
					if (i != 0 && rootA[i] < 0)
						sb.append(String.format("%s%.2f*x^%d", whatSign(rootA[i]), Math.abs(rootA[i]), (degree - i)));
					else
						sb.append(String.format("%.2f*x^%d", rootA[i], (degree - i)));
				else
					sb.append(String.format("%s%.2f", whatSign(rootA[i]), Math.abs(rootA[i])));
			}
		}

		textPolynomial.setText(sb.toString());
	}

	private String whatSign(double number)
	{
		return number >= 0 ? " + " : " - ";
	}

	private void tabbedPaneStateChanged(ChangeEvent e)
	{
		if (tabbedPane.getSelectedIndex() == 1)
		{
			if (tableXY.getModel().getRowCount() != 0)
			{
				// DefaultTableModel model = (DefaultTableModel)
				// tableXY.getModel();
				TableModel model = tableXY.getModel();

				int length = model.getRowCount();
				double[] newX = new double[length];
				double[] newY = new double[length];

				try
				{
					for (int i = 0; i < length; i++)
					{
						newX[i] = Double.parseDouble(model.getValueAt(i, 1).toString());
						newY[i] = Double.parseDouble(model.getValueAt(i, 2).toString());
					}
				} catch (NumberFormatException exp)
				{
					newX = null;
					newY = null;
					tabbedPane.setSelectedIndex(0);
					JOptionPane.showMessageDialog(null, "You sir - reptile. You have entered the wrong data");

					return;
				}

				updatePoints(newX, newY);

				checkValueXkAndDegree();

			} else
			{
				tabbedPane.setSelectedIndex(0);
				JOptionPane.showMessageDialog(null, "You are forgotten input data!");
			}

		}
	}

	boolean isSetXkAndDegree = false;

	public void checkValueXkAndDegree()
	{
		if (textXk.getText().equals("") || textDegree.getText().equals(""))
		{
			btnFind.setEnabled(false);
			mntmSaveAs.setEnabled(false);
			isSetXkAndDegree = false;
		} else
		{

			btnFind.setEnabled(true);
			mntmSaveAs.setEnabled(true);

			double tmpXk = 0;
			int tmpDegree = 1;

			try
			{
				// if (!textXk.getText().equals(""))
				tmpXk = Double.parseDouble(textXk.getText());

				// if (!textDegree.getText().equals(""))
				tmpDegree = Integer.parseInt(textDegree.getText());

			} catch (NumberFormatException e)
			{
				btnFind.setEnabled(false);
				JOptionPane.showMessageDialog(null, "You crazy fish. Data is wrong");

				return;
			}

			if (tmpXk != xk || tmpDegree != degree)
				isSetXkAndDegree = false;

			if (!isSetXkAndDegree)
			{
				setXk(tmpXk);
				setDegree(tmpDegree);

				isSetXkAndDegree = true;

				find();
				JOptionPane.showMessageDialog(null, String.format("xk = %f\ndegree = %d", xk, degree));
			}
		}
	}

	private void buttonAddActionPerformed(ActionEvent e)
	{
		int rowIndex = tableXY.getSelectedRow();

		// If didn't choose any row, that add in end
		if (rowIndex < 0)
			rowIndex = tableXY.getModel().getRowCount();

		double x = 0.0;
		double y = 0.0;

		addDataTableXY(rowIndex, x, y);

	}

	private void addDataTableXY(int rowIndex, double x, double y)
	{
		((DefaultTableModel) tableXY.getModel()).insertRow(rowIndex, new Object[] { rowIndex + 1, x, y });

		for (int i = rowIndex + 1; i < tableXY.getModel().getRowCount(); i++)
		{
			tableXY.getModel().setValueAt(i + 1, i, 0);
		}

		rowIndexTableXY++;
	}

	private void buttonRemoveActionPerformed(ActionEvent e)
	{
		int rowIndex = tableXY.getSelectedRow();

		// If didn't choose any row, that delete last
		if (rowIndex < 0)
			if (tableXY.getModel().getRowCount() != 0)
				rowIndex = tableXY.getModel().getRowCount();
			else
				rowIndex = -1;

		// int rowIndex = tableXY.getModel().getRowCount() - 1;
		if (rowIndex >= 0)
			removeDataTableXY(rowIndex);

	}

	private void removeDataTableXY(int rowIndex)
	{
		if (rowIndex >= 0 && tableXY.getModel().getRowCount() > 1)
		{
			for (int i = rowIndex + 1; i < tableXY.getModel().getRowCount(); i++)
			{
				Object x = tableXY.getModel().getValueAt(i, 1);
				Object y = tableXY.getModel().getValueAt(i, 2);

				((DefaultTableModel) tableXY.getModel()).setValueAt(i, i - 1, 0);
				tableXY.getModel().setValueAt(x, i - 1, 1);
				tableXY.getModel().setValueAt(y, i - 1, 2);

			}
		}

		rowIndexTableXY--;
		((DefaultTableModel) tableXY.getModel()).removeRow(tableXY.getModel().getRowCount() - 1);

	}

	private boolean isUpdatePoints = false;

	private LeastSquares getInstanceLeastSquares()
	{
		if (!isUpdatePoints)
		{
			leastSquares = new LeastSquares(x, y);
			isUpdatePoints = true;
		}

		return leastSquares;
	}

	public void updatePoints(double[] x, double[] y)
	{
		this.x = x;
		this.y = y;

		isUpdatePoints = false; // Data don't update in leastSquares
		isDraw = false;
	}

	public void setXk(double xk)
	{
		if (xk > 0)
			this.xk = xk;
	}

	public void setDegree(int degree)
	{
		if (degree > 0 && degree < 20)
		{
			this.degree = degree;
		}
	}

	private boolean isClearImage = false;

	private void clearForm()
	{
		textXk.setText("");
		textDegree.setText("");
		textPolynomial.setText("P1(x) = ");
		textDispersion.setText("");
		textSumOfSquares.setText("");

		while (tableXY.getModel().getRowCount() > 0)
			removeDataTableXY(tableXY.getModel().getRowCount() - 1);

		clearTableInterpolation();

		tabbedPane.setSelectedIndex(0);

		isClearImage = true;
		panelGraphic.repaint();
	}

	private void clearTableInterpolation()
	{
		DefaultTableModel modelInter = (DefaultTableModel) tableInterpolation.getModel();
		while (modelInter.getRowCount() > 0)
			modelInter.removeRow((tableInterpolation.getModel().getRowCount() - 1));
	}

	private void updateForm(double newXk, int newDegree, double[] newX, double[] newY)
	{
		textXk.setText(Double.toString(newXk));
		textDegree.setText(Integer.toString(newDegree));

		for (int i = 0; i < newX.length; i++)
			addDataTableXY(i, newX[i], newY[i]);
	}
}
