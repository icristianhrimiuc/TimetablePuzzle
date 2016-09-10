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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.AcademicSessionJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.FacultyJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.UserJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.AcademicSessionDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.FacultyDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.UserDAO;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.administration.AcademicSession;
import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.Faculty;
import timetablepuzzle.entities.administration.User;
import timetablepuzzle.entities.administration.User.UserType;
import timetablepuzzle.entities.inputdata.StudentGroup;
import timetablepuzzle.swing.windows.cards.TextCard;
import timetablepuzzle.swing.windows.cards.administration.academicSessions.AcademicSessionsCard;
import timetablepuzzle.swing.windows.cards.administration.academicYears.AcademicYearsCard;
import timetablepuzzle.swing.windows.cards.administration.courseOfferings.CourseOfferingsCard;
import timetablepuzzle.swing.windows.cards.administration.curriculas.CurriculasCard;
import timetablepuzzle.swing.windows.cards.administration.departments.DepartmentsCard;
import timetablepuzzle.swing.windows.cards.administration.faculties.FacultiesCard;
import timetablepuzzle.swing.windows.cards.administration.subjectAreas.SubjectAreasCard;
import timetablepuzzle.swing.windows.cards.administration.users.UsersCard;
import timetablepuzzle.swing.windows.cards.administration.yearsOfStudy.YearsOfStudyCard;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.swing.windows.cards.courseTimetabling.classes.ClassesCard;
import timetablepuzzle.swing.windows.cards.courseTimetabling.solutions.SolutionsCard;
import timetablepuzzle.swing.windows.cards.courseTimetabling.timetable.TimetableCard;
import timetablepuzzle.swing.windows.cards.inputdata.instructorMeetings.InstructorMeetingsCard;
import timetablepuzzle.swing.windows.cards.inputdata.instructors.InstructorsCard;
import timetablepuzzle.swing.windows.cards.inputdata.offerings.OfferingsCard;
import timetablepuzzle.swing.windows.cards.inputdata.rooms.RoomsCard;
import timetablepuzzle.swing.windows.cards.inputdata.studentGroups.StudentGroupsCard;
import timetablepuzzle.swing.windows.cards.other.buildings.BuildingsCard;
import timetablepuzzle.swing.windows.cards.other.roomTypes.RoomTypesCard;
import timetablepuzzle.swing.windows.cards.other.timePreferences.TimePreferencesCard;
import timetablepuzzle.usecases.solution.SolutionCreator;

public class MainWindow implements ActionListener {
	/***************** Static properties ****************/
	final static String HOME_CARD = "Home";

	// Course Timetabling menu
	final static String TIMETABLE_CARD = "Timetable";
	final static String ASSIGNED_CLASSES_CARD = "Assigned Classses";
	final static String UNASSIGNED_CLASSES_CARD = "Unassigned Classses";
	final static String SOLUTIONS_CARD = "Saved TimeTables";

	// Administration menu
	final static String ACADEMIC_SESSIONS_CARD = "Academic Sessions";
	final static String ACADEMIC_YEARS_CARD = "Academic Years";
	final static String COURSE_OFFERINDS_CARD = "Course Offerings";
	final static String CURRICULAS_CARD = "Curriculas";
	final static String SUBJECT_AREAS_CARD = "Subject Areas";
	final static String YEARS_OF_STUDY_CARD = "Years of Study";
	final static String DEPARTMENTS_CARD = "Departments";
	final static String FACULTIES_CARD = "Faculties";

	// Input data menu
	final static String INSTRUCTORS_CARD = "Instructors";
	final static String ROOMS_CARD = "Rooms";
	final static String STUDENT_GROUPS_CARD = "Student Groups";
	final static String OFFERINGS_CARD = "Offerings";
	final static String INSTRUCTOR_MEETINGS_CARD = "Instructor Meetings";

	// Other menu
	final static String BUILDING_CARD = "Buildings";
	final static String ROOM_TYPE_CARD = "Room Types";
	final static String TIME_PREFERENCES_CARD = "Time Preferences";

	// Help menu
	final static String ABOUT_CARD = "About";

	// Header section
	final static String USERS_CARD = "Users";

	private static final FacultyDAO facultyDAO = new FacultyJPADAOService();
	private static final UserDAO userDAO = new UserJPADAOService();
	private static final AcademicSessionDAO academicSessionDAO = new AcademicSessionJPADAOService();

	/****************** Regular properties *************/
	// Main window fields
	private LoginDialog loginDialog;
	private JFrame frame;
	// User required information
	private User loggedUser;
	private AcademicYear viewedAcademicYear;
	private AcademicSession viewedAcademicSession;
	private Faculty viewedFaculty;
	private Solution acceptedSolution;
	private Color bgColor;
	// ComboBoxes
	private CustomComboBoxModel<Faculty> comboBoxViewedFacultyModel;
	private JComboBox<Faculty> comboBoxViewedFaculty;
	private CustomComboBoxModel<AcademicYear> comboBoxViewedAcademicYearModel;
	private JComboBox<AcademicYear> comboBoxViewedAcademicYear;
	private CustomComboBoxModel<AcademicSession> comboBoxViewedAcademicSessionModel;
	private JComboBox<AcademicSession> comboBoxViewedAcademicSession;
	// A collection of easy retrievable cards
	private HashMap<String, JPanel> cards;
	private JPanel cardsPanel;
	private CardLayout cardLayout;

	/**
	 * Default constructor. Creates the application
	 */
	public MainWindow(Color bgColor) {
		Login(bgColor);
	}

	private void Login(Color bgColor) {
		// Initialize the main frame
		this.frame = new JFrame();
		this.frame.setSize(1050, 750);
		this.frame.setMinimumSize(new Dimension(1050, 750));
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("TimetablePuzzle - University Timetabling Application");
		this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resources\\icon.png"));
		this.frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		// Window color
		this.bgColor = bgColor;

		// Initialize the login dialog and display it
		this.loginDialog = new LoginDialog(frame, true);
		this.loggedUser = loginDialog.execute();

		if (loginDialog.isValidUser()) {
			// The login operation was successful. Set the user required
			// information
			this.viewedFaculty = loggedUser.getLastViewedFaculty();
			this.viewedAcademicYear = loggedUser.getLastViewedAcademicYear();
			this.viewedAcademicSession = loggedUser.getLastViewedAcademicSession();
			if (this.viewedAcademicSession != null) {
				this.acceptedSolution = this.viewedAcademicSession.getAcceptedSolution();
			}
			this.frame.setJMenuBar(CreateMenuBar(this.loggedUser.getUserType()));
			FillComboBoxes();

			// Create all the cards
			this.cards = new HashMap<String, JPanel>();
			this.cards.put(HOME_CARD,
					new TextCard("src\\resources\\homeBackground.png", "src\\resources\\homeText.txt"));
			// Course Timetabling menu
			this.cards.put(TIMETABLE_CARD, new TimetableCard(bgColor, this.viewedFaculty, this.viewedAcademicYear,
					this.viewedAcademicSession));
			this.cards.put(ASSIGNED_CLASSES_CARD, new ClassesCard(this.bgColor, this.acceptedSolution, true));
			this.cards.put(UNASSIGNED_CLASSES_CARD, new ClassesCard(this.bgColor, this.acceptedSolution, false));
			this.cards.put(SOLUTIONS_CARD, new SolutionsCard(bgColor));
			// Administration menu
			this.cards.put(ACADEMIC_SESSIONS_CARD, new AcademicSessionsCard(this.bgColor));
			this.cards.put(ACADEMIC_YEARS_CARD, new AcademicYearsCard(this.bgColor));
			this.cards.put(COURSE_OFFERINDS_CARD, new CourseOfferingsCard(this.bgColor));
			this.cards.put(CURRICULAS_CARD, new CurriculasCard(this.bgColor));
			this.cards.put(SUBJECT_AREAS_CARD, new SubjectAreasCard(this.bgColor));
			this.cards.put(YEARS_OF_STUDY_CARD, new YearsOfStudyCard(this.bgColor));
			this.cards.put(DEPARTMENTS_CARD, new DepartmentsCard(this.bgColor));
			this.cards.put(FACULTIES_CARD, new FacultiesCard(this.bgColor));
			// Input data menu
			this.cards.put(INSTRUCTORS_CARD, new InstructorsCard(this.bgColor));
			this.cards.put(ROOMS_CARD, new RoomsCard(this.bgColor));
			this.cards.put(STUDENT_GROUPS_CARD, new StudentGroupsCard(this.bgColor));
			this.cards.put(OFFERINGS_CARD, new OfferingsCard(this.bgColor));
			this.cards.put(INSTRUCTOR_MEETINGS_CARD, new InstructorMeetingsCard(this.bgColor));
			// Other menu
			this.cards.put(BUILDING_CARD, new BuildingsCard(this.bgColor));
			this.cards.put(ROOM_TYPE_CARD, new RoomTypesCard(this.bgColor));
			this.cards.put(TIME_PREFERENCES_CARD, new TimePreferencesCard(this.bgColor));
			// Help menu
			this.cards.put(ABOUT_CARD,
					new TextCard("src\\resources\\homeBackground.png", "src\\resources\\aboutText.txt"));

			// Header section
			this.cards.put(USERS_CARD, new UsersCard(bgColor));

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
		return this.bgColor;
	}

	/****************
	 * Methods that model the class behavior
	 ********************/
	private void FillComboBoxes() {
		// Viewed Faculty combo box
		this.comboBoxViewedFacultyModel = new CustomComboBoxModel<Faculty>();
		RefreshComboBoxViewedFaculty();
		this.comboBoxViewedFaculty = new JComboBox<Faculty>(this.comboBoxViewedFacultyModel);
		this.comboBoxViewedFaculty.setFocusable(false);
		this.comboBoxViewedFaculty.setUI(new BasicComboBoxUI());
		((JLabel) this.comboBoxViewedFaculty.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		this.comboBoxViewedFaculty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewedFaculty = (Faculty) comboBoxViewedFaculty.getSelectedItem();
				viewedAcademicYear = null;
				viewedAcademicSession = null;
				RefreshComboBoxViewedFaculty();
				RefreshComboBoxViewedAcademicYear();
				RefreshComboBoxViewedAcademicSession();
				RefreshTimeTableCard();
			}
		});

		// Viewed Academic Year combo box
		this.comboBoxViewedAcademicYearModel = new CustomComboBoxModel<AcademicYear>();
		RefreshComboBoxViewedAcademicYear();
		this.comboBoxViewedAcademicYear = new JComboBox<AcademicYear>(this.comboBoxViewedAcademicYearModel);
		this.comboBoxViewedAcademicYear.setFocusable(false);
		this.comboBoxViewedAcademicYear.setUI(new BasicComboBoxUI());
		((JLabel) this.comboBoxViewedAcademicYear.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		this.comboBoxViewedAcademicYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewedAcademicYear = (AcademicYear) comboBoxViewedAcademicYear.getSelectedItem();
				viewedAcademicSession = null;
				RefreshComboBoxViewedAcademicYear();
				RefreshComboBoxViewedAcademicSession();
			}
		});

		// Viewed Academic Session combo box
		this.comboBoxViewedAcademicSessionModel = new CustomComboBoxModel<AcademicSession>();
		RefreshComboBoxViewedAcademicSession();
		this.comboBoxViewedAcademicSession = new JComboBox<AcademicSession>(this.comboBoxViewedAcademicSessionModel);
		this.comboBoxViewedAcademicSession.setFocusable(false);
		this.comboBoxViewedAcademicSession.setUI(new BasicComboBoxUI());
		((JLabel) this.comboBoxViewedAcademicSession.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		this.comboBoxViewedAcademicSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewedAcademicSession = (AcademicSession) comboBoxViewedAcademicSession.getSelectedItem();
				RefreshComboBoxViewedAcademicSession();
				RefreshSolution();
			}
		});
	}

	private void RefreshComboBoxViewedFaculty() {
		this.comboBoxViewedFacultyModel.setData(facultyDAO.GetAll());
		this.comboBoxViewedFacultyModel.setSelectedItem(this.viewedFaculty);
		this.frame.repaint();
	}

	private void RefreshComboBoxViewedAcademicYear() {
		if (this.viewedFaculty != null) {
			this.comboBoxViewedAcademicYearModel.setData(this.viewedFaculty.getAcademicYears());
		} else {
			this.comboBoxViewedAcademicYearModel.setData(new ArrayList<AcademicYear>());
			this.viewedAcademicYear = null;
		}
		this.comboBoxViewedAcademicYearModel.setSelectedItem(this.viewedAcademicYear);
		this.frame.repaint();
	}

	private void RefreshComboBoxViewedAcademicSession() {
		if (this.viewedAcademicYear != null) {
			this.comboBoxViewedAcademicSessionModel.setData(this.viewedAcademicYear.getAcademicSessions());
		} else {

			this.comboBoxViewedAcademicSessionModel.setData(new ArrayList<AcademicSession>());
			this.viewedAcademicSession = null;
		}
		this.comboBoxViewedAcademicSessionModel.setSelectedItem(this.viewedAcademicSession);
		this.frame.repaint();
	}

	private void RefreshSolution() {
		if (this.viewedAcademicSession != null) {
			this.acceptedSolution = viewedAcademicSession.getAcceptedSolution();
			if (this.acceptedSolution == null) {
				int dialogResult = JOptionPane.showConfirmDialog(frame,
						"The selected Academic Session has no timetable!\n"
								+ "Would you like to Generate Classes and create a new timetable?",
						"No Timetable", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					Term term = this.viewedAcademicSession.getTerm();
					StudentGroup parentStudentGroup = this.viewedAcademicYear.getParentStudentGroup();
					String solutionName = String.format("Generated solution. AcademicYear:%s. AcademicSession:%s",
							this.viewedAcademicYear.getName(), this.viewedAcademicSession.getName());
					List<Class> classes = this.viewedFaculty.getClasses(term, parentStudentGroup);
					SolutionCreator solutionCreator = new SolutionCreator(solutionName, classes);
					this.acceptedSolution  = solutionCreator.CreateNewSolution();
					this.viewedAcademicSession.setAcceptedSolution(this.acceptedSolution);
					academicSessionDAO.merge(this.viewedAcademicSession);
				}
			}
		}
		RefreshTimeTableCard();
		((ClassesCard) this.cards.get(ASSIGNED_CLASSES_CARD)).setNewSolution(this.acceptedSolution);
		((ClassesCard) this.cards.get(UNASSIGNED_CLASSES_CARD)).setNewSolution(this.acceptedSolution);
	}

	private void RefreshTimeTableCard() {
		this.cardLayout.show(this.cardsPanel, HOME_CARD);
		this.cards.put(TIMETABLE_CARD, new TimetableCard(this.bgColor, this.viewedFaculty, this.viewedAcademicYear,
				this.viewedAcademicSession));
	}

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

		// Log out menu
		JMenu mnLogOut = new JMenu("Log Out");
		mnLogOut.addMouseListener(new MouseListener() {
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
				loggedUser.setLastViewedFaculty(viewedFaculty);
				loggedUser.setLastViewedAcademicYear(viewedAcademicYear);
				loggedUser.setLastViewedAcademicSession(viewedAcademicSession);
				userDAO.merge(loggedUser);
				frame.setVisible(false);
				Login(bgColor);
			}
		});
		jMenuBar.add(mnLogOut);

		// Return the created menu bar
		return jMenuBar;
	}

	private JMenu CreateJMenuCourseTimeTabling() {
		JMenu mnCourseTimetabling = new JMenu("Course Timetabling");

		// Course timetabling menu items
		JMenuItem mntmTimetable = new JMenuItem(TIMETABLE_CARD);
		JMenuItem mntmAssignedClasses = new JMenuItem(ASSIGNED_CLASSES_CARD);
		JMenuItem mntmUnassignedClasses = new JMenuItem(UNASSIGNED_CLASSES_CARD);
		JMenuItem mntmSavedTimetables = new JMenuItem(SOLUTIONS_CARD);

		// Add action listeners
		mntmTimetable.addActionListener(this);
		mntmAssignedClasses.addActionListener(this);
		mntmUnassignedClasses.addActionListener(this);
		mntmSavedTimetables.addActionListener(this);

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
		JMenuItem mntmCourseOfferings = new JMenuItem(COURSE_OFFERINDS_CARD);
		JMenuItem mntmCurriculas = new JMenuItem(CURRICULAS_CARD);
		JMenuItem mntmSubjectAreas = new JMenuItem(SUBJECT_AREAS_CARD);
		JMenuItem mntmYearsOfStudy = new JMenuItem(YEARS_OF_STUDY_CARD);
		JMenuItem mntmDepartments = new JMenuItem(DEPARTMENTS_CARD);
		JMenuItem mntmFaculties = new JMenuItem(FACULTIES_CARD);

		// Add action listeners
		mntmAcademicSessions.addActionListener(this);
		mntmAcademicYears.addActionListener(this);
		mntmCourseOfferings.addActionListener(this);
		mntmCurriculas.addActionListener(this);
		mntmSubjectAreas.addActionListener(this);
		mntmYearsOfStudy.addActionListener(this);
		mntmDepartments.addActionListener(this);
		mntmFaculties.addActionListener(this);

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
		JMenuItem mntmStudentgroups = new JMenuItem(STUDENT_GROUPS_CARD);
		JMenuItem mntmOfferings = new JMenuItem(OFFERINGS_CARD);
		JMenuItem mntmInstructorMeetings = new JMenuItem(INSTRUCTOR_MEETINGS_CARD);

		// Add action listeners
		mntmInstructors.addActionListener(this);
		mntmRooms.addActionListener(this);
		mntmStudentgroups.addActionListener(this);
		mntmOfferings.addActionListener(this);
		mntmInstructorMeetings.addActionListener(this);

		// Add menu items
		mnInputData.add(mntmInstructors);
		mnInputData.add(mntmRooms);
		mnInputData.add(mntmStudentgroups);
		mnInputData.add(mntmOfferings);
		mnInputData.add(new JSeparator());
		mnInputData.add(mntmInstructorMeetings);

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
		JMenuItem mntmAbout = new JMenuItem(ABOUT_CARD);
		JMenuItem mntmHelp = new JMenuItem("Help");

		// Add action listeners
		mntmAbout.addActionListener(this);

		// Add menu items
		mnHelp.add(mntmAbout);
		mnHelp.add(mntmHelp);
		return mnHelp;
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
		cardLayout.show(cardsPanel, HOME_CARD);

		mainPanel.add(cardsPanel, BorderLayout.CENTER);

		pane.add(mainPanel);
	}

	private JPanel CreateHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
		headerPanel.add(Box.createHorizontalGlue());
		headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		// Create header sections
		JPanel hUserSection = CreateButtonUserHeaderSection("User Name&Type:", loggedUser.toString(), "Show all users");
		JPanel hViewedFaculty = CreateFacultyHeaderSection("Faculty:", viewedFaculty, "Change faculty");
		JPanel hViewedAcademicYear = CreateAcademicYearHeaderSection("Academic year:", viewedAcademicYear,
				"Change year");
		JPanel hViewedAcademicSession = CreateAcademicSessionHeaderSection("Academic session:", viewedAcademicSession,
				"Change session");

		// Add the different section to the header panel
		headerPanel.add(hUserSection);
		headerPanel.add(hViewedFaculty);
		headerPanel.add(hViewedAcademicYear);
		headerPanel.add(hViewedAcademicSession);

		return headerPanel;
	}

	private JPanel CreateButtonUserHeaderSection(String jLabelText, String jButtonText, String jButtonToolTipText) {
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
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardsPanel, USERS_CARD);
			}
		});

		headerSection.add(jLabel);
		headerSection.add(jButton);

		return headerSection;
	}

	private JPanel CreateFacultyHeaderSection(String jLabelText, Faculty viewedFaculty, String toolTipText) {
		JPanel headerSection = new JPanel();
		headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
		// Create a button to appear like a label
		JLabel jLabel = new JLabel(jLabelText);
		jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.comboBoxViewedFaculty.setForeground(Color.gray);
		this.comboBoxViewedFaculty.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.comboBoxViewedFaculty.setToolTipText(toolTipText);
		this.comboBoxViewedFaculty.setOpaque(false);
		this.comboBoxViewedFaculty.setCursor(new Cursor(Cursor.HAND_CURSOR));

		headerSection.add(jLabel);
		headerSection.add(this.comboBoxViewedFaculty);

		return headerSection;
	}

	private JPanel CreateAcademicYearHeaderSection(String jLabelText, AcademicYear viewedAcademicYear,
			String toolTipText) {
		JPanel headerSection = new JPanel();
		headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
		// Create a button to appear like a label
		JLabel jLabel = new JLabel(jLabelText);
		jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.comboBoxViewedAcademicYear.setForeground(Color.gray);
		this.comboBoxViewedAcademicYear.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.comboBoxViewedAcademicYear.setToolTipText(toolTipText);
		this.comboBoxViewedAcademicYear.setOpaque(false);
		this.comboBoxViewedAcademicYear.setCursor(new Cursor(Cursor.HAND_CURSOR));

		headerSection.add(jLabel);
		headerSection.add(this.comboBoxViewedAcademicYear);

		return headerSection;
	}

	private JPanel CreateAcademicSessionHeaderSection(String jLabelText, AcademicSession viewedAcademicSession,
			String toolTipText) {
		JPanel headerSection = new JPanel();
		headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
		// Create a button to appear like a label
		JLabel jLabel = new JLabel(jLabelText);
		jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.comboBoxViewedAcademicSession.setForeground(Color.gray);
		this.comboBoxViewedAcademicSession.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.comboBoxViewedAcademicSession.setToolTipText(toolTipText);
		this.comboBoxViewedAcademicSession.setOpaque(false);
		this.comboBoxViewedAcademicSession.setCursor(new Cursor(Cursor.HAND_CURSOR));

		headerSection.add(jLabel);
		headerSection.add(this.comboBoxViewedAcademicSession);

		return headerSection;
	}
}
