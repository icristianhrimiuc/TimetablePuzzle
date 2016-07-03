package timetablepuzzle.spring.windows;

import java.awt.EventQueue;

public class Program {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main mainWindow = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
