package timetablepuzzle.swing.windows.cards.inputdata.offerings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
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

import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.InstructorJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.OfferingJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.RoomJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.InstructorDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.OfferingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.InstructorMeetings;
import timetablepuzzle.entities.inputdata.Offering;
import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.entities.inputdata.Offering.DatePattern;
import timetablepuzzle.entities.inputdata.Offering.OfferingType;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;

public class OfferingsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(OfferingsCard.class.getName());
	private static OfferingDAO offeringDAOService = new OfferingJPADAOService();
	private static InstructorDAO instructorDAOService = new InstructorJPADAOService();
	private static RoomDAO roomDAOService = new RoomJPADAOService();

	private OfferingsTableModel offeringsTableModel;
	private JTable offeringsTable;
	private JTextField textFieldName;
	private JTextField textFieldNrOfTimeSlots;
	private CustomComboBoxModel<OfferingType> comboBoxOfferingTypeModel;
	private JComboBox<OfferingType> comboBoxOfferingType;
	private CustomComboBoxModel<DatePattern> comboBoxDatePatternModel;
	private JComboBox<DatePattern> comboBoxDatePattern;
	private JLabel notificationLabel;
	private CustomComboBoxModel<Instructor> comboBoxInstructorModel;
	private JComboBox<Instructor> comboBoxInstructor;
	private CustomComboBoxModel<Room> comboBoxRoomModel;
	private JComboBox<Room> comboBoxRoom;
	private JTextField textFieldNrOfMeetings;
	private DefaultListModel<InstructorMeetings> instructorMeetingsListModel;
	private JList<InstructorMeetings> instructorMeetingsList;
	private int idOfTheOfferingToUpdate;

	public OfferingsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Offerings table
		this.offeringsTableModel = new OfferingsTableModel();
		RefreshTable();
		this.offeringsTable = new JTable(this.offeringsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.offeringsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);
		this.textFieldNrOfTimeSlots = CreatePropertyTextField(10);
		AddDocumentListener(this.textFieldNrOfTimeSlots);

		// OfferingType combo box
		this.comboBoxOfferingTypeModel = new CustomComboBoxModel<OfferingType>();
		RefreshComboBoxOfferingType();
		this.comboBoxOfferingType = new JComboBox<OfferingType>(this.comboBoxOfferingTypeModel);

		// Building combo box
		this.comboBoxDatePatternModel = new CustomComboBoxModel<DatePattern>();
		RefreshComboBoxDatePattern();
		this.comboBoxDatePattern = new JComboBox<DatePattern>(this.comboBoxDatePatternModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		// Instructor combo box
		this.comboBoxInstructorModel = new CustomComboBoxModel<Instructor>();
		RefreshComboBoxInstructor();
		this.comboBoxInstructor = new JComboBox<Instructor>(this.comboBoxInstructorModel);

		// Room combo box
		this.comboBoxRoomModel = new CustomComboBoxModel<Room>();
		RefreshComboBoxRoom();
		this.comboBoxRoom = new JComboBox<Room>(this.comboBoxRoomModel);

		this.textFieldNrOfMeetings = CreatePropertyTextField(5);
		AddDocumentListener(this.textFieldNrOfMeetings);
		
		// Instructor Meetings list
		this.instructorMeetingsListModel = new DefaultListModel<InstructorMeetings>();
		RefreshListInstructorMeetings();
		this.instructorMeetingsList = new JList<InstructorMeetings>(this.instructorMeetingsListModel);

		this.idOfTheOfferingToUpdate = 0;
		SetOfferingsCardComponents();
	}

	private void RefreshTable() {
		this.offeringsTableModel.setData(offeringDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.offeringsTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.offeringsTable.getColumnModel().getColumn(1).setMinWidth(150);
		this.offeringsTable.getColumnModel().getColumn(1).setMaxWidth(150);
		this.offeringsTable.getColumnModel().getColumn(2).setMinWidth(100);
		this.offeringsTable.getColumnModel().getColumn(2).setMaxWidth(100);
		this.offeringsTable.getColumnModel().getColumn(3).setMinWidth(90);
		this.offeringsTable.getColumnModel().getColumn(3).setMaxWidth(90);
		this.offeringsTable.getColumnModel().getColumn(4).setMinWidth(90);
		this.offeringsTable.getColumnModel().getColumn(4).setMaxWidth(90);
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
						.setText("Please make sure that the Nr. of Time Slots field contains only digits.");
			}
		}
	}

	private void RefreshComboBoxOfferingType() {
		this.comboBoxOfferingTypeModel.setData(Arrays.asList(Offering.OfferingType.values()));
		this.repaint();
	}

	private void RefreshComboBoxDatePattern() {
		this.comboBoxDatePatternModel.setData(Arrays.asList(Offering.DatePattern.values()));
		this.repaint();
	}

	private void RefreshComboBoxInstructor() {
		this.comboBoxInstructorModel.setData(instructorDAOService.GetAll());
	}

	private void RefreshComboBoxRoom() {
		this.comboBoxRoomModel.setData(roomDAOService.GetAll());
	}

	private void RefreshListInstructorMeetings() {
		this.instructorMeetingsListModel.clear();		
	}

	private void SetOfferingsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewOfferingPanel());
		this.add(CreateViewAllOfferingsPanel());
	}

	private JPanel CreateCreateNewOfferingPanel() {
		JPanel createNewOfferingPanel = new JPanel(new GridLayout(1, 2));
		createNewOfferingPanel.add(CreatePropertiesPanel());
		createNewOfferingPanel.add(CreateInstructorMeetingsPanel());

		return createNewOfferingPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Nr. of Time Slots", this.textFieldNrOfTimeSlots));
		propertiesPanel.add(CreatePropertyPanel("Offering Type", this.comboBoxOfferingType));
		propertiesPanel.add(CreatePropertyPanel("Date Pattern", this.comboBoxDatePattern));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Offering"));

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
		String offeringName = this.textFieldName.getText();
		String number = this.textFieldNrOfTimeSlots.getText();
		OfferingType offeringType = (OfferingType) this.comboBoxOfferingType.getSelectedItem();
		DatePattern datePattern = (DatePattern) this.comboBoxDatePattern.getSelectedItem();
		List<InstructorMeetings> instructorMeetings = Collections.list(this.instructorMeetingsListModel.elements()); 

		if ((offeringName.isEmpty()) || (number.isEmpty()) || (offeringType == null) || (datePattern == null) || (instructorMeetings.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new offering with empty property field.");
		} else {
			try {
				int nrOfTimeSlots = Integer.parseInt(number);

				// Create new offering
				Offering offering = new Offering(this.idOfTheOfferingToUpdate, offeringName, nrOfTimeSlots,
						offeringType, datePattern, instructorMeetings);

				if (this.idOfTheOfferingToUpdate != 0) {
					offeringDAOService.merge(offering);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on offering with id {0} and name {1}.",
							new Object[] { offering.getId(), offering.getName() });
				} else {
					offeringDAOService.merge(offering);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on offering  with name {1}. ",
							new Object[] { offering.getName() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel.setText("Please make sure that the capacity field contains only digits!");
				LOGGER.log(Level.SEVERE, "Attempt to create a new offering with unvalid properties." + e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the offering. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the offering" + e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.offeringsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a offering while no row was selected.");
		} else {
			Offering existingOffering = this.offeringsTableModel.elementAt(selecteRow);
			this.idOfTheOfferingToUpdate = existingOffering.getId();
			this.textFieldName.setText(existingOffering.getName());
			this.textFieldNrOfTimeSlots.setText(Integer.toString(existingOffering.getNrOfTimeSlots()));
			this.comboBoxOfferingType.setSelectedItem(existingOffering.getType());
			this.comboBoxDatePattern.setSelectedItem(existingOffering.getDatePattern());
			AddToInstructorMeetingsList(existingOffering.getNrOfMeetingsPerInstructor());
			this.repaint();
		}
	}
	
	private void AddToInstructorMeetingsList(List<InstructorMeetings> instructorMeetings){
		RefreshListInstructorMeetings();
		for(InstructorMeetings meetings : instructorMeetings){
			this.instructorMeetingsListModel.addElement(meetings);
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.offeringsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a offering while no row was selected.");
		} else {
			try {
				Offering existingOffering = this.offeringsTableModel.elementAt(selecteRow);
				offeringDAOService.remove(existingOffering);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on offering with id {0} and named {1}.",
						new Object[] { existingOffering.getId(), existingOffering.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this offering."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting offering. Please make sure that nothing else depends on this offering."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.textFieldNrOfTimeSlots.setText("");
		RefreshComboBoxOfferingType();
		RefreshComboBoxDatePattern();
		this.notificationLabel.setText("  ");
		RefreshInstructorMeetingsPanel();
		RefreshListInstructorMeetings();
		this.idOfTheOfferingToUpdate = 0;
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

	private Component CreateInstructorMeetingsPanel() {		
		JPanel instructorMeetingsPanel = new JPanel();
		instructorMeetingsPanel.setLayout(new BoxLayout(instructorMeetingsPanel, BoxLayout.Y_AXIS));
		instructorMeetingsPanel.add(CreateNewInstructorMeetingsPanel());
		instructorMeetingsPanel.add(CreateInstructorMeetingsListPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(instructorMeetingsPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Add Instructor Meetings"));

		return adjustmentPanel;
	}

	private Component CreateNewInstructorMeetingsPanel() {

		JPanel newInstructorMeetingsPanel = new JPanel();
		newInstructorMeetingsPanel.setLayout(new BoxLayout(newInstructorMeetingsPanel, BoxLayout.Y_AXIS));
		newInstructorMeetingsPanel.add(CreatePropertyPanel("Instructor", this.comboBoxInstructor));
		newInstructorMeetingsPanel.add(CreatePropertyPanel("Room", this.comboBoxRoom));
		newInstructorMeetingsPanel.add(CreatePropertyPanel("Nr. of Meetings", this.textFieldNrOfMeetings));
		newInstructorMeetingsPanel.add(CreateInstructorMeetingsButtonsPanel());

		return newInstructorMeetingsPanel;
	}
	
	private JPanel CreateInstructorMeetingsButtonsPanel(){
		JPanel instructorMeetingsButtonsPanel = new JPanel();
		JButton buttonSaveNewInstructorMeetings= new JButton("Add");
		buttonSaveNewInstructorMeetings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewInstructorMeetings();
			}
		});
		JButton buttonDeleteSelectedInstructorMeetings = new JButton("Remove selected");
		buttonDeleteSelectedInstructorMeetings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveSelectedInstructorMeetings();
			}
		});
		
		// Add buttons
		instructorMeetingsButtonsPanel.add(buttonSaveNewInstructorMeetings);
		instructorMeetingsButtonsPanel.add(buttonDeleteSelectedInstructorMeetings);
		
		return instructorMeetingsButtonsPanel;
	}

	private Component CreateInstructorMeetingsListPanel() {
		JPanel instructorMeetingsListPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(this.instructorMeetingsList);
		instructorMeetingsListPanel.add(scrollPane);
		
		return instructorMeetingsListPanel;
	}

	private void AddNewInstructorMeetings() {
		Instructor instructor = (Instructor) this.comboBoxInstructor.getSelectedItem();
		Room room = (Room) this.comboBoxRoom.getSelectedItem();
		String number = this.textFieldNrOfMeetings.getText();

		if ((instructor == null) || (room == null) || (number.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new instructorMeetings with empty property field.");
		} else {
			try {
				// Get info
				int nrOfMeetings = Integer.parseInt(number);
				InstructorMeetings instructorMeetings = new InstructorMeetings(0, nrOfMeetings, instructor, room);
				RefreshInstructorMeetingsPanel();
				this.instructorMeetingsListModel.addElement(instructorMeetings);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the instructorMeetings. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}
	
	private void RemoveSelectedInstructorMeetings() {
		InstructorMeetings selecteItem = this.instructorMeetingsList.getSelectedValue();
		if (selecteItem == null) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a instructorMeetings while no row was selected.");
		} else {
			try {
				this.instructorMeetingsListModel.removeElement(selecteItem);
				this.notificationLabel.setText(" ");
				this.repaint();
			} catch (Exception e) {
				this.notificationLabel.setText(
						"An error occured. Please make sure that nothing else depends on this instructorMeetings."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting instructorMeetings. Please make sure that nothing else depends on this instructorMeetings."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshInstructorMeetingsPanel() {
		this.notificationLabel.setText("  ");
		RefreshComboBoxInstructor();
		RefreshComboBoxRoom();
		this.textFieldNrOfMeetings.setText("");
	}

	private JPanel CreateViewAllOfferingsPanel() {
		JPanel viewAllOfferingsPanel = new JPanel(new BorderLayout());
		viewAllOfferingsPanel.setBorder(CreateRaisedBevelTitledBorder("View All Offerings"));

		JScrollPane scrollPane = new JScrollPane();
		this.offeringsTable.setShowVerticalLines(true);
		this.offeringsTable.setShowHorizontalLines(true);
		this.offeringsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.offeringsTable);

		viewAllOfferingsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllOfferingsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
