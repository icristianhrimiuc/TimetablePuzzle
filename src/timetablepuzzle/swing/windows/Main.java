package timetablepuzzle.swing.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Component;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import timetablepuzzle.eclipselink.entities.administration.User;

import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;

public class Main {
    private LoginDialog _loginDialog;
	private JFrame _frame;
	private User _loggedUser;
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
		_frame.setTitle("TimetablePuzzle - University Timetabling Application");
		_frame.setSize(800, 600);
		_frame.setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\DesktopI\\Licenta\\chestii\\Java\\TimetablePuzzle\\src\\resources\\icon.png"));
		_loginDialog = new LoginDialog(_frame, true);
		_loginDialog.setVisible(true);
		_loggedUser = _loginDialog.get_loggedUser();
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// TO DO: this function will try to get the user
		// It can't since the user is set after the credentials are inserted;
		this.AddComponentToPane(_frame.getContentPane());
		
		
		/****************** Here i define the menu bar********************/
		JMenuBar menuBarAdmin = new JMenuBar();
		_frame.setJMenuBar(menuBarAdmin);
		
		
		// Input Data menu
		JMenu mnInputData = new JMenu("InputData");
		menuBarAdmin.add(mnInputData);
		
		JMenuItem mntmCourseOfferings = new JMenuItem("Course Offerings");
		mnInputData.add(mntmCourseOfferings);
		
		JMenuItem mntmOfferings = new JMenuItem("Offerings");
		mnInputData.add(mntmOfferings);
		
		JSeparator separator = new JSeparator();
		mnInputData.add(separator);
		
		JMenuItem mntmClasses = new JMenuItem("Classes");
		mnInputData.add(mntmClasses);
		
		JMenuItem mntmInstructors = new JMenuItem("Instructors");
		mnInputData.add(mntmInstructors);
		
		JMenuItem mntmRooms = new JMenuItem("Rooms");
		mnInputData.add(mntmRooms);
		
		JMenuItem mntmStudentgroups = new JMenuItem("StudentGroups");
		mnInputData.add(mntmStudentgroups);
		
		
		// Course Timetabling menu
		JMenu mnCourseTimetabling = new JMenu("Course Timetabling");
		menuBarAdmin.add(mnCourseTimetabling);
		
		JMenuItem mntmSavedTimetables = new JMenuItem("Saved Timetables");
		mnCourseTimetabling.add(mntmSavedTimetables);
		
		JSeparator separator_1 = new JSeparator();
		mnCourseTimetabling.add(separator_1);
		
		JMenuItem mntmTimetable = new JMenuItem("Timetable");
		mnCourseTimetabling.add(mntmTimetable);
		
		JMenuItem mntmAssignedClasses = new JMenuItem("Assigned Classes");
		mnCourseTimetabling.add(mntmAssignedClasses);
		
		JMenuItem mntmUnassignedClasses = new JMenuItem("Unassigned Classes");
		mnCourseTimetabling.add(mntmUnassignedClasses);
		
		
		// Administration menu
		JMenu mnAdministration = new JMenu("Administration");
		menuBarAdmin.add(mnAdministration);
		
		JMenuItem mntmFaculties = new JMenuItem("Faculties");
		mnAdministration.add(mntmFaculties);
		
		JMenuItem mntmDepartments = new JMenuItem("Departments");
		mnAdministration.add(mntmDepartments);
		
		JMenuItem mntmYearsOfStudy = new JMenuItem("Years Of Study");
		mnAdministration.add(mntmYearsOfStudy);
		
		JMenuItem mntmSubjectAreas = new JMenuItem("Subject Areas");
		mnAdministration.add(mntmSubjectAreas);
		
		JMenuItem mntmCurriculas = new JMenuItem("Curriculas");
		mnAdministration.add(mntmCurriculas);
		
		JSeparator separator_2 = new JSeparator();
		mnAdministration.add(separator_2);
		
		JMenuItem mntmAcademicYears = new JMenuItem("Academic Years");
		mnAdministration.add(mntmAcademicYears);
		
		JMenuItem mntmAcademicSessions = new JMenuItem("Academic Sessions");
		mnAdministration.add(mntmAcademicSessions);
		
		JMenuItem mntmBuildings = new JMenuItem("Buildings");
		mnAdministration.add(mntmBuildings);
		
		
		// Other menu
		JMenu mnOther = new JMenu("Other");
		menuBarAdmin.add(mnOther);
		
		JMenuItem mntmLocations = new JMenuItem("Locations");
		mnOther.add(mntmLocations);
		
		JMenuItem menuItem = new JMenuItem("Time Preferences");
		mnOther.add(menuItem);
		
		JMenuItem mntmDatePatterns = new JMenuItem("Date Patterns");
		mnOther.add(mntmDatePatterns);
		
		JMenuItem mntmInstructorMeetings = new JMenuItem("Instructor Meetings");
		mnOther.add(mntmInstructorMeetings);
		
		JMenuItem mntmRoomtypes = new JMenuItem("RoomTypes");
		mnOther.add(mntmRoomtypes);
		
		JMenuItem mntmRoomfeatures = new JMenuItem("RoomFeatures");
		mnOther.add(mntmRoomfeatures);
		
		
		// Help menu
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setInheritsPopupMenu(true);
		menuBarAdmin.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		menuBarAdmin.add(Box.createHorizontalGlue());
		
		
		// Preferences menu
		JMenu mnPreferences = new JMenu("Preferences");
		menuBarAdmin.add(mnPreferences);
		
		JMenuItem mntmChangeRole = new JMenuItem("Change Role");
		mnPreferences.add(mntmChangeRole);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnPreferences.add(mntmSettings);
		
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mnPreferences.add(mntmChangePassword);
		
		
		// Log out menu
		JMenu mnLogOut = new JMenu("Log Out");
		menuBarAdmin.add(mnLogOut);
		/****************************************************/		
	}
	
	/********************Getters and setters****************/
	public JFrame getFrame()
	{
		return this._frame;
	}
    
    public void AddComponentToPane(Container pane) {
    	
        /******************Create the HOME card***************************/
    	JPanel homeCard = new JPanel();
    	homeCard.setLayout(new BoxLayout(homeCard, BoxLayout.Y_AXIS));
        
        // Create the header
    	JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
        
        // Create the user type & user last name section
        String userLastName = _loggedUser.get_lastName();
        String userType = _loggedUser.get_userType().toString();
        JPanel hUserSection = CreateHeaderSection("User name&type:", userLastName + "-" + userType.toLowerCase(),"Show all users");
        
        //Create the academic session section
        String lastViewedAcadSession = _loggedUser.get_lastViewedAcadSession().get_name();
        JPanel hViewedAcademicSection = CreateHeaderSection("Academic session:", lastViewedAcadSession,"Change session");
        
        //Create the faculty section
        String lastViewedFaculty = _loggedUser.get_lastViewedFaculty().get_name();
        JPanel hViewedFaculty = CreateHeaderSection("Faculty:", lastViewedFaculty,"Change faculty");  
        
        // Add the different section to the header panel
        headerPanel.add(hUserSection);
        headerPanel.add(hViewedAcademicSection);
        headerPanel.add(hViewedFaculty);
        
        // Create the centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.LIGHT_GRAY);
        
        
        homeCard.add(headerPanel);
        homeCard.add(centerPanel);
        
    	
    	JPanel timetableCard = new JPanel();
    	timetableCard.add(new JButton());
         
    	
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        // Create the home card
        cards.add(homeCard, HOMECARD);        
        cards.add(timetableCard, TIMETABLECARD);
         
        pane.add(cards, BorderLayout.CENTER);
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
