package timetablepuzzle.swing.windows.cards.other.academicSessions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import timetablepuzzle.eclipselink.DAO.JPA.services.SolutionJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.AcademicSessionJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.SolutionDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.AcademicSessionDAO;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.other.AcademicSession;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;
import timetablepuzzle.swing.windows.cards.common.CustomDateFormatter;

public class AcademicSessionsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(AcademicSessionsCard.class.getName());
	private static AcademicSessionDAO academicSessionsDAOService = new AcademicSessionJPADAOService();
	private static SolutionDAO solutionDAOService = new SolutionJPADAOService();

	private AcademicSessionsTableModel academicSessionsTableModel;
	private JTable academicSessionsTable;
	private JLabel notificationLabel;
	private JTextField textFieldName;
	private UtilDateModel datePickerSessionStartDateModel;
	private JDatePickerImpl datePickerSessionStartDate;
	private UtilDateModel datePickerClassesEndDateModel;
	private JDatePickerImpl datePickerClassesEndDate;
	private UtilDateModel datePickerExamsStartDateModel;
	private JDatePickerImpl datePickerExamsStartDate;
	private UtilDateModel datePickerSessionEndDateModel;
	private JDatePickerImpl datePickerSessionEndDate;
	private CustomComboBoxModel<Solution> comboBoxAcceptedSolutionModel;
	private JComboBox<Solution> comboBoxAcceptedSolution;
	private int idOfTheAcademicSessionToUpdate;

	public AcademicSessionsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Academic sessions table
		this.academicSessionsTableModel = new AcademicSessionsTableModel();
		RefreshTable();
		this.academicSessionsTable = new JTable(this.academicSessionsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.academicSessionsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		this.textFieldName = new JTextField(30);
		this.textFieldName.setHorizontalAlignment(JTextField.CENTER);
		this.textFieldName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNew();
			}
		});

		// Date pickers
		this.datePickerSessionStartDateModel = CreateUtilDateModel();
		this.datePickerSessionStartDate = CreateJDatePicker(this.datePickerSessionStartDateModel);
		this.datePickerClassesEndDateModel = CreateUtilDateModel();
		this.datePickerClassesEndDate = CreateJDatePicker(this.datePickerClassesEndDateModel);
		this.datePickerExamsStartDateModel = CreateUtilDateModel();
		this.datePickerExamsStartDate = CreateJDatePicker(this.datePickerExamsStartDateModel);
		this.datePickerSessionEndDateModel = CreateUtilDateModel();
		this.datePickerSessionEndDate = CreateJDatePicker(this.datePickerSessionEndDateModel);

		// Accepted solution combo box
		this.comboBoxAcceptedSolutionModel = new CustomComboBoxModel<Solution>();
		RefreshComboBoxAcceptedSolution();
		this.comboBoxAcceptedSolution = new JComboBox<Solution>(this.comboBoxAcceptedSolutionModel);

		this.idOfTheAcademicSessionToUpdate = 0;
		SetAcademicSessionCardComponents();
	}

	private void RefreshTable() {
		this.academicSessionsTableModel.setData(academicSessionsDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.academicSessionsTable.getColumnModel().getColumn(0).setMaxWidth(40);
	}
	
	private UtilDateModel CreateUtilDateModel(){
		UtilDateModel utilDateModel = new UtilDateModel();
		utilDateModel.setValue(new Date());
		utilDateModel.setSelected(true);
		
		return utilDateModel;
	}
	
	private JDatePickerImpl CreateJDatePicker(UtilDateModel utilDatemodel){
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePickerImpl jDatePicker = new JDatePickerImpl(
				new JDatePanelImpl(utilDatemodel, p),
				new CustomDateFormatter());
		
		return jDatePicker;
	}
	
	private void RefreshComboBoxAcceptedSolution(){
		List<Solution> solutions = solutionDAOService.GetAll();
		solutions.add(null);
		this.comboBoxAcceptedSolutionModel.setData(solutions);
	}

	private void SetAcademicSessionCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewAcademicSessionPanel());
		this.add(CreateViewAllAcademicSessionsPanel());
	}

	private JPanel CreateCreateNewAcademicSessionPanel() {
		JPanel createNewAcademicSessionPanel = new JPanel(new GridLayout(1, 1));
		createNewAcademicSessionPanel.add(CreatePropertiesPanel());

		return createNewAcademicSessionPanel;
	}

	private JPanel CreatePropertiesPanel() {		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Session Start Date", this.datePickerSessionStartDate));
		propertiesPanel.add(CreatePropertyPanel("Classes End Date  ", this.datePickerClassesEndDate));
		propertiesPanel.add(CreatePropertyPanel("Exams Start Date  ", this.datePickerExamsStartDate));
		propertiesPanel.add(CreatePropertyPanel("Session End Date  ", this.datePickerSessionEndDate));
		propertiesPanel.add(AddOptionalLabel(CreatePropertyPanel("AcceptedSolution  ", this.comboBoxAcceptedSolution)));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Academic Session"));

		return adjustmentPanel;
	}

	private JPanel CreatePropertyPanel(String propertyName, JComponent propertyComponent) {
		JPanel propertyPanel = new JPanel();
		JLabel labelName = new JLabel(propertyName, JLabel.TRAILING);
		labelName.setLabelFor(propertyComponent);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		propertyPanel.add(labelName);
		propertyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		propertyPanel.add(propertyComponent);

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
		JButton buttonEditSelectedRow = new JButton("Edit selected row");
		buttonEditSelectedRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadSelectedRowDetailsForEdit();
			}
		});
		JButton buttonDeleteSelectedRow = new JButton("Delete selected row");
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
		Calendar sessionStartDate = DateToCalendar((Date)this.datePickerSessionStartDate.getModel().getValue());
		Calendar classesEndDate = DateToCalendar((Date)this.datePickerClassesEndDate.getModel().getValue());
		Calendar examsStartDate = DateToCalendar((Date)this.datePickerExamsStartDate.getModel().getValue());
		Calendar sessionEndDate = DateToCalendar((Date)this.datePickerSessionEndDate.getModel().getValue());
		Solution acceptedSolution = (Solution) this.comboBoxAcceptedSolution.getSelectedItem();

		if (name.isEmpty()) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new academicSession with empty property field.");
		} else {
			try {
				AcademicSession academicSession = new AcademicSession(this.idOfTheAcademicSessionToUpdate, name, sessionStartDate,
						classesEndDate, examsStartDate, sessionEndDate, acceptedSolution);
				
				// Save the academicSession to the database
				if (this.idOfTheAcademicSessionToUpdate != 0) {
					academicSessionsDAOService.merge(academicSession);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on academicSession with id {0} and named {1}.",
							new Object[] { academicSession.getId(), academicSession.getName() });
				} else {
					academicSessionsDAOService.persist(academicSession);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on academicSession with the following name: {0}. ",
							new Object[] { academicSession.getName() });
				}
			} catch (NumberFormatException e) {
				this.notificationLabel
						.setText("Please make sure that the latitude and longitude fields contain valid coordinates.");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the academicSession. Check log files for more info!");
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
	}
	
	private static Calendar DateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  
		  return cal;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.academicSessionsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a academicSession while no row was selected.");
		} else {
			AcademicSession existingAcademicSession = this.academicSessionsTableModel.elementAt(selecteRow);
			this.idOfTheAcademicSessionToUpdate = existingAcademicSession.getId();
			this.textFieldName.setText(existingAcademicSession.getName());
			this.datePickerSessionStartDateModel.setValue(existingAcademicSession.getSessionStartDate().getTime());
			this.datePickerClassesEndDateModel.setValue(existingAcademicSession.getClassesEndDate().getTime());
			this.datePickerExamsStartDateModel.setValue(existingAcademicSession.getExamsStartDate().getTime());
			this.datePickerSessionEndDateModel.setValue(existingAcademicSession.getSessionEndDate().getTime());		
			this.comboBoxAcceptedSolution.setSelectedItem(existingAcademicSession.getAcceptedSolution());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.academicSessionsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a academicSession while no row was selected.");
		} else {
			try {
				AcademicSession existingAcademicSession = this.academicSessionsTableModel.elementAt(selecteRow);
				academicSessionsDAOService.remove(existingAcademicSession);
				RefreshTable();
				this.notificationLabel.setText(" ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on academicSession with id {0} and named {1}. ",
						new Object[] { existingAcademicSession.getId(), existingAcademicSession.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this academicSession."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting academicSession.  Please make sure that nothing else depends on this academicSession."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.datePickerSessionStartDateModel.setValue(new Date());
		this.datePickerClassesEndDateModel.setValue(new Date());
		this.datePickerExamsStartDateModel.setValue(new Date());
		this.datePickerSessionEndDateModel.setValue(new Date());
		this.comboBoxAcceptedSolution.setSelectedIndex(-1);
		this.idOfTheAcademicSessionToUpdate = 0;
		this.notificationLabel.setText(" ");
		this.RefreshTable();
		this.repaint();
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

	private JPanel CreateViewAllAcademicSessionsPanel() {
		JPanel viewAllAcademicSessionsPanel = new JPanel(new BorderLayout());
		viewAllAcademicSessionsPanel.setBorder(CreateRaisedBevelTitledBorder("All Academic Sessions"));

		JScrollPane scrollPane = new JScrollPane();
		this.academicSessionsTable.setShowVerticalLines(true);
		this.academicSessionsTable.setShowHorizontalLines(true);
		this.academicSessionsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.academicSessionsTable);

		viewAllAcademicSessionsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllAcademicSessionsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
