package timetablepuzzle.swing.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.UserJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.UserDAO;
import timetablepuzzle.entities.administration.User;
import timetablepuzzle.swing.PasswordAuthentication;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog{
	private static PasswordAuthentication passAuth = new PasswordAuthentication();
    private UserDAO userDAOService = new UserJPADAOService();
	
	private JFrame parentFrame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel statusLabel;
    private User loggedUser;
    private boolean isValidUser;

    public LoginDialog() {
        this(null, true);
    }

    public LoginDialog(final JFrame parentFrame, boolean modal) {
        super(parentFrame, modal);
        this.parentFrame = parentFrame;
    	initializeComponentsAndWindow();
    }
    
	public User execute() {
		refreshAll();
		this.setVisible(true);
        
		return loggedUser;
	}

	private void refreshAll() {
		this.usernameField.setText("");
		this.passwordField.setText("");
		this.statusLabel.setText("");
		this.isValidUser = false;
	}
	
	public boolean isValidUser() {
		return isValidUser;
	}
	
	private void initializeComponentsAndWindow()
	{
    	initializeUsernameFieldWithListener();
    	initializePasswordFieldWithListener();
    	initializeStatusLabel();
        initializeLoginDialogWindow();
	}
    
    private void initializeUsernameFieldWithListener()
    {
    	usernameField = new JTextField(15);
    	usernameField.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e)
            {
        		checkCredentials();
            }
        });
    }
    
    private void initializePasswordFieldWithListener()
    {
    	passwordField = new JPasswordField();
    	passwordField.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e)
            {
        		checkCredentials();
            }
        });
    }
    
    private void initializeStatusLabel()
    {
    	statusLabel = new JLabel(" ");
    	statusLabel.setForeground(Color.RED);
    	statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void initializeLoginDialogWindow()
    {
    	this.setLayout(new BorderLayout());
        this.add(createUsernameNPassPanel(), BorderLayout.CENTER);
        this.add(createButtonsAndStatusLabelPanel(), BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }
        });
    }
    
    private JPanel createUsernameNPassPanel()
    {
    	JPanel usernameNPass = new JPanel();
    	usernameNPass.add(createUsernameNPassLabelsPanel());
    	usernameNPass.add(createUsernameNPassTextFieldsPanel());
    	
    	return usernameNPass;
    }
    
    private JPanel createUsernameNPassTextFieldsPanel(){
    	JPanel usernameNPassTextFields = new JPanel(new GridLayout(2, 1));
    	initializeUsernameFieldWithListener();
    	initializePasswordFieldWithListener();
    	usernameNPassTextFields.add(usernameField);
    	usernameNPassTextFields.add(passwordField);
    	
    	return usernameNPassTextFields;
    }
    
    private JPanel createUsernameNPassLabelsPanel()
    {
    	JPanel usernameNPassLabels = new JPanel(new GridLayout(2, 1));
    	usernameNPassLabels.add(new JLabel("Username"));
    	usernameNPassLabels.add(new JLabel("Password"));
    	
    	return usernameNPassLabels;
    }
    
    private JPanel createButtonsAndStatusLabelPanel()
    {
    	JPanel buttonsNStatusLabel = new JPanel(new BorderLayout());
    	buttonsNStatusLabel.add(createLoginNCancelButtonsPanel(),BorderLayout.CENTER);
    	buttonsNStatusLabel.add(statusLabel, BorderLayout.NORTH);
    	
        return buttonsNStatusLabel;
    }
    
    private JPanel createLoginNCancelButtonsPanel()
    {
    	JPanel loginNCancelButtons = new JPanel();
    	loginNCancelButtons.add(createButtonLoginWithListener());
    	loginNCancelButtons.add(createButtonCancelWithListener());
    	
    	return loginNCancelButtons;
    }
    
    private JButton createButtonLoginWithListener()
    {
    	JButton buttonLogin = new JButton("Login");
    	buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	checkCredentials();
            }
        });
    	
    	return buttonLogin;
    }
    
    private JButton createButtonCancelWithListener()
    {
    	JButton buttonCancel = new JButton("Cancel");
    	buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                parentFrame.dispose();
                System.exit(0);
            }
        });
    	
    	return buttonCancel;
    }
    
    private void checkCredentials()
    {
    	String username = usernameField.getText();
    	User tempUser = userDAOService.findByUsername(username);
    	if(tempUser != null)
    	{
            if(passAuth.authenticate(passwordField.getPassword(), tempUser.getPasswordToken()))
            {
            	isValidUser = true;
            	loggedUser = tempUser;
                this.setVisible(false);
            } else {
                statusLabel.setText("Invalid password!");
            }
    	}else {
    		statusLabel.setText("Invalid username!");
        }
    }
}
