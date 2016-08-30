package timetablepuzzle.swing.windows.cards.other;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.RoomFeatureJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.RoomTypeJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.RoomFeatureDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.RoomTypeDAO;
import timetablepuzzle.eclipselink.entities.administration.RoomFeature;
import timetablepuzzle.eclipselink.entities.administration.RoomType;

public class RoomTypeCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(BuildingCard.class.getName());
	private static RoomTypeDAO roomTypeDAOService = new RoomTypeJPADAOService();
	private static RoomFeatureDAO roomFeatureDAOService = new RoomFeatureJPADAOService();

	private RoomTypeTableModel roomTypeTableModel;
	private JTable roomTypesTable;
	private JLabel notificationLabel;
	private JTextField textFieldName;
	private JTextField textFieldMinCapacity;
	private JTextField textFieldMaxCapacity;
	private JPanel transferableItemsPanel;
	private int idOfTheRoomTypeToUpdate;

	public RoomTypeCard(Color backgroundColor) {
		this.setBackground(backgroundColor);
		
		this.roomTypeTableModel = new RoomTypeTableModel();
		RefreshTable();
		this.roomTypesTable = new JTable(this.roomTypeTableModel);
		SetColumnsMaxSizes();
		
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		
		this.textFieldName = new JTextField(30);
		this.textFieldMinCapacity = new JFormattedTextField();
		this.textFieldMinCapacity.setColumns(20);
		this.textFieldMaxCapacity = new JFormattedTextField();
		this.textFieldMaxCapacity.setColumns(20);
		this.transferableItemsPanel = new JPanel();
		
		SetRoomTypeCardComponents();
	}

	private void RefreshTable() {
		this.roomTypeTableModel.setData(roomTypeDAOService.GetAll());
		this.roomTypeTableModel.fireTableDataChanged();
	}

	private void SetColumnsMaxSizes() {
		this.roomTypesTable.getColumnModel().getColumn(0).setMaxWidth(40);
		this.roomTypesTable.getColumnModel().getColumn(2).setMaxWidth(100);
		this.roomTypesTable.getColumnModel().getColumn(3).setMaxWidth(100);
	}

	private void RefreshTransferableItemsControl(List<RoomFeature> listDataForRightList) {
		List<RoomFeature> listDataForLeftList = roomFeatureDAOService.GetAll();
		for (RoomFeature data : listDataForRightList) {
			listDataForLeftList.remove(data);
		}
		ListBoxesWithTransferableItems control = new ListBoxesWithTransferableItems(
				listDataForLeftList.toArray(), listDataForRightList.toArray());

		this.transferableItemsPanel.removeAll();
		this.transferableItemsPanel.add(control);
		this.transferableItemsPanel.revalidate();
		this.transferableItemsPanel.repaint();
	}

	private void AddDocumentListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				CheckIfNumber();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				CheckIfNumber();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				CheckIfNumber();
			}
		});
	}

	private void CheckIfNumber() {
		String minTextContent = this.textFieldMinCapacity.getText();
		String maxTextContent = this.textFieldMaxCapacity.getText();

		if (!minTextContent.isEmpty() && !maxTextContent.isEmpty()) {
			try {
				Integer.parseInt(minTextContent);
				Integer.parseInt(maxTextContent);
				this.notificationLabel.setText("  ");
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the latitude and longitude fields contain valid coordinates.");
			}
		}
	}

	private void SetRoomTypeCardComponents() {
		this.setLayout(new BorderLayout());
		this.add(CreateCreateNewRoomTypePanel(), BorderLayout.NORTH);
		this.add(CreateViewAllRoomTypesPanel(), BorderLayout.CENTER);
	}

	private JPanel CreateCreateNewRoomTypePanel() {
		JPanel createNewRoomTypePanel = new JPanel();
		createNewRoomTypePanel.setLayout(new BoxLayout(createNewRoomTypePanel, BoxLayout.X_AXIS));
		createNewRoomTypePanel.add(CreatePropertiesPanel());
		createNewRoomTypePanel.add(CreateAddRoomFeaturesPanel());

		return createNewRoomTypePanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update room type"));

		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Minimum capacity", this.textFieldMinCapacity));
		propertiesPanel.add(CreatePropertyPanel("Maximum  capacity", this.textFieldMaxCapacity));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		return propertiesPanel;
	}

	private JPanel CreatePropertyPanel(String propertyName, JTextField propertyTextField) {
		JPanel propertyPanel = new JPanel();
		propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.X_AXIS));
		JLabel labelName = new JLabel(propertyName, JLabel.TRAILING);
		labelName.setLabelFor(propertyTextField);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel adjustmentPanel = new JPanel();
		propertyTextField.setHorizontalAlignment(JTextField.CENTER);
		adjustmentPanel.add(propertyTextField);
		propertyPanel.add(labelName);
		propertyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		propertyPanel.add(adjustmentPanel);

		return propertyPanel;
	}

	private JPanel CreateCrudButtonsPanel() {
		JPanel crudButtonsPanel = new JPanel();
		JButton buttonSaveNewRoomType = new JButton("Save");
		buttonSaveNewRoomType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNewRoomType();
			}
		});
		;
		JButton buttonEditSelectedRow = new JButton("Edit selected row");
		buttonEditSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadSelectedRowDetailsForEdit();
			}
		});
		;
		JButton buttonDeleteSelectedRow = new JButton("Delete selected row");
		buttonDeleteSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteSelectedRow();
			}
		});
		;

		crudButtonsPanel.add(buttonSaveNewRoomType);
		crudButtonsPanel.add(buttonEditSelectedRow);
		crudButtonsPanel.add(buttonDeleteSelectedRow);

		return crudButtonsPanel;
	}

	private void CreateAndSaveNewRoomType() {
		String name = this.textFieldName.getText();
		String min = this.textFieldMinCapacity.getText();
		String max = this.textFieldMaxCapacity.getText();

		if (name.isEmpty() || min.isEmpty() || max.isEmpty()) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new roomType with empty property field.");
		} else {
			try {
				int minCapacity = Integer.parseInt(min);
				int maxCapacity = Integer.parseInt(max);
				ListBoxesWithTransferableItems control = (ListBoxesWithTransferableItems)this.transferableItemsPanel.getComponent(1);
				List<Object> data = control.getRightListBoxData();
				List<RoomFeature> roomFeatures = new ArrayList<RoomFeature>();
				for(Object featureData : data)
				{
					roomFeatures.add((RoomFeature)featureData);
				}
				
				RoomType roomType = new RoomType();
				roomType.setName(name);
				roomType.setMinCapacity(minCapacity);
				roomType.setMaxCapacity(maxCapacity);
				roomType.setRoomFeatures(roomFeatures);

				// Save the building to the database
				if (this.idOfTheRoomTypeToUpdate != 0) {
					roomTypeDAOService.Update(this.idOfTheRoomTypeToUpdate, roomType);
					this.idOfTheRoomTypeToUpdate = 0;
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on roomType with id {0} and named {1}.",
							new Object[] { roomType.getId(), roomType.getName() });
				} else {
					roomTypeDAOService.persist(roomType);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on roomType with the following name: {0}. ",
							new Object[] { roomType.getName() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the Min Capacity and Max Capacity fields contain valid values.");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the roomType. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}

	private void ClearAllFields() {
		this.textFieldName.setText("");
		this.textFieldMinCapacity.setText("");
		this.textFieldMaxCapacity.setText("");
		this.notificationLabel.setText(" ");
		RefreshTransferableItemsControl(new ArrayList<RoomFeature>());
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.roomTypesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a roomType while no row was selected.");
		} else {
			RoomType existingRoomType = this.roomTypeTableModel.elementAt(selecteRow);
			List<RoomFeature> existingRoomFeatures = existingRoomType.getRoomFeatures();
			this.idOfTheRoomTypeToUpdate = existingRoomType.getId();
			this.textFieldName.setText(existingRoomType.getName());
			this.textFieldMinCapacity.setText(Integer.toString(existingRoomType.getMinCapacity()));
			this.textFieldMaxCapacity.setText(Integer.toString(existingRoomType.getMaxCapacity()));
			RefreshTransferableItemsControl(existingRoomFeatures);
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.roomTypesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a roomType while no row was selected.");
		} else {
			try {
				RoomType existingRoomType = this.roomTypeTableModel.elementAt(selecteRow);
				roomTypeDAOService.remove(existingRoomType);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on roomType with id {0} and named {1}. ",
						new Object[] { existingRoomType.getId(), existingRoomType.getName() });
			} catch (Exception e) {
				this.notificationLabel.setText("An error occured. Please try again.");
				LOGGER.log(Level.SEVERE, "Exception occured on deleting roomType. " + e.toString(), e);
			}
		}
	}
	
	private JPanel CreateAddRoomFeaturesPanel(){
		JPanel addRoomFeaturesPanel = new JPanel();
		addRoomFeaturesPanel.setLayout(new BoxLayout(addRoomFeaturesPanel, BoxLayout.Y_AXIS));
		addRoomFeaturesPanel.setBorder(CreateRaisedBevelTitledBorder("Add Room Features"));
		JTextField newFeatureTextField = new JTextField(20);
		JPanel newFeaturePanel = CreatePropertyPanel("Create new feature", newFeatureTextField);
		JButton createNewFeatureButton = new JButton("Save");
		createNewFeatureButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String feature = newFeatureTextField.getText();
				if(!feature.isEmpty()){
					RoomFeature roomFeature = new RoomFeature();
					roomFeature.setFeature(feature);
					roomFeatureDAOService.persist(roomFeature);
					newFeatureTextField.setText("");
					RefreshTransferableItemsControl(new ArrayList<RoomFeature>());
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on roomFeature with the following feature: {0}. ",
							new Object[] { roomFeature.getFeature() });
				}				
			}
		});
		newFeaturePanel.add(createNewFeatureButton);
		addRoomFeaturesPanel.add(newFeaturePanel);
		addRoomFeaturesPanel.add(this.transferableItemsPanel);
		
		return addRoomFeaturesPanel;
	}

	private JPanel CreateViewAllRoomTypesPanel() {
		JPanel viewAllBuildingsPanel = new JPanel(new BorderLayout());
		viewAllBuildingsPanel.setBorder(CreateRaisedBevelTitledBorder("All room types"));

		JScrollPane scrollPane = new JScrollPane();
		this.roomTypesTable.setShowVerticalLines(true);
		this.roomTypesTable.setShowHorizontalLines(true);
		this.roomTypesTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.roomTypesTable);

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
