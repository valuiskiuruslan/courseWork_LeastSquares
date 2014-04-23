package ru.valuev.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
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

import ru.valuev.theMethodLeastSquares.LeastSquares;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Form1 extends JFrame
{
	private JTable tableXY;
	private JTextField textPolynomial;
	private JTable tableInterpolation;
	private JTextField textXk;
	private JTextField textDegree;
	private JTextField textDispersion;
	private JTextField textSumOfSquares;
	private JTabbedPane tabbedPane;
	private JButton btnFind;

	public Form1()
	{
		setSize(new Dimension(730, 440));
		setTitle("Title");
		getContentPane().setLayout(null);

		JPanel panelGraphic = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
			}
		};
		panelGraphic.setBounds(282, 35, 421, 240);
		getContentPane().add(panelGraphic);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				tabbedPaneStateChanged(e);
			}
		});
		tabbedPane.setBounds(10, 11, 262, 264);
		getContentPane().add(tabbedPane);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setName("");
		tabbedPane.addTab("Your points", null, layeredPane, null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 165, 189);
		layeredPane.add(scrollPane);

		tableXY = new JTable();
		tableXY.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u2116", "X", "Y" }));
		tableXY.getColumnModel().getColumn(0).setPreferredWidth(30);
		scrollPane.setViewportView(tableXY);

		JLabel label = new JLabel("Input your data here:");
		label.setBounds(10, 11, 165, 14);
		layeredPane.add(label);

		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonAddActionPerformed(e);
			}
		});
		buttonAdd.setBounds(206, 39, 41, 23);
		layeredPane.add(buttonAdd);

		JButton buttonRemove = new JButton("-");
		buttonRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonRemoveActionPerformed(e);
			}
		});
		buttonRemove.setBounds(206, 73, 41, 23);
		layeredPane.add(buttonRemove);

		JButton buttonEdit = new JButton("...");
		buttonEdit.setBounds(206, 107, 41, 23);
		layeredPane.add(buttonEdit);

		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Interpolation or prediction", null, layeredPane_1, null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 36, 124, 187);
		layeredPane_1.add(scrollPane_1);

		tableInterpolation = new JTable();
		tableInterpolation.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u2116", "Ei", "Pi" }));
		tableInterpolation.getColumnModel().getColumn(0).setPreferredWidth(30);
		scrollPane_1.setViewportView(tableInterpolation);

		JLabel lblWhatFind = new JLabel("What find:");
		lblWhatFind.setBounds(144, 18, 70, 14);
		layeredPane_1.add(lblWhatFind);

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
		textXk.setBounds(177, 43, 70, 20);
		layeredPane_1.add(textXk);
		textXk.setColumns(10);

		JLabel lblInterpolationDegree = new JLabel("Interpolation degree:");
		lblInterpolationDegree.setBounds(144, 72, 113, 14);
		layeredPane_1.add(lblInterpolationDegree);

		JLabel lblXk = new JLabel("xk = ");
		lblXk.setBounds(148, 46, 39, 14);
		layeredPane_1.add(lblXk);

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
		textDegree.setBounds(177, 97, 32, 20);
		layeredPane_1.add(textDegree);
		textDegree.setColumns(10);

		JLabel lblM = new JLabel("m = ");
		lblM.setBounds(148, 100, 46, 14);
		layeredPane_1.add(lblM);

		JLabel lblResults = new JLabel("Results:");
		lblResults.setBounds(10, 11, 46, 14);
		layeredPane_1.add(lblResults);

		btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnFindActionPerformed(e);
			}
		});
		btnFind.setBounds(144, 132, 89, 23);
		layeredPane_1.add(btnFind);

		JLabel lblGraphic = new JLabel("Graphic:");
		lblGraphic.setBounds(282, 11, 71, 14);
		getContentPane().add(lblGraphic);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 286, 693, 87);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblYourPolynomial = new JLabel("Your polynomial:");
		lblYourPolynomial.setBounds(10, 0, 98, 14);
		panel_1.add(lblYourPolynomial);

		textPolynomial = new JTextField();
		textPolynomial.setText("P1(x) = ");
		textPolynomial.setBounds(10, 25, 673, 20);
		panel_1.add(textPolynomial);
		textPolynomial.setColumns(10);

		JLabel lblDispersion = new JLabel("Dispersion:");
		lblDispersion.setBounds(10, 59, 70, 14);
		panel_1.add(lblDispersion);

		textDispersion = new JTextField();
		textDispersion.setBounds(92, 56, 86, 20);
		panel_1.add(textDispersion);
		textDispersion.setColumns(10);

		JLabel lblSum = new JLabel("Sum of squared residuals:");
		lblSum.setBounds(200, 59, 173, 14);
		panel_1.add(lblSum);

		textSumOfSquares = new JTextField();
		textSumOfSquares.setBounds(383, 56, 86, 20);
		panel_1.add(textSumOfSquares);
		textSumOfSquares.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mnFile.add(mntmSaveAs);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmUndo = new JMenuItem("Undo");
		mnEdit.add(mntmUndo);

		JMenu mnAction = new JMenu("Action");
		menuBar.add(mnAction);

		JMenuItem mntmReset = new JMenuItem("Reset");
		mnAction.add(mntmReset);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		JMenuItem menuItem = new JMenuItem("");
		mnHelp.add(menuItem);

		initialize();
	}

	// My Code
	// -------------------------------------------------------------------------------------------------

	private int rowIndexTableXY = 0;

	private LeastSquares leastSquares;
	private double[] x;
	private double[] y;
	private double xk;
	private int degree;

	private void initialize()
	{
		// leastSquares = new LeastSquares();
	}

	private void btnFindActionPerformed(ActionEvent e)
	{
		find();
	}

	private void find()
	{
		leastSquares = getInstanceLeastSquares();

		leastSquares.setDegree(degree);

		outputPolynomial(leastSquares.getRootA());
		textDispersion.setText(String.format("%.5f", leastSquares.getVariance()));
		textSumOfSquares.setText(String.format("%.5f", leastSquares.getSumResidualSquare()));
	}

	private void outputPolynomial(double[] rootA)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("P%d(x) = ", degree));

		boolean isEnd = false;

		for (int i = 0; i < rootA.length; i++)
		{
			isEnd = (degree - i) == 0;

			if (!isEnd)
				sb.append(String.format("%.2f*x^%d + ", rootA[i], (degree - i)));
			else
				sb.append(String.format("%.2f", rootA[i]));
		}

		textPolynomial.setText(sb.toString());
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
			isSetXkAndDegree = false;
		} else
		{

			btnFind.setEnabled(true);

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
				JOptionPane.showMessageDialog(null,	String.format("xk = %f\t\t\tdegree = %d", xk, degree));
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
}
