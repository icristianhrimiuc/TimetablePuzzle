package timetablepuzzle.eclipselink.entities.administration;

import timetablepuzzle.eclipselink.entities.E;
import javax.persistence.*;

@Entity
@Table(name="users")
public class User extends E{
	/************Static Properties***********/
	public static enum UserType{NOT_ASSIGNED,GUEST,SECRETARY,INSTRUCTOR,ADMIN};
	/************Regular Properties***********/
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
	
	/*
	 * Default constructor
	 */
	public User()
	{
		this(0,"NoUsername","NoToken",UserType.NOT_ASSIGNED, "NoFirstName", "NoLastName");
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
			String firstName, String lastName)
	{
		this._externalId = external_id;
		this.set_username(username);
		this.set_token(token);
		this.set_userType(userType);
		this.set_firstName(firstName);
		this.set_lastName(lastName);
	}

	/****************Getters and setters********************/
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
	/***********Properties that model the class behavior***************/
}
