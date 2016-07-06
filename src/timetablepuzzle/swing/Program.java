package timetablepuzzle.swing;

import java.awt.Color;
import java.awt.EventQueue;

import timetablepuzzle.swing.windows.Main;

public class Program {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main mainWindow = new Main(Color.LIGHT_GRAY);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}