package timetablepuzzle.swing;

import java.awt.Color;
import java.awt.EventQueue;

import timetablepuzzle.swing.windows.MainWindow;

public class Program {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow mainWindow = new MainWindow(Color.LIGHT_GRAY);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}