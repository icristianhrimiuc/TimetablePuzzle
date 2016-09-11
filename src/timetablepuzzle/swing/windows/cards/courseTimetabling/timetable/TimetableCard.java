package timetablepuzzle.swing.windows.cards.courseTimetabling.timetable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.sun.xml.internal.ws.util.StringUtils;

import timetablepuzzle.eclipselink.DAO.JPA.services.SolutionJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.SolutionDAO;
import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.administration.AcademicSession;
import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.Department;
import timetablepuzzle.entities.administration.Faculty;
import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.inputdata.StudentGroup;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.entities.other.TimePreferences.DayOfTheWeek;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.usecases.solution.SolutionAssignManager;
import timetablepuzzle.usecases.solution.SolutionUnassignManager;
import timetablepuzzle.usecases.solution.TimeslotPattern;

public class TimetableCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final SolutionDAO solutionDAO = new SolutionJPADAOService();

	private Color backGroundColor;
	private Faculty viewedFaculty;
	private AcademicYear viewedAcademicYear;
	private AcademicSession viewedAcademicSession;
	private List<Department> departments;
	private StudentGroup parentStudentGroup;
	private Solution acceptedSolution;
	// A collection of easy retrievable cards
	private HashMap<String, JPanel> cards;
	private JPanel cardsPanel;
	private CardLayout cardLayout;
	// Fields that control what card to display
	private ButtonGroup departmentsButtonGroup;
	private ButtonGroup yearsOfStudyButtonGroup;
	private ButtonGroup daysOfTheWeekButtonGroup;
	// Controls
	private SolutionAssignManager assignManager;
	private SolutionUnassignManager unassignManager;
	private DefaultListModel<Class> assignedClassesListModel;
	private JList<Class> assigneClassesList;
	private DefaultListModel<Class> unassignedClassesListModel;
	private JList<Class> unassigneClassesList;
	private CustomComboBoxModel<DayOfTheWeek> comboBoxDayOfTheWeekModel;
	private JComboBox<DayOfTheWeek> comboboxDayOfTheWeek;
	private CustomComboBoxModel<String> comboBoxTimeOfDayModel;
	private JComboBox<String> comboboxTimeOfDay;
	private JLabel notificationLabel;

	public TimetableCard(Color backGroundColor, Faculty viewedFaculty, AcademicYear viewedAcademicYear,
			AcademicSession viewedAcademicSession) {
		this.backGroundColor = backGroundColor;
		this.setBackground(this.backGroundColor);

		setData(viewedFaculty, viewedAcademicYear, viewedAcademicSession);
	}

	/***********************
	 * Getters and setters
	 ***************************************/
	public void setData(Faculty viewedFaculty, AcademicYear viewedAcademicYear, AcademicSession viewedAcademicSession) {
		setViewedFaculty(viewedFaculty);
		setViewedAcademicYear(viewedAcademicYear);
		setViewedAcademicSession(viewedAcademicSession);
		CreateTimetableCardPanel();

		// Managers
		this.assignManager = new SolutionAssignManager(this.acceptedSolution);
		this.unassignManager = new SolutionUnassignManager(this.acceptedSolution);

		this.repaint();
	}

	private void setViewedFaculty(Faculty viewedFaculty) {
		this.viewedFaculty = viewedFaculty;
		if (this.viewedFaculty == null) {
			this.departments = new ArrayList<Department>();
		} else {
			this.departments = this.viewedFaculty.getDepartments();
		}
		this.parentStudentGroup = new StudentGroup();
		this.acceptedSolution = new Solution();
	}

	private void setViewedAcademicYear(AcademicYear viewedAcademicYear) {
		this.viewedAcademicYear = viewedAcademicYear;
		if (this.viewedAcademicYear == null) {
			this.parentStudentGroup = new StudentGroup();
		} else {
			this.parentStudentGroup = this.viewedAcademicYear.getParentStudentGroup();
		}
		this.acceptedSolution = new Solution();
	}

	private void setViewedAcademicSession(AcademicSession viewedAcademicSession) {
		this.viewedAcademicSession = viewedAcademicSession;
		if (this.viewedAcademicSession == null) {
			this.acceptedSolution = new Solution();
		} else {
			this.acceptedSolution = this.viewedAcademicSession.getAcceptedSolution();
		}
	}
	
	private void RefreshSolution(){
		Solution solution = solutionDAO.findById(this.acceptedSolution.getId());
		if(solution != null){
			this.acceptedSolution = solution;
		}
	}

	/********************
	 * Methods that model the class behavior
	 *********************/
	public JPanel CreateTimetableCardPanel() {
		this.setBackground(backGroundColor);
		this.setLayout(new BorderLayout());

		// List of assigned classes
		this.unassignedClassesListModel = new DefaultListModel<Class>();
		RefreshUnassignedClassesList();
		this.unassigneClassesList = new JList<Class>(this.unassignedClassesListModel);
		this.unassigneClassesList.setVisibleRowCount(15);
		this.unassigneClassesList.setFixedCellWidth(15);

		// Day of the week combo box
		this.comboBoxDayOfTheWeekModel = new CustomComboBoxModel<DayOfTheWeek>();
		RefreshComboBoxDayOfTheWeek();
		this.comboboxDayOfTheWeek = new JComboBox<DayOfTheWeek>(this.comboBoxDayOfTheWeekModel);

		// Time of day combo box
		this.comboBoxTimeOfDayModel = new CustomComboBoxModel<String>();
		RefreshComboBoxTimeOfDay();
		this.comboboxTimeOfDay = new JComboBox<String>(this.comboBoxTimeOfDayModel);

		// List of assigned classes
		this.assignedClassesListModel = new DefaultListModel<Class>();
		RefreshAssignedClassesList();
		this.assigneClassesList = new JList<Class>(this.assignedClassesListModel);
		this.assigneClassesList.setVisibleRowCount(15);
		this.assigneClassesList.setFixedCellWidth(15);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		// Create the west panel.It will contain a radio button for each
		// department in the faculty
		JPanel westPanel = CreateWestPanel();
		// Create the south panel. It will contain a radio button for each
		// academic year
		JPanel southPanel = CreateSouthPanel();
		// Create the north panel. It will contain a radio button for each day
		// of the week
		JPanel northPanel = CreateNorthPanel();
		// Create the east panel. It will contain 2 lists
		JPanel eastPanel = CreateEastPanel();
		// Create the center panel. It will hold off the tables as cards
		JPanel centerPanel = CreateCenterPanel();
		ShowCardByName();

		// Add components to panel
		this.add(westPanel, BorderLayout.WEST);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);

		return this;
	}

	private void RefreshUnassignedClassesList() {
		// Create a list with unassigned classes
		List<Class> unassignedClasses;
		if (this.acceptedSolution != null) {
			unassignedClasses = this.acceptedSolution.GetUnassignedClasses();
		} else {
			unassignedClasses = new ArrayList<Class>();
		}
		this.unassignedClassesListModel.clear();
		for (Class oneClass : unassignedClasses) {
			if (oneClass != null) {
				this.unassignedClassesListModel.addElement(oneClass);
			}
		}
		this.repaint();
	}

	private void RefreshComboBoxDayOfTheWeek() {
		List<DayOfTheWeek> daysOfTheWeek = new ArrayList<DayOfTheWeek>();
		daysOfTheWeek.add(DayOfTheWeek.MONDAY);
		daysOfTheWeek.add(DayOfTheWeek.TUESDAY);
		daysOfTheWeek.add(DayOfTheWeek.WEDNESDAY);
		daysOfTheWeek.add(DayOfTheWeek.THURSDAY);
		daysOfTheWeek.add(DayOfTheWeek.FRIDAY);
		this.comboBoxDayOfTheWeekModel.setData(daysOfTheWeek);
		this.repaint();
	}

	private void RefreshComboBoxTimeOfDay() {
		List<String> timesOfDay = new ArrayList<String>();
		for (int i = 0; i < TimeslotPattern.NrOfTimeSlotsPerDay; i++) {
			timesOfDay.add(Integer.toString(i + 8));
		}
		this.comboBoxTimeOfDayModel.setData(timesOfDay);
		this.repaint();
	}

	private void RefreshAssignedClassesList() {
		// Create a list with unassigned classes
		List<Class> aClasses;
		if (this.acceptedSolution != null) {
			aClasses = this.acceptedSolution.GetAssignedClasses();
		} else {
			aClasses = new ArrayList<Class>();
		}
		this.assignedClassesListModel.clear();
		for (Class oneClass : aClasses) {
			if (oneClass != null) {
				this.assignedClassesListModel.addElement(oneClass);
			}
		}
		this.repaint();
	}

	private JPanel CreateWestPanel() {
		JPanel westPanel = new JPanel();
		westPanel.setBackground(this.backGroundColor);
		westPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Create the academic session radio buttons section
		this.departmentsButtonGroup = new ButtonGroup();
		boolean isAnyDepartmentSelelected = false;
		for (int i = 0; i < this.departments.size(); i++) {
			JRadioButton jRadioButton = new JRadioButton(StringUtils.capitalize(this.departments.get(i).getName()));
			if (!isAnyDepartmentSelelected) {
				jRadioButton.setSelected(true);
				isAnyDepartmentSelelected = true;
			}
			jRadioButton.setBackground(this.backGroundColor);
			jRadioButton.setHorizontalTextPosition(SwingConstants.CENTER);
			jRadioButton.setVerticalTextPosition(JRadioButton.TOP);
			jRadioButton.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						ShowCardByName();
					}
				}
			});

			departmentsButtonGroup.add(jRadioButton);
			c.gridy = i;
			westPanel.add(jRadioButton, c);
		}

		return westPanel;
	}

	private JPanel CreateSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(this.backGroundColor);
		southPanel.setLayout(new GridBagLayout());
		YearOfStudy.CollegeYear[] yearsOfStudy = YearOfStudy.CollegeYear.values();

		// Create the academic session radio buttons section
		this.yearsOfStudyButtonGroup = new ButtonGroup();
		boolean isAnyYearOfStudySelected = false;
		for (YearOfStudy.CollegeYear yearOfStudy : yearsOfStudy) {
			if (yearOfStudy != YearOfStudy.CollegeYear.UNASSIGNED) {
				JRadioButton jRadioButton = new JRadioButton(StringUtils.capitalize(yearOfStudy.name()));
				if (!isAnyYearOfStudySelected) {
					jRadioButton.setSelected(true);
					isAnyYearOfStudySelected = true;
				}
				jRadioButton.setBackground(backGroundColor);
				jRadioButton.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							ShowCardByName();
						}
					}
				});

				yearsOfStudyButtonGroup.add(jRadioButton);
				southPanel.add(jRadioButton);
			}
		}

		return southPanel;
	}

	private JPanel CreateNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setBackground(this.backGroundColor);
		northPanel.setLayout(new GridBagLayout());
		TimePreferences.DayOfTheWeek[] daysOfTheWeek = TimePreferences.DayOfTheWeek.values();

		// Create the academic session radio buttons section
		this.daysOfTheWeekButtonGroup = new ButtonGroup();
		boolean isAnyDayOfTheWeekSelected = false;
		for (TimePreferences.DayOfTheWeek dayOfTheWeek : daysOfTheWeek) {
			if (dayOfTheWeek != DayOfTheWeek.SATURDAY && dayOfTheWeek != DayOfTheWeek.SUNDAY) {
				JRadioButton jRadioButton = new JRadioButton(StringUtils.capitalize(dayOfTheWeek.name()));
				if (!isAnyDayOfTheWeekSelected) {
					jRadioButton.setSelected(true);
					isAnyDayOfTheWeekSelected = true;
				}
				jRadioButton.setBackground(this.backGroundColor);
				jRadioButton.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							ShowCardByName();
						}
					}
				});

				daysOfTheWeekButtonGroup.add(jRadioButton);
				northPanel.add(jRadioButton);
			}
		}

		return northPanel;
	}

	private JPanel CreateEastPanel() {
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setBackground(this.backGroundColor);

		JPanel uScrollPane = CreateScrollableListOfClassesPanel(this.unassigneClassesList, backGroundColor,
				" Unassigned classes: " + this.unassignedClassesListModel.size() + " ");
		uScrollPane.add(this.notificationLabel);
		uScrollPane.add(CreateAssignClassInSolutionPanel());

		JPanel aScrollPane = CreateScrollableListOfClassesPanel(this.assigneClassesList, backGroundColor,
				" Assigned classes: " + this.assignedClassesListModel.size() + " ");
		aScrollPane.add(CreateUnAssignClassInSolutionPanel());

		// Add components to the east panel
		eastPanel.add(CreateSolverControlsPanel());
		eastPanel.add(uScrollPane);
		eastPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		eastPanel.add(aScrollPane);

		return eastPanel;
	}

	private JPanel CreateScrollableListOfClassesPanel(JList<Class> listOfClasses, Color bgColor, String borderText) {
		JScrollPane scrollPane = new JScrollPane(listOfClasses);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel wrapperPanel = new JPanel();
		wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
		wrapperPanel.add(scrollPane);
		wrapperPanel.setBackground(bgColor);
		wrapperPanel.setBorder(CreateEmptyTitleBorder(borderText));

		return wrapperPanel;
	}

	private TitledBorder CreateEmptyTitleBorder(String titleText) {
		TitledBorder emptyTitleBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 20, 0, 5),
				titleText);
		emptyTitleBorder.setTitlePosition(TitledBorder.TOP);

		return emptyTitleBorder;
	}

	private JPanel CreateSolverControlsPanel() {
		JPanel solverControlsPanel = new JPanel();
		solverControlsPanel.setLayout(new BoxLayout(solverControlsPanel, BoxLayout.X_AXIS));
		solverControlsPanel.setBackground(backGroundColor);
		solverControlsPanel.setBorder(CreateEmptyTitleBorder("Solver controls"));
		solverControlsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		// Text field for the number of steps
		JTextField jtfNrOfSteps = new JTextField();
		jtfNrOfSteps.setText("Steps");
		jtfNrOfSteps.setForeground(Color.GRAY);

		// Solver buttons
		JButton jbDoSteps = new JButton("Step");
		JButton jbRun = new JButton("Run");

		// Add controls to panel
		solverControlsPanel.add(jtfNrOfSteps);
		solverControlsPanel.add(jbDoSteps);
		solverControlsPanel.add(jbRun);

		return solverControlsPanel;
	}

	private JPanel CreateAssignClassInSolutionPanel() {
		JButton assignButton = new JButton("Assign");
		assignButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AssignClassInSolution();
			}
		});

		JPanel assignClassInSolutionPanel = new JPanel();
		assignClassInSolutionPanel.setLayout(new BoxLayout(assignClassInSolutionPanel, BoxLayout.X_AXIS));
		assignClassInSolutionPanel.add(this.comboboxDayOfTheWeek);
		assignClassInSolutionPanel.add(this.comboboxTimeOfDay);
		assignClassInSolutionPanel.add(assignButton);

		return assignClassInSolutionPanel;
	}

	private void AssignClassInSolution() {
		DayOfTheWeek dayOfTheWeek = (DayOfTheWeek) this.comboboxDayOfTheWeek.getSelectedItem();
		String timeOfDay = (String) this.comboboxTimeOfDay.getSelectedItem();
		Class uClass = (Class) this.unassigneClassesList.getSelectedValue();
		if (dayOfTheWeek != null && timeOfDay != null && uClass != null) {
			int dayAndTimeSlot = (dayOfTheWeek.ordinal() * TimeslotPattern.NrOfTimeSlotsPerDay)
					+ (Integer.parseInt(timeOfDay) - 8);
			String status = this.assignManager.Assign(uClass, dayAndTimeSlot).toString();
			this.notificationLabel.setText(status);
			RefreshSolution();
			RefreshAssignedClassesList();
			RefreshUnassignedClassesList();
		}else{
			this.notificationLabel.setText("Select a class,a day and a time");
		}
	}

	private JPanel CreateUnAssignClassInSolutionPanel() {
		JPanel unassignClassInSolutionPanel = new JPanel();
		unassignClassInSolutionPanel.setLayout(new BoxLayout(unassignClassInSolutionPanel, BoxLayout.X_AXIS));

		JButton unassignClassButton = new JButton("Unassign Class");
		unassignClassButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UnassignClassInSolution();
			}
		});

		JButton setClassFixedButton = new JButton("Fix class");
		setClassFixedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SetClassFixed();
			}
		});
		
		unassignClassInSolutionPanel.add(unassignClassButton);
		unassignClassInSolutionPanel.add(setClassFixedButton);

		return unassignClassInSolutionPanel;
	}

	private void UnassignClassInSolution() {
		Class aClass = (Class) this.assigneClassesList.getSelectedValue();
		if (aClass != null) {
			this.unassignManager.Unassign(aClass);
			RefreshSolution();
			RefreshAssignedClassesList();
			RefreshUnassignedClassesList();
		}
	}

	private void SetClassFixed() {
		Class aClass = (Class) this.assigneClassesList.getSelectedValue();
		if (aClass != null) {
			acceptedSolution.SetClassFixed(aClass.getId(), true);
		}
	}

	private JPanel CreateCenterPanel() {
		CreateCardsPanel();

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout());
		centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		centerPanel.add(cardsPanel);

		return centerPanel;
	}

	private void CreateCardsPanel() {
		CreateAllCards();
		// Create the panel that contains the "cards".
		this.cardsPanel = new JPanel(new CardLayout());
		this.cardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		for (String cardName : this.cards.keySet()) {
			this.cardsPanel.add(this.cards.get(cardName), cardName);
		}
		this.cardLayout = (CardLayout) cardsPanel.getLayout();
	}

	private void CreateAllCards() {
		this.cards = new HashMap<String, JPanel>();

		List<StudentGroup> departmentGroups = this.parentStudentGroup.getComposingGroups();
		for (StudentGroup departmentGroup : departmentGroups) {
			List<StudentGroup> yearOfStudyGroups = departmentGroup.getComposingGroups();
			for (StudentGroup yearOfStudyGroup : yearOfStudyGroups) {
				for (int dayIndex = 0; dayIndex < TimeslotPattern.NrOfDays; dayIndex++) {
					String cardName = GetCardName(departmentGroup.getCode(), yearOfStudyGroup.getCode(), dayIndex);
					String cardHeaderText = GetCardHeaderText(departmentGroup, yearOfStudyGroup, dayIndex);
					JPanel tablePanel = CreateTablePanel(cardName, cardHeaderText, yearOfStudyGroup, dayIndex);
					this.cards.put(cardName, tablePanel);
				}
			}
		}
	}

	private String GetCardName(String departmentName, String yearOfStudy, int dayIndex) {
		return String.format("%s/%s/%d", departmentName, yearOfStudy, dayIndex);
	}

	private String GetCardHeaderText(StudentGroup departmentGroup, StudentGroup yearOfStudygroup, int dayIndex) {
		return String.format("Timetable for Dep: %s; Year: %s; Day: %s; ", departmentGroup.getCode(),
				yearOfStudygroup.getCode(), TimePreferences.DayOfTheWeek.values()[dayIndex].toString());
	}

	private JPanel CreateTablePanel(String cardName, String cardHeaderText, StudentGroup departmentGroup,
			int dayIndex) {
		TimetableTableModel tableModel = new TimetableTableModel(this.acceptedSolution, departmentGroup, dayIndex);
		JTable table = new JTable(tableModel);
		ConfigureTable(table);

		JScrollPane tableScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(cardHeaderText);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, BorderLayout.NORTH);
		panel.add(tableScrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createLineBorder(Color.RED));

		return panel;
	}

	private void ConfigureTable(JTable table) {
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, cellRenderer);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setTableHeader(null);
		table.setBorder(null);
		table.setRowHeight(43);
	}

	private void ShowCardByName() {
		String departmentName = getSelectedButtonText(this.departmentsButtonGroup);
		String yearOfStudy = getSelectedButtonText(this.yearsOfStudyButtonGroup);
		int dayIndex = DayOfTheWeek.valueOf(getSelectedButtonText(daysOfTheWeekButtonGroup)).ordinal();
		String nameOfTheCardToShow = GetCardName(departmentName, yearOfStudy, dayIndex);
		this.cardLayout.show(cardsPanel, nameOfTheCardToShow);
	}

	private String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}
}
