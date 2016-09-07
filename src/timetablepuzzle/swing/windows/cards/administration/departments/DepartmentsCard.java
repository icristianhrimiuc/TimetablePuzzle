package timetablepuzzle.swing.windows.cards.administration.departments;

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

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.YearOfStudyJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.DepartmentJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.YearOfStudyDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.DepartmentDAO;
import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.administration.Department;
import timetablepuzzle.swing.windows.cards.common.ListBoxesWithTransferableItems;

public class DepartmentsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(DepartmentsCard.class.getName());
	private static DepartmentDAO departmentDAOService = new DepartmentJPADAOService();
	private static YearOfStudyDAO yearOfStudyDAOService = new YearOfStudyJPADAOService();

	private DepartmentsTableModel departmentsTableModel;
	private JTable departmentsTable;
	private JTextField textFieldName;
	private JLabel notificationLabel;
	private ListBoxesWithTransferableItems transferableItemsControl;
	private int idOfTheDepartmentToUpdate;

	public DepartmentsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Departments table
		this.departmentsTableModel = new DepartmentsTableModel();
		RefreshTable();
		this.departmentsTable = new JTable(this.departmentsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.departmentsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Transferable items control
		this.transferableItemsControl = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible years of study", "Added years of study");
		RefreshTransferableItemsControl(new ArrayList<YearOfStudy>());

		this.idOfTheDepartmentToUpdate = 0;
		SetDepartmentsCardComponents();
	}

	private void RefreshTable() {
		this.departmentsTableModel.setData(departmentDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.departmentsTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.departmentsTable.getColumnModel().getColumn(1).setMinWidth(150);
		this.departmentsTable.getColumnModel().getColumn(1).setMaxWidth(150);
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

	private void RefreshTransferableItemsControl(List<YearOfStudy> addedYearsOfStudy) {
		List<YearOfStudy> leftListYearsOfStudy = yearOfStudyDAOService.GetAll();
		List<YearOfStudy> rightListYearsOfStudy = new ArrayList<YearOfStudy>();
		for(YearOfStudy addedYearOfStudy : addedYearsOfStudy){
			leftListYearsOfStudy.remove(addedYearOfStudy);
			rightListYearsOfStudy.add(addedYearOfStudy);
		}
		leftListYearsOfStudy.sort(Comparator.comparing(YearOfStudy::toString));
		rightListYearsOfStudy.sort(Comparator.comparing(YearOfStudy::toString));
		this.transferableItemsControl.ReInitializeLists(leftListYearsOfStudy.toArray(), rightListYearsOfStudy.toArray());
	}

	private void SetDepartmentsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewDepartmentPanel());
		this.add(CreateViewAllDepartmentsPanel());
	}

	private JPanel CreateCreateNewDepartmentPanel() {
		JPanel createNewDepartmentPanel = new JPanel(new GridLayout(1, 2));
		createNewDepartmentPanel.add(CreatePropertiesPanel());
		createNewDepartmentPanel.add(CreateDepartmentYearsOfStudyPanel());

		return createNewDepartmentPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Department"));

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
		List<YearOfStudy> yearsOfStudy = ConvertToListOfYearsOfStudy(this.transferableItemsControl.GetRightListElements()); 

		if ((name.isEmpty()) || (yearsOfStudy.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new department with empty property field.");
		} else {
			try {
				// Create new
				Department department = new Department(this.idOfTheDepartmentToUpdate, name, yearsOfStudy);

				if (this.idOfTheDepartmentToUpdate != 0) {
					departmentDAOService.merge(department);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on department with id {0} and name {1}.",
							new Object[] { department.getId(), department.getName() });
				} else {
					departmentDAOService.merge(department);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on department  with name {1}. ",
							new Object[] { department.getName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the department. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the department" + e.toString(), e);
			}
		}
	}

	private List<YearOfStudy> ConvertToListOfYearsOfStudy(List<Object> elements) {
		List<YearOfStudy> yearsOfStudy = new ArrayList<YearOfStudy>();
		for(Object element : elements)
		{
			yearsOfStudy.add((YearOfStudy)element);
		}
		
		return yearsOfStudy;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.departmentsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a department while no row was selected.");
		} else {
			Department existingDepartment = this.departmentsTableModel.elementAt(selecteRow);
			this.idOfTheDepartmentToUpdate = existingDepartment.getId();
			this.textFieldName.setText(existingDepartment.getName());
			RefreshTransferableItemsControl(existingDepartment.getYearsOfStudy());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.departmentsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a department while no row was selected.");
		} else {
			try {
				Department existingDepartment = this.departmentsTableModel.elementAt(selecteRow);
				departmentDAOService.remove(existingDepartment);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on department with id {0} and named {1}.",
						new Object[] { existingDepartment.getId(), existingDepartment.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this department."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting department. Please make sure that nothing else depends on this department."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.notificationLabel.setText("  ");
		RefreshTransferableItemsControl(new ArrayList<YearOfStudy>());
		this.idOfTheDepartmentToUpdate = 0;
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

	private Component CreateDepartmentYearsOfStudyPanel() {		
		JPanel instructorMeetingsPanel = new JPanel();
		instructorMeetingsPanel.setLayout(new BoxLayout(instructorMeetingsPanel, BoxLayout.Y_AXIS));
		instructorMeetingsPanel.add(this.transferableItemsControl);
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(instructorMeetingsPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Department-Years Of Study"));
		
		return adjustmentPanel;
	}

	private JPanel CreateViewAllDepartmentsPanel() {
		JPanel viewAllDepartmentsPanel = new JPanel(new BorderLayout());
		viewAllDepartmentsPanel.setBorder(CreateRaisedBevelTitledBorder("View All Departments"));

		JScrollPane scrollPane = new JScrollPane();
		this.departmentsTable.setShowVerticalLines(true);
		this.departmentsTable.setShowHorizontalLines(true);
		this.departmentsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.departmentsTable);

		viewAllDepartmentsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllDepartmentsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}

