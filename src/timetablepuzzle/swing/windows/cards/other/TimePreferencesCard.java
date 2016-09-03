package timetablepuzzle.swing.windows.cards.other;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.TimePreferencesJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.TimePreferencesDAO;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences;
import timetablepuzzle.usecases.solution.TimeslotPattern;

public class TimePreferencesCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(BuildingsCard.class.getName());
	private static TimePreferencesDAO timePreferencesDAOService = new TimePreferencesJPADAOService();

	private TimePreferencesTableModel timePreferencesTableModel;
	private JTable timePreferencesTable;
	private JLabel notificationLabel;
	private JTextField textFieldMonPreferences;
	private JTextField textFieldTuePreferences;
	private JTextField textFieldWedPreferences;
	private JTextField textFieldThuPreferences;
	private JTextField textFieldFriPreferences;
	private int idOfTheTimePreferencesToUpdate;

	public TimePreferencesCard(Color backgroundColor) {
		this.setBackground(backgroundColor);
		
		this.timePreferencesTableModel = new TimePreferencesTableModel();
		RefreshTable();
		this.timePreferencesTable = new JTable(this.timePreferencesTableModel);
		SetColumnsMaxSizes();
		
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		
		this.textFieldMonPreferences = new JTextField(40);
		this.textFieldTuePreferences = new JTextField(40);
		this.textFieldWedPreferences = new JTextField(40);
		this.textFieldThuPreferences = new JTextField(40);
		this.textFieldFriPreferences = new JTextField(40);
		
		SetRoomTypeCardComponents();
	}

	private void RefreshTable() {
		this.timePreferencesTableModel.setData(timePreferencesDAOService.GetAll());
		this.timePreferencesTableModel.fireTableDataChanged();
	}

	private void SetColumnsMaxSizes() {
		this.timePreferencesTable.getColumnModel().getColumn(0).setMaxWidth(60);
	}

	private void SetRoomTypeCardComponents() {
		this.setLayout(new GridLayout(2,1));
		this.add(CreateCreateNewTimePreferencesPanel());
		this.add(CreateViewAllTimePreferencesPanel());
	}

	private JPanel CreateCreateNewTimePreferencesPanel() {
		JPanel createNewTimePreferencesPanel = new JPanel(new GridLayout(1,2));
		createNewTimePreferencesPanel.add(CreatePropertiesPanel());
		createNewTimePreferencesPanel.add(CreateExplanatoryPanel());

		return createNewTimePreferencesPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Monday Preferences", this.textFieldMonPreferences));
		propertiesPanel.add(CreatePropertyPanel("Tuesday Preferences", this.textFieldTuePreferences));
		propertiesPanel.add(CreatePropertyPanel("Wednesday Preferences", this.textFieldWedPreferences));
		propertiesPanel.add(CreatePropertyPanel("Thursday Preferences", this.textFieldThuPreferences));
		propertiesPanel.add(CreatePropertyPanel("Friday Preferences", this.textFieldFriPreferences));
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update timePreferences"));
		ClearAllFields();
		
		return adjustmentPanel;
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

	private JPanel CreatePropertyPanel(String propertyName, JTextField propertyTextField) {
		JPanel propertyPanel = new JPanel();
		JLabel labelName = new JLabel(propertyName, JLabel.TRAILING);
		labelName.setLabelFor(propertyTextField);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		propertyTextField.setHorizontalAlignment(JTextField.CENTER);	
		propertyTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNewTimePreferences();
			}
		});
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
				CreateAndSaveNewTimePreferences();
			}
		});
		;
		JButton buttonEditSelectedRow = new JButton("Edit selected");
		buttonEditSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadSelectedRowDetailsForEdit();
			}
		});
		;
		JButton buttonDeleteSelectedRow = new JButton("Delete selected");
		buttonDeleteSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteSelectedRow();
			}
		});
		;
		JButton buttonEmptyFields = new JButton("Empty Fields");
		buttonEmptyFields.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClearAllFields();
			}
		});
		;

		crudButtonsPanel.add(buttonSave);
		crudButtonsPanel.add(buttonEditSelectedRow);
		crudButtonsPanel.add(buttonDeleteSelectedRow);
		crudButtonsPanel.add(buttonEmptyFields);

		return crudButtonsPanel;
	}

	private void CreateAndSaveNewTimePreferences() {
		String monPreferences = this.textFieldMonPreferences.getText();
		String tuePreferences = this.textFieldTuePreferences.getText();
		String wedPreferences = this.textFieldWedPreferences.getText();
		String thuPreferences = this.textFieldThuPreferences.getText();
		String friPreferences = this.textFieldFriPreferences.getText();		

		if (monPreferences.isEmpty() || tuePreferences.isEmpty() || wedPreferences.isEmpty() || 
				thuPreferences.isEmpty() || friPreferences.isEmpty()) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new record of timePreferences with empty property field.");
		} else {
			try {				
				// Create new entity
				TimePreferences timePreferences = new TimePreferences();
				timePreferences.setMonPreferences(monPreferences);
				timePreferences.setTuePreferences(tuePreferences);
				timePreferences.setWedPreferences(wedPreferences);
				timePreferences.setThuPreferences(thuPreferences);
				timePreferences.setFriPreferences(friPreferences);

				// Save the entity to the database
				if (this.idOfTheTimePreferencesToUpdate != 0) {
					timePreferencesDAOService.Update(this.idOfTheTimePreferencesToUpdate, timePreferences);
					this.idOfTheTimePreferencesToUpdate = 0;
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on timePreferences with id {0}.",
							new Object[] { timePreferences.getId()});
				} else {
					timePreferencesDAOService.persist(timePreferences);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on timePreferences. ");
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the timePreferences. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the timePreferences" + e.toString(), e);
			}
		}
	}

	private void ClearAllFields() {
		this.textFieldMonPreferences.setText(TimeslotPattern.GenerateFreeDay());
		this.textFieldTuePreferences.setText(TimeslotPattern.GenerateFreeDay());
		this.textFieldWedPreferences.setText(TimeslotPattern.GenerateFreeDay());
		this.textFieldThuPreferences.setText(TimeslotPattern.GenerateFreeDay());
		this.textFieldFriPreferences.setText(TimeslotPattern.GenerateFreeDay());
		this.notificationLabel.setText("");
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.timePreferencesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a timePreferences while no row was selected.");
		} else {
			TimePreferences existingTimePreferences = this.timePreferencesTableModel.elementAt(selecteRow);
			this.idOfTheTimePreferencesToUpdate = existingTimePreferences.getId();
			this.textFieldMonPreferences.setText(existingTimePreferences.getMonPreferences());
			this.textFieldTuePreferences.setText(existingTimePreferences.getTuePreferences());
			this.textFieldWedPreferences.setText(existingTimePreferences.getWedPreferences());
			this.textFieldThuPreferences.setText(existingTimePreferences.getThuPreferences());
			this.textFieldFriPreferences.setText(existingTimePreferences.getFriPreferences());
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.timePreferencesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a timePreferences while no row was selected.");
		} else {
			try {
				TimePreferences existingTimePreferences = this.timePreferencesTableModel.elementAt(selecteRow);
				timePreferencesDAOService.remove(existingTimePreferences);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on timePreferences with id {0}.",
						new Object[] { existingTimePreferences.getId()});
			} catch (Exception e) {
				this.notificationLabel.setText("An error occured. Please make sure that nothing else depends on this timePreferences."
						+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE, "Exception occured on deleting roomType. Please make sure that nothing else depends on this timePreferences."
						+ e.toString(), e);
			}
		}
	}
	
	private JPanel CreateExplanatoryPanel(){
		JPanel explanatoryPanel = new JPanel();
		
		return explanatoryPanel;
	}

	private JPanel CreateViewAllTimePreferencesPanel() {
		JPanel viewAllTimePreferencesPanel = new JPanel(new BorderLayout());
		viewAllTimePreferencesPanel.setBorder(CreateRaisedBevelTitledBorder("View All Time Preferences"));

		JScrollPane scrollPane = new JScrollPane();
		this.timePreferencesTable.setShowVerticalLines(true);
		this.timePreferencesTable.setShowHorizontalLines(true);
		this.timePreferencesTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.timePreferencesTable);

		viewAllTimePreferencesPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllTimePreferencesPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
