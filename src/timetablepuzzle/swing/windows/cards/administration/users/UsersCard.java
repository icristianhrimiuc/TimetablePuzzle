package timetablepuzzle.swing.windows.cards.administration.users;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.UserJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.UserDAO;
import timetablepuzzle.entities.administration.User;
import timetablepuzzle.entities.administration.User.UserType;
import timetablepuzzle.swing.PasswordAuthentication;
import timetablepuzzle.swing.windows.cards.common.CustomComboBoxModel;

public class UsersCard extends JPanel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(UsersCard.class.getName());
	private static UserDAO userDAOService = new UserJPADAOService();

	private UsersTableModel usersTableModel;
	private JTable usersTable;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldUsername;
	private CustomComboBoxModel<UserType> comboBoxUserTypeModel;
	private JComboBox<UserType> comboBoxUserType;
	private JPasswordField passwordField;
	private JLabel notificationLabel;
	private int idOfTheUserToUpdate;
	private PasswordAuthentication passAuth;

	public UsersCard(Color backgroundColor) {
		this.setBackground(backgroundColor);

		// Users table
		this.usersTableModel = new UsersTableModel();
		RefreshTable();
		this.usersTable = new JTable(this.usersTableModel);
		DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
		defaultRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.usersTable.setDefaultRenderer(String.class, defaultRenderer);
		SetColumnsMaxSizes();

		// Property text fields
		this.textFieldFirstName = CreatePropertyTextField(15);
		this.textFieldLastName = CreatePropertyTextField(20);
		this.textFieldUsername = CreatePropertyTextField(30);
		this.passwordField = new JPasswordField(25);
		this.passwordField.setHorizontalAlignment(JTextField.CENTER);
		
		// UserType combo box
		this.comboBoxUserTypeModel = new CustomComboBoxModel<UserType>();
		RefreshComboBoxUserType();
		this.comboBoxUserType = new JComboBox<UserType>(this.comboBoxUserTypeModel);

		// Notification label
		this.notificationLabel = new JLabel("  ");
		this.notificationLabel.setForeground(Color.RED);
		this.notificationLabel.setAlignmentX(CENTER_ALIGNMENT);

		this.idOfTheUserToUpdate = 0;
		this.passAuth = new PasswordAuthentication();
		SetUsersCardComponents();
	}

	private void RefreshTable() {
		this.usersTableModel.setData(userDAOService.GetAll());
	}

	private void SetColumnsMaxSizes() {
		this.usersTable.getColumnModel().getColumn(0).setMaxWidth(60);
	}
	
	private JTextField CreatePropertyTextField(int width){
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
	
	private void RefreshComboBoxUserType(){
		this.comboBoxUserTypeModel.setData(Arrays.asList(UserType.values()));
		this.repaint();
	}

	private void SetUsersCardComponents() {
		this.setLayout(new GridLayout(2, 1));
		this.add(CreateNewUserPanel());
		this.add(CreateViewAllUsersPanel());
	}

	private JPanel CreateNewUserPanel() {
		JPanel createNewUserPanel = new JPanel(new GridLayout(1, 1));
		createNewUserPanel.add(CreatePropertiesPanel());

		return createNewUserPanel;
	}

	private JPanel CreatePropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
		propertiesPanel.add(CreatePropertyPanel("First Name", this.textFieldFirstName));
		propertiesPanel.add(CreatePropertyPanel("Last Name", this.textFieldLastName));
		propertiesPanel.add(CreatePropertyPanel("Username", this.textFieldUsername));
		propertiesPanel.add(CreatePropertyPanel("Password", this.passwordField));
		propertiesPanel.add(CreatePropertyPanel("User Type", this.comboBoxUserType));
		propertiesPanel.add(this.notificationLabel);
		propertiesPanel.add(CreateCrudButtonsPanel());

		// Adjust properties on center
		JPanel adjustmentPanel = CreateAdjustmentPanel(propertiesPanel);
		adjustmentPanel.setBorder(CreateRaisedBevelTitledBorder("Create/Update User"));
		
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
		String firstName = this.textFieldFirstName.getText();
		String lastName = this.textFieldLastName.getText();
		String username = this.textFieldUsername.getText();
		char[] pass = this.passwordField.getPassword();
		UserType userType = (UserType)this.comboBoxUserType.getSelectedItem(); 

		if ((firstName.isEmpty()) || (lastName.isEmpty()) || (username.isEmpty()) || (pass.length == 0) || (userType == null)) {
			this.notificationLabel.setText("Please make sure that all the property fields are filled!");
			LOGGER.log(Level.WARNING, "Attempt to create a new user with empty property field.");
		} else {
			try {
				String password = this.passAuth.hash(pass);
				User user = new User(this.idOfTheUserToUpdate, firstName, lastName, username, password, userType, null, null, null);

				if (this.idOfTheUserToUpdate != 0) {
					userDAOService.merge(user);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Updated successfully!");
					LOGGER.log(Level.FINE, "Update performed on user with id {0} and named {1} {2}.",
							new Object[] { user.getId(), user.getFirstName(), user.getLastName() });
				} else {
					userDAOService.merge(user);
					RefreshAllFields();
					JOptionPane.showMessageDialog(null, "Saved successfully!");
					LOGGER.log(Level.FINE, "Create performed on user named {1} {2}. ",
							new Object[] { user.getFirstName(), user.getLastName() });
				}
			} catch (Exception e) {
				this.notificationLabel
						.setText("A error occured while saving the user. Check log files for more info!");
				LOGGER.log(Level.SEVERE, "A error occured while saving the user" + e.toString(), e);
			}
		}
	}

	private void LoadSelectedRowDetailsForEdit() {
		int selecteRow = this.usersTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to edit a user while no row was selected.");
		} else {
			User existingUser = this.usersTableModel.elementAt(selecteRow);
			this.idOfTheUserToUpdate = existingUser.getId();
			this.textFieldFirstName.setText(existingUser.getFirstName());
			this.textFieldLastName.setText(existingUser.getLastName());
			this.textFieldUsername.setText(existingUser.getUsername());
			this.passwordField.setText("********");
			this.comboBoxUserTypeModel.setSelectedItem(existingUser.getUserType());
			this.repaint();
		}
	}

	private void DeleteSelectedRow() {
		int selecteRow = this.usersTable.getSelectedRow();
		if (selecteRow == -1) {
			this.notificationLabel.setText("Please select a row from the table first.");
			LOGGER.log(Level.WARNING, "An attempt was made to delete a user while no row was selected.");
		} else {
			try {
				User existingUser = this.usersTableModel.elementAt(selecteRow);
				userDAOService.remove(existingUser);
				RefreshTable();
				this.notificationLabel.setText("  ");
				JOptionPane.showMessageDialog(null, "Deleted successfully!");
				LOGGER.log(Level.FINE, "Delete performed on user with id {0} and named {1} {2}.",
						new Object[] { existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName()});
			} catch (Exception e) {
				this.notificationLabel
						.setText("An error occured. Please make sure that nothing else depends on this user."
								+ " Check log files for more info.");
				LOGGER.log(Level.SEVERE,
						"Exception occured on deleting user. Please make sure that nothing else depends on this user."
								+ e.toString(),	e);
			}
		}
	}

	private void RefreshAllFields() {
		this.textFieldFirstName.setText("");
		this.textFieldLastName.setText("");
		this.textFieldUsername.setText("");
		this.passwordField.setText("");
		RefreshComboBoxUserType();
		this.notificationLabel.setText("  ");
		this.idOfTheUserToUpdate = 0;
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

	private JPanel CreateViewAllUsersPanel() {
		JPanel viewAllUsersPanel = new JPanel(new BorderLayout());
		viewAllUsersPanel.setBorder(CreateRaisedBevelTitledBorder("View All Users"));

		JScrollPane scrollPane = new JScrollPane();
		this.usersTable.setShowVerticalLines(true);
		this.usersTable.setShowHorizontalLines(true);
		this.usersTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(this.usersTable);

		viewAllUsersPanel.add(scrollPane, BorderLayout.CENTER);

		return viewAllUsersPanel;
	}

	private TitledBorder CreateRaisedBevelTitledBorder(String title) {
		Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder raisedBevelTitledBorder = BorderFactory.createTitledBorder(raisedBevelBorder, title);
		raisedBevelTitledBorder.setTitleJustification(TitledBorder.LEFT);

		return raisedBevelTitledBorder;
	}
}

