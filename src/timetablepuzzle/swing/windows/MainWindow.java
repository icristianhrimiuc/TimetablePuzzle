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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.administration.AcademicSession;
import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.Faculty;
import timetablepuzzle.entities.administration.User;
import timetablepuzzle.entities.administration.User.UserType;
import timetablepuzzle.swing.windows.cards.HomeCard;
import timetablepuzzle.swing.windows.cards.TimetableCard;
import timetablepuzzle.swing.windows.cards.administration.academicSessions.AcademicSessionsCard;
import timetablepuzzle.swing.windows.cards.administration.academicYears.AcademicYearsCard;
import timetablepuzzle.swing.windows.cards.administration.instructorMeetings.InstructorMeetingsCard;
import timetablepuzzle.swing.windows.cards.inputdata.instructors.InstructorsCard;
import timetablepuzzle.swing.windows.cards.inputdata.rooms.RoomsCard;
import timetablepuzzle.swing.windows.cards.inputdata.studentGroups.StudentGroupsCard;
import timetablepuzzle.swing.windows.cards.other.buildings.BuildingsCard;
import timetablepuzzle.swing.windows.cards.other.roomTypes.RoomTypesCard;
import timetablepuzzle.swing.windows.cards.other.timePreferences.TimePreferencesCard;

public class MainWindow implements ActionListener {
	/***************** Static properties ****************/
	final static String HOME_CARD = "Home";
	final static String TIMETABLE_CARD = "Timetable";
	// Other menu
	final static String ACADEMIC_SESSIONS_CARD = "Academic Sessions";
	final static String ACADEMIC_YEARS_CARD = "Academic Years";
	final static String BUILDING_CARD = "Buildings";
	final static String ROOM_TYPE_CARD = "Room Types";
	final static String TIME_PREFERENCES_CARD = "Time Preferences";
	
	// Input data menu
	final static String INSTRUCTORS_CARD = "Instructors";
	final static String ROOMS_CARD = "Rooms";
	final static String STUDENT_GROUPS_CARD = "Student Groups";
	
	// Administration menu
	final static String INSTRUCTOR_MEETINGS_CARD = "Instructor Meetings";
	
	/****************** Regular properties *************/
	// Main window fields
	private LoginDialog loginDialog;
	private JFrame frame;
	// User required information
	private User loggedUser;
	private AcademicYear viewedAcadYear;
	private AcademicSession viewedAcadSession;
	private Faculty viewedFaculty;
	private Solution acceptedSolution;
	private Color bgColor;
	// A collection of easy retrievable cards
	private HashMap<String, JPanel> cards;
	private JPanel cardsPanel;
	private CardLayout cardLayout;

	/**
	 * Default constructor. Creates the application
	 */
	public MainWindow(Color bgColor) {
		// Initialize the main frame
		frame = new JFrame();
		frame.setSize(1050, 700);
		frame.setMinimumSize(new Dimension(1050, 700));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("TimetablePuzzle - University Timetabling Application");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resources\\icon.png"));

		// Initialize the login dialog and display it
		loginDialog = new LoginDialog(frame, true);
		loggedUser = loginDialog.execute();

		if (loginDialog.isValidUser()) {
			// The login operation was successful. Set the user required
			// information
			viewedAcadYear = loggedUser.getLastViewedAcademicYear();
			viewedAcadSession = loggedUser.getLastViewedAcademicSession();
			viewedFaculty = loggedUser.getLastViewedFaculty();
			this.bgColor = bgColor;

			// Set the rest of the main window's properties
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			frame.setJMenuBar(CreateMenuBar(loggedUser.getUserType()));

			// Create all the cards
			cards = new HashMap<String, JPanel>();
			cards.put(HOME_CARD, new HomeCard("src\\resources\\homeBackground.png", "src\\resources\\homeText.txt"));
			cards.put(TIMETABLE_CARD, new TimetableCard(loggedUser, bgColor));
			// Other menu
			cards.put(ACADEMIC_SESSIONS_CARD, new AcademicSessionsCard(bgColor));
			cards.put(ACADEMIC_YEARS_CARD, new AcademicYearsCard(bgColor));
			cards.put(BUILDING_CARD, new BuildingsCard(bgColor));
			cards.put(ROOM_TYPE_CARD, new RoomTypesCard(bgColor));
			cards.put(TIME_PREFERENCES_CARD, new TimePreferencesCard(bgColor));
			// Input data menu
			cards.put(INSTRUCTORS_CARD, new InstructorsCard(bgColor));
			cards.put(ROOMS_CARD, new RoomsCard(bgColor));
			cards.put(STUDENT_GROUPS_CARD, new StudentGroupsCard(bgColor));
			//Administration menu
			cards.put(INSTRUCTOR_MEETINGS_CARD, new InstructorMeetingsCard(bgColor));
			
			// Add components to the content pane
			this.AddComponentToPane(frame.getContentPane());

			// Display the main frame
			frame.setVisible(true);
		}
	}

	/******************** Getters and setters ****************/
	public JFrame getFrame() {
		return this.frame;
	}

	public Color getBgColor() {
		return bgColor;
	}

	/****************
	 * Methods that model the class behavior
	 ********************/
	private JMenuBar CreateMenuBar(UserType userType) {
		JMenuBar jMenuBar = new JMenuBar();

		// Home menu
		JMenu mnHome = new JMenu(HOME_CARD);
		mnHome.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout = (CardLayout) cardsPanel.getLayout();
				cardLayout.show(cardsPanel, HOME_CARD);
			}
		});
		jMenuBar.add(mnHome);

		// Course Timetabling menu
		if (userType == UserType.ADMIN || userType == UserType.SECRETARY || userType == UserType.INSTRUCTOR) {
			JMenu mnCourseTimetabling = CreateJMenuCourseTimeTabling();
			jMenuBar.add(mnCourseTimetabling);
		}

		// Administration menu
		if (userType == UserType.ADMIN || userType == UserType.SECRETARY || userType == UserType.INSTRUCTOR) {
			JMenu mnAdministration = CreateJMenuAdministration();
			jMenuBar.add(mnAdministration);
		}

		// Input Data menu
		JMenu mnInputData = CreateJMenuInputData();
		jMenuBar.add(mnInputData);

		// Other menu
		if (userType == UserType.ADMIN || userType == UserType.SECRETARY) {
			JMenu mnOther = CreateJMenuOther();
			jMenuBar.add(mnOther);
		}

		// Help menu
		JMenu mnHelp = CreateJMenuHelp();
		jMenuBar.add(mnHelp);

		jMenuBar.add(Box.createHorizontalGlue());

		// Preferences menu
		if (userType == UserType.ADMIN) {
			JMenu mnPreferences = CreateJMenuPreferences();
			jMenuBar.add(mnPreferences);
		}

		// Log out menu
		JMenu mnLogOut = new JMenu("Log Out");
		jMenuBar.add(mnLogOut);

		// Return the created menu bar
		return jMenuBar;
	}

	private JMenu CreateJMenuCourseTimeTabling() {
		JMenu mnCourseTimetabling = new JMenu("Course Timetabling");
		
		// Course timetabling menu items
		JMenuItem mntmTimetable = new JMenuItem(TIMETABLE_CARD);
		JMenuItem mntmAssignedClasses = new JMenuItem("Assigned Classes");
		JMenuItem mntmUnassignedClasses = new JMenuItem("Unassigned Classes");
		JMenuItem mntmSavedTimetables = new JMenuItem("Saved Timetables");
		
		// Add action listeners
		mntmTimetable.addActionListener(this);
		
		// Add menu items
		mnCourseTimetabling.add(mntmTimetable);
		mnCourseTimetabling.add(mntmAssignedClasses);
		mnCourseTimetabling.add(mntmUnassignedClasses);
		mnCourseTimetabling.add(new JSeparator());
		mnCourseTimetabling.add(mntmSavedTimetables);
		return mnCourseTimetabling;
	}

	private JMenu CreateJMenuAdministration() {
		JMenu mnAdministration = new JMenu("Administration");
		
		// Administration menu items
		JMenuItem mntmAcademicSessions = new JMenuItem(ACADEMIC_SESSIONS_CARD);
		JMenuItem mntmAcademicYears = new JMenuItem(ACADEMIC_YEARS_CARD);
		JMenuItem mntmCourseOfferings = new JMenuItem("Course Offerings");
		JMenuItem mntmCurriculas = new JMenuItem("Curriculas");
		JMenuItem mntmSubjectAreas = new JMenuItem("Subject Areas");
		JMenuItem mntmYearsOfStudy = new JMenuItem("Years Of Study");
		JMenuItem mntmDepartments = new JMenuItem("Departments");
		JMenuItem mntmFaculties = new JMenuItem("Faculties");
		
		// Add action listeners
		mntmAcademicSessions.addActionListener(this);
		mntmAcademicYears.addActionListener(this);
		
		// Add menu items
		mnAdministration.add(mntmAcademicSessions);
		mnAdministration.add(mntmAcademicYears);
		mnAdministration.add(new JSeparator());
		mnAdministration.add(mntmCourseOfferings);
		mnAdministration.add(mntmCurriculas);
		mnAdministration.add(mntmSubjectAreas);
		mnAdministration.add(mntmYearsOfStudy);
		mnAdministration.add(mntmDepartments);
		mnAdministration.add(new JSeparator());
		mnAdministration.add(mntmFaculties);
		
		return mnAdministration;
	}

	private JMenu CreateJMenuInputData() {
		JMenu mnInputData = new JMenu("InputData");

		// Input data menu items
		JMenuItem mntmInstructors = new JMenuItem(INSTRUCTORS_CARD);
		JMenuItem mntmRooms = new JMenuItem(ROOMS_CARD);
		JMenuItem mntmInstructorMeetings = new JMenuItem(INSTRUCTOR_MEETINGS_CARD);
		JMenuItem mntmStudentgroups = new JMenuItem(STUDENT_GROUPS_CARD);
		JMenuItem mntmOfferings = new JMenuItem("Offerings");

		// Add action listeners
		mntmInstructors.addActionListener(this);
		mntmRooms.addActionListener(this);
		mntmInstructorMeetings.addActionListener(this);
		mntmStudentgroups.addActionListener(this);
		
		// Add menu items
		mnInputData.add(mntmInstructors);
		mnInputData.add(mntmRooms);
		mnInputData.add(mntmInstructorMeetings);
		mnInputData.add(mntmStudentgroups);
		mnInputData.add(mntmOfferings);

		return mnInputData;
	}

	private JMenu CreateJMenuOther() {
		JMenu mnOther = new JMenu("Other");
		// Other menu items
		JMenuItem mntmBuildings = new JMenuItem(BUILDING_CARD);
		JMenuItem mntmRoomtypes = new JMenuItem(ROOM_TYPE_CARD);
		JMenuItem menuTimePreferences = new JMenuItem(TIME_PREFERENCES_CARD);
		
		// Add action listeners
		mntmBuildings.addActionListener(this);
		mntmRoomtypes.addActionListener(this);
		menuTimePreferences.addActionListener(this);
		
		// Add menu items
		mnOther.add(mntmBuildings);
		mnOther.add(mntmRoomtypes);
		mnOther.add(new JSeparator());
		mnOther.add(menuTimePreferences);
		
		return mnOther;
	}

	private JMenu CreateJMenuHelp() {
		JMenu mnHelp = new JMenu("Help");
		// Help menu items
		JMenuItem mntmAbout = new JMenuItem("About");
		JMenuItem mntmHelp = new JMenuItem("Help");
		// Add menu items
		mnHelp.add(mntmAbout);
		mnHelp.add(mntmHelp);
		return mnHelp;
	}

	private JMenu CreateJMenuPreferences() {
		JMenu mnPreferences = new JMenu("Preferences");
		// Preferences menu items
		JMenuItem mntmChangeRole = new JMenuItem("Change Role");
		JMenuItem mntmSettings = new JMenuItem("Settings");
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		// Add menu items
		mnPreferences.add(mntmChangeRole);
		mnPreferences.add(mntmSettings);
		mnPreferences.add(mntmChangePassword);
		return mnPreferences;
	}

	public void actionPerformed(ActionEvent e) {
		// ...Get information from the action event...
		// ...Display it in the text area...
		String action = e.getActionCommand();
		cardLayout.show(cardsPanel, action);
	}

	public void AddComponentToPane(Container pane) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(CreateHeaderPanel());
		mainPanel.add(new JSeparator());

		// Create the panel that contains the "cards".
		cardsPanel = new JPanel(new CardLayout());

		for (String cardName : cards.keySet()) {
			cardsPanel.add(cards.get(cardName), cardName);
		}
		cardLayout = (CardLayout) cardsPanel.getLayout();
		cardLayout.show(cardsPanel, STUDENT_GROUPS_CARD);

		mainPanel.add(cardsPanel, BorderLayout.CENTER);

		pane.add(mainPanel);
	}

	private JPanel CreateHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
		headerPanel.add(Box.createHorizontalGlue());
		headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		// Create header sections
		JPanel hUserSection = CreateHeaderSection("User Name&Type:", loggedUser.toString(), "Show all users");
		JPanel hViewedFaculty = CreateHeaderSection("Faculty:", viewedFaculty.toString(), "Change faculty");
		JPanel hViewedAcademicYear = CreateHeaderSection("Academic year:", viewedAcadYear.toString(), "Change year");
		JPanel hViewedAcademicSession = CreateHeaderSection("Academic session:", viewedAcadSession.toString(), "Change session");

		// Add the different section to the header panel
		headerPanel.add(hUserSection);
		headerPanel.add(hViewedFaculty);
		headerPanel.add(hViewedAcademicYear);
		headerPanel.add(hViewedAcademicSession);

		return headerPanel;
	}

	private JPanel CreateHeaderSection(String jLabelText, String jButtonText, String jButtonToolTipText) {
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
		jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		headerSection.add(jLabel);
		headerSection.add(jButton);

		return headerSection;
	}
}
