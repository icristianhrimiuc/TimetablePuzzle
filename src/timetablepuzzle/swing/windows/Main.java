package timetablepuzzle.swing.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Component;

import javax.swing.*;

import java.util.List;
import java.util.Set;

import com.sun.xml.internal.ws.util.StringUtils;

import timetablepuzzle.eclipselink.entities.administration.*;
import timetablepuzzle.eclipselink.entities.inputdata.Class;
import timetablepuzzle.eclipselink.entities.inputdata.Solution;

public class Main {
    private LoginDialog _loginDialog;
	private JFrame _frame;
	// User required information
	private User _loggedUser;
	private AcademicYear _viewedAcadYear;
	private AcademicSession _viewedAcadSession;
	private Faculty _viewedFaculty;
	private Solution _acceptedSolution;
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
		
		// The login operation was successful. Set the user required information
		_loggedUser = _loginDialog.get_loggedUser();
		_viewedAcadYear = _loggedUser.get_lastViewedAcadYear();
		_viewedAcadSession = _loggedUser.get_lastViewedAcadSession();
		_viewedFaculty = _loggedUser.get_lastViewedFaculty();
		
		// Set the rest of the main window's properties
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// TO DO: this function will try to get the user
		// It can't since the user is set after the credentials are inserted;
		this.AddComponentToPane(_frame.getContentPane());
		
		
		/****************** Here i define the menu bar********************/
		JMenuBar menuBarAdmin = CreateMenuBarAdmin();
		_frame.setJMenuBar(menuBarAdmin);
	}
	
	/********************Getters and setters****************/
	public JFrame getFrame()
	{
		return this._frame;
	}
    
	/****************Methods that model the class behavior********************/
	private JMenuBar CreateMenuBarAdmin()
	{
		JMenuBar menuBarAdmin = new JMenuBar();
		
		
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
		
		// Return the created menu bar
		return menuBarAdmin;
	}
	
    public void AddComponentToPane(Container pane) 
    {          
    	JPanel homeCard = CreateHomeCard();
    	JPanel timetableCard = CreateTimetableCard();         
    	
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        // Create the home card
        cards.add(homeCard, HOMECARD);        
        cards.add(timetableCard, TIMETABLECARD);
         
        pane.add(cards, BorderLayout.CENTER);
    }
    
    private JPanel CreateHomeCard()
    {
    	JPanel homeCard = new JPanel();
    	homeCard.setLayout(new BoxLayout(homeCard, BoxLayout.Y_AXIS));
        // Create the header panel
    	JPanel headerPanel = CreateHeaderPanel();        
        // Create the centerPanel
        JPanel centerPanel = CreateTimetablePanel(Color.LIGHT_GRAY);
        // Add components to panel
        homeCard.add(headerPanel);
        homeCard.add(new JSeparator());
        homeCard.add(centerPanel);
        
        return homeCard;
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
    
    public JPanel CreateTimetablePanel(Color bgColor)
    {
    	JPanel timetablePanel = new JPanel();
    	timetablePanel.setBackground(bgColor);
    	timetablePanel.setLayout(new BorderLayout());
        
        // Create the north panel. It will contain a radio button for each day of the week
        JPanel northPanel = new JPanel();
        northPanel.setBackground(bgColor);
        northPanel.setLayout(new GridBagLayout());
        TimePreferences.Day[] daysOfTheWeek = TimePreferences.Day.values();
        // Create the academic session radio buttons section
        ButtonGroup dotwbg = new ButtonGroup();
        for(TimePreferences.Day dayOfTheWeek : daysOfTheWeek)
        {
        	JRadioButton jrb = new JRadioButton(StringUtils.capitalize(dayOfTheWeek.name()));
        	jrb.setBackground(bgColor);
        	dotwbg.add(jrb);
        	northPanel.add(jrb);
        }        
        
        // Create the south panel. It will contain a radio button for each academic year
        JPanel southPanel = new JPanel();
        southPanel.setBackground(bgColor);
        southPanel.setLayout(new GridBagLayout());
        YearOfStudy.Year[] yearsOfStudy = YearOfStudy.Year.values();
        // Create the academic session radio buttons section
        ButtonGroup yosbg = new ButtonGroup();
        for(YearOfStudy.Year yearOfStudy : yearsOfStudy)
        {
        	if(yearOfStudy != YearOfStudy.Year.UNASSIGNED)
        	{
	        	JRadioButton jrb = new JRadioButton(StringUtils.capitalize(yearOfStudy.name()));
	        	jrb.setBackground(bgColor);
	        	yosbg.add(jrb);
	        	southPanel.add(jrb);
        	}
        }
        
        // Create the west panel.It will contain a radio button for each department in the faculty
        JPanel westPanel = new JPanel();
        westPanel.setBackground(bgColor);
        westPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        List<Department> departments = _viewedFaculty.get_departments();
        // Create the academic session radio buttons section
        ButtonGroup dbg = new ButtonGroup();
        for(int i=0; i<departments.size(); i++)
        {
        	JRadioButton jrb = new JRadioButton(StringUtils.capitalize(departments.get(i).get_name()));
        	jrb.setBackground(bgColor);
        	jrb.setHorizontalTextPosition(SwingConstants.CENTER);
        	jrb.setVerticalTextPosition(JRadioButton.TOP);
        	dbg.add(jrb);
        	c.gridy = i;
        	westPanel.add(jrb,c);
        }
        
        // Create the east panel. It will contain 2 lists
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBackground(bgColor);
        // Create a list with unassigned classes
        DefaultListModel<String> uClassesListModel = new DefaultListModel<String>();
        List<Class> uClasses = _viewedAcadSession.get_solution().get_unassignedClasses();
        for(Class uClass : uClasses)
        {
        	String className = uClass.get_meeting().get_name();
        	int nrOfRemovals = _acceptedSolution.get_nrOfRemovals(uClass.get_externalId());
        	uClassesListModel.addElement(className+"("+nrOfRemovals+")");
        }
        JList<String> jListUnassignedClasses = new JList<String>(uClassesListModel);
        jListUnassignedClasses.setBackground(bgColor);
        // Label to show the number of unassigned classes
        JLabel jlNrOfUClasses = new JLabel(" Nr. of unassigned classes: " + uClasses.size() + " ");
        
        
        // Create a list with assigned classes
        DefaultListModel<String> aClassesListModel = new DefaultListModel<String>();
        Set<Class> aClasses = _viewedAcadSession.get_solution().get_assignedClasses().keySet();
        for(Class aClass : aClasses)
        {
        	String className = aClass.get_meeting().get_name();
        	int nrOfRemovals = _acceptedSolution.get_nrOfRemovals(aClass.get_externalId());
        	aClassesListModel.addElement(className+"("+nrOfRemovals+")");
        }
        JList<String> jListAssignedClasses = new JList<String>(aClassesListModel);
        jListAssignedClasses.setBackground(bgColor);
        // Label to show the number of assigned classes
        JLabel jlNrOfAClasses = new JLabel(" Nr. of assigned classes: " + aClasses.size() + " ");
        jlNrOfAClasses.setBackground(bgColor);
        
        // Add components to the east panel
        eastPanel.add(jlNrOfUClasses);
        eastPanel.add(jListUnassignedClasses);
        eastPanel.add(jlNrOfAClasses);
        eastPanel.add(jListAssignedClasses);
        
        // Create the center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        
        // Add components to panel
        timetablePanel.add(northPanel, BorderLayout.NORTH);
        timetablePanel.add(southPanel, BorderLayout.SOUTH);
        timetablePanel.add(westPanel, BorderLayout.WEST);
        timetablePanel.add(eastPanel, BorderLayout.EAST);
        timetablePanel.add(centerPanel, BorderLayout.CENTER);
        
        
        return timetablePanel;
    }
    
    private JPanel CreateTimetableCard()
    {
    	JPanel timetableCard = new JPanel();
    	// TO DO: add functionality to this card
    	
    	return timetableCard;
    }
}
