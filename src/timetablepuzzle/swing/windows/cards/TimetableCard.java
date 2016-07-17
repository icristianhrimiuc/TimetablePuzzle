package timetablepuzzle.swing.windows.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

import timetablepuzzle.eclipselink.entities.administration.AcademicSession;
import timetablepuzzle.eclipselink.entities.administration.AcademicYear;
import timetablepuzzle.eclipselink.entities.administration.Department;
import timetablepuzzle.eclipselink.entities.administration.Faculty;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences;
import timetablepuzzle.eclipselink.entities.administration.User;
import timetablepuzzle.eclipselink.entities.administration.YearOfStudy;
import timetablepuzzle.eclipselink.entities.inputdata.Class;
import timetablepuzzle.eclipselink.entities.inputdata.Solution;

public class TimetableCard extends JPanel{
	private static final long serialVersionUID = 1L;

	// User required information
	private User _loggedUser;
	private AcademicYear _viewedAcadYear;
	private AcademicSession _viewedAcadSession;
	private Faculty _viewedFaculty;
	private Solution _acceptedSolution;
	private Color _bgColor;
	
	public TimetableCard(User loggerUser, AcademicYear viewedAcadYear, AcademicSession viewedAcadSession,
			Faculty viewedFaculty, Solution acceptedSolution, Color bgColor)
	{
		this.set_loggedUser(loggerUser);
		this.set_viewedAcadYear(viewedAcadYear);
		this.set_viewedAcadSession(viewedAcadSession);
		this.set_viewedFaculty(viewedFaculty);
		this.set_acceptedSolution(acceptedSolution);
		this.set_bgColor(bgColor);
		this.CreateTimetableCardPanel();
	}

	/******************Getters and setters********************/
	public User get_loggedUser() {
		return _loggedUser;
	}

	public void set_loggedUser(User _loggedUser) {
		this._loggedUser = _loggedUser;
	}

	public AcademicYear get_viewedAcadYear() {
		return _viewedAcadYear;
	}

	public void set_viewedAcadYear(AcademicYear _viewedAcadYear) {
		this._viewedAcadYear = _viewedAcadYear;
	}

	public AcademicSession get_viewedAcadSession() {
		return _viewedAcadSession;
	}

	public void set_viewedAcadSession(AcademicSession _viewedAcadSession) {
		this._viewedAcadSession = _viewedAcadSession;
	}

	public Faculty get_viewedFaculty() {
		return _viewedFaculty;
	}

	public void set_viewedFaculty(Faculty _viewedFaculty) {
		this._viewedFaculty = _viewedFaculty;
	}

	public Solution get_acceptedSolution() {
		return _acceptedSolution;
	}

	public void set_acceptedSolution(Solution _acceptedSolution) {
		this._acceptedSolution = _acceptedSolution;
	}
	
	public Color get_bgColor() {
		return _bgColor;
	}

	public void set_bgColor(Color _bgColor) {
		this._bgColor = _bgColor;
	}
	
	/********************Methods that model the class behavior*********************/
	
	public JPanel CreateTimetableCardPanel()
    {
    	this.setBackground(_bgColor);
    	this.setLayout(new BorderLayout());
        
        // Create the north panel. It will contain a radio button for each day of the week
        JPanel northPanel = new JPanel();
        northPanel.setBackground(_bgColor);
        northPanel.setLayout(new GridBagLayout());
        TimePreferences.Day[] daysOfTheWeek = TimePreferences.Day.values();
        // Create the academic session radio buttons section
        ButtonGroup dotwbg = new ButtonGroup();
        boolean dotwSel = false;
        for(TimePreferences.Day dayOfTheWeek : daysOfTheWeek)
        {
        	JRadioButton jrb = new JRadioButton(StringUtils.capitalize(dayOfTheWeek.name()));
        	if(!dotwSel)
        	{
        		jrb.setSelected(true);
        		dotwSel = true;
        	}
        	jrb.setBackground(_bgColor);
        	dotwbg.add(jrb);
        	northPanel.add(jrb);
        }
        
        // Create the south panel. It will contain a radio button for each academic year
        JPanel southPanel = new JPanel();
        southPanel.setBackground(_bgColor);
        southPanel.setLayout(new GridBagLayout());
        YearOfStudy.Year[] yearsOfStudy = YearOfStudy.Year.values();
        // Create the academic session radio buttons section
        ButtonGroup yosbg = new ButtonGroup();
        boolean yosSel = false;
        for(YearOfStudy.Year yearOfStudy : yearsOfStudy)
        {
        	if(yearOfStudy != YearOfStudy.Year.UNASSIGNED)
        	{
	        	JRadioButton jrb = new JRadioButton(StringUtils.capitalize(yearOfStudy.name()));
	        	if(!yosSel)
	        	{
	        		jrb.setSelected(true);
	        		yosSel = true;
	        	}
	        	jrb.setBackground(_bgColor);
	        	yosbg.add(jrb);
	        	southPanel.add(jrb);
        	}
        }
        
        // Create the west panel.It will contain a radio button for each department in the faculty
        JPanel westPanel = new JPanel();
        westPanel.setBackground(_bgColor);
        westPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        List<Department> departments = _viewedFaculty.get_departments();
        // Create the academic session radio buttons section
        ButtonGroup dbg = new ButtonGroup();
        boolean dSel = false;
        for(int i=0; i<departments.size(); i++)
        {
        	JRadioButton jrb = new JRadioButton(StringUtils.capitalize(departments.get(i).get_name()));
        	if(!dSel)
        	{
        		jrb.setSelected(true);
        		dSel = true;
        	}
        	jrb.setBackground(_bgColor);
        	jrb.setHorizontalTextPosition(SwingConstants.CENTER);
        	jrb.setVerticalTextPosition(JRadioButton.TOP);
        	dbg.add(jrb);
        	c.gridy = i;
        	westPanel.add(jrb,c);
        }
        
        // Create the east panel. It will contain 2 lists
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBackground(_bgColor);
        // Create a list with unassigned classes
        List<Class> uClasses = _viewedAcadSession.get_solution().get_unassignedClasses();
        JComponent uScrollPane = CreateScrollableListOfClasses(uClasses, _bgColor, " Unassigned classes: " + uClasses.size() + " ");
        // Create a list with assigned classes
        Set<Class> aClasses = _viewedAcadSession.get_solution().get_assignedClasses().keySet();
        JComponent aScrollPane = CreateScrollableListOfClasses(aClasses, _bgColor, " Assigned classes: " + aClasses.size() + " ");
        // Create a panel for the solver controls
        JPanel solverControls = new JPanel();
        solverControls.setLayout(new BoxLayout(solverControls, BoxLayout.X_AXIS));
        solverControls.setBackground(_bgColor);
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
    
    private JComponent CreateScrollableListOfClasses(Collection<Class> classes, Color bgColor, String borderText) {
        DefaultListModel<String> classListModel = new DefaultListModel<String>();
        for(Class oneClass : classes)
        {
        	String className = oneClass.get_meeting().get_name();
        	int nrOfRemovals = _acceptedSolution.get_nrOfRemovals(oneClass.get_externalId());
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
