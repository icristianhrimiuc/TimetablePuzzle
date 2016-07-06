package timetablepuzzle.swing.windows.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HomeCard extends JPanel{
	/**
	 * TO DO: figure out what this field means
	 */
	private static final long serialVersionUID = 1L;
	/***************Regular properties*********************/
	private Color _bgColor;
	private String _bgImageFilePath;
	private Toolkit _toolkit;
	private MediaTracker _mediaTracker;
	private Image _bgImage;
	
	public HomeCard(String imageFilePath, Color bgColor)
	{
		_bgColor = bgColor;
		_bgImageFilePath = imageFilePath;
		_toolkit = Toolkit.getDefaultToolkit();
		_mediaTracker = new MediaTracker(this);
		LoadImabeFromFile();
		
		// Set the panel properties
		this.setLayout(new GridBagLayout());
		this.setBackground(_bgColor);
		
		JTextArea jtaWelcome = new JTextArea("Welcome to Timetable Puzzle! A university timetabling application!" + "\n" +
		"The main functionalities for this application can be found in the top menu bar or in the header section" + "\n\n" + 
		"In the header section you will notice a couple of fields. These fields display the current user and his rights," + 
		"the selected academic year, the selected academic session and the chosen faculty. These fields narrow down the total stored solutions, " +
		"so that the user can get around easier. The user can work on only one solution at a given time, so all this fields must be completed for the solver to work." + "\n\n" +
		"The input data menu allows the user to view all|insert new|update|remove objects that are considered input data for the solver."+ 
		"This may be course offerings|offerings|classes|instructors|rooms|student groups." + "\n\n" + 
		"The course timetabling menu allows the user to view a list of saved timetables for the selected faculty during the selected academic session." +
		"He can also view the accepted solution, and try to solve|improve it by using the solver. The last two menu items display a list of assigned|unassigned classes." + "\n\n" + 
		"The administration menu allows the user to view all|insert new|update|remove objects that are considered administration data for the timetable." + 
		"These objects are faculties|departments|years of study|subject areas|curriculas and also academic years|academic sessions|buildings" + "\n\n" + 
		"The other menu allows the user to view all|insert new|update|remove objects that are nor input data, nor administration data, "
		+ "just objects that help in organising things a bit. " + 
		"These objects are locations|time preferences|date patterns|instructor meetings|room types|room features." + "\n\n" + 
		"Help menu gives information about the application it's purpose and developers and a couple of usefull tips in understanding the system." +
		"Preferences menu contains options about the user role, user settings and user password" + 
		"Last one is the logout menu to get the user out of the application. This will save the curent selected settings in the database, and they will be" + 
		"reloaded when the application is restarted." + "\n\n" +
		"Best of luck!");
		jtaWelcome.setBackground(new Color(0,0,0,0));
		jtaWelcome.setFont(new Font("Courier New",Font.BOLD, 14));
		jtaWelcome.setForeground(Color.BLACK);
		jtaWelcome.setBorder(null);
		jtaWelcome.setEditable(false);
		jtaWelcome.setLineWrap(true);
		jtaWelcome.setWrapStyleWord(true);
		jtaWelcome.setSize(800, 600);
		jtaWelcome.setHighlighter(null);
		jtaWelcome.setOpaque(false);
		
		this.add(jtaWelcome);
		
	}
	
	private void LoadImabeFromFile()
	{
		_bgImage = _toolkit.getImage(_bgImageFilePath);
        _mediaTracker.addImage(_bgImage, 0);
        try {
			_mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(_bgImage, 0, 0, this);
        super.paintComponent(g);
        if (_bgImage != null) {
            int x = (getWidth() - _bgImage.getWidth(null)) / 2;
            int y = (getHeight() - _bgImage.getHeight(null)) / 2;
            g.drawImage(_bgImage, x, y, this);
        }
    }
}
