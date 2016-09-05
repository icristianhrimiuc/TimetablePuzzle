package timetablepuzzle.swing.windows.cards.other;

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

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.RoomFeatureJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.RoomTypeJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.RoomFeatureDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.RoomTypeDAO;
import timetablepuzzle.eclipselink.entities.administration.RoomFeature;
import timetablepuzzle.eclipselink.entities.administration.RoomType;

public class RoomTypesCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(BuildingsCard.class.getName());
	private static RoomTypeDAO roomTypeDAOService = new RoomTypeJPADAOService();
	private static RoomFeatureDAO roomFeatureDAOService = new RoomFeatureJPADAOService();

	private RoomTypesTableModel roomTypeTableModel;
	private JTable roomTypesTable;
	private JLabel notificationLabel;
	private JTextField textFieldName;
	private JTextField textFieldMinCapacity;
	private JTextField textFieldMaxCapacity;
	private ListBoxesWithTransferableItems transferableItemsControl;
	private int idOfTheRoomTypeToUpdate;

	public RoomTypesCard(Color backgroundColor) {
		this.setBackground(backgroundColor);
		
		this.roomTypeTableModel = new RoomTypesTableModel();
		RefreshTable();
		this.roomTypesTable = new JTable(this.roomTypeTableModel);
		SetColumnsMaxSizes();
		
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		
		this.textFieldName = new JTextField(30);
		this.textFieldMinCapacity = new JTextField(10);
		AddDocumentListener(this.textFieldMinCapacity);
		this.textFieldMaxCapacity = new JTextField(10);
		AddDocumentListener(this.textFieldMaxCapacity);
		this.transferableItemsControl = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible room features", "Added room features");
		RefreshTransferableItemsControl(new ArrayList<RoomFeature>());
		
		SetRoomTypeCardComponents();
	}

	private void RefreshTable() {
		this.roomTypeTableModel.setData(roomTypeDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.roomTypesTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.roomTypesTable.getColumnModel().getColumn(2).setMaxWidth(120);
		this.roomTypesTable.getColumnModel().getColumn(3).setMaxWidth(120);
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
						.setText("Please make sure that the Min and Max Capacity fields contain only digits.");
			}
		}
	}

	private void RefreshTransferableItemsControl(List<RoomFeature> addedRoomFeatures) {
		List<RoomFeature> leftListRoomFeatures = roomFeatureDAOService.GetAll();
		List<RoomFeature> rightListRoomFeatures = new ArrayList<RoomFeature>();
		for(RoomFeature addedRoomFeature : addedRoomFeatures){
			leftListRoomFeatures.remove(addedRoomFeature);
			rightListRoomFeatures.add(addedRoomFeature);
		}
		leftListRoomFeatures.sort(Comparator.comparing(RoomFeature::toString));
		rightListRoomFeatures.sort(Comparator.comparing(RoomFeature::getFeature));
		this.transferableItemsControl.ReInitializeLists(leftListRoomFeatures.toArray(), rightListRoomFeatures.toArray());
	}

	private void SetRoomTypeCardComponents() {
		this.setLayout(new GridLayout(2,1));
		this.add(CreateCreateNewRoomTypePanel());
		this.add(CreateViewAllRoomTypesPanel());
	}

	private JPanel CreateCreateNewRoomTypePanel() {
		JPanel createNewRoomTypePanel = new JPanel(new GridLayout(1,2));
		createNewRoomTypePanel.add(CreatePropertiesPanel());
		createNewRoomTypePanel.add(CreateAddRoomFeaturesPanel());

		return createNewRoomTypePanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Minimum capacity", this.textFieldMinCapacity));
		propertiesPanel.add(CreatePropertyPanel("Maximum  capacity", this.textFieldMaxCapacity));
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update room type"));
		
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
				CreateAndSaveNewRoomType();
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
				CreateAndSaveNewRoomType();
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

	private void CreateAndSaveNewRoomType() {
		String name = this.textFieldName.getText();
		String min = this.textFieldMinCapacity.getText();
		String max = this.textFieldMaxCapacity.getText();

		if (name.isEmpty() || min.isEmpty() || max.isEmpty()) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new roomType with empty property field.");
		} else {
			try {
				// Get info
				int minCapacity = Integer.parseInt(min);
				int maxCapacity = Integer.parseInt(max);
				List<RoomFeature> roomFeatures = ConvertToListOfRoomFeatures(this.transferableItemsControl.GetRightListElements());
				
				// Create new entity
				RoomType roomType = new RoomType(this.idOfTheRoomTypeToUpdate, name, minCapacity, maxCapacity, roomFeatures);

				// Save the entity to the database
				if (this.idOfTheRoomTypeToUpdate != 0) {
					roomTypeDAOService.merge(roomType);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on roomType with id {0} and named {1}.",
							new Object[] { roomType.getId(), roomType.getName() });
				} else {
					roomTypeDAOService.merge(roomType);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on roomType with the following name: {0}. ",
							new Object[] { roomType.getName() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the Min Capacity and Max Capacity fields contain only digits.");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the roomType. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}

	private List<RoomFeature> ConvertToListOfRoomFeatures(List<Object> elements) {
		List<RoomFeature> roomFeatures = new ArrayList<RoomFeature>();
		for(Object element : elements)
		{
			roomFeatures.add((RoomFeature)element);
		}
		
		return roomFeatures;
	}

	private void ClearAllFields() {
		this.textFieldName.setText("");
		this.textFieldMinCapacity.setText("");
		this.textFieldMaxCapacity.setText("");
		this.notificationLabel.setText(" ");
		this.idOfTheRoomTypeToUpdate = 0;
		RefreshTransferableItemsControl(new ArrayList<RoomFeature>());
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.roomTypesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
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
			this.notificationLabel.setText("Please select a row from the table first.");
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
				this.notificationLabel.setText("An error occured. Please make sure that nothing else depends on this roomType."
						+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE, "Exception occured on deleting roomType. Please make sure that nothing else depends on this roomType."
						+ e.toString(), e);
			}
		}
	}
	
	private JPanel CreateAddRoomFeaturesPanel(){
		JPanel addRoomFeaturesPanel = new JPanel();
		addRoomFeaturesPanel.setLayout(new BoxLayout(addRoomFeaturesPanel, BoxLayout.Y_AXIS));
		addRoomFeaturesPanel.add(CreateNewFeaturePanel());
		addRoomFeaturesPanel.add(CreateDeleteRoomFeaturesPanel());
		
		JPanel transferableItemsPanel = new JPanel();
		transferableItemsPanel.add(this.transferableItemsControl);
		addRoomFeaturesPanel.add(transferableItemsPanel);
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(addRoomFeaturesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Add Room Features"));
		
		return adjustmentPanel;
	}

	private JPanel CreateNewFeaturePanel() {
		// TextField
		JTextField newFeatureTextField = new JTextField(20);
		newFeatureTextField.setHorizontalAlignment(JTextField.CENTER);
		newFeatureTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNewFeature(newFeatureTextField);
			}
		});
		
		// Label
		JLabel newFeatureLabel = new JLabel("Create new feature", JLabel.TRAILING);
		newFeatureLabel.setLabelFor(newFeatureTextField);
		newFeatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Button
		JButton newFeatureButton = new JButton("Save");
		newFeatureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				CreateAndSaveNewFeature(newFeatureTextField);
			}
		});
		
		// Panel
		JPanel newFeaturePanel = new JPanel();
		newFeaturePanel.add(newFeatureLabel);
		newFeaturePanel.add(Box.createRigidArea(new Dimension(5, 0)));
		newFeaturePanel.add(newFeatureTextField);
		newFeaturePanel.add(newFeatureButton);
		
		return newFeaturePanel;
	}

	private void CreateAndSaveNewFeature(JTextField newFeatureTextField) {
		String feature = newFeatureTextField.getText();
		if(!feature.isEmpty()){
			feature = feature.substring(0, 1).toUpperCase() + feature.substring(1);
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
	
	private JPanel CreateDeleteRoomFeaturesPanel(){
		JButton deleteRoomFeaturesButton  =new JButton("Delete the selected room features permanently");
		deleteRoomFeaturesButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Object> leftSelectedElements = transferableItemsControl.GetLeftListSelectedElements();
				List<Object> rightSelectedElements = transferableItemsControl.GetRightListSelectedElements();
				List<RoomFeature> leftSelectedRoomFeatures = ConvertToListOfRoomFeatures(leftSelectedElements);
				List<RoomFeature> rightSelectedRoomFeatures = ConvertToListOfRoomFeatures(rightSelectedElements);
				DeleteRoomFeatures(leftSelectedRoomFeatures);
				DeleteRoomFeatures(rightSelectedRoomFeatures);
			}
		});

		JPanel deleteRoomFeaturesPanel = new JPanel();
		deleteRoomFeaturesPanel.add(deleteRoomFeaturesButton);
		
		return deleteRoomFeaturesPanel;
	}
	
	private void DeleteRoomFeatures(List<RoomFeature> roomFeatures){
		try{
			for(RoomFeature roomFeature : roomFeatures){
				roomFeatureDAOService.remove(roomFeature);
			}			
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "An error occured. Please make sure that nothing else depends on this roomFeature."
					+ " Check log files for more info.");
			LOGGER.log(Level.SEVERE, "An error occured. Please make sure that nothing else depends on this roomFeature.", e);
		}
		RefreshTransferableItemsControl(new ArrayList<RoomFeature>());
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
