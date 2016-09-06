package timetablepuzzle.swing.windows.cards.inputdata.rooms;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.RoomJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.BuildingJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.RoomTypeJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.TimePreferencesJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.BuildingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.RoomTypeDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.TimePreferencesDAO;
import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.entities.other.Building;
import timetablepuzzle.entities.other.RoomType;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.swing.windows.cards.other.timePreferences.WeekPreferencesCellRenderer;
import timetablepuzzle.swing.windows.cards.other.timePreferences.WeekPreferencesTableModel;

public class RoomsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(RoomsCard.class.getName());
	private static RoomDAO roomDAOService = new RoomJPADAOService();
	private static RoomTypeDAO roomTypeDAOService = new RoomTypeJPADAOService();
	private static BuildingDAO buildingDAOService = new BuildingJPADAOService();
	private static TimePreferencesDAO timePreferencesDAOService = new TimePreferencesJPADAOService();

	private RoomsTableModel roomsTableModel;
	private JTable roomsTable;
	private JTextField textFieldName;
	private JTextField textFieldCode;
	private JTextField textFieldCapacity;
	private CustomComboBoxModel<RoomType> comboBoxRoomTypeModel;
	private JComboBox<RoomType> comboBoxRoomType;
	private CustomComboBoxModel<Building> comboBoxBuildingModel;
	private JComboBox<Building> comboBoxBuilding;
	private CustomComboBoxModel<TimePreferences> comboBoxTimePreferencesModel;
	private JComboBox<TimePreferences> comboBoxTimePreferences;
	private JLabel notificationLabel;
	private WeekPreferencesTableModel weekPreferencesTableModel;
	private JTable weekPreferencesTable;
	private SimpleDateFormat dateFormat;
	private int idOfTheRoomToUpdate;

	public RoomsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Rooms table
		this.roomsTableModel = new RoomsTableModel();
		RefreshTable();
		this.roomsTable = new JTable(this.roomsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.roomsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);
		this.textFieldCode = CreatePropertyTextField(10);
		this.textFieldCapacity = CreatePropertyTextField(10);
		AddDocumentListener(this.textFieldCapacity);

		// RoomType combo box
		this.comboBoxRoomTypeModel = new CustomComboBoxModel<RoomType>();
		RefreshComboBoxRoomType();
		this.comboBoxRoomType = new JComboBox<RoomType>(this.comboBoxRoomTypeModel);

		// Building combo box
		this.comboBoxBuildingModel = new CustomComboBoxModel<Building>();
		RefreshComboBoxBuilding();
		this.comboBoxBuilding = new JComboBox<Building>(this.comboBoxBuildingModel);

		// TimePreferences combo box
		this.comboBoxTimePreferencesModel = new CustomComboBoxModel<TimePreferences>();
		RefreshComboBoxTimePreferences();
		this.comboBoxTimePreferences = new JComboBox<TimePreferences>(this.comboBoxTimePreferencesModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		// Week Preferences table
		this.weekPreferencesTableModel = new WeekPreferencesTableModel();
		RefreshTableWeekPreferences();
		this.weekPreferencesTable = new JTable(this.weekPreferencesTableModel);
		ConfigureWeekPreferencesTable();

		this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		this.idOfTheRoomToUpdate = 0;
		SetRoomsCardComponents();
	}

	private void RefreshTable() {
		this.roomsTableModel.setData(roomDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.roomsTable.getColumnModel().getColumn(0).setMaxWidth(60);
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
						.setText("Please make sure that the capacity field contains only digits.");
			}
		}
	}

	private void RefreshComboBoxRoomType() {
		this.comboBoxRoomTypeModel.setData(roomTypeDAOService.GetAll());
		this.repaint();
	}

	private void RefreshComboBoxBuilding() {
		this.comboBoxBuildingModel.setData(buildingDAOService.GetAll());
		this.repaint();
	}

	private void RefreshComboBoxTimePreferences() {
		List<TimePreferences> timePreferences = timePreferencesDAOService.GetAll();
		TimePreferences createNew = new TimePreferences();
		createNew.setName("Create new");
		timePreferences.add(0, createNew);
		this.comboBoxTimePreferencesModel.setData(timePreferences);
		this.comboBoxTimePreferencesModel.setSelectedItem(createNew);
		this.repaint();
	}

	private void RefreshTableWeekPreferences() {
		this.weekPreferencesTableModel.setData((TimePreferences) this.comboBoxTimePreferences.getSelectedItem());
	}

	private void ConfigureWeekPreferencesTable() {
		this.weekPreferencesTable.setRowHeight(32);
		for (int i = 0; i <= 12; i++) {
			this.weekPreferencesTable.getColumnModel().getColumn(i).setMaxWidth(36);
		}
		WeekPreferencesCellRenderer cellRenderer = new WeekPreferencesCellRenderer();
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

	private void SetRoomsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewRoomPanel());
		this.add(CreateViewAllRoomsPanel());
	}

	private JPanel CreateCreateNewRoomPanel() {
		JPanel createNewRoomPanel = new JPanel(new GridLayout(1, 2));
		createNewRoomPanel.add(CreatePropertiesPanel());
		createNewRoomPanel.add(CreateWeekPreferencesPanel());

		return createNewRoomPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Code", this.textFieldCode));
		propertiesPanel.add(CreatePropertyPanel("Capacity", this.textFieldCapacity));
		propertiesPanel.add(CreatePropertyPanel("Room Type", this.comboBoxRoomType));
		propertiesPanel.add(CreatePropertyPanel("Building", this.comboBoxBuilding));
		propertiesPanel.add(CreatePropertyPanel("Time Preferences", this.comboBoxTimePreferences));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Room"));

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
		weekPreferencesPanel.add(CreateShortExplanatoryPanelPanel());

		// Adjust panel on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(weekPreferencesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Room Time Preferences"));

		return adjustmentPanel;
	}

	private JPanel CreateShortExplanatoryPanelPanel() {
		JPanel explanatoryPanel = new JPanel();
		explanatoryPanel.setLayout(new BoxLayout(explanatoryPanel, BoxLayout.X_AXIS));
		for (int i = 0; i < TimePreferences.TimePreference.values().length; i++) {
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
		String roomName = this.textFieldName.getText();
		String code = this.textFieldCode.getText();
		String capacity = this.textFieldCapacity.getText();
		RoomType roomType = (RoomType) this.comboBoxRoomType.getSelectedItem();
		Building building = (Building) this.comboBoxBuilding.getSelectedItem();

		if ((roomName.isEmpty()) || (code.isEmpty()) || (capacity.isEmpty()) || (roomType == null)
				|| (building == null)) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new room with empty property field.");
		} else {
			try {
				int cap = Integer.parseInt(capacity);
				TimePreferences timePreferences = this.weekPreferencesTableModel.getData();
				String name = String.format("Preference for %s(%s)", code, this.dateFormat.format(new Date()));
				timePreferences.setName(name);

				// Create new room
				Room room = new Room(this.idOfTheRoomToUpdate, roomName, code, cap, roomType, building,
						timePreferences);

				if (this.idOfTheRoomToUpdate != 0) {
					roomDAOService.merge(room);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on room with id {0} and code {1}.",
							new Object[] { room.getId(), room.getCode() });
				} else {
					roomDAOService.merge(room);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on room  with code {1}. ", new Object[] { room.getCode() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel.setText("Please make sure that the capacity field contains only digits!");
				LOGGER.log(Level.SEVERE, "Attempt to create a new room with unvalid properties." + e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel.setText("A error occured while saving the room. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the room" + e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.roomsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a room while no row was selected.");
		} else {
			Room existingRoom = this.roomsTableModel.elementAt(selecteRow);
			this.idOfTheRoomToUpdate = existingRoom.getId();
			this.textFieldName.setText(existingRoom.getName());
			this.textFieldCode.setText(existingRoom.getCode());
			this.textFieldCapacity.setText(Integer.toString(existingRoom.getCapacity()));
			this.comboBoxRoomType.setSelectedItem(existingRoom.getType());
			this.comboBoxBuilding.setSelectedItem(existingRoom.getBuilding());
			TimePreferences existingTimePreferences = DeepCopyTimePreferences(existingRoom.getTimePreferences());
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
		int selecteRow = this.roomsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a room while no row was selected.");
		} else {
			try {
				Room existingRoom = this.roomsTableModel.elementAt(selecteRow);
				roomDAOService.remove(existingRoom);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on room with id {0} and named {1}.",
						new Object[] { existingRoom.getId(), existingRoom.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this room."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting room. Please make sure that nothing else depends on this room."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.textFieldCode.setText("");
		this.textFieldCapacity.setText("");
		RefreshComboBoxRoomType();
		RefreshComboBoxBuilding();
		RefreshComboBoxTimePreferences();
		this.notificationLabel.setText("  ");
		RefreshTableWeekPreferences();
		this.idOfTheRoomToUpdate = 0;
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

	private JPanel CreateViewAllRoomsPanel() {
		JPanel viewAllRoomsPanel = new JPanel(new BorderLayout());
		viewAllRoomsPanel.setBorder(CreateRaisedBevelTitledBorder("View All Rooms"));

		JScrollPane scrollPane = new JScrollPane();
		this.roomsTable.setShowVerticalLines(true);
		this.roomsTable.setShowHorizontalLines(true);
		this.roomsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.roomsTable);

		viewAllRoomsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllRoomsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
