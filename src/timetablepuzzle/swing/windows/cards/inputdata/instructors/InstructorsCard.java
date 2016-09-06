package timetablepuzzle.swing.windows.cards.inputdata.instructors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.InstructorJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.TimePreferencesJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.InstructorDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.TimePreferencesDAO;
import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.swing.windows.cards.other.timePreferences.WeekPreferencesCellRenderer;
import timetablepuzzle.swing.windows.cards.other.timePreferences.WeekPreferencesTableModel;

public class InstructorsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(InstructorsCard.class.getName());
	private static InstructorDAO instructorDAOService = new InstructorJPADAOService();
	private static TimePreferencesDAO timePreferencesDAOService = new TimePreferencesJPADAOService();

	private InstructorsTableModel instructorsTableModel;
	private JTable instructorsTable;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldAcademicTitle;
	private JLabel notificationLabel;
	private CustomComboBoxModel<TimePreferences> comboBoxTimePreferencesModel;
	private JComboBox<TimePreferences> comboBoxTimePreferences;
	private WeekPreferencesTableModel weekPreferencesTableModel;
	private JTable weekPreferencesTable;
	private SimpleDateFormat dateFormat;
	private int idOfTheInstructorToUpdate;

	public InstructorsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Instructors table
		this.instructorsTableModel = new InstructorsTableModel();
		RefreshTable();
		this.instructorsTable = new JTable(this.instructorsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.instructorsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldFirstName = CreatePropertyTextField(15);
		this.textFieldLastName = CreatePropertyTextField(20);
		this.textFieldAcademicTitle = CreatePropertyTextField(30);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// TimePreferences combo box
		this.comboBoxTimePreferencesModel = new CustomComboBoxModel<TimePreferences>();
		RefreshComboBoxTimePreferences();
		this.comboBoxTimePreferences = new JComboBox<TimePreferences>(this.comboBoxTimePreferencesModel);

		// Week Preferences table
		this.weekPreferencesTableModel = new WeekPreferencesTableModel();
		RefreshTableWeekPreferences();
		this.weekPreferencesTable = new JTable(this.weekPreferencesTableModel);
		ConfigureWeekPreferencesTable();
		
		this.dateFormat =new SimpleDateFormat("dd-MM-yyyy");

		this.idOfTheInstructorToUpdate = 0;
		SetInstructorsCardComponents();
	}

	private void RefreshTable() {
		this.instructorsTableModel.setData(instructorDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.instructorsTable.getColumnModel().getColumn(0).setMaxWidth(60);
	}
	
	private JTextField CreatePropertyTextField(int width){
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
	
	private void RefreshComboBoxTimePreferences(){
		List<TimePreferences> timePreferences = timePreferencesDAOService.GetAll();
		TimePreferences createNew = new TimePreferences();
		createNew.setName("Create new");
		timePreferences.add(0, createNew);
		this.comboBoxTimePreferencesModel.setData(timePreferences);
		this.comboBoxTimePreferencesModel.setSelectedItem(createNew);
		this.repaint();
	}

	private void RefreshTableWeekPreferences() {
		this.weekPreferencesTableModel.setData((TimePreferences)this.comboBoxTimePreferences.getSelectedItem());
	}

	private void ConfigureWeekPreferencesTable() {
		this.weekPreferencesTable.setRowHeight(32);
		for (int i = 0; i <= 12; i++) {
			this.weekPreferencesTable.getColumnModel().getColumn(i).setMaxWidth(36);
		}
		WeekPreferencesCellRenderer cellRenderer =  new WeekPreferencesCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.weekPreferencesTable.setDefaultRenderer(String.class, cellRenderer);
		this.weekPreferencesTable.setRowSelectionAllowed(false);
		this.weekPreferencesTable.setColumnSelectionAllowed(false);
		this.weekPreferencesTable.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		this.weekPreferencesTable.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				Point point = e.getPoint();
				int rowIndex = target.rowAtPoint(point);
				int columnIndex = target.columnAtPoint(point);
				WeekPreferencesTableModel tableModel = (WeekPreferencesTableModel) weekPreferencesTable.getModel();
				tableModel.incrementCellPreference(rowIndex, columnIndex);
				weekPreferencesTable.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	private void SetInstructorsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewInstructorPanel());
		this.add(CreateViewAllInstructorsPanel());
	}

	private JPanel CreateCreateNewInstructorPanel() {
		JPanel createNewInstructorPanel = new JPanel(new GridLayout(1, 2));
		createNewInstructorPanel.add(CreatePropertiesPanel());
		createNewInstructorPanel.add(CreateWeekPreferencesPanel());

		return createNewInstructorPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("First Name", this.textFieldFirstName));
		propertiesPanel.add(CreatePropertyPanel("Last Name", this.textFieldLastName));
		propertiesPanel.add(CreatePropertyPanel("Academic Title", this.textFieldAcademicTitle));
		propertiesPanel.add(CreatePropertyPanel("Time Preferences", this.comboBoxTimePreferences));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Instructor"));
		
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

	private JPanel CreateWeekPreferencesPanel() {
		JPanel weekPreferencesPanel = new JPanel();
		weekPreferencesPanel.setLayout(new BoxLayout(weekPreferencesPanel, BoxLayout.Y_AXIS));
		weekPreferencesPanel.add(this.weekPreferencesTable);
		weekPreferencesPanel.add(CreateShortExplanatoryPanel());

		// Adjust panel on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(weekPreferencesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Instructor Time Preferences"));

		return adjustmentPanel;
	}

	private JPanel CreateShortExplanatoryPanel() {
		JPanel explanatoryPanel = new JPanel();
		explanatoryPanel.setLayout(new BoxLayout(explanatoryPanel, BoxLayout.X_AXIS));
		for (int i=0; i<TimePreferences.TimePreference.values().length; i++) {
			Color color = WeekPreferencesCellRenderer.ColorsForPreference[i];
			explanatoryPanel.add(CreateColorTextField(color));
		}

		return explanatoryPanel;
	}

	private JTextField CreateColorTextField(Color color) {
		JTextField colorTextField = new JTextField(5);
		colorTextField.setBackground(color);
		colorTextField.setEditable(false);

		return colorTextField;
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
		String firstName = this.textFieldFirstName.getText();
		String lastName = this.textFieldLastName.getText();
		String academicTitle = this.textFieldAcademicTitle.getText();

		if ((firstName.isEmpty()) || (lastName.isEmpty()) || (academicTitle.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new instructor with empty property field.");
		} else {
			try {
				TimePreferences timePreferences = this.weekPreferencesTableModel.getData();
				String name = String.format("Preference for %s %s(%s)", firstName, lastName, this.dateFormat.format(new Date()));
				timePreferences.setName(name);
				
				Instructor instructor = new Instructor(this.idOfTheInstructorToUpdate, firstName, lastName, academicTitle, timePreferences);

				if (this.idOfTheInstructorToUpdate != 0) {
					instructorDAOService.merge(instructor);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on instructor with id {0} and named {1} {2}.",
							new Object[] { instructor.getId(), instructor.getFirstName(), instructor.getLastName() });
				} else {
					instructorDAOService.merge(instructor);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on instructor named {1} {2}. ",
							new Object[] { instructor.getFirstName(), instructor.getLastName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the instructor. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the instructor" + e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.instructorsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a instructor while no row was selected.");
		} else {
			Instructor existingInstructor = this.instructorsTableModel.elementAt(selecteRow);
			this.idOfTheInstructorToUpdate = existingInstructor.getId();
			this.textFieldFirstName.setText(existingInstructor.getFirstName());
			this.textFieldLastName.setText(existingInstructor.getLastName());
			this.textFieldAcademicTitle.setText(existingInstructor.getAcademicTitle());
			TimePreferences existingTimePreferences = DeepCopyTimePreferences(existingInstructor.getTimePreferences());
			this.comboBoxTimePreferencesModel.setSelectedItem(existingTimePreferences);
			this.weekPreferencesTableModel.setData(existingTimePreferences);
			this.repaint();
		}
	}

	private TimePreferences DeepCopyTimePreferences(TimePreferences original) {
		TimePreferences copy = new TimePreferences(original.getId(), original.getName(), original.getMonPreferences(),
				original.getTuePreferences(), original.getWedPreferences(), original.getThuPreferences(),
				original.getFriPreferences());

		return copy;
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.instructorsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a instructor while no row was selected.");
		} else {
			try {
				Instructor existingInstructor = this.instructorsTableModel.elementAt(selecteRow);
				instructorDAOService.remove(existingInstructor);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on instructor with id {0} and named {1} {2}.",
						new Object[] { existingInstructor.getId(), existingInstructor.getFirstName(), existingInstructor.getLastName()});
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this instructor."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting instructor. Please make sure that nothing else depends on this instructor."
								+ e.toString(),	e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldFirstName.setText("");
		this.textFieldLastName.setText("");
		this.textFieldAcademicTitle.setText("");
		this.notificationLabel.setText("  ");
		RefreshComboBoxTimePreferences();
		RefreshTableWeekPreferences();
		this.idOfTheInstructorToUpdate = 0;
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

	private JPanel CreateViewAllInstructorsPanel() {
		JPanel viewAllInstructorsPanel = new JPanel(new BorderLayout());
		viewAllInstructorsPanel.setBorder(CreateRaisedBevelTitledBorder("View All Instructors"));

		JScrollPane scrollPane = new JScrollPane();
		this.instructorsTable.setShowVerticalLines(true);
		this.instructorsTable.setShowHorizontalLines(true);
		this.instructorsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.instructorsTable);

		viewAllInstructorsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllInstructorsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}

