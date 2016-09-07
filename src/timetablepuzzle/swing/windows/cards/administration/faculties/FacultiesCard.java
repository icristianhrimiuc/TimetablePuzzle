package timetablepuzzle.swing.windows.cards.administration.faculties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.AcademicYearJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.DepartmentJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.FacultyJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.AcademicYearDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.DepartmentDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.FacultyDAO;
import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.Department;
import timetablepuzzle.entities.administration.Faculty;
import timetablepuzzle.swing.windows.cards.common.ListBoxesWithTransferableItems;

public class FacultiesCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(FacultiesCard.class.getName());
	private static FacultyDAO facultyDAOService = new FacultyJPADAOService();
	private static AcademicYearDAO academicYearDAOService = new AcademicYearJPADAOService();
	private static DepartmentDAO departmentDAOService = new DepartmentJPADAOService();

	private FacultiesTableModel facultiesTableModel;
	private JTable facultiesTable;
	private JTextField textFieldName;
	private JLabel notificationLabel;
	private ListBoxesWithTransferableItems transferableItemsControlAcademicYears;
	private ListBoxesWithTransferableItems transferableItemsControlDepartments;
	private int idOfTheFacultyToUpdate;

	public FacultiesCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Faculties table
		this.facultiesTableModel = new FacultiesTableModel();
		RefreshTable();
		this.facultiesTable = new JTable(this.facultiesTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.facultiesTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Transferable items control for academicYears
		this.transferableItemsControlAcademicYears = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible academic years", "Added academic years");
		RefreshTransferableItemsControlAcademicYears(new ArrayList<AcademicYear>());
		
		// Transferable items control for departments
		this.transferableItemsControlDepartments = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible departments", "Added departments");
		RefreshTransferableItemsControlDepartments(new ArrayList<Department>());

		this.idOfTheFacultyToUpdate = 0;
		SetFacultiesCardComponents();
	}

	private void RefreshTable() {
		this.facultiesTableModel.setData(facultyDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.facultiesTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.facultiesTable.getColumnModel().getColumn(1).setMinWidth(200);
		this.facultiesTable.getColumnModel().getColumn(1).setMaxWidth(200);
	}

	private JTextField CreatePropertyTextField(int width) {
		JTextField propertyTextField = new JTextField(width);
		propertyTextField.setHorizontalAlignment(JTextField.CENTER);
		propertyTextField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNew();
			}
		});

		return propertyTextField;
	}

	private void RefreshTransferableItemsControlAcademicYears(List<AcademicYear> addedAcademicYears) {
		List<AcademicYear> leftListAcademicYears = academicYearDAOService.GetAll();
		List<AcademicYear> rightListAcademicYears = new ArrayList<AcademicYear>();
		for(AcademicYear addedAcademicYear : addedAcademicYears){
			leftListAcademicYears.remove(addedAcademicYear);
			rightListAcademicYears.add(addedAcademicYear);
		}
		leftListAcademicYears.sort(Comparator.comparing(AcademicYear::toString));
		rightListAcademicYears.sort(Comparator.comparing(AcademicYear::toString));
		this.transferableItemsControlAcademicYears.ReInitializeLists(leftListAcademicYears.toArray(), rightListAcademicYears.toArray());
	}

	private void RefreshTransferableItemsControlDepartments(List<Department> addedDepartments) {
		List<Department> leftListDepartments = departmentDAOService.GetAll();
		List<Department> rightListDepartments = new ArrayList<Department>();
		for(Department addedDepartment : addedDepartments){
			leftListDepartments.remove(addedDepartment);
			rightListDepartments.add(addedDepartment);
		}
		leftListDepartments.sort(Comparator.comparing(Department::toString));
		rightListDepartments.sort(Comparator.comparing(Department::toString));
		this.transferableItemsControlDepartments.ReInitializeLists(leftListDepartments.toArray(), rightListDepartments.toArray());
	}

	private void SetFacultiesCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewFacultyPanel());
		this.add(CreateViewAllFacultiesPanel());
	}

	private JPanel CreateCreateNewFacultyPanel() {
		JPanel createNewFacultyPanel = new JPanel(new GridLayout(1, 2));
		createNewFacultyPanel.add(CreatePropertiesPanel());
		createNewFacultyPanel.add(CreateFacultyDepartmentsPanel());

		return createNewFacultyPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(this.transferableItemsControlAcademicYears);
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Faculty-Academic Years"));

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
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNew();
			}
		});
		JButton buttonEditSelectedRow = new JButton("Edit selected");
		buttonEditSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadSelectedRowDetailsForEdit();
			}
		});
		JButton buttonDeleteSelectedRow = new JButton("Delete selected");
		buttonDeleteSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteSelectedRow();
			}
		});
		JButton buttonRefreshAllFields = new JButton("Refresh All Fields");
		buttonRefreshAllFields.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefreshAllFields();
			}
		});

		crudButtonsPanel.add(buttonSave);
		crudButtonsPanel.add(buttonEditSelectedRow);
		crudButtonsPanel.add(buttonDeleteSelectedRow);
		crudButtonsPanel.add(buttonRefreshAllFields);

		return crudButtonsPanel;
	}

	private void CreateAndSaveNew() {
		String name = this.textFieldName.getText();
		List<AcademicYear> academicYears = ConvertToListOfAcademicYears(this.transferableItemsControlAcademicYears.GetRightListElements());
		List<Department> departments = ConvertToListOfDepartments(this.transferableItemsControlDepartments.GetRightListElements()); 

		if ((name.isEmpty()) || (academicYears.isEmpty()) || (departments.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new faculty with empty property field.");
		} else {
			try {
				// Create new
				Faculty faculty = new Faculty(this.idOfTheFacultyToUpdate, name, academicYears , departments);

				if (this.idOfTheFacultyToUpdate != 0) {
					facultyDAOService.merge(faculty);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on faculty with id {0} and name {1}.",
							new Object[] { faculty.getId(), faculty.getName() });
				} else {
					facultyDAOService.merge(faculty);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on faculty  with name {1}. ",
							new Object[] { faculty.getName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the faculty. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the faculty" + e.toString(), e);
			}
		}
	}

	private List<AcademicYear> ConvertToListOfAcademicYears(List<Object> elements) {
		List<AcademicYear> academicYears = new ArrayList<AcademicYear>();
		for(Object element : elements)
		{
			academicYears.add((AcademicYear)element);
		}
		
		return academicYears;
	}

	private List<Department> ConvertToListOfDepartments(List<Object> elements) {
		List<Department> departments = new ArrayList<Department>();
		for(Object element : elements)
		{
			departments.add((Department)element);
		}
		
		return departments;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.facultiesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a faculty while no row was selected.");
		} else {
			Faculty existingFaculty = this.facultiesTableModel.elementAt(selecteRow);
			this.idOfTheFacultyToUpdate = existingFaculty.getId();
			this.textFieldName.setText(existingFaculty.getName());
			RefreshTransferableItemsControlAcademicYears(existingFaculty.getAcademicYears());
			RefreshTransferableItemsControlDepartments(existingFaculty.getDepartments());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.facultiesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a faculty while no row was selected.");
		} else {
			try {
				Faculty existingFaculty = this.facultiesTableModel.elementAt(selecteRow);
				facultyDAOService.remove(existingFaculty);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on faculty with id {0} and named {1}.",
						new Object[] { existingFaculty.getId(), existingFaculty.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this faculty."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting faculty. Please make sure that nothing else depends on this faculty."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.notificationLabel.setText("  ");
		RefreshTransferableItemsControlDepartments(new ArrayList<Department>());
		this.idOfTheFacultyToUpdate = 0;
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

	private Component CreateFacultyDepartmentsPanel() {		
		JPanel instructorMeetingsPanel = new JPanel();
		instructorMeetingsPanel.setLayout(new BoxLayout(instructorMeetingsPanel, BoxLayout.Y_AXIS));
		instructorMeetingsPanel.add(this.transferableItemsControlDepartments);
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(instructorMeetingsPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Faculty-Departments"));
		
		return adjustmentPanel;
	}

	private JPanel CreateViewAllFacultiesPanel() {
		JPanel viewAllFacultiesPanel = new JPanel(new BorderLayout());
		viewAllFacultiesPanel.setBorder(CreateRaisedBevelTitledBorder("View All Faculties"));

		JScrollPane scrollPane = new JScrollPane();
		this.facultiesTable.setShowVerticalLines(true);
		this.facultiesTable.setShowHorizontalLines(true);
		this.facultiesTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.facultiesTable);

		viewAllFacultiesPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllFacultiesPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
