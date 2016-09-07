package timetablepuzzle.swing.windows.cards.administration.curriculas;

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

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.CourseOfferingJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.CurriculaJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CourseOfferingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CurriculaDAO;
import timetablepuzzle.entities.administration.CourseOffering;
import timetablepuzzle.entities.administration.Curricula;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.swing.windows.cards.common.ListBoxesWithTransferableItems;

public class CurriculasCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(CurriculasCard.class.getName());
	private static CurriculaDAO curriculaDAOService = new CurriculaJPADAOService();
	private static CourseOfferingDAO courseOfferingDAOService = new CourseOfferingJPADAOService();

	private CurriculasTableModel curriculasTableModel;
	private JTable curriculasTable;
	private JTextField textFieldName;
	private CustomComboBoxModel<CollegeYear> comboBoxYearModel;
	private JComboBox<CollegeYear> comboBoxYear;
	private CustomComboBoxModel<Term> comboBoxTermModel;
	private JComboBox<Term> comboBoxTerm;
	private JLabel notificationLabel;
	private ListBoxesWithTransferableItems transferableItemsControl;
	private int idOfTheCurriculaToUpdate;

	public CurriculasCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Curricular table
		this.curriculasTableModel = new CurriculasTableModel();
		RefreshTable();
		this.curriculasTable = new JTable(this.curriculasTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.curriculasTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);

		// Year combo box
		this.comboBoxYearModel = new CustomComboBoxModel<CollegeYear>();
		RefreshComboBoxYear();
		this.comboBoxYear = new JComboBox<CollegeYear>(this.comboBoxYearModel);

		// Term combo box
		this.comboBoxTermModel = new CustomComboBoxModel<Term>();
		RefreshComboBoxTerm();
		this.comboBoxTerm = new JComboBox<Term>(this.comboBoxTermModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Transferable items control
		this.transferableItemsControl = new ListBoxesWithTransferableItems(new Object[]{}, new Object[]{},
				"Possible room features", "Added room features");
		RefreshTransferableItemsControl(new ArrayList<CourseOffering>());

		this.idOfTheCurriculaToUpdate = 0;
		SetCurriculasCardComponents();
	}

	private void RefreshTable() {
		this.curriculasTableModel.setData(curriculaDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.curriculasTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.curriculasTable.getColumnModel().getColumn(1).setMinWidth(150);
		this.curriculasTable.getColumnModel().getColumn(1).setMaxWidth(150);
		this.curriculasTable.getColumnModel().getColumn(2).setMinWidth(80);
		this.curriculasTable.getColumnModel().getColumn(2).setMaxWidth(80);
		this.curriculasTable.getColumnModel().getColumn(3).setMinWidth(80);
		this.curriculasTable.getColumnModel().getColumn(3).setMaxWidth(80);
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

	private void RefreshComboBoxTerm() {
		this.comboBoxTermModel.setData(Arrays.asList(Term.values()));
		this.repaint();
	}

	private void RefreshTransferableItemsControl(List<CourseOffering> addedCourseOfferings) {
		List<CourseOffering> leftListRoomFeatures = courseOfferingDAOService.GetAll();
		List<CourseOffering> rightListRoomFeatures = new ArrayList<CourseOffering>();
		for(CourseOffering addedCourseOffering : addedCourseOfferings){
			leftListRoomFeatures.remove(addedCourseOffering);
			rightListRoomFeatures.add(addedCourseOffering);
		}
		leftListRoomFeatures.sort(Comparator.comparing(CourseOffering::toString));
		rightListRoomFeatures.sort(Comparator.comparing(CourseOffering::toString));
		this.transferableItemsControl.ReInitializeLists(leftListRoomFeatures.toArray(), rightListRoomFeatures.toArray());
	}

	private void SetCurriculasCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewCurriculaPanel());
		this.add(CreateViewAllCurriculasPanel());
	}

	private JPanel CreateCreateNewCurriculaPanel() {
		JPanel createNewCurriculaPanel = new JPanel(new GridLayout(1, 2));
		createNewCurriculaPanel.add(CreatePropertiesPanel());
		createNewCurriculaPanel.add(CreateCurriculaCoursesPanel());

		return createNewCurriculaPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Year", this.comboBoxYear));
		propertiesPanel.add(CreatePropertyPanel("Term", this.comboBoxTerm));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Curricula"));

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
		Term term = (Term) this.comboBoxTerm.getSelectedItem();
		List<CourseOffering> courses = ConvertToListOfCourseOfferings(this.transferableItemsControl.GetRightListElements()); 

		if ((name.isEmpty()) || (year == null) || (term == null) || (courses.isEmpty())) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new curricula with empty property field.");
		} else {
			try {
				// Create new
				Curricula curricula = new Curricula(this.idOfTheCurriculaToUpdate, name, year, term, courses);

				if (this.idOfTheCurriculaToUpdate != 0) {
					curriculaDAOService.merge(curricula);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on curricula with id {0} and name {1}.",
							new Object[] { curricula.getId(), curricula.getName() });
				} else {
					curriculaDAOService.merge(curricula);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on curricula  with name {1}. ",
							new Object[] { curricula.getName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the curricula. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the curricula" + e.toString(), e);
			}
		}
	}

	private List<CourseOffering> ConvertToListOfCourseOfferings(List<Object> elements) {
		List<CourseOffering> courseOfferings = new ArrayList<CourseOffering>();
		for(Object element : elements)
		{
			courseOfferings.add((CourseOffering)element);
		}
		
		return courseOfferings;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.curriculasTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a curricula while no row was selected.");
		} else {
			Curricula existingCurricula = this.curriculasTableModel.elementAt(selecteRow);
			this.idOfTheCurriculaToUpdate = existingCurricula.getId();
			this.textFieldName.setText(existingCurricula.getName());
			this.comboBoxYear.setSelectedItem(existingCurricula.getYear());
			this.comboBoxTerm.setSelectedItem(existingCurricula.getTerm());
			RefreshTransferableItemsControl(existingCurricula.getCourses());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.curriculasTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a curricula while no row was selected.");
		} else {
			try {
				Curricula existingCurricula = this.curriculasTableModel.elementAt(selecteRow);
				curriculaDAOService.remove(existingCurricula);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on curricula with id {0} and named {1}.",
						new Object[] { existingCurricula.getId(), existingCurricula.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this curricula."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting curricula. Please make sure that nothing else depends on this curricula."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		RefreshComboBoxYear();
		RefreshComboBoxTerm();
		this.notificationLabel.setText("  ");
		RefreshTransferableItemsControl(new ArrayList<CourseOffering>());
		this.idOfTheCurriculaToUpdate = 0;
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

	private Component CreateCurriculaCoursesPanel() {		
		JPanel instructorMeetingsPanel = new JPanel();
		instructorMeetingsPanel.setLayout(new BoxLayout(instructorMeetingsPanel, BoxLayout.Y_AXIS));
		instructorMeetingsPanel.add(this.transferableItemsControl);
		
		JPanel adjustmentPanel = CreateAdjustmentPanel(instructorMeetingsPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Curricula's Course Offerings"));
		
		return adjustmentPanel;
	}

	private JPanel CreateViewAllCurriculasPanel() {
		JPanel viewAllCurriculasPanel = new JPanel(new BorderLayout());
		viewAllCurriculasPanel.setBorder(CreateRaisedBevelTitledBorder("View All Curriculas"));

		JScrollPane scrollPane = new JScrollPane();
		this.curriculasTable.setShowVerticalLines(true);
		this.curriculasTable.setShowHorizontalLines(true);
		this.curriculasTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.curriculasTable);

		viewAllCurriculasPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllCurriculasPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
