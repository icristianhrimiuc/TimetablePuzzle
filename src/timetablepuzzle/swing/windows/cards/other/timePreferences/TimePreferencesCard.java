package timetablepuzzle.swing.windows.cards.other.timePreferences;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableCellRenderer;

import timetablepuzzle.eclipselink.DAO.JPA.services.other.TimePreferencesJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.other.TimePreferencesDAO;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.swing.windows.cards.other.buildings.BuildingsCard;

public class TimePreferencesCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(BuildingsCard.class.getName());
	private static TimePreferencesDAO timePreferencesDAOService = new TimePreferencesJPADAOService();

	private TimePreferencesTableModel timePreferencesTableModel;
	private JTable timePreferencesTable;
	private JLabel notificationLabel;
	private WeekPreferencesTableModel weekPreferencesTableModel;
	private JTable weekPreferencesTable;
	private int idOfTheTimePreferencesToUpdate;

	public TimePreferencesCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		this.timePreferencesTableModel = new TimePreferencesTableModel();
		RefreshTable();
		this.timePreferencesTable = new JTable(this.timePreferencesTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.timePreferencesTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);

		this.weekPreferencesTableModel = new WeekPreferencesTableModel();
		RefreshTableWeekPreferences();
		this.weekPreferencesTable = new JTable(this.weekPreferencesTableModel);
		ConfigureWeekPreferencesTable();

		SetTimePreferencesCardComponents();
	}

	private void RefreshTable() {
		this.timePreferencesTableModel.setData(timePreferencesDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.timePreferencesTable.getColumnModel().getColumn(0).setMaxWidth(60);
	}

	private void RefreshTableWeekPreferences() {
		this.weekPreferencesTableModel.setData(new TimePreferences());
	}

	private void ConfigureWeekPreferencesTable() {
		this.weekPreferencesTable.setRowHeight(35);
		for (int i = 0; i <= 12; i++) {
			this.weekPreferencesTable.getColumnModel().getColumn(i).setMaxWidth(35);
		}
		this.weekPreferencesTable.setDefaultRenderer(String.class, new WeekPreferencesCellRenderer());
		this.weekPreferencesTable.setRowSelectionAllowed(false);
		this.weekPreferencesTable.setColumnSelectionAllowed(false);
		this.weekPreferencesTable.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		this.weekPreferencesTable.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				Point point = e.getPoint();
				int rowIndex = target.rowAtPoint(point);
				int columnIndex = target.columnAtPoint(point);
				WeekPreferencesTableModel tableModel = (WeekPreferencesTableModel) weekPreferencesTable.getModel();
				tableModel.incrementCellPreference(rowIndex, columnIndex);
				weekPreferencesTable.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	private void SetTimePreferencesCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateCreateNewTimePreferencesPanel());
		this.add(CreateViewAllTimePreferencesPanel());
	}

	private JPanel CreateCreateNewTimePreferencesPanel() {
		JPanel createNewTimePreferencesPanel = new JPanel(new GridLayout(1, 2));
		createNewTimePreferencesPanel.add(CreateWeekPreferencesPanel());
		createNewTimePreferencesPanel.add(CreateExplanatoryPanelPanel());

		return createNewTimePreferencesPanel;
	}

	private JPanel CreateWeekPreferencesPanel() {
		JPanel weekPreferencesPanel = new JPanel();
		weekPreferencesPanel.setLayout(new BoxLayout(weekPreferencesPanel, BoxLayout.Y_AXIS));
		weekPreferencesPanel.add(this.weekPreferencesTable);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);
		weekPreferencesPanel.add(this.notificationLabel);
		weekPreferencesPanel.add(CreateCrudButtonsPanel());
		
		// Adjust panel on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(weekPreferencesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update Time Preferences"));

		return adjustmentPanel;
	}

	private JPanel CreateExplanatoryPanelPanel() {
		JPanel explanatoryPanel = new JPanel();
		explanatoryPanel.setLayout(new BoxLayout(explanatoryPanel, BoxLayout.Y_AXIS));
		for (int i = TimePreferences.TimePreference.values().length -1; i >= 0; i--) {
			Color color = WeekPreferencesCellRenderer.ColorsForPreference[i];
			String text = TimePreferences.getTimePreferenceNameByIndex(i);
			explanatoryPanel.add(CreateColorPanel(text ,color));
		}
		
		// Adjust panel on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(explanatoryPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Color/Preference relations"));

		return adjustmentPanel;
	}

	private JPanel CreateColorPanel(String text, Color color) {
		JTextField colorTextField = new JTextField(10);
		colorTextField.setBackground(color);
		colorTextField.setEditable(false);
		
		JLabel colorLabel = new JLabel(text, JLabel.TRAILING);
		colorLabel.setLabelFor(colorTextField);
		colorLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel colorPanel = new JPanel();
		colorPanel.add(colorLabel);
		colorPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		colorPanel.add(colorTextField);

		return colorPanel;
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

	private JPanel CreateCrudButtonsPanel() {
		JPanel crudButtonsPanel = new JPanel();
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAndSaveNewTimePreferences();
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

	private void CreateAndSaveNewTimePreferences() {
		try {
			TimePreferences timePreferences = this.weekPreferencesTableModel.getData();

			if (this.idOfTheTimePreferencesToUpdate != 0) {
				timePreferencesDAOService.Update(this.idOfTheTimePreferencesToUpdate, timePreferences);
				RefreshTable();
				ClearAllFields();
				JOptionPane.showMessageDialog(null, "Updated successfully!");
				LOGGER.log(Level.FINE, "Update performed on timePreferences with id {0}.",
						new Object[] { timePreferences.getId() });
			} else {
				timePreferencesDAOService.persist(timePreferences);
				RefreshTable();
				ClearAllFields();
				JOptionPane.showMessageDialog(null, "Saved successfully!");
				LOGGER.log(Level.FINE, "Create performed on timePreferences. ");
			}
		} catch (Exception e) {
			this.notificationLabel
					.setText("A error occured while saving the timePreferences. Check log files for more info!");
			LOGGER.log(Level.SEVERE, "A error occured while saving the timePreferences" + e.toString(), e);
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.timePreferencesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a timePreferences while no row was selected.");
		} else {
			TimePreferences existingTimePreferences = this.timePreferencesTableModel.elementAt(selecteRow);
			this.idOfTheTimePreferencesToUpdate = existingTimePreferences.getId();
			this.weekPreferencesTableModel.setData(existingTimePreferences);
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.timePreferencesTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a timePreferences while no row was selected.");
		} else {
			try {
				TimePreferences existingTimePreferences = this.timePreferencesTableModel.elementAt(selecteRow);
				timePreferencesDAOService.remove(existingTimePreferences);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on timePreferences with id {0}.",
						new Object[] { existingTimePreferences.getId() });
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this timePreferences."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting roomType. Please make sure that nothing else depends on this timePreferences."
								+ e.toString(),
						e);
			}
		}
	}

	private void ClearAllFields() {
		RefreshTableWeekPreferences();
		this.idOfTheTimePreferencesToUpdate = 0;
		this.notificationLabel.setText("  ");
	}

	private JPanel CreateViewAllTimePreferencesPanel() {
		JPanel viewAllTimePreferencesPanel = new JPanel(new BorderLayout());
		viewAllTimePreferencesPanel.setBorder(CreateRaisedBevelTitledBorder("View All Time Preferences"));

		JScrollPane scrollPane = new JScrollPane();
		this.timePreferencesTable.setShowVerticalLines(true);
		this.timePreferencesTable.setShowHorizontalLines(true);
		this.timePreferencesTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.timePreferencesTable);

		viewAllTimePreferencesPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllTimePreferencesPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}
