package timetablepuzzle.swing.windows.cards.administration.instructorMeetings;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.InstructorMeetingsJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.InstructorJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.RoomJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.InstructorMeetingsDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.InstructorDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
import timetablepuzzle.entities.administration.InstructorMeetings;
import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;

public class InstructorMeetingsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(InstructorMeetingsCard.class.getName());
	private static InstructorMeetingsDAO instructorMeetingsDAOService = new InstructorMeetingsJPADAOService();
	private static InstructorDAO instructorDAOService = new InstructorJPADAOService();
	private static RoomDAO roomDAOService = new RoomJPADAOService();

	private InstructorMeetingsTableModel instructorMeetingsTableModel;
	private JTable instructorsMeetingsTable;
	private JLabel notificationLabel;
	private CustomComboBoxModel<Instructor> comboBoxInstructorModel;
	private JComboBox<Instructor> comboBoxInstructor;
	private CustomComboBoxModel<Room> comboBoxRoomModel;
	private JComboBox<Room> comboBoxRoom;
	private JTextField textFieldNrOfMeetings;
	private int idOfTheInstructorMeetingsToUpdate;

	public InstructorMeetingsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Instructor Meetings table
		this.instructorMeetingsTableModel = new InstructorMeetingsTableModel();
		RefreshTable();
		this.instructorsMeetingsTable = new JTable(this.instructorMeetingsTableModel);
		SetColumnsMaxSizes();

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

		this.textFieldNrOfMeetings = new JTextField(10);
		this.textFieldNrOfMeetings.setHorizontalAlignment(JTextField.CENTER);
		this.textFieldNrOfMeetings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNew();
			}
		});
		AddDocumentListener(this.textFieldNrOfMeetings);

		SetInstructorMeetingsCardComponents();
	}

	private void RefreshTable() {
		this.instructorMeetingsTableModel.setData(instructorMeetingsDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.instructorsMeetingsTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.instructorsMeetingsTable.getColumnModel().getColumn(3).setMaxWidth(60);
	}

	private void RefreshComboBoxInstructor() {
		this.comboBoxInstructorModel.setData(instructorDAOService.GetAll());
	}

	private void RefreshComboBoxRoom() {
		this.comboBoxRoomModel.setData(roomDAOService.GetAll());
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
						.setText("Please make sure that the number of meetings field contains only digits.");
			}
		}
	}

	private void SetInstructorMeetingsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewInstructorMeetingsPanel());
		this.add(CreateViewAllInstructorMeetingsPanel());
	}

	private JPanel CreateCreateNewInstructorMeetingsPanel() {
		JPanel createNewInstructorMeetingsPanel = new JPanel(new GridLayout(1, 1));
		createNewInstructorMeetingsPanel.add(CreatePropertiesPanel());

		return createNewInstructorMeetingsPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Instructor", this.comboBoxInstructor));
		propertiesPanel.add(CreatePropertyPanel("Room", this.comboBoxRoom));
		propertiesPanel.add(CreatePropertyPanel("Nr. of meetings", this.textFieldNrOfMeetings));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Instructor Meetings"));

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
		Instructor instructor = (Instructor) this.comboBoxInstructor.getSelectedItem();
		Room room = (Room) this.comboBoxRoom.getSelectedItem();
		String nrOfMeetings = this.textFieldNrOfMeetings.getText();

		if ((instructor == null) || (room == null) || (nrOfMeetings.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new instructorMeetings with empty property field.");
		} else {
			try {
				// Get info
				int nrOfMeetingsPerInstructor = Integer.parseInt(nrOfMeetings);

				// Create new entity
				InstructorMeetings instructorMeetings = new InstructorMeetings(this.idOfTheInstructorMeetingsToUpdate,
						nrOfMeetingsPerInstructor, instructor, room);

				// Save the entity to the database
				if (this.idOfTheInstructorMeetingsToUpdate != 0) {
					instructorMeetingsDAOService.merge(instructorMeetings);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE,
							"Update performed on instructorMeetings with id {0}, instructor {1}, and room {2}.",
							new Object[] { instructorMeetings.getId(), instructor.toString(), room.toString() });
				} else {
					instructorMeetingsDAOService.merge(instructorMeetings);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE,
							"Create performed on instructorMeetings with id {0}, instructor {1}, and room {2}.",
							new Object[] { instructorMeetings.getId(), instructor.toString(), room.toString() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the instructorMeetings. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.instructorsMeetingsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a instructorMeetings while no row was selected.");
		} else {
			InstructorMeetings existingInstructorMeetings = this.instructorMeetingsTableModel.elementAt(selecteRow);
			this.idOfTheInstructorMeetingsToUpdate = existingInstructorMeetings.getId();
			this.comboBoxInstructor.setSelectedItem(existingInstructorMeetings.getInstructor());
			this.comboBoxRoom.setSelectedItem(existingInstructorMeetings.getRoom());
			this.textFieldNrOfMeetings.setText(Integer.toString(existingInstructorMeetings.getNrOfMeetings()));
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.instructorsMeetingsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a instructorMeetings while no row was selected.");
		} else {
			try {
				InstructorMeetings existingInstructorMeetings = this.instructorMeetingsTableModel.elementAt(selecteRow);
				instructorMeetingsDAOService.remove(existingInstructorMeetings);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE,
						"Delete performed on instructorMeetings with id {0}, instructor {1}, and room {2}.",
						new Object[] { existingInstructorMeetings.getId(),
								existingInstructorMeetings.getInstructor().toString(),
								existingInstructorMeetings.getRoom().toString() });
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

	private void RefreshAllFields() {
		this.comboBoxInstructor.setSelectedIndex(-1);
		this.comboBoxRoom.setSelectedIndex(-1);
		this.textFieldNrOfMeetings.setText("");
		this.notificationLabel.setText("  ");
		this.idOfTheInstructorMeetingsToUpdate = 0;
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

	private JPanel CreateViewAllInstructorMeetingsPanel() {
		JPanel viewAllInstructorMeetingsPanel = new JPanel(new BorderLayout());
		viewAllInstructorMeetingsPanel.setBorder(CreateRaisedBevelTitledBorder("All Instructor Meetings"));

		JScrollPane scrollPane = new JScrollPane();
		this.instructorsMeetingsTable.setShowVerticalLines(true);
		this.instructorsMeetingsTable.setShowHorizontalLines(true);
		this.instructorsMeetingsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.instructorsMeetingsTable);

		viewAllInstructorMeetingsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllInstructorMeetingsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
