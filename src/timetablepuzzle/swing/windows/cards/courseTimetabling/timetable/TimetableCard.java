package timetablepuzzle.swing.windows.cards.courseTimetabling.timetable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import timetablepuzzle.usecases.solution.TimeslotPattern;

public class TimetableCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

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

	/********************
	 * Methods that model the class behavior
	 *********************/
	public JPanel CreateTimetableCardPanel() {
		this.setBackground(backGroundColor);
		this.setLayout(new BorderLayout());

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

		// Create a list with unassigned classes
		List<Class> uClasses;
		if (this.acceptedSolution != null) {
			uClasses = this.acceptedSolution.GetUnassignedClasses();
		} else {
			uClasses = new ArrayList<Class>();
		}
		JComponent uScrollPane = CreateScrollableListOfClasses(uClasses, backGroundColor,
				" Unassigned classes: " + uClasses.size() + " ");

		// Create a list with assigned classes
		List<Class> aClasses;
		if (this.acceptedSolution != null) {
			aClasses = this.acceptedSolution.GetAssignedClasses();
		} else {
			aClasses = new ArrayList<Class>();
		}
		JComponent aScrollPane = CreateScrollableListOfClasses(aClasses, backGroundColor,
				" Assigned classes: " + aClasses.size() + " ");

		// Create a panel for the solver controls
		JPanel solverControls = new JPanel();
		solverControls.setLayout(new BoxLayout(solverControls, BoxLayout.X_AXIS));
		solverControls.setBackground(backGroundColor);
		solverControls.setBorder(CreateEmptyTitleBorder("Solver controls"));
		solverControls.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		// Text field for the number of steps
		JTextField jtfNrOfSteps = new JTextField();
		jtfNrOfSteps.setText("Steps");
		jtfNrOfSteps.setForeground(Color.GRAY);

		// Solver buttons
		JButton jbDoSteps = new JButton("Step");
		JButton jbRun = new JButton("Run");

		// Add controls to panel
		solverControls.add(jtfNrOfSteps);
		solverControls.add(jbDoSteps);
		solverControls.add(jbRun);

		// Add components to the east panel
		eastPanel.add(solverControls);
		eastPanel.add(uScrollPane);
		eastPanel.add(aScrollPane);

		return eastPanel;
	}

	private JComponent CreateScrollableListOfClasses(Collection<Class> classes, Color bgColor, String borderText) {
		DefaultListModel<String> classListModel = new DefaultListModel<String>();
		for (Class oneClass : classes) {
			if (oneClass != null) {
				String className = oneClass.getOffering().getName();
				int nrOfRemovals = acceptedSolution.GetNrOfRemovals(oneClass.getId());
				classListModel.addElement(className + "(" + nrOfRemovals + ")");
			}
		}
		JList<String> jListClasses = new JList<String>(classListModel);
		jListClasses.setVisibleRowCount(10);
		jListClasses.setFixedCellWidth(15);
		JScrollPane scrollPane = new JScrollPane(jListClasses);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel wrapperPanel = new JPanel(new BorderLayout());
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
