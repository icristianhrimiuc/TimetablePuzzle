package timetablepuzzle.swing.windows.cards.inputdata.studentGroups;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.StudentGroupJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.StudentGroupDAO;
import timetablepuzzle.entities.inputdata.StudentGroup;
import timetablepuzzle.swing.windows.cards.common.ListBoxesWithTransferableItems;

public class StudentGroupsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(StudentGroupsCard.class.getName());
	private static StudentGroupDAO studentGroupDAOService = new StudentGroupJPADAOService();

	private StudentGroupsTableModel studentGroupTableModel;
	private JTable studentGroupsTable;
	private JTextField textFieldName;
	private JTextField textFieldCode;
	private JTextField textFieldNrOfStudents;
	private JLabel notificationLabel;
	private ListBoxesWithTransferableItems transferableItemsControl;
	private int idOfTheStudentGroupToUpdate;

	public StudentGroupsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);
		
		// Strudent Groups table
		this.studentGroupTableModel = new StudentGroupsTableModel();
		RefreshTable();
		this.studentGroupsTable = new JTable(this.studentGroupTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.studentGroupsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();
		
		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Property text fields
		this.textFieldName = CreatePropertyTextField(30);
		this.textFieldCode =  CreatePropertyTextField(10);
		this.textFieldNrOfStudents = CreatePropertyTextField(10);
		AddDocumentListener(this.textFieldNrOfStudents);
		
		// Transferable items control
		this.transferableItemsControl = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible student groups", "Added student groups");
		RefreshTransferableItemsControl(new StudentGroup());
		
		this.idOfTheStudentGroupToUpdate = 0;
		SetStudentGroupCardComponents();
	}

	private void RefreshTable() {
		this.studentGroupTableModel.setData(studentGroupDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.studentGroupsTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.studentGroupsTable.getColumnModel().getColumn(1).setMinWidth(150);
		this.studentGroupsTable.getColumnModel().getColumn(1).setMaxWidth(150);
		this.studentGroupsTable.getColumnModel().getColumn(2).setMaxWidth(80);
		this.studentGroupsTable.getColumnModel().getColumn(3).setMinWidth(100);
		this.studentGroupsTable.getColumnModel().getColumn(3).setMaxWidth(100);
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

	private void AddDocumentListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				CheckIfNumber(textField);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				CheckIfNumber(textField);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				CheckIfNumber(textField);
			}
		});
	}

	private void CheckIfNumber(JTextField textField) {
		String textContent = textField.getText();

		if (!textContent.isEmpty()) {
			try {
				Integer.parseInt(textContent);
				this.notificationLabel.setText("  ");
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the Nr. of students field contains only digits.");
			}
		}
	}

	private void RefreshTransferableItemsControl(StudentGroup studentGroup) {
		List<StudentGroup> leftListStudentGroups = studentGroupDAOService.GetAll();
		List<StudentGroup> rightListStudentGroups = new ArrayList<StudentGroup>();
		
		leftListStudentGroups.remove(studentGroup);		
		for(StudentGroup addedStudentGroup : studentGroup.getAllComposingGroupsHierachically()){
			leftListStudentGroups.remove(addedStudentGroup);
			rightListStudentGroups.add(addedStudentGroup);
		}
		leftListStudentGroups.sort(Comparator.comparing(StudentGroup::toString));
		rightListStudentGroups.sort(Comparator.comparing(StudentGroup::toString));
		this.transferableItemsControl.ReInitializeLists(leftListStudentGroups.toArray(), rightListStudentGroups.toArray());
	}

	private void SetStudentGroupCardComponents() {
		this.setLayout(new GridLayout(2,1));
		this.add(CreateCreateNewStudentGroupPanel());
		this.add(CreateViewAllStudentGroupsPanel());
	}

	private JPanel CreateCreateNewStudentGroupPanel() {
		JPanel createNewStudentGroupPanel = new JPanel(new GridLayout(1,2));
		createNewStudentGroupPanel.add(CreatePropertiesPanel());
		createNewStudentGroupPanel.add(CreateAddStudentGroupsPanel());

		return createNewStudentGroupPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Code", this.textFieldCode));
		propertiesPanel.add(CreatePropertyPanel("Nr. of students", this.textFieldNrOfStudents));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Student Group"));
		
		return adjustmentPanel;
	}

	private JPanel CreatePropertyPanel(String propertyName, JTextField propertyTextField) {
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
		String code = this.textFieldCode.getText();
		String number = this.textFieldNrOfStudents.getText();
		List<StudentGroup> composingGroups = ConvertToListOfStudentGroups(this.transferableItemsControl.GetRightListElements());

		if (name.isEmpty() || code.isEmpty() || (number.isEmpty() && composingGroups.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new studentGroup with empty property field.");
		} else {
			try {
				// Get info
				int nrOfStudents;
				if(composingGroups.isEmpty()){
					nrOfStudents = Integer.parseInt(number);
				}else{
					nrOfStudents = CalculateTheNumberOfStudents(composingGroups);
				}				
				
				// Create new entity
				StudentGroup studentGroup = new StudentGroup(this.idOfTheStudentGroupToUpdate, name, code, nrOfStudents, composingGroups);

				// Save the entity to the database
				if (this.idOfTheStudentGroupToUpdate != 0) {
					studentGroupDAOService.merge(studentGroup);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on studentGroup with id {0} and code {1}.",
							new Object[] { studentGroup.getId(), studentGroup.getCode() });
				} else {
					studentGroupDAOService.merge(studentGroup);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on studentGroup with the following code: {0}. ",
							new Object[] { studentGroup.getCode() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the Min Capacity and Max Capacity fields contain only digits.");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the studentGroup. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}
	
	private int CalculateTheNumberOfStudents(List<StudentGroup> composingGroups){
		int nrOfStudents = 0;
		for(StudentGroup studentGroup : composingGroups){
			nrOfStudents += studentGroup.getNrOfStudents();
		}
		
		return nrOfStudents;
	}

	private List<StudentGroup> ConvertToListOfStudentGroups(List<Object> elements) {
		List<StudentGroup> studentGroups = new ArrayList<StudentGroup>();
		for(Object element : elements)
		{
			studentGroups.add((StudentGroup)element);
		}
		
		return studentGroups;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.studentGroupsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a studentGroup while no row was selected.");
		} else {
			StudentGroup existingStudentGroup = this.studentGroupTableModel.elementAt(selecteRow);
			this.idOfTheStudentGroupToUpdate = existingStudentGroup.getId();
			this.textFieldName.setText(existingStudentGroup.getName());
			this.textFieldCode.setText(existingStudentGroup.getCode());
			this.textFieldNrOfStudents.setText(Integer.toString(existingStudentGroup.getNrOfStudents()));
			RefreshTransferableItemsControl(existingStudentGroup);
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.studentGroupsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a studentGroup while no row was selected.");
		} else {
			try {
				StudentGroup existingStudentGroup = this.studentGroupTableModel.elementAt(selecteRow);
				studentGroupDAOService.remove(existingStudentGroup);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on studentGroup with id {0} and named {1}. ",
						new Object[] { existingStudentGroup.getId(), existingStudentGroup.getName() });
			} catch (Exception e) {
				this.notificationLabel.setText("An error occured. Please make sure that nothing else depends on this studentGroup."
						+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE, "Exception occured on deleting studentGroup. Please make sure that nothing else depends on this studentGroup."
						+ e.toString(), e);
			}
		}
	}
	
	private JPanel CreateAddStudentGroupsPanel(){
		JPanel addStudentGroupsPanel = new JPanel();
		addStudentGroupsPanel.setLayout(new BoxLayout(addStudentGroupsPanel, BoxLayout.Y_AXIS));
		
		JPanel transferableItemsPanel = new JPanel();
		transferableItemsPanel.add(this.transferableItemsControl);
		addStudentGroupsPanel.add(transferableItemsPanel);
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(addStudentGroupsPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Add Room Features"));
		
		return adjustmentPanel;
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.textFieldCode.setText("");
		this.textFieldNrOfStudents.setText("");
		this.notificationLabel.setText(" ");
		this.idOfTheStudentGroupToUpdate = 0;
		RefreshTransferableItemsControl(new StudentGroup());
		RefreshTable();
	}
	
	private JPanel CreateAdjustmentPanel(JPanel componentPanel){
		JPanel adjustmentPanel = new JPanel();
		adjustmentPanel.add(componentPanel);
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, componentPanel, 0, SpringLayout.HORIZONTAL_CENTER, adjustmentPanel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, componentPanel, 0, SpringLayout.VERTICAL_CENTER, adjustmentPanel);
		adjustmentPanel.setLayout(layout);
		
		return adjustmentPanel;
	}

	private JPanel CreateViewAllStudentGroupsPanel() {
		JPanel viewAllBuildingsPanel = new JPanel(new BorderLayout());
		viewAllBuildingsPanel.setBorder(CreateRaisedBevelTitledBorder("All Room Types"));

		JScrollPane scrollPane = new JScrollPane();
		this.studentGroupsTable.setShowVerticalLines(true);
		this.studentGroupsTable.setShowHorizontalLines(true);
		this.studentGroupsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.studentGroupsTable);

		viewAllBuildingsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllBuildingsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}

