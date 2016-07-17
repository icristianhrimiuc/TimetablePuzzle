package timetablepuzzle.swing.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import timetablepuzzle.eclipselink.entities.administration.*;
import timetablepuzzle.eclipselink.entities.administration.User.UserType;
import timetablepuzzle.eclipselink.entities.inputdata.Solution;
import timetablepuzzle.swing.windows.cards.HomeCard;
import timetablepuzzle.swing.windows.cards.TimetableCard;

public class Main implements ActionListener{
	/*****************Static properties****************/
    final static String HOMECARD = "HomeCard";
    final static String TIMETABLECARD = "TimetableCard";
    /******************Regular properties*************/
    // Main window fields
    private LoginDialog _loginDialog;
	private JFrame _frame;
	// User required information
	private User _loggedUser;
	private AcademicYear _viewedAcadYear;
	private AcademicSession _viewedAcadSession;
	private Faculty _viewedFaculty;
	private Solution _acceptedSolution;
	private Color _bgColor;
	// A collection of easy retrievable cards
	private HashMap<String,JPanel> _cards;
	private JPanel _cardsPanel;
	private CardLayout _cardLayout;

	/**
	 * Default constructor. Creates the application
	 */
	public Main(Color bgColor) {
		// Initialize the main frame
		_frame = new JFrame();
		_frame.setSize(800, 600);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setTitle("TimetablePuzzle - University Timetabling Application");
		_frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resources\\icon.png"));
		
		// Initialize the login dialog and display it
		_loginDialog = new LoginDialog(_frame, true);
		_loggedUser = _loginDialog.execute();
		
		if(_loginDialog.isValidUser())
		{
			// The login operation was successful. Set the user required information
			_viewedAcadYear = _loggedUser.get_lastViewedAcadYear();
			_viewedAcadSession = _loggedUser.get_lastViewedAcadSession();
			_viewedFaculty = _loggedUser.get_lastViewedFaculty();
			_bgColor = bgColor;

			// Set the rest of the main window's properties
			_frame.setExtendedState(_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			_frame.setJMenuBar(CreateMenuBar(_loggedUser.get_userType()));
			
			// Create all the cards
			_cards = new HashMap<String,JPanel>();
			_cards.put(HOMECARD,  new HomeCard("src\\resources\\homeBackground.png",
					"src\\resources\\homeText.txt"));
			_cards.put(TIMETABLECARD, new TimetableCard(_loggedUser, 
					_viewedAcadYear, _viewedAcadSession, _viewedFaculty, 
					_acceptedSolution, bgColor));
			
			// Add components to the content pane
			this.AddComponentToPane(_frame.getContentPane());
			
			// Display the main frame
			_frame.setVisible(true);
		}
	}
	
	/********************Getters and setters****************/
	public JFrame getFrame()
	{
		return this._frame;
	}
	
	public Color get_bgColor() {
		return _bgColor;
	}
    
	/****************Methods that model the class behavior********************/
	
	private JMenuBar CreateMenuBar(UserType userType)
	{
		JMenuBar jMenuBar = new JMenuBar();
		
		// Home menu
		JMenu mnHome = new JMenu("Home");
		mnHome.addActionListener(this);
		// Add menu to menu bar
		jMenuBar.add(mnHome);
		
		// Input Data menu
		JMenu mnInputData = new JMenu("InputData");
		// Input data menu items
		JMenuItem mntmCourseOfferings = new JMenuItem("Course Offerings");
		mntmCourseOfferings.addActionListener(this);
		JMenuItem mntmOfferings = new JMenuItem("Offerings");
		JMenuItem mntmClasses = new JMenuItem("Classes");
		JMenuItem mntmInstructors = new JMenuItem("Instructors");
		JMenuItem mntmRooms = new JMenuItem("Rooms");
		JMenuItem mntmStudentgroups = new JMenuItem("StudentGroups");
		// Add menu items
		mnInputData.add(mntmCourseOfferings);
		mnInputData.add(mntmOfferings);
		mnInputData.add(new JSeparator());
		mnInputData.add(mntmClasses);
		mnInputData.add(mntmInstructors);
		mnInputData.add(mntmRooms);		
		mnInputData.add(mntmStudentgroups);
		// Add menu to menu bar
		jMenuBar.add(mnInputData);
		
		
		if(userType == UserType.ADMIN || userType == UserType.SECRETARY || userType == UserType.INSTRUCTOR)
		{
			// Course Timetabling menu
			JMenu mnCourseTimetabling = new JMenu("Course Timetabling");
			// Course timetabling menu items
			JMenuItem mntmSavedTimetables = new JMenuItem("Saved Timetables");
			JMenuItem mntmTimetable = new JMenuItem("Timetable");
			mntmTimetable.addActionListener(this);
			JMenuItem mntmAssignedClasses = new JMenuItem("Assigned Classes");
			JMenuItem mntmUnassignedClasses = new JMenuItem("Unassigned Classes");
			// Add menu items
			mnCourseTimetabling.add(mntmSavedTimetables);
			mnCourseTimetabling.add(new JSeparator());
			mnCourseTimetabling.add(mntmTimetable);
			mnCourseTimetabling.add(mntmAssignedClasses);		
			mnCourseTimetabling.add(mntmUnassignedClasses);
			// Add menu to menu bar
			jMenuBar.add(mnCourseTimetabling);
		}
		
		if(userType == UserType.ADMIN || userType == UserType.SECRETARY || userType == UserType.INSTRUCTOR)
		{
			// Administration menu
			JMenu mnAdministration = new JMenu("Administration");
			// Administration menu items
			JMenuItem mntmFaculties = new JMenuItem("Faculties");
			JMenuItem mntmDepartments = new JMenuItem("Departments");
			JMenuItem mntmYearsOfStudy = new JMenuItem("Years Of Study");
			JMenuItem mntmSubjectAreas = new JMenuItem("Subject Areas");
			JMenuItem mntmCurriculas = new JMenuItem("Curriculas");
			JMenuItem mntmAcademicYears = new JMenuItem("Academic Years");
			JMenuItem mntmAcademicSessions = new JMenuItem("Academic Sessions");
			JMenuItem mntmBuildings = new JMenuItem("Buildings");
			// Add menu items
			mnAdministration.add(mntmFaculties);
			mnAdministration.add(mntmDepartments);
			mnAdministration.add(mntmYearsOfStudy);
			mnAdministration.add(mntmSubjectAreas);
			mnAdministration.add(mntmCurriculas);
			mnAdministration.add(new JSeparator());
			mnAdministration.add(mntmAcademicYears);
			mnAdministration.add(mntmAcademicSessions);		
			mnAdministration.add(mntmBuildings);
			// Add menu to menu bar
			jMenuBar.add(mnAdministration);
		}
		
		if(userType == UserType.ADMIN || userType == UserType.SECRETARY)
		{
			// Other menu
			JMenu mnOther = new JMenu("Other");
			// Other menu items
			JMenuItem mntmLocations = new JMenuItem("Locations");
			JMenuItem menuItem = new JMenuItem("Time Preferences");
			JMenuItem mntmDatePatterns = new JMenuItem("Date Patterns");
			JMenuItem mntmInstructorMeetings = new JMenuItem("Instructor Meetings");
			JMenuItem mntmRoomtypes = new JMenuItem("RoomTypes");
			JMenuItem mntmRoomfeatures = new JMenuItem("RoomFeatures");
			// Add menu items
			mnOther.add(mntmLocations);
			mnOther.add(menuItem);
			mnOther.add(mntmDatePatterns);
			mnOther.add(mntmInstructorMeetings);
			mnOther.add(mntmRoomtypes);		
			mnOther.add(mntmRoomfeatures);
			// Add menu to menu bar
			jMenuBar.add(mnOther);
		}
		
		
		// Help menu
		JMenu mnHelp = new JMenu("Help");
		// Help menu items
		JMenuItem mntmAbout = new JMenuItem("About");
		JMenuItem mntmHelp = new JMenuItem("Help");
		// Add menu items
		mnHelp.add(mntmAbout);
		mnHelp.add(mntmHelp);
		// Add menu to menu bar
		jMenuBar.add(mnHelp);
		
		
		jMenuBar.add(Box.createHorizontalGlue());
		
		if(userType == UserType.ADMIN)
		{
			// Preferences menu
			JMenu mnPreferences = new JMenu("Preferences");
			// Preferences menu items
			JMenuItem mntmChangeRole = new JMenuItem("Change Role");
			JMenuItem mntmSettings = new JMenuItem("Settings");
			JMenuItem mntmChangePassword = new JMenuItem("Change Password");
			// Add menu items
			mnPreferences.add(mntmChangeRole);
			mnPreferences.add(mntmSettings);
			mnPreferences.add(mntmChangePassword);
			// Add menu to menu bar
			jMenuBar.add(mnPreferences);
		}
		
		
		// Log out menu
		JMenu mnLogOut = new JMenu("Log Out");
		// Add menu to menu bar
		jMenuBar.add(mnLogOut);
		
		
		// Return the created menu bar
		return jMenuBar;
	}
	
	public void actionPerformed(ActionEvent e) {
        //...Get information from the action event...
        //...Display it in the text area...
		String action = e.getActionCommand();
		if("Timetable".equals(action))
		{
			_cardLayout.show(_cardsPanel, TIMETABLECARD);
		}else{
			_cardLayout.show(_cardsPanel, HOMECARD);
		}
    }
	
    public void AddComponentToPane(Container pane)
    {
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    	mainPanel.add(CreateHeaderPanel());
    	mainPanel.add(new JSeparator());
    	
        //Create the panel that contains the "cards".
        _cardsPanel = new JPanel(new CardLayout());
        
        for(String cardName : _cards.keySet())
        {
        	_cardsPanel.add(_cards.get(cardName),cardName);
        }
        _cardLayout = (CardLayout) _cardsPanel.getLayout();
        _cardLayout.show(_cardsPanel, HOMECARD);
         
        mainPanel.add(_cardsPanel, BorderLayout.CENTER);
        
        pane.add(mainPanel);
    }
    
    private JPanel CreateHeaderPanel()
    {
    	JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
        
        // Create the user type & user last name section
        String userName = _loggedUser.get_firstName() + " " + _loggedUser.get_lastName();
        String userType = _loggedUser.get_userType().toString();
        JPanel hUserSection = CreateHeaderSection("User Name&Type:", userName + "-" + userType.toLowerCase(),"Show all users");
        
        //Create the academic year section
        String lastViewedAcadYear = _viewedAcadYear.get_yearPeriod();
        JPanel hViewedAcademicYear = CreateHeaderSection("Academic year:", lastViewedAcadYear,"Change year");
        
        //Create the academic session section
        String lastViewedAcadSession = _viewedAcadSession.get_name();
        JPanel hViewedAcademicSession = CreateHeaderSection("Academic session:", lastViewedAcadSession,"Change session");
        
        //Create the faculty section
        String lastViewedFaculty = _loggedUser.get_lastViewedFaculty().get_name();
        JPanel hViewedFaculty = CreateHeaderSection("Faculty:", lastViewedFaculty,"Change faculty");  
        
        // Add the different section to the header panel
        headerPanel.add(hUserSection);
        headerPanel.add(hViewedAcademicYear);
        headerPanel.add(hViewedAcademicSession);
        headerPanel.add(hViewedFaculty);
        
        return headerPanel;
    }
    
    private JPanel CreateHeaderSection(String jLabelText, String jButtonText, String jButtonToolTipText)
    {
    	JPanel headerSection = new JPanel();
    	headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
        // Create a button to appear like a label
    	JLabel jLabel = new JLabel(jLabelText);
    	jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	JButton jButton = new JButton(jButtonText);
    	jButton.setForeground(Color.gray);
    	jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	jButton.setFocusPainted(false);
        jButton.setToolTipText(jButtonToolTipText);
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setCursor( new Cursor(Cursor.HAND_CURSOR) );
        
        headerSection.add(jLabel);
        headerSection.add(jButton);
        
        return headerSection;
    }

}
