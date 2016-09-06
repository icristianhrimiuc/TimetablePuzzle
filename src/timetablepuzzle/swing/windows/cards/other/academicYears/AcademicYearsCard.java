package timetablepuzzle.swing.windows.cards.other.academicYears;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import timetablepuzzle.eclipselink.DAO.JPA.services.other.AcademicSessionJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.AcademicYearJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.other.AcademicSessionDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.AcademicYearDAO;
import timetablepuzzle.entities.other.AcademicSession;
import timetablepuzzle.entities.other.AcademicYear;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;

public class AcademicYearsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(AcademicYearsCard.class.getName());
	private static AcademicYearDAO academicYearDAOService = new AcademicYearJPADAOService();
	private static AcademicSessionDAO academicSessionDAOService = new AcademicSessionJPADAOService();
	
	private AcademicYearsTableModel academicYearsTableModel;
	private JTable academicYearsTable;
	private JLabel notificationLabel;
	private JTextField textFieldName;
	private CustomComboBoxModel<AcademicSession> comboBoxFirstAcademicSessionModel;
	private JComboBox<AcademicSession> comboBoxFirstAcademicSession;
	private CustomComboBoxModel<AcademicSession> comboBoxSecondAcademicSessionModel;
	private JComboBox<AcademicSession> comboBoxSecondAcademicSession;
	private CustomComboBoxModel<AcademicSession> comboBoxThirdAcademicSessionModel;
	private JComboBox<AcademicSession> comboBoxThirdAcademicSession;
	private int idOfTheAcademicYearToUpdate;

	public AcademicYearsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);
		
		// Academic Sessions table
		this.academicYearsTableModel = new AcademicYearsTableModel();
		RefreshTable();
		this.academicYearsTable = new JTable(this.academicYearsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.academicYearsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();
		
		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		this.textFieldName = new JTextField(20);
		this.textFieldName.setHorizontalAlignment(JTextField.CENTER);
		this.textFieldName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNew();
			}
		});
		
		// First Academic Session combo box
		this.comboBoxFirstAcademicSessionModel = new CustomComboBoxModel<AcademicSession>();
		RefreshComboBoxFirstAcademicSession();
		this.comboBoxFirstAcademicSession = new JComboBox<AcademicSession>(this.comboBoxFirstAcademicSessionModel);		
		
		// Second Academic Session combo box
		this.comboBoxSecondAcademicSessionModel = new CustomComboBoxModel<AcademicSession>();
		RefreshComboBoxSecondAcademicSession();
		this.comboBoxSecondAcademicSession = new JComboBox<AcademicSession>(this.comboBoxSecondAcademicSessionModel);		
		
		// Third Academic Session combo box
		this.comboBoxThirdAcademicSessionModel = new CustomComboBoxModel<AcademicSession>();
		RefreshComboBoxThirdAcademicSession();
		this.comboBoxThirdAcademicSession = new JComboBox<AcademicSession>(this.comboBoxThirdAcademicSessionModel);
		
		this.idOfTheAcademicYearToUpdate = 0;
		
		SetAcademicYearsCardComponents();
	}

	private void RefreshTable() {
		this.academicYearsTableModel.setData(academicYearDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.academicYearsTable.getColumnModel().getColumn(0).setMaxWidth(40);
	}
	
	private void RefreshComboBoxFirstAcademicSession(){
		this.comboBoxFirstAcademicSessionModel.setData(academicSessionDAOService.GetAll());
	}
	
	private void RefreshComboBoxSecondAcademicSession(){
		this.comboBoxSecondAcademicSessionModel.setData(academicSessionDAOService.GetAll());
	}
	
	private void RefreshComboBoxThirdAcademicSession(){
		List<AcademicSession> academisSessions = academicSessionDAOService.GetAll();
		academisSessions.add(null);
		this.comboBoxThirdAcademicSessionModel.setData(academisSessions);
	}

	private void SetAcademicYearsCardComponents() {
		this.setLayout(new GridLayout(2,1));
		this.add(CreateCreateNewAcademicYearPanel());
		this.add(CreateViewAllAcademicYearsPanel());
	}

	private JPanel CreateCreateNewAcademicYearPanel() {
		JPanel createNewAcademicYearPanel = new JPanel(new GridLayout(1,1));
		createNewAcademicYearPanel.add(CreatePropertiesPanel());

		return createNewAcademicYearPanel;
	}

	private JPanel CreatePropertiesPanel() {		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("First Academic Session", this.comboBoxFirstAcademicSession));
		propertiesPanel.add(CreatePropertyPanel("Second Academic Session", this.comboBoxSecondAcademicSession));
		propertiesPanel.add(AddOptionalLabel(CreatePropertyPanel("Third Academic Session ", this.comboBoxThirdAcademicSession)));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Academic Year"));
		
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
	
	private JPanel AddOptionalLabel(JPanel propertyPanel){
		JLabel optionalLabel = new JLabel("Optional*");
		optionalLabel.setForeground(Color.LIGHT_GRAY);
		propertyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		propertyPanel.add(optionalLabel);
		
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

	private void CreateAndSaveNew() {
		String name = this.textFieldName.getText();
		AcademicSession firstAcademicSession = (AcademicSession)this.comboBoxFirstAcademicSession.getSelectedItem();
		AcademicSession secondAcademicSession = (AcademicSession)this.comboBoxSecondAcademicSession.getSelectedItem();
		AcademicSession thirdAcademicSession = (AcademicSession)this.comboBoxThirdAcademicSession.getSelectedItem();

		if ((name.isEmpty()) || (firstAcademicSession == null) || (secondAcademicSession == null)) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new academicYear with empty property field.");
		} else {
			try {				
				// Create new entity
				AcademicYear academicYear = new AcademicYear(this.idOfTheAcademicYearToUpdate, name, firstAcademicSession,
						secondAcademicSession);
				if(thirdAcademicSession != null){
					academicYear.setThirdAcademicSession(thirdAcademicSession);
				}

				// Save the entity to the database
				if (this.idOfTheAcademicYearToUpdate != 0) {
					academicYearDAOService.merge(academicYear);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on academicYear with id {0} and named {1}.",
							new Object[] { academicYear.getId(), academicYear.getName() });
				} else {
					academicYearDAOService.merge(academicYear);
					RefreshTable();
					ClearAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on academicYears with id {0} and named {1}.",
							new Object[] { academicYear.getId(), academicYear.getName()});
				}
			}catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the academicYear. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.academicYearsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a academicYear while no row was selected.");
		} else {
			AcademicYear existingAcademicYear = this.academicYearsTableModel.elementAt(selecteRow);
			this.idOfTheAcademicYearToUpdate = existingAcademicYear.getId();
			this.textFieldName.setText(existingAcademicYear.getName());
			this.comboBoxFirstAcademicSession.setSelectedItem(existingAcademicYear.getFirstAcademicSession());
			this.comboBoxSecondAcademicSession.setSelectedItem(existingAcademicYear.getSecondAcademicSession());
			this.comboBoxThirdAcademicSession.setSelectedItem(existingAcademicYear.getThirdAcademicSession());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.academicYearsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a academicYear while no row was selected.");
		} else {
			try {
				AcademicYear existingAcademicYear = this.academicYearsTableModel.elementAt(selecteRow);
				academicYearDAOService.remove(existingAcademicYear);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on academicYears with id {0} and named {1}.",
						new Object[] { existingAcademicYear.getId(), existingAcademicYear.getName()});
			} catch (Exception e) {
				this.notificationLabel.setText("An error occured. Please make sure that nothing else depends on this academicYear."
						+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE, "Exception occured on deleting academicYears. Please make sure that nothing else depends on this academicYear."
						+ e.toString(), e);
			}
		}
	}

	private void ClearAllFields() {
		this.textFieldName.setText("");
		this.comboBoxFirstAcademicSession.setSelectedItem(null);
		this.comboBoxSecondAcademicSession.setSelectedItem(null);
		this.comboBoxThirdAcademicSession.setSelectedItem(null);
		this.notificationLabel.setText("  ");
		this.idOfTheAcademicYearToUpdate = 0;
		this.repaint();
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

	private JPanel CreateViewAllAcademicYearsPanel() {
		JPanel viewAllAcademicYearsPanel = new JPanel(new BorderLayout());
		viewAllAcademicYearsPanel.setBorder(CreateRaisedBevelTitledBorder("All Academic Years"));

		JScrollPane scrollPane = new JScrollPane();
		this.academicYearsTable.setShowVerticalLines(true);
		this.academicYearsTable.setShowHorizontalLines(true);
		this.academicYearsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.academicYearsTable);

		viewAllAcademicYearsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllAcademicYearsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}

