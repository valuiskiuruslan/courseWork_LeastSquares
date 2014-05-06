package ru.valuev.graph;

import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			//	Form f = new Form();
				FormTest f = new FormTest();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);				
			}			
		});
	}

}
