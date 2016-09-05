package timetablepuzzle.swing.windows.cards.other;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.BuildingJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.BuildingDAO;
import timetablepuzzle.eclipselink.entities.administration.Building;
import timetablepuzzle.eclipselink.entities.administration.Location;

public class BuildingsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(BuildingsCard.class.getName());
	private static BuildingDAO buildingsDAOService = new BuildingJPADAOService();

	private GoogleStaticMapsURLBuilder staticMapUrlBuilder;
	private BuildingsTableModel buildingsTableModel;
	private JTable buildingsTable;
	private JLabel labelMap;
	private JLabel notificationLabel;
	private JTextField textFieldName;
	private JTextField textFieldAbbreviation;
	private JTextField textFieldAddress;
	private JTextField textFieldLatitude;
	private JTextField textFieldLongitude;
	private int idOfTheBuildingToUpdate;

	public BuildingsCard(Color backgroundColor) {
		SetupBuildingCard(backgroundColor);
		this.staticMapUrlBuilder = new GoogleStaticMapsURLBuilder();
		this.buildingsTableModel = new BuildingsTableModel();
		RefreshTable();
		this.buildingsTable = new JTable(this.buildingsTableModel);
		SetColumnsMaxSizes();
		this.labelMap = new JLabel();
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.textFieldName = new JTextField(30);
		this.textFieldAbbreviation = new JTextField(10);
		this.textFieldAddress = new JTextField(30);
		this.textFieldLatitude = new JTextField(20);
		AddDocumentListener(this.textFieldLatitude);
		this.textFieldLongitude = new JTextField(20);
		AddDocumentListener(textFieldLongitude);
		this.idOfTheBuildingToUpdate = 0;
		SetBuildingCardComponents();
	}
	
	private void SetupBuildingCard(Color backgroundColor){
		this.setBackground(backgroundColor);
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e){
				String height = Integer.toString(e.getComponent().getHeight()/2 - 40);
				String width = Integer.toString(e.getComponent().getWidth()/2 - 60);
				String size =  width + "x" + height;
				staticMapUrlBuilder.setSize(size);
				RefreshMap();
			}			
		});
	}

	private void RefreshTable() {
		this.buildingsTableModel.setData(buildingsDAOService.GetAll());
	}

	private void SetColumnsMaxSizes(){
		this.buildingsTable.getColumnModel().getColumn(0).setMaxWidth(40);
		this.buildingsTable.getColumnModel().getColumn(2).setMaxWidth(150);
		this.buildingsTable.getColumnModel().getColumn(4).setMaxWidth(120);
		this.buildingsTable.getColumnModel().getColumn(5).setMaxWidth(120);
	}
	private void AddDocumentListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				LoadMapCoordinates();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				LoadMapCoordinates();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				LoadMapCoordinates();
			}
		});
	}

	private void LoadMapCoordinates() {
		String latitude = this.textFieldLatitude.getText();
		String longitude = this.textFieldLongitude.getText();
		if (!latitude.isEmpty() && !longitude.isEmpty()) {
			try {
				Double.parseDouble(latitude);
				Double.parseDouble(longitude);
				this.staticMapUrlBuilder.setCenterLatitude(latitude);
				this.staticMapUrlBuilder.setCenterLongitude(longitude);
				this.notificationLabel.setText("  ");
				RefreshMap();
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the latitude and longitude fields contain valid coordinates.");
			}
		}
	}

	private void SetBuildingCardComponents() {
		this.setLayout(new GridLayout(2,1));
		this.add(CreateCreateNewBuildingPanel());
		this.add(CreateViewAllBuildingsPanel());
	}

	private JPanel CreateCreateNewBuildingPanel() {
		JPanel createNewBuildingPanel = new JPanel(new GridLayout(1, 2));
		createNewBuildingPanel.add(CreatePropertiesPanel());
		createNewBuildingPanel.add(CreateStaticGoogleMapPanel());

		return createNewBuildingPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));

		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Abbreviation", this.textFieldAbbreviation));
		propertiesPanel.add(CreatePropertyPanel("Address", this.textFieldAddress));
		propertiesPanel.add(CreatePropertyPanel("Latitude", this.textFieldLatitude));
		propertiesPanel.add(CreatePropertyPanel("Longitude", this.textFieldLongitude));
		propertiesPanel.add(this.notificationLabel);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		propertiesPanel.add(CreateCrudButtonsPanel());
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update building"));
		
		return adjustmentPanel;
	}

	private JPanel CreatePropertyPanel(String propertyName, JTextField propertyTextField) {
		JPanel propertyPanel = new JPanel();
		JLabel labelName = new JLabel(propertyName, JLabel.TRAILING);
		labelName.setLabelFor(propertyTextField);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		propertyTextField.setHorizontalAlignment(JTextField.CENTER);
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
				CreateAndSaveNewBuilding();
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

	private void CreateAndSaveNewBuilding() {
		String name = this.textFieldName.getText();
		String abbreviation = this.textFieldAbbreviation.getText();
		String address = this.textFieldAddress.getText();
		String latitude = this.textFieldLatitude.getText();
		String longitude = this.textFieldLongitude.getText();

		if (name.isEmpty() || abbreviation.isEmpty() || address.isEmpty() || latitude.isEmpty()
				|| longitude.isEmpty()) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new building with empty property field.");
		} else {
			try {
				double lat = Double.parseDouble(latitude);
				double lon = Double.parseDouble(latitude);
				Location newLocation = new Location();
				newLocation.setAddress(address);
				newLocation.setLatitude(lat);
				newLocation.setLongitude(lon);
				Building building = new Building();
				building.setName(name);
				building.setAbbreviation(abbreviation);
				building.setLocation(newLocation);
				
				// Save the building to the database
				if (this.idOfTheBuildingToUpdate != 0) {
					buildingsDAOService.Update(this.idOfTheBuildingToUpdate, building);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on building with id {0} and named {1}.",
							new Object[] { building.getId(), building.getName() });
				} else {
					buildingsDAOService.persist(building);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on building with the following name: {0}. ",
							new Object[] { building.getName() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the latitude and longitude fields contain valid coordinates.");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the building. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}
	
	private void ClearAllFields(){
		this.textFieldName.setText("");
		this.textFieldAbbreviation.setText("");
		this.textFieldAddress.setText("");
		this.textFieldLatitude.setText("");
		this.textFieldLongitude.setText("");
		this.idOfTheBuildingToUpdate = 0;
		this.notificationLabel.setText(" ");
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.buildingsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a building while no row was selected.");
		} else {
			Building existingBuilding = this.buildingsTableModel.elementAt(selecteRow);
			Location existingLocation = existingBuilding.getLocation();
			this.idOfTheBuildingToUpdate = existingBuilding.getId();
			this.textFieldName.setText(existingBuilding.getName());
			this.textFieldAbbreviation.setText(existingBuilding.getAbbreviation());
			this.textFieldAddress.setText(existingLocation.getAddress());
			this.textFieldLatitude.setText(Double.toString(existingLocation.getLatitude()));
			this.textFieldLongitude.setText(Double.toString(existingLocation.getLongitude()));
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.buildingsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a building while no row was selected.");
		} else {
			try {
				Building existingBuilding = this.buildingsTableModel.elementAt(selecteRow);
				buildingsDAOService.remove(existingBuilding);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on building with id {0} and named {1}. ",
						new Object[] { existingBuilding.getId(), existingBuilding.getName() });
			} catch (Exception e) {
				this.notificationLabel.setText("An error occured. Please make sure that nothing else depends on this building."
						+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE, "Exception occured on deleting building.  Please make sure that nothing else depends on this building."
						+ e.toString(), e);
			}
		}
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

	private JPanel CreateStaticGoogleMapPanel() {
		JPanel embededGoogleMapPanel = new JPanel();
		embededGoogleMapPanel.setLayout(new BoxLayout(embededGoogleMapPanel, BoxLayout.X_AXIS));
		embededGoogleMapPanel.add(CreateMapZoomSlider());
		embededGoogleMapPanel.add(this.labelMap);
		RefreshMap();
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(embededGoogleMapPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Building's Location by coordinates"));

		return adjustmentPanel;
	}

	private JSlider CreateMapZoomSlider() {
		JSlider mapZoomSlider = new JSlider(JSlider.VERTICAL, 0, 10, 7);
		// Turn on labels at major tick marks.
		mapZoomSlider.setMajorTickSpacing(10);
		mapZoomSlider.setMinorTickSpacing(1);
		mapZoomSlider.setPaintTicks(true);
		mapZoomSlider.setPaintLabels(true);
		mapZoomSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int mapZoom = (int) source.getValue();
					staticMapUrlBuilder.setZoom(Integer.toString(10 + mapZoom));
					RefreshMap();
				}
			}
		});

		return mapZoomSlider;
	}

	private void RefreshMap() {
		String imageUrl = this.staticMapUrlBuilder.GetStaticMapURL();
		String destinationFile = "image.jpg";

		try {
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();

			Image image = ImageIO.read(new File(destinationFile));
			ImageIcon imageMap = new ImageIcon(image);
			this.labelMap.setIcon(imageMap);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	private JPanel CreateViewAllBuildingsPanel() {
		JPanel viewAllBuildingsPanel = new JPanel(new BorderLayout());
		viewAllBuildingsPanel.setBorder(CreateRaisedBevelTitledBorder("All buildings"));

		JScrollPane scrollPane = new JScrollPane();
		this.buildingsTable.setShowVerticalLines(true);
		this.buildingsTable.setShowHorizontalLines(true);
		this.buildingsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.buildingsTable);

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
