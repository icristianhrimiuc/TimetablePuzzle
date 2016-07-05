package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User{
	/************Static Properties***********/
	public static enum UserType{NOT_ASSIGNED,GUEST,SECRETARY,INSTRUCTOR,ADMIN};
	/************Regular Properties***********/
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	@Column(name="username")
	private String _username;
	
	@Column(name="pass_token")
	private String _token;
	
	@Column(name="user_type")
	private UserType _userType;

	@Column(name="first_name")
	private String _firstName;
	
	@Column(name="last_name")
	private String _lastName;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lastviewed_acadsession")
	private AcademicSession _lastViewedAcadSession;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lastviewed_faculty")
	private Faculty _lastViewedFaculty;
	
	/*
	 * Default constructor
	 */
	public User()
	{
		this(0,"NoUsername","NoToken",UserType.NOT_ASSIGNED, "NoFirstName",
				"NoLastName", new AcademicSession(), new Faculty());
	}
	
	/**
	 * Parameterized constructor
	 * @param external_id
	 * @param username
	 * @param md5Password
	 * @param userType
	 * @param firstName
	 * @param lastName
	 */
	public User(int external_id, String username, String token, UserType userType,
			String firstName, String lastName, AcademicSession lastViewedAcadSession, 
			Faculty lastViewedFaculty)
	{
		this._externalId = external_id;
		this.set_username(username);
		this.set_token(token);
		this.set_userType(userType);
		this.set_firstName(firstName);
		this.set_lastName(lastName);
		this.set_lastViewedAcadSession(lastViewedAcadSession);
		this.set_lastViewedFaculty(lastViewedFaculty);
	}

	/****************Getters and setters********************/
	
	public int get_externalId() {
		return _externalId;
	}
	
	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public String get_token() {
		return _token;
	}

	public void set_token(String token) {
		this._token = token;
	}

	public UserType get_userType() {
		return _userType;
	}

	public void set_userType(UserType _userType) {
		this._userType = _userType;
	}

	public String get_firstName() {
		return _firstName;
	}

	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}

	public String get_lastName() {
		return _lastName;
	}

	public void set_lastName(String _lastName) {
		this._lastName = _lastName;
	}

	public AcademicSession get_lastViewedAcadSession() {
		return _lastViewedAcadSession;
	}

	public void set_lastViewedAcadSession(AcademicSession _lastViewedAcadSession) {
		this._lastViewedAcadSession = _lastViewedAcadSession;
	}

	public Faculty get_lastViewedFaculty() {
		return _lastViewedFaculty;
	}

	public void set_lastViewedFaculty(Faculty _lastViewedFaculty) {
		this._lastViewedFaculty = _lastViewedFaculty;
	}
	/***********Properties that model the class behavior***************/
}
