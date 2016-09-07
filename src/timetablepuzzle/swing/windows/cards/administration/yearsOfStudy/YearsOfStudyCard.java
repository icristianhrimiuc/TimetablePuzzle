package timetablepuzzle.swing.windows.cards.administration.yearsOfStudy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.SubjectAreaJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.YearOfStudyJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.SubjectAreaDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.YearOfStudyDAO;
import timetablepuzzle.entities.administration.SubjectArea;
import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.swing.windows.cards.common.ListBoxesWithTransferableItems;

public class YearsOfStudyCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(YearsOfStudyCard.class.getName());
	private static YearOfStudyDAO yearOfStudyDAOService = new YearOfStudyJPADAOService();
	private static SubjectAreaDAO subjectAreaDAOService = new SubjectAreaJPADAOService();

	private YearsOfStudyTableModel yearsOfStudyTableModel;
	private JTable yearsOfStudyTable;
	private JTextField textFieldName;
	private CustomComboBoxModel<CollegeYear> comboBoxYearModel;
	private JComboBox<CollegeYear> comboBoxYear;
	private JLabel notificationLabel;
	private ListBoxesWithTransferableItems transferableItemsControl;
	private int idOfTheYearOfStudyToUpdate;

	public YearsOfStudyCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// YearsOfStudy table
		this.yearsOfStudyTableModel = new YearsOfStudyTableModel();
		RefreshTable();
		this.yearsOfStudyTable = new JTable(this.yearsOfStudyTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.yearsOfStudyTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);

		// Year combo box
		this.comboBoxYearModel = new CustomComboBoxModel<CollegeYear>();
		RefreshComboBoxYear();
		this.comboBoxYear = new JComboBox<CollegeYear>(this.comboBoxYearModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Transferable items control
		this.transferableItemsControl = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible subject areas", "Added subject areas");
		RefreshTransferableItemsControl(new ArrayList<SubjectArea>());

		this.idOfTheYearOfStudyToUpdate = 0;
		SetYearsOfStudyCardComponents();
	}

	private void RefreshTable() {
		this.yearsOfStudyTableModel.setData(yearOfStudyDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.yearsOfStudyTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.yearsOfStudyTable.getColumnModel().getColumn(1).setMinWidth(150);
		this.yearsOfStudyTable.getColumnModel().getColumn(1).setMaxWidth(150);
		this.yearsOfStudyTable.getColumnModel().getColumn(2).setMinWidth(80);
		this.yearsOfStudyTable.getColumnModel().getColumn(2).setMaxWidth(80);
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

	private void RefreshComboBoxYear() {
		this.comboBoxYearModel.setData(Arrays.asList(CollegeYear.values()));
		this.repaint();
	}

	private void RefreshTransferableItemsControl(List<SubjectArea> addedSubjectAreas) {
		List<SubjectArea> leftListSubjectAreas = subjectAreaDAOService.GetAll();
		List<SubjectArea> rightListSubjectAreas = new ArrayList<SubjectArea>();
		for(SubjectArea addedSubjectArea : addedSubjectAreas){
			leftListSubjectAreas.remove(addedSubjectArea);
			rightListSubjectAreas.add(addedSubjectArea);
		}
		leftListSubjectAreas.sort(Comparator.comparing(SubjectArea::toString));
		rightListSubjectAreas.sort(Comparator.comparing(SubjectArea::toString));
		this.transferableItemsControl.ReInitializeLists(leftListSubjectAreas.toArray(), rightListSubjectAreas.toArray());
	}

	private void SetYearsOfStudyCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewYearOfStudyPanel());
		this.add(CreateViewAllYearsOfStudyPanel());
	}

	private JPanel CreateCreateNewYearOfStudyPanel() {
		JPanel createNewYearOfStudyPanel = new JPanel(new GridLayout(1, 2));
		createNewYearOfStudyPanel.add(CreatePropertiesPanel());
		createNewYearOfStudyPanel.add(CreateYearOfStudySubjectAreasPanel());

		return createNewYearOfStudyPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("College Year", this.comboBoxYear));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Year Of Study"));

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
		String name = this.textFieldName.getText();
		CollegeYear year = (CollegeYear) this.comboBoxYear.getSelectedItem();
		List<SubjectArea> subjectAreas = ConvertToListOfSubjectAreas(this.transferableItemsControl.GetRightListElements()); 

		if ((name.isEmpty()) || (year == null) || (subjectAreas.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new yearOfStudy with empty property field.");
		} else {
			try {
				// Create new
				YearOfStudy yearOfStudy = new YearOfStudy(this.idOfTheYearOfStudyToUpdate, name, year, subjectAreas);

				if (this.idOfTheYearOfStudyToUpdate != 0) {
					yearOfStudyDAOService.merge(yearOfStudy);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on yearOfStudy with id {0} and name {1}.",
							new Object[] { yearOfStudy.getId(), yearOfStudy.getName() });
				} else {
					yearOfStudyDAOService.merge(yearOfStudy);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on yearOfStudy  with name {1}. ",
							new Object[] { yearOfStudy.getName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the yearOfStudy. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the yearOfStudy" + e.toString(), e);
			}
		}
	}

	private List<SubjectArea> ConvertToListOfSubjectAreas(List<Object> elements) {
		List<SubjectArea> subjectAreas = new ArrayList<SubjectArea>();
		for(Object element : elements)
		{
			subjectAreas.add((SubjectArea)element);
		}
		
		return subjectAreas;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.yearsOfStudyTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a yearOfStudy while no row was selected.");
		} else {
			YearOfStudy existingYearOfStudy = this.yearsOfStudyTableModel.elementAt(selecteRow);
			this.idOfTheYearOfStudyToUpdate = existingYearOfStudy.getId();
			this.textFieldName.setText(existingYearOfStudy.getName());
			this.comboBoxYear.setSelectedItem(existingYearOfStudy.getCollegeYear());
			RefreshTransferableItemsControl(existingYearOfStudy.getSubjectAreas());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.yearsOfStudyTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a yearOfStudy while no row was selected.");
		} else {
			try {
				YearOfStudy existingYearOfStudy = this.yearsOfStudyTableModel.elementAt(selecteRow);
				yearOfStudyDAOService.remove(existingYearOfStudy);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on yearOfStudy with id {0} and named {1}.",
						new Object[] { existingYearOfStudy.getId(), existingYearOfStudy.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this yearOfStudy."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting yearOfStudy. Please make sure that nothing else depends on this yearOfStudy."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		RefreshComboBoxYear();
		this.notificationLabel.setText("  ");
		RefreshTransferableItemsControl(new ArrayList<SubjectArea>());
		this.idOfTheYearOfStudyToUpdate = 0;
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

	private Component CreateYearOfStudySubjectAreasPanel() {		
		JPanel instructorMeetingsPanel = new JPanel();
		instructorMeetingsPanel.setLayout(new BoxLayout(instructorMeetingsPanel, BoxLayout.Y_AXIS));
		instructorMeetingsPanel.add(this.transferableItemsControl);
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(instructorMeetingsPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Year Of Study-Subject Areas"));
		
		return adjustmentPanel;
	}

	private JPanel CreateViewAllYearsOfStudyPanel() {
		JPanel viewAllYearsOfStudyPanel = new JPanel(new BorderLayout());
		viewAllYearsOfStudyPanel.setBorder(CreateRaisedBevelTitledBorder("View All Years Of Study"));

		JScrollPane scrollPane = new JScrollPane();
		this.yearsOfStudyTable.setShowVerticalLines(true);
		this.yearsOfStudyTable.setShowHorizontalLines(true);
		this.yearsOfStudyTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.yearsOfStudyTable);

		viewAllYearsOfStudyPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllYearsOfStudyPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}

