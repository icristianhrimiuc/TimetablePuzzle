package timetablepuzzle.swing.windows.cards.courseTimetabling.solutions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
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

import timetablepuzzle.eclipselink.DAO.JPA.services.SolutionJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.SolutionDAO;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.Class;

public class SolutionsCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(SolutionsCard.class.getName());
	private static SolutionDAO solutionDAOService = new SolutionJPADAOService();

	private SolutionsTableModel solutionsTableModel;
	private JTable solutionsTable;
	private JTextField textFieldName;
	private JLabel notificationLabel;
	private DefaultListModel<Class> listOfClassesModel;
	private JList<Class> listOfClasses;

	public SolutionsCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Solutions table
		this.solutionsTableModel = new SolutionsTableModel();
		RefreshTable();
		this.solutionsTable = new JTable(this.solutionsTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.solutionsTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		// List of classes
		this.listOfClassesModel = new DefaultListModel<Class>();
		RefreshListOfClasses();
		this.listOfClasses = new JList<Class>(this.listOfClassesModel);

		SetSolutionsCardComponents();
	}

	private void RefreshTable() {
		this.solutionsTableModel.setData(solutionDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.solutionsTable.getColumnModel().getColumn(0).setMaxWidth(60);
		this.solutionsTable.getColumnModel().getColumn(1).setMinWidth(150);
		this.solutionsTable.getColumnModel().getColumn(1).setMaxWidth(150);
	}

	private JTextField CreatePropertyTextField(int width) {
		JTextField propertyTextField = new JTextField(width);
		propertyTextField.setHorizontalAlignment(JTextField.CENTER);

		return propertyTextField;
	}

	private void RefreshListOfClasses() {
		this.listOfClassesModel.clear();
	}

	private void SetSolutionsCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewSolutionPanel());
		this.add(CreateViewAllSolutionsPanel());
	}

	private JPanel CreateCreateNewSolutionPanel() {
		JPanel createNewSolutionPanel = new JPanel(new GridLayout(1, 2));
		createNewSolutionPanel.add(CreatePropertiesPanel());
		createNewSolutionPanel.add(CreateSolutionClassesPanel());

		return createNewSolutionPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("View Solution"));

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
		JButton buttonEditSelectedRow = new JButton("View selected");
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

		crudButtonsPanel.add(buttonEditSelectedRow);
		crudButtonsPanel.add(buttonDeleteSelectedRow);
		crudButtonsPanel.add(buttonRefreshAllFields);

		return crudButtonsPanel;
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.solutionsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a solution while no row was selected.");
		} else {
			Solution existingSolution = this.solutionsTableModel.elementAt(selecteRow);
			this.textFieldName.setText(existingSolution.getName());
			List<Class> classes = new ArrayList<Class>();
			classes.addAll(existingSolution.GetAssignedClasses());
			classes.addAll(existingSolution.GetUnassignedClasses());
			AddToListOfClasses(classes);
			this.repaint();
		}
	}

	private void AddToListOfClasses(List<Class> classes) {
		RefreshListOfClasses();
		for (Class aClass : classes) {
			this.listOfClassesModel.addElement(aClass);
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.solutionsTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a solution while no row was selected.");
		} else {
			try {
				Solution existingSolution = this.solutionsTableModel.elementAt(selecteRow);
				solutionDAOService.remove(existingSolution);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on solution with id {0} and named {1}.",
						new Object[] { existingSolution.getId(), existingSolution.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this solution."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting solution. Please make sure that nothing else depends on this solution."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.notificationLabel.setText("  ");
		RefreshListOfClasses();
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

	private Component CreateSolutionClassesPanel() {
		JPanel listOfClassesPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(this.listOfClasses);
		listOfClassesPanel.add(scrollPane);

		JPanel adjustmentPanel = CreateAdjustmentPanel(listOfClassesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Solution's Classes"));

		return adjustmentPanel;
	}

	private JPanel CreateViewAllSolutionsPanel() {
		JPanel viewAllSolutionsPanel = new JPanel(new BorderLayout());
		viewAllSolutionsPanel.setBorder(CreateRaisedBevelTitledBorder("View All Solutions"));

		JScrollPane scrollPane = new JScrollPane();
		this.solutionsTable.setShowVerticalLines(true);
		this.solutionsTable.setShowHorizontalLines(true);
		this.solutionsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.solutionsTable);

		viewAllSolutionsPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllSolutionsPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
