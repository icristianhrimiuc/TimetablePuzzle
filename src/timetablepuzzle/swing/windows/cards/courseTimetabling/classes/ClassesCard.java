package timetablepuzzle.swing.windows.cards.courseTimetabling.classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import timetablepuzzle.eclipselink.DAO.JPA.services.SolutionJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.SolutionDAO;
import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.Solution;

public class ClassesCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(ClassesCard.class.getName());
	private static SolutionDAO solutionDAO = new SolutionJPADAOService();

	private int acceptedSolutionId;
	private boolean assigned;
	private String assignedText;
	private ClassesTableModel classesTableModel;
	private JTable classesTable;
	private JTextField textFieldCourseTitle;
	private JTextField textFieldCourseAbbreviation;
	private JTextField textFieldDepartmentName;
	private JTextField textFieldCollegeYear;
	private JTextField textFieldSubjectAreaName;
	private JTextField textFieldTerm;
	private JTextField textFieldOffering;
	private JTextField textFieldAssignedRoom;
	private JTextField textFieldAssignedInstructor;
	private JTextField textFieldAssignedParentStudentGroup;
	private JLabel notificationLabel;

	public ClassesCard(Color backgroundColor, Solution acceptedSolution, boolean assigned) {
		this.setBackground(backgroundColor);
		if(acceptedSolution != null){
			this.acceptedSolutionId = acceptedSolution.getId();
		}else{
			this.acceptedSolutionId = 0;
		}
		this.assigned = assigned;
		this.assignedText = this.assigned ? "Assigned" : "Unassigned";

		// Classes table
		this.classesTableModel = new ClassesTableModel();
		RefreshTable();
		this.classesTable = new JTable(this.classesTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.classesTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldCourseTitle = CreatePropertyTextField(25);
		this.textFieldCourseAbbreviation = CreatePropertyTextField(25);
		this.textFieldDepartmentName = CreatePropertyTextField(25);
		this.textFieldCollegeYear = CreatePropertyTextField(25);
		this.textFieldSubjectAreaName = CreatePropertyTextField(25);
		this.textFieldTerm = CreatePropertyTextField(25);
		this.textFieldOffering = CreatePropertyTextField(25);
		this.textFieldAssignedRoom = CreatePropertyTextField(25);
		this.textFieldAssignedInstructor = CreatePropertyTextField(25);
		this.textFieldAssignedParentStudentGroup = CreatePropertyTextField(25);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		SetClassesCardComponents();
	}

	private void RefreshTable() {
		Solution solution = solutionDAO.findById(this.acceptedSolutionId);
		if(solution != null){
		if (this.assigned) {
			this.classesTableModel.setData(solution.GetAssignedClasses());
		} else {
			this.classesTableModel.setData(solution.GetUnassignedClasses());
		}}else{
			this.classesTableModel.setData(new ArrayList<Class>());
		}
	}

	private void SetColumnsMaxSizes() {
		this.classesTable.getColumnModel().getColumn(0).setMaxWidth(60);
	}

	private JTextField CreatePropertyTextField(int width) {
		JTextField propertyTextField = new JTextField(width);
		propertyTextField.setHorizontalAlignment(JTextField.CENTER);
		propertyTextField.setEditable(false);
		propertyTextField.setBackground(Color.WHITE);
		propertyTextField.setForeground(Color.BLACK);

		return propertyTextField;
	}

	private void SetClassesCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateViewClassPanel());
		this.add(CreateViewAllClassesPanel());
	}

	private JPanel CreateViewClassPanel() {
		JPanel viewClassPanel = new JPanel(new GridLayout(1, 2));
		viewClassPanel.add(CreateFirstPropertiesPanel());
		viewClassPanel.add(CreateSecondPropertiesPanel());

		return viewClassPanel;
	}

	private JPanel CreateFirstPropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Course Title", this.textFieldCourseTitle));
		propertiesPanel.add(CreatePropertyPanel("Course Abbreviation", this.textFieldCourseAbbreviation));
		propertiesPanel.add(CreatePropertyPanel("Department Name", this.textFieldDepartmentName));
		propertiesPanel.add(CreatePropertyPanel("College Year", this.textFieldCollegeYear));
		propertiesPanel.add(CreatePropertyPanel("Subject Area Name", this.textFieldSubjectAreaName));
		propertiesPanel.add(CreatePropertyPanel("Term", this.textFieldTerm));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder(this.assignedText + " Class Details"));

		return adjustmentPanel;
	}

	private JPanel CreateSecondPropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Offering", this.textFieldOffering));
		propertiesPanel.add(CreatePropertyPanel("Assigned Room", this.textFieldAssignedRoom));
		propertiesPanel.add(CreatePropertyPanel("Assigned Instructor", this.textFieldAssignedInstructor));
		propertiesPanel.add(CreatePropertyPanel("Assigned Student Group", this.textFieldAssignedParentStudentGroup));

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder(this.assignedText + " Class resources"));

		return adjustmentPanel;
	}

	private JPanel CreatePropertyPanel(String propertyName, JComponent propertyTextField) {
		JPanel propertyPanel = new JPanel();
		JLabel labelName = new JLabel(propertyName, JLabel.TRAILING);
		labelName.setLabelFor(propertyTextField);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		propertyPanel.add(labelName);
		propertyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		propertyPanel.add(propertyTextField);

		return propertyPanel;
	}

	private JPanel CreateCrudButtonsPanel() {
		JPanel crudButtonsPanel = new JPanel();
		JButton buttonViewSelectedRow = new JButton("View selected");
		buttonViewSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadSelectedRowDetailsForView();
			}
		});
		JButton buttonRefreshAllFields = new JButton("Refresh All Fields");
		buttonRefreshAllFields.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefreshAllFields();
			}
		});

		crudButtonsPanel.add(buttonViewSelectedRow);
		crudButtonsPanel.add(buttonRefreshAllFields);

		return crudButtonsPanel;
	}

	private void LoadSelectedRowDetailsForView() {
		int selecteRow = this.classesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a class while no row was selected.");
		} else {
			Class existingClass = this.classesTableModel.elementAt(selecteRow);
			this.textFieldCourseTitle.setText(existingClass.getCourseTitle());
			this.textFieldCourseAbbreviation.setText(existingClass.getCourseAbbreviation());
			this.textFieldDepartmentName.setText(existingClass.getDepartmentName());
			this.textFieldCollegeYear.setText(existingClass.getCollegeYear().toString());
			this.textFieldSubjectAreaName.setText(existingClass.getSubjectAreaName());
			this.textFieldTerm.setText(existingClass.getTerm().toString());
			this.textFieldOffering.setText(existingClass.getOffering().toString());
			this.textFieldAssignedRoom.setText(existingClass.getAssignedRoom().toString());
			this.textFieldAssignedInstructor.setText(existingClass.getAssignedInstructor().toString());
			this.textFieldAssignedParentStudentGroup.setText(existingClass.getAssignedParentStudentGroup().toString());
			this.notificationLabel.setText("  ");
		}
	}

	private void RefreshAllFields() {
		this.textFieldCourseTitle.setText("");
		this.textFieldCourseAbbreviation.setText("");
		this.textFieldDepartmentName.setText("");
		this.textFieldCollegeYear.setText("");
		this.textFieldSubjectAreaName.setText("");
		this.textFieldTerm.setText("");
		this.textFieldOffering.setText("");
		this.textFieldAssignedRoom.setText("");
		this.textFieldAssignedInstructor.setText("");
		this.textFieldAssignedParentStudentGroup.setText("");
		this.notificationLabel.setText("  ");
		RefreshTable();
	}

	private JPanel CreateAdjustmentPanel(JPanel componentPanel) {
		JPanel adjustmentPanel = new JPanel();
		adjustmentPanel.add(componentPanel);
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, componentPanel, 0, SpringLayout.HORIZONTAL_CENTER,
				adjustmentPanel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, componentPanel, 0, SpringLayout.VERTICAL_CENTER,
				adjustmentPanel);
		adjustmentPanel.setLayout(layout);

		return adjustmentPanel;
	}

	private JPanel CreateViewAllClassesPanel() {
		JPanel viewAllClassesPanel = new JPanel(new GridLayout());
		viewAllClassesPanel.setBorder(CreateRaisedBevelTitledBorder("View All " + this.assignedText + " Classes"));

		this.classesTable.setShowVerticalLines(true);
		this.classesTable.setShowHorizontalLines(true);
		this.classesTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(this.classesTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//this.classesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		viewAllClassesPanel.add(scrollPane);

		return viewAllClassesPanel;
	}
	

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
	
	public void setNewSolution(Solution newSolution){
		if(newSolution != null)
		{
			this.acceptedSolutionId = newSolution.getId();
		}else{
			this.acceptedSolutionId = 0;
		}
		RefreshAllFields();
	}
}
