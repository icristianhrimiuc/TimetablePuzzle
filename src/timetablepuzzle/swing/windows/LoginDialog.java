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
import timetablepuzzle.eclipselink.entities.administration.User;
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
    	InitializeComponentsAndWindow();
    }
    
	public User execute() {
		this.setVisible(true);
        
		return loggedUser;
	}
	
	public boolean isValidUser() {
		return isValidUser;
	}
	
	private void InitializeComponentsAndWindow()
	{
    	InitializeUsernameFieldWithListener();
    	InitializePasswordFieldWithListener();
    	InitializeStatusLabel();
        InitializeLoginDialogWindow();
	}
    
    private void InitializeUsernameFieldWithListener()
    {
    	usernameField = new JTextField(15);
    	usernameField.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e)
            {
        		PopulateTheLoggedUserField();
            }
        });
    }
    
    private void InitializePasswordFieldWithListener()
    {
    	passwordField = new JPasswordField();
    	passwordField.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e)
            {
        		PopulateTheLoggedUserField();
            }
        });
    }
    
    private void InitializeStatusLabel()
    {
    	statusLabel = new JLabel(" ");
    	statusLabel.setForeground(Color.RED);
    	statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void InitializeLoginDialogWindow()
    {
    	this.setLayout(new BorderLayout());
        this.add(CreateUsernameNPassPanel(), BorderLayout.CENTER);
        this.add(CreateButtonsAndStatusLabelPanel(), BorderLayout.SOUTH);
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
    
    private JPanel CreateUsernameNPassPanel()
    {
    	JPanel usernameNPass = new JPanel();
    	usernameNPass.add(CreateUsernameNPassLabelsPanel());
    	usernameNPass.add(CreateUsernameNPassTextFieldsPanel());
    	
    	return usernameNPass;
    }
    
    private JPanel CreateUsernameNPassTextFieldsPanel(){
    	JPanel usernameNPassTextFields = new JPanel(new GridLayout(2, 1));
    	InitializeUsernameFieldWithListener();
    	InitializePasswordFieldWithListener();
    	usernameNPassTextFields.add(usernameField);
    	usernameNPassTextFields.add(passwordField);
    	
    	return usernameNPassTextFields;
    }
    
    private JPanel CreateUsernameNPassLabelsPanel()
    {
    	JPanel usernameNPassLabels = new JPanel(new GridLayout(2, 1));
    	usernameNPassLabels.add(new JLabel("Utilizator"));
    	usernameNPassLabels.add(new JLabel("Parola"));
    	
    	return usernameNPassLabels;
    }
    
    private JPanel CreateButtonsAndStatusLabelPanel()
    {
    	JPanel buttonsNStatusLabel = new JPanel(new BorderLayout());
    	buttonsNStatusLabel.add(CreateLoginNCancelButtonsPanel(),BorderLayout.CENTER);
    	buttonsNStatusLabel.add(statusLabel, BorderLayout.NORTH);
    	
        return buttonsNStatusLabel;
    }
    
    private JPanel CreateLoginNCancelButtonsPanel()
    {
    	JPanel loginNCancelButtons = new JPanel();
    	loginNCancelButtons.add(CreateButtonLoginWithListener());
    	loginNCancelButtons.add(CreateButtonCancelWithListener());
    	
    	return loginNCancelButtons;
    }
    
    private JButton CreateButtonLoginWithListener()
    {
    	JButton buttonLogin = new JButton("Logare");
    	buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	PopulateTheLoggedUserField();
            }
        });
    	
    	return buttonLogin;
    }
    
    private JButton CreateButtonCancelWithListener()
    {
    	JButton buttonCancel = new JButton("Anuleaza");
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
    
    private void PopulateTheLoggedUserField()
    {
    	String username = usernameField.getText();
    	User tempUser = userDAOService.findByUsername(username);
    	if(tempUser != null)
    	{
            if(passAuth.authenticate(passwordField.getPassword(), tempUser.get_token()))
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
