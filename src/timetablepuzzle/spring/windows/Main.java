package timetablepuzzle.spring.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
    private LoginDialog _loginDialog;
	private JFrame _frame;
	// A panel that uses CardLayout
	JPanel cards;
    final static String HOMECARD = "Home card";
    final static String TIMETABLECARD = "Timetable card";

	/**
	 * Default constructor. Creates the application
	 */
	public Main() {
		// Initialize the login dialog and display it
		_frame = new JFrame();
		_loginDialog = new LoginDialog(_frame, true);
		_loginDialog.setVisible(true);
		
		// Set the properties of the main window's frame
		_frame.setTitle("TimetablePuzzle");
		_frame.setLocationRelativeTo(null);
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.AddComponentToPane(_frame.getContentPane());
	}
	
	/********************Getters and setters****************/
	public JFrame getFrame()
	{
		return this._frame;
	}
    
    public void AddComponentToPane(Container pane) {
        // Create the "cards"
    	JPanel homeCard = new JPanel();
    	homeCard.add(new JTextField("TextField", 20));
    	
    	JPanel timetableCard = new JPanel();
    	timetableCard.add(new JButton());
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(homeCard, HOMECARD);
        cards.add(timetableCard, TIMETABLECARD);
         
        pane.add(cards, BorderLayout.CENTER);
    }
}
