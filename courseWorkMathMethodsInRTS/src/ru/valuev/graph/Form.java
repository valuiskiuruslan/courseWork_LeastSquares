package ru.valuev.graph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.valuev.graph.plotGraph.Plotter;
import javax.swing.JTabbedPane;

public class Form extends JFrame {
	private JPanel panel;

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
	private JTextField txtMovingPointX;
	private JTextField txtMovingPointY;
	
	
	private boolean isFirstTime = true;

	public Form() {
		setSize(new Dimension(628, 350));

		getContentPane().setBackground(SystemColor.control);
		getContentPane().setLayout(null);

		intializeCordiante();

		panel = new JPanel() {
			private Plotter p;

			@Override
			public void paintComponent(Graphics g) {
				// I don't know? How it's fixed
				p = new Plotter(g, new Plotter.Function() {
					public double fun(double x) {
						return func1(x);
					}
				}, this.getWidth(), this.getHeight());

				p.addFunction(new Plotter.Function() {
					public double fun(double x) {
						return func2(x);
					}
				});

				p.draw(xStart, yMin, xFinal, yMax, null, movingPointX);
				txtMovingPointX.setText(String.format("%.4f", p.getMovingX()));
				txtMovingPointY.setText(String.format("%.4f", p.getMovingY()));
			}
		};
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.requestFocusInWindow();
			}
		});
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				panelKeyPressed(e);
			}
		});
		panel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {

				double factor = (e.getWheelRotation() == 1) ? 2d : (1d / 2d);
				
				

				if (scale < 16d) {
//					xStart = ((xStart == 0) ? 1 : xStart) * factor;
//					yMin = ((yMin == 0) ? 1 : yMin) * factor;
//					xFinal = ((xFinal == 0) ? 1 : xFinal) * factor;
//					yMax = ((yMax == 0) ? 1 : yMax) * factor;
					
//					xStartScale *= factor;
//					yMinScale *= factor;
//					xFinalScale *= factor;
//					yMaxScale *= factor;
					
//					xStartScale = ((xStartScale == 0) ? 1 : xStartScale) * factor;
//					yMinScale = ((yMinScale == 0) ? 1 : yMinScale) * factor;
//					xFinalScale = ((xFinalScale == 0) ? 1 : xFinalScale) * factor;
//					yMaxScale = ((yMaxScale == 0) ? 1 : yMaxScale) * factor;
					
					boolean isIncrease = (factor == (1d / 2d));
					
					//outputCoordinate(xStart, yMin, xFinal, yMax);
					//setScale(isIncrease);
					
					
					
					
				//	txtWheel.setText(resize + "");
					
					if (isIncrease) {						
						resize *= factor;
						
						yMax -= resize;
						yMin += resize;
						
						xFinal -= resize;
						xStart += resize;
						
						
						
					} else {
//						if (isFirstTime) {
//							resize /= factor;
//							isFirstTime = false;
//						}
						
						if (resize < 40) {
						
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
				
				if (scale >= 16d && factor == 1 / 2d) {
					//resize *= factor;
					scale *= factor; 		
				}
			}

		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				panelMouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();
				
				//JOptionPane.showMessageDialog(null, "Mouse move");
				
				movingPointX = currentX;
				panel.repaint();
			}
		});

		panel.setBounds(181, 61, 421, 240);
		getContentPane().add(panel);

		this.setMinimumSize(this.getSize());
		this.pack();
		panel.requestFocusInWindow();
		
		txtMovingPointX = new JTextField();
		txtMovingPointX.setBounds(45, 127, 103, 20);
		getContentPane().add(txtMovingPointX);
		txtMovingPointX.setColumns(10);
		
		txtMovingPointY = new JTextField();
		txtMovingPointY.setBounds(45, 158, 103, 20);
		getContentPane().add(txtMovingPointY);
		txtMovingPointY.setColumns(10);
		
		JLabel lblX = new JLabel("x =");
		lblX.setBounds(10, 130, 46, 14);
		getContentPane().add(lblX);
		
		JLabel lblY = new JLabel("y = ");
		lblY.setBounds(10, 161, 46, 14);
		getContentPane().add(lblY);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 120, 80);
		getContentPane().add(tabbedPane);
		
		points = new Point[] { new Point(0, 0)}; 
	}

	private void intializeCordiante() {
		xStart  = -2.5;
		yMin = -2.5;
		xFinal = 2.5;
		yMax = 2.5;
	}

	private double func1(double x) {
		return x * x;
	}

	private double func2(double x) {
		return Math.sin(x) + 100;
	}

	
	private Point[] getPoints() {
		return points;
	}

	private void panelKeyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
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

		panel.repaint();
	}

	private void panelMouseDragged(MouseEvent e) {
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

		//outputCoordinate(xStart, yMin, xFinal, yMax);

		panel.repaint();
	}
	
	private void setScale(boolean isIncrease) {
		double SC = 2d * scale;
		
		
		if (isIncrease) {
			
			xStart -= SC;
		//	yMin -=  scale;
			xFinal += SC;
		//	yMax -= scale;
			
			
		} else {
			xStart += SC;
			//yMin += yMinScale;
			xFinal -= SC;
			//yMax += yMaxScale;
		}
		
		panel.repaint();
		
	}

//	private void outputCoordinate(double x0, double y0, double x1, double y1) {
//		txtXstart.setText(String.format("%.4f", x0));
//		txtYmin.setText(String.format("%.4f", y0));
//		txtXfinal.setText(String.format("%.4f", x1));
//		txtYmax.setText(String.format("%.4f", y1));
//	}
}
