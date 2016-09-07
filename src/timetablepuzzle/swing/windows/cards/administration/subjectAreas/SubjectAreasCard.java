package timetablepuzzle.swing.windows.cards.administration.subjectAreas;

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

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.CurriculaJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.SubjectAreaJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CurriculaDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.SubjectAreaDAO;
import timetablepuzzle.entities.administration.Curricula;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.SubjectArea;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;

public class SubjectAreasCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(SubjectAreasCard.class.getName());
	private static SubjectAreaDAO subjectAreaDAOService = new SubjectAreaJPADAOService();
	private static CurriculaDAO curriculaDAOService = new CurriculaJPADAOService();

	private SubjectAreasTableModel subjectAreasTableModel;
	private JTable subjectAreasTable;
	private JTextField textFieldName;
	private JTextField textFieldAbbreviation;
	private CustomComboBoxModel<Curricula> comboBoxFirstTermCurriculaModel;
	private JComboBox<Curricula> comboBoxFirstTermCurricula;
	private CustomComboBoxModel<Curricula> comboBoxSecondTermCurriculaModel;
	private JComboBox<Curricula> comboBoxSecondTermCurricula;
	private CustomComboBoxModel<Curricula> comboBoxThirdTermCurriculaModel;
	private JComboBox<Curricula> comboBoxThirdTermCurricula;
	private JLabel notificationLabel;
	private int idOfTheSubjectAreaToUpdate;

	public SubjectAreasCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// SubjectAreas table
		this.subjectAreasTableModel = new SubjectAreasTableModel();
		RefreshTable();
		this.subjectAreasTable = new JTable(this.subjectAreasTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.subjectAreasTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldName = CreatePropertyTextField(15);
		this.textFieldAbbreviation = CreatePropertyTextField(10);

		// First Term Curricula combo box
		this.comboBoxFirstTermCurriculaModel = new CustomComboBoxModel<Curricula>();
		RefreshComboBoxFirstTermCurricula();
		this.comboBoxFirstTermCurricula = new JComboBox<Curricula>(this.comboBoxFirstTermCurriculaModel);

		// Second Term Curricula combo box
		this.comboBoxSecondTermCurriculaModel = new CustomComboBoxModel<Curricula>();
		RefreshComboBoxSecondTermCurricula();
		this.comboBoxSecondTermCurricula = new JComboBox<Curricula>(this.comboBoxSecondTermCurriculaModel);

		// Third Term Curricula combo box
		this.comboBoxThirdTermCurriculaModel = new CustomComboBoxModel<Curricula>();
		RefreshComboBoxThirdTermCurricula();
		this.comboBoxThirdTermCurricula = new JComboBox<Curricula>(this.comboBoxThirdTermCurriculaModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		this.idOfTheSubjectAreaToUpdate = 0;
		SetSubjectAreasCardComponents();
	}

	private void RefreshTable() {
		this.subjectAreasTableModel.setData(subjectAreaDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.subjectAreasTable.getColumnModel().getColumn(0).setMaxWidth(60);
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

	private void RefreshComboBoxFirstTermCurricula() {
		this.comboBoxFirstTermCurriculaModel.setData(FilterByTerm(curriculaDAOService.GetAll(), Term.FIRST));
		this.repaint();
	}

	private void RefreshComboBoxSecondTermCurricula() {
		this.comboBoxSecondTermCurriculaModel.setData(FilterByTerm(curriculaDAOService.GetAll(), Term.SECOND));
		this.repaint();
	}

	private void RefreshComboBoxThirdTermCurricula() {
		this.comboBoxThirdTermCurriculaModel.setData(FilterByTerm(curriculaDAOService.GetAll(), Term.THIRD));
		this.repaint();
	}

	private List<Curricula> FilterByTerm(List<Curricula> curriculas, Term term) {
		List<Curricula> curriculaByTerm = new ArrayList<Curricula>();
		for (Curricula curricula : curriculas) {
			if (curricula.getTerm() == term) {
				curriculaByTerm.add(curricula);
			}
		}

		return curriculaByTerm;
	}

	private void SetSubjectAreasCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewSubjectAreaPanel());
		this.add(CreateViewAllSubjectAreasPanel());
	}

	private JPanel CreateCreateNewSubjectAreaPanel() {
		JPanel createNewSubjectAreaPanel = new JPanel(new GridLayout(1, 1));
		createNewSubjectAreaPanel.add(CreatePropertiesPanel());

		return createNewSubjectAreaPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("Name", this.textFieldName));
		propertiesPanel.add(CreatePropertyPanel("Abbreviation", this.textFieldAbbreviation));
		propertiesPanel.add(CreatePropertyPanel("First Term Curricula", this.comboBoxFirstTermCurricula));
		propertiesPanel.add(CreatePropertyPanel("Second Term Curricula", this.comboBoxSecondTermCurricula));
		propertiesPanel.add(CreatePropertyPanel("Third Term Curricula", this.comboBoxThirdTermCurricula));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Subject Area"));

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
		String abbreviation = this.textFieldAbbreviation.getText();
		Curricula firstTermCurricula = (Curricula) this.comboBoxFirstTermCurricula.getSelectedItem();
		Curricula secondTermCurricula = (Curricula) this.comboBoxSecondTermCurricula.getSelectedItem();
		Curricula thirdTermCurricula = (Curricula) this.comboBoxSecondTermCurricula.getSelectedItem();

		if ((name.isEmpty()) || (abbreviation.isEmpty()) || (firstTermCurricula == null)
				|| (secondTermCurricula == null)) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new subjectArea with empty property field.");
		} else {
			try {
				// Create new
				SubjectArea subjectArea = new SubjectArea(this.idOfTheSubjectAreaToUpdate, name, abbreviation,
						firstTermCurricula, secondTermCurricula);
				if (thirdTermCurricula != null) {
					subjectArea.setCurriculaToStudyByTerm(Term.THIRD, thirdTermCurricula);
				}

				if (this.idOfTheSubjectAreaToUpdate != 0) {
					subjectAreaDAOService.merge(subjectArea);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on subjectArea with id {0} and name {1}.",
							new Object[] { subjectArea.getId(), subjectArea.getName() });
				} else {
					subjectAreaDAOService.merge(subjectArea);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on subjectArea  with name {1}. ",
							new Object[] { subjectArea.getName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the subjectArea. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the subjectArea" + e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.subjectAreasTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a subjectArea while no row was selected.");
		} else {
			SubjectArea existingSubjectArea = this.subjectAreasTableModel.elementAt(selecteRow);
			this.idOfTheSubjectAreaToUpdate = existingSubjectArea.getId();
			this.textFieldName.setText(existingSubjectArea.getName());
			this.textFieldAbbreviation.setText(existingSubjectArea.getAbbreviation());
			this.comboBoxFirstTermCurricula.setSelectedItem(existingSubjectArea.getCurriculaToStudyByTerm(Term.FIRST));
			this.comboBoxSecondTermCurricula
					.setSelectedItem(existingSubjectArea.getCurriculaToStudyByTerm(Term.SECOND));
			this.comboBoxThirdTermCurricula.setSelectedItem(existingSubjectArea.getCurriculaToStudyByTerm(Term.THIRD));
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.subjectAreasTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a subjectArea while no row was selected.");
		} else {
			try {
				SubjectArea existingSubjectArea = this.subjectAreasTableModel.elementAt(selecteRow);
				subjectAreaDAOService.remove(existingSubjectArea);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on subjectArea with id {0} and named {1}.",
						new Object[] { existingSubjectArea.getId(), existingSubjectArea.getName() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this subjectArea."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting subjectArea. Please make sure that nothing else depends on this subjectArea."
								+ e.toString(),
						e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldName.setText("");
		this.textFieldAbbreviation.setText("");
		RefreshComboBoxFirstTermCurricula();
		RefreshComboBoxSecondTermCurricula();
		RefreshComboBoxThirdTermCurricula();
		this.notificationLabel.setText("  ");
		this.idOfTheSubjectAreaToUpdate = 0;
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

	private JPanel CreateViewAllSubjectAreasPanel() {
		JPanel viewAllSubjectAreasPanel = new JPanel(new BorderLayout());
		viewAllSubjectAreasPanel.setBorder(CreateRaisedBevelTitledBorder("View All Subject Areas"));

		JScrollPane scrollPane = new JScrollPane();
		this.subjectAreasTable.setShowVerticalLines(true);
		this.subjectAreasTable.setShowHorizontalLines(true);
		this.subjectAreasTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.subjectAreasTable);

		viewAllSubjectAreasPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllSubjectAreasPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
