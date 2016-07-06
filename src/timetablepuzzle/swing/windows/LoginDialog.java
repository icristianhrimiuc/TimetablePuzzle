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
	/**************Final fields*************/
	private final JLabel _jlblUsername = new JLabel("Utilizator");
    private final JLabel _jlblPassword = new JLabel("Parola");
    private final JTextField _jtfUsername = new JTextField(15);
    private final JPasswordField _jpfPassword = new JPasswordField();
    private final JButton _jbtOk = new JButton("Logare");
    private final JButton _jbtCancel = new JButton("Anuleaza");
    private final JLabel _jlblStatus = new JLabel(" ");
    /**************Normal fields************/
    private static PasswordAuthentication passAuth = new PasswordAuthentication();
    private UserDAO _userDAOService;
    private User _loggedUser;
    private boolean _isValidUser;

    /**
     * Default constructor.
     */
    public LoginDialog() {
        this(null, true);
    }

    /**
     * Parameterized constructor.
     * @param parent
     * @param modal
     */
    public LoginDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        // Create panel for the labels of the user name and password
        JPanel p3 = new JPanel(new GridLayout(2, 1));
        p3.add(_jlblUsername);
        p3.add(_jlblPassword);
        // Create panel for the text fields of the user name and password 
        JPanel p4 = new JPanel(new GridLayout(2, 1));
        p4.add(_jtfUsername);
        p4.add(_jpfPassword);
        // New panel for the above defined panels
        JPanel p1 = new JPanel();
        p1.add(p3);
        p1.add(p4);
        // Create panel for the login and cancel buttons
        JPanel p2 = new JPanel();
        p2.add(_jbtOk);
        p2.add(_jbtCancel);
        // Create global panel
        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p2, BorderLayout.CENTER);
        p5.add(_jlblStatus, BorderLayout.NORTH);
        _jlblStatus.setForeground(Color.RED);
        _jlblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        // Set the properties of this dialog
        this.setLayout(new BorderLayout());
        this.add(p1, BorderLayout.CENTER);
        this.add(p5, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }
        });
        
        
        // Text Field user name listener
        _jtfUsername.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e)
            {
        		_loggedUser = CheckLoginInfo(parent);
            }
        });
        
        // Password field password listener
        _jpfPassword.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e)
            {
        		_loggedUser = CheckLoginInfo(parent);
            }
        });        

        // Button Login listener
        _jbtOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	_loggedUser = CheckLoginInfo(parent);
            }
        });
        
        // Button Cancel listener
        _jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                parent.dispose();
                System.exit(0);
            }
        });
        
       	// Initialize the UserDAOService
    	this._userDAOService  = new UserJPADAOService();
    }
    
    private User CheckLoginInfo(final JFrame parent)
    {
    	// TO DO: get the user from the database, if one exists, 
    	// and check its password token against this strings token
    	String username = _jtfUsername.getText();
    	User tempUser = _userDAOService.findByUsername(username);
    	if(tempUser != null)
    	{
            if(passAuth.authenticate(_jpfPassword.getPassword(), tempUser.get_token()))
            {
            	_isValidUser = true;
                setVisible(false);
            } else {
                _jlblStatus.setText("Invalid password!");
            }
    	}else {
            _jlblStatus.setText("Invalid username!");
        }
    	
    	return tempUser;
    }
    
    /*******************Getters and setters************************/

	public User get_loggedUser() {
		return _loggedUser;
	}
	

	public boolean isValidUser() {
		return _isValidUser;
	}
}
