package timetablepuzzle.swing.windows.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.sun.xml.internal.ws.util.StringUtils;

import timetablepuzzle.eclipselink.DAO.JPA.services.SolutionJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.SolutionDAO;
import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.administration.AcademicSession;
import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.Department;
import timetablepuzzle.entities.administration.Faculty;
import timetablepuzzle.entities.administration.User;
import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.other.TimePreferences;

public class TimetableCard extends JPanel{
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final SolutionDAO solutionDAO = new SolutionJPADAOService();

	private Color backGroundColor;
	private Solution acceptedSolution;
	
	public TimetableCard(Color backGroundColor, int acceptedSolutionId)
	{
		this.backGroundColor = backGroundColor;
		this.acceptedSolution = solutionDAO.findById(acceptedSolutionId);
		
		this.setBackground(this.backGroundColor);
		this.CreateTimetableCardPanel();
	}
	
	/********************Methods that model the class behavior*********************/
	public JPanel CreateTimetableCardPanel()
    {
    	this.setBackground(backGroundColor);
    	this.setLayout(new BorderLayout());
        
        // Create the north panel. It will contain a radio button for each day of the week
        JPanel northPanel = CreateNorthPanel();        
        // Create the south panel. It will contain a radio button for each academic year
        JPanel southPanel = CreateSouthPanel();        
        // Create the west panel.It will contain a radio button for each department in the faculty
        JPanel westPanel = CreateWestPanel();        
        // Create the east panel. It will contain 2 lists
        JPanel eastPanel = CreateEastPanel();
        
        // Create the center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Add components to panel
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(centerPanel, BorderLayout.CENTER);
        
        return this;
    }

	private JPanel CreateNorthPanel() {
		JPanel northPanel = new JPanel();
        northPanel.setBackground(this.backGroundColor);
        northPanel.setLayout(new GridBagLayout());
        TimePreferences.DayOfTheWeek[] daysOfTheWeek = TimePreferences.DayOfTheWeek.values();
        
        // Create the academic session radio buttons section
        ButtonGroup daysOfTheWeekButtonGroup = new ButtonGroup();
        boolean isAnyDayOfTheWeekSelected = false;
        for(TimePreferences.DayOfTheWeek dayOfTheWeek : daysOfTheWeek)
        {
        	JRadioButton jRadioButton = new JRadioButton(StringUtils.capitalize(dayOfTheWeek.name()));
        	if(!isAnyDayOfTheWeekSelected)
        	{
        		jRadioButton.setSelected(true);
        		isAnyDayOfTheWeekSelected = true;
        	}
        	jRadioButton.setBackground(this.backGroundColor);
        	daysOfTheWeekButtonGroup.add(jRadioButton);
        	northPanel.add(jRadioButton);
        }
        
		return northPanel;
	}

	private JPanel CreateSouthPanel() {
		JPanel southPanel = new JPanel();
        southPanel.setBackground(this.backGroundColor);
        southPanel.setLayout(new GridBagLayout());
        YearOfStudy.CollegeYear[] yearsOfStudy = YearOfStudy.CollegeYear.values();
        
        // Create the academic session radio buttons section
        ButtonGroup yearsOfStudyButtonGroup = new ButtonGroup();
        boolean isAnyYearOfStudySelected = false;
        for(YearOfStudy.CollegeYear yearOfStudy : yearsOfStudy)
        {
        	if(yearOfStudy != YearOfStudy.CollegeYear.UNASSIGNED)
        	{
	        	JRadioButton jRadioButton = new JRadioButton(StringUtils.capitalize(yearOfStudy.name()));
	        	if(!isAnyYearOfStudySelected)
	        	{
	        		jRadioButton.setSelected(true);
	        		isAnyYearOfStudySelected = true;
	        	}
	        	jRadioButton.setBackground(backGroundColor);
	        	yearsOfStudyButtonGroup.add(jRadioButton);
	        	southPanel.add(jRadioButton);
        	}
        }
        
		return southPanel;
	}

	private JPanel CreateWestPanel() {
		JPanel westPanel = new JPanel();
        westPanel.setBackground(this.backGroundColor);
        westPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        List<Department> departments = viewedFaculty.getDepartments();
        
        // Create the academic session radio buttons section
        ButtonGroup departmentsButtonGroup = new ButtonGroup();
        boolean isAnyDepartmentSelelected = false;
        for(int i=0; i<departments.size(); i++)
        {
        	JRadioButton jRadioButton = new JRadioButton(StringUtils.capitalize(departments.get(i).getName()));
        	if(!isAnyDepartmentSelelected)
        	{
        		jRadioButton.setSelected(true);
        		isAnyDepartmentSelelected = true;
        	}
        	jRadioButton.setBackground(this.backGroundColor);
        	jRadioButton.setHorizontalTextPosition(SwingConstants.CENTER);
        	jRadioButton.setVerticalTextPosition(JRadioButton.TOP);
        	departmentsButtonGroup.add(jRadioButton);
        	c.gridy = i;
        	westPanel.add(jRadioButton,c);
        }
        
		return westPanel;
	}

	private JPanel CreateEastPanel() {
		JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBackground(this.backGroundColor);
        
        // Create a list with unassigned classes
        List<Class> uClasses = this.acceptedSolution.GetUnassignedClasses();
        JComponent uScrollPane = CreateScrollableListOfClasses(uClasses, backGroundColor, " Unassigned classes: " + uClasses.size() + " ");
        
        // Create a list with assigned classes
        List<Class> aClasses = this.acceptedSolution.GetAssignedClasses();
        JComponent aScrollPane = CreateScrollableListOfClasses(aClasses, backGroundColor, " Assigned classes: " + aClasses.size() + " ");
        
        // Create a panel for the solver controls
        JPanel solverControls = new JPanel();
        solverControls.setLayout(new BoxLayout(solverControls, BoxLayout.X_AXIS));
        solverControls.setBackground(backGroundColor);
        solverControls.setBorder(CreateEmptyTitleBorder("Solver controls"));
        solverControls.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        
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
        for(Class oneClass : classes)
        {
        	String className = oneClass.getOffering().getName();
        	int nrOfRemovals = acceptedSolution.getNrOfRemovals(oneClass.getId());
        	classListModel.addElement(className+"("+nrOfRemovals+")");
        }
        JList<String> jListClasses = new JList<String>(classListModel);
        jListClasses.setVisibleRowCount(10);
        jListClasses.setFixedCellWidth(10);
        JScrollPane scrollPane = new JScrollPane(jListClasses);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(scrollPane);
        wrapperPanel.setBackground(bgColor);		
        wrapperPanel.setBorder(CreateEmptyTitleBorder(borderText));
        
        return wrapperPanel;
    }
    
    private TitledBorder CreateEmptyTitleBorder(String titleText)
    {
    	TitledBorder emptyTitleBorder = BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(10,20,0,5), titleText);
		emptyTitleBorder.setTitlePosition(TitledBorder.TOP);
		
		return emptyTitleBorder;
    }

}
