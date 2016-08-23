package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User{
	public static enum UserType{NOT_ASSIGNED,GUEST,INSTRUCTOR,SECRETARY,ADMIN};
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password_token")
	private String passwordToken;
	
	@Column(name="user_type")
	private UserType userType;

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lastviewed_academicyear_id")
	private AcademicYear lastViewedAcademicYear;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lastviewed_academicsession_id")
	private AcademicSession lastViewedAcademicSession;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lastviewed_faculty_id")
	private Faculty lastViewedFaculty;

	public User()
	{
		this(0,"NoUsername","NoToken",UserType.NOT_ASSIGNED, "NoFirstName",
				"NoLastName", new AcademicYear(), new AcademicSession(), new Faculty());
	}

	public User(int id, String username, String passwordToken, UserType userType,
			String firstName, String lastName, AcademicYear lastViewedAcademicYear, 
			AcademicSession lastViewedAcademicSession, Faculty lastViewedFaculty)
	{
		this.id = id;
		this.setUsername(username);
		this.setToken(passwordToken);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setLastViewedAcademicYear(lastViewedAcademicYear);
		this.setLastViewedAcademicSession(lastViewedAcademicSession);
		this.setLastViewedFaculty(lastViewedFaculty);
	}

	/****************Getters and setters********************/
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public void setToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AcademicYear getLastViewedAcademicYear() {
		return lastViewedAcademicYear;
	}

	public void setLastViewedAcademicYear(AcademicYear lastViewedAcademicYear) {
		this.lastViewedAcademicYear = lastViewedAcademicYear;
	}
	
	public AcademicSession getLastViewedAcademicSession() {
		return lastViewedAcademicSession;
	}

	public void setLastViewedAcademicSession(AcademicSession lastViewedAcademicSession) {
		this.lastViewedAcademicSession = lastViewedAcademicSession;
	}

	public Faculty getLastViewedFaculty() {
		return lastViewedFaculty;
	}

	public void setLastViewedFaculty(Faculty lastViewedFaculty) {
		this.lastViewedFaculty = lastViewedFaculty;
	}
	
	/***********Properties that model the class behavior***************/
}
