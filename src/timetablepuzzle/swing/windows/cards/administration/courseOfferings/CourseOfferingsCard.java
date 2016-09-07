package timetablepuzzle.swing.windows.cards.administration.courseOfferings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.OfferingJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CourseOfferingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.OfferingDAO;
import timetablepuzzle.entities.administration.CourseOffering;
import timetablepuzzle.entities.inputdata.Offering;
import timetablepuzzle.entities.inputdata.Offering.OfferingType;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;

public class CourseOfferingsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(CourseOfferingsCard.class.getName());
	private static CourseOfferingDAO courseOfferingDAOService = new CourseOfferingJPADAOService();
	private static OfferingDAO offeringDAOService = new OfferingJPADAOService();

	private CourseOfferingsTableModel courseOfferingsTableModel;
	private JTable courseOfferingsTable;
	private JTextField textFieldTitle;
	private JTextField textFieldAbbreviation;
	private CustomComboBoxModel<Offering> comboBoxLectureModel;
	private JComboBox<Offering> comboBoxLecture;
	private CustomComboBoxModel<Offering> comboBoxIndividualMeetingsModel;
	private JComboBox<Offering> comboBoxIndividualMeetings;
	private JLabel notificationLabel;
	private int idOfTheCourseOfferingToUpdate;

	public CourseOfferingsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// CourseOfferings table
		this.courseOfferingsTableModel = new CourseOfferingsTableModel();
		RefreshTable();
		this.courseOfferingsTable = new JTable(this.courseOfferingsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.courseOfferingsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldTitle = CreatePropertyTextField(15);
		this.textFieldAbbreviation = CreatePropertyTextField(10);

		// Lecture combo box
		this.comboBoxLectureModel = new CustomComboBoxModel<Offering>();
		RefreshComboBoxLecture();
		this.comboBoxLecture = new JComboBox<Offering>(this.comboBoxLectureModel);

		// Individual meetings combo box
		this.comboBoxIndividualMeetingsModel = new CustomComboBoxModel<Offering>();
		RefreshComboBoxIndividualMeetings();
		this.comboBoxIndividualMeetings = new JComboBox<Offering>(this.comboBoxIndividualMeetingsModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		this.idOfTheCourseOfferingToUpdate = 0;
		SetCourseOfferingsCardComponents();
	}

	private void RefreshTable() {
		this.courseOfferingsTableModel.setData(courseOfferingDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.courseOfferingsTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.courseOfferingsTable.getColumnModel().getColumn(1).setMinWidth(130);
		this.courseOfferingsTable.getColumnModel().getColumn(1).setMaxWidth(130);
		this.courseOfferingsTable.getColumnModel().getColumn(2).setMinWidth(100);
		this.courseOfferingsTable.getColumnModel().getColumn(2).setMaxWidth(100);
		this.courseOfferingsTable.getColumnModel().getColumn(3).setMinWidth(150);
		this.courseOfferingsTable.getColumnModel().getColumn(3).setMaxWidth(150);
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

	private void RefreshComboBoxLecture() {
		List<Offering> offerings = offeringDAOService.GetAll();
		if (offerings != null) {
			this.comboBoxLectureModel.setData(GetLectures(offerings));
			this.repaint();
		}
	}
	
	private List<Offering> GetLectures(List<Offering> offerings){
		List<Offering> lectures = new ArrayList<Offering>();
		for (Offering offering : offerings) {
			if(offering.getType() == OfferingType.LECTURE) {
				lectures.add(offering);
			}
		}
		
		return lectures;
	}

	private void RefreshComboBoxIndividualMeetings() {
		List<Offering> offerings = offeringDAOService.GetAll();
		if (offerings != null) {
			this.comboBoxIndividualMeetingsModel.setData(GetIndividualMeetings(offerings));
			this.repaint();
		}
	}

	private List<Offering> GetIndividualMeetings(List<Offering> offerings){
		List<Offering> individualMeetings = new ArrayList<Offering>();
		for (Offering offering : offerings) {
			if((offering.getType() == OfferingType.LABORATORY) || (offering.getType() == OfferingType.SEMINARY)) {
				individualMeetings.add(offering);
			}
		}
		
		return individualMeetings;
	}

	private void SetCourseOfferingsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewCourseOfferingPanel());
		this.add(CreateViewAllCourseOfferingsPanel());
	}

	private JPanel CreateCreateNewCourseOfferingPanel() {
		JPanel createNewCourseOfferingPanel = new JPanel(new GridLayout(1, 1));
		createNewCourseOfferingPanel.add(CreatePropertiesPanel());

		return createNewCourseOfferingPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Title", this.textFieldTitle));
		propertiesPanel.add(CreatePropertyPanel("Abbreviation", this.textFieldAbbreviation));
		propertiesPanel.add(CreatePropertyPanel("Lecture", this.comboBoxLecture));
		propertiesPanel.add(CreatePropertyPanel("Individual Meetings", this.comboBoxIndividualMeetings));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Course Offering"));

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
		String title = this.textFieldTitle.getText();
		String abbreviation = this.textFieldAbbreviation.getText();
		Offering lecture = (Offering) this.comboBoxLecture.getSelectedItem();
		Offering individualMeetings = (Offering) this.comboBoxIndividualMeetings.getSelectedItem();

		if ((title.isEmpty()) || (abbreviation.isEmpty()) || (lecture == null) || (individualMeetings == null)) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new courseOffering with empty property field.");
		} else {
			try {
				// Create new courseOffering
				CourseOffering courseOffering = new CourseOffering(this.idOfTheCourseOfferingToUpdate, title,
						abbreviation, lecture, individualMeetings);

				if (this.idOfTheCourseOfferingToUpdate != 0) {
					courseOfferingDAOService.merge(courseOffering);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on courseOffering with id {0} and abbreviation {1}.",
							new Object[] { courseOffering.getId(), courseOffering.getAbbreviation() });
				} else {
					courseOfferingDAOService.merge(courseOffering);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on courseOffering  with abbreviation {1}. ",
							new Object[] { courseOffering.getAbbreviation() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the courseOffering. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the courseOffering" + e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.courseOfferingsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a courseOffering while no row was selected.");
		} else {
			CourseOffering existingCourseOffering = this.courseOfferingsTableModel.elementAt(selecteRow);
			this.idOfTheCourseOfferingToUpdate = existingCourseOffering.getId();
			this.textFieldTitle.setText(existingCourseOffering.getTitle());
			this.textFieldAbbreviation.setText(existingCourseOffering.getAbbreviation());
			this.comboBoxLecture.setSelectedItem(existingCourseOffering.getLecture());
			this.comboBoxIndividualMeetings.setSelectedItem(existingCourseOffering.getIndividualMeetings());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.courseOfferingsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a courseOffering while no row was selected.");
		} else {
			try {
				CourseOffering existingCourseOffering = this.courseOfferingsTableModel.elementAt(selecteRow);
				courseOfferingDAOService.remove(existingCourseOffering);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on courseOffering with id {0} and abbreviation {1}.",
						new Object[] { existingCourseOffering.getId(), existingCourseOffering.getAbbreviation() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this courseOffering."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting courseOffering. Please make sure that nothing else depends on this courseOffering."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldTitle.setText("");
		this.textFieldAbbreviation.setText("");
		RefreshComboBoxLecture();
		RefreshComboBoxIndividualMeetings();
		this.notificationLabel.setText("  ");
		this.idOfTheCourseOfferingToUpdate = 0;
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

	private JPanel CreateViewAllCourseOfferingsPanel() {
		JPanel viewAllCourseOfferingsPanel = new JPanel(new BorderLayout());
		viewAllCourseOfferingsPanel.setBorder(CreateRaisedBevelTitledBorder("View All Course Offerings"));

		JScrollPane scrollPane = new JScrollPane();
		this.courseOfferingsTable.setShowVerticalLines(true);
		this.courseOfferingsTable.setShowHorizontalLines(true);
		this.courseOfferingsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.courseOfferingsTable);

		viewAllCourseOfferingsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllCourseOfferingsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
