package timetablepuzzle.entities.inputdata;

import javax.persistence.*;

import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.entities.other.TimePreferences.DayOfTheWeek;
import timetablepuzzle.entities.other.TimePreferences.TimePreference;

@Entity
@Table(name="instructors")
public class Instructor{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="first_name",nullable=false)
	private String firstName;
	
	@Column(name="last_name",nullable=false)
	private String lastName;
	
	@Column(name="academic_title")
	private String academicTitle;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},optional=false)
	@JoinColumn(name="timepreferences_id", unique=true, nullable=false, updatable=false)
	private TimePreferences timePreferences;

	public Instructor()
	{
		this(0,"Nemo", "Nobody", "NoPosition",new TimePreferences());
	}

	public Instructor(int id, String firstName, String lastName, String academicTitle,
			TimePreferences timePreferences)
	{
		this.id = id;
		this.setFirstName(firstName);
		this.setLastName(lastName);
		setAcademicTitle(academicTitle);
		setTimePreferences(timePreferences);
	}
	
	/**********Getters and setters**************/
	public int getId() {
		return id;
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

	public String getAcademicTitle() {
		return this.academicTitle;
	}

	public void setAcademicTitle(String commonCourses) {
		this.academicTitle = commonCourses;
	}
	
	public TimePreferences getTimePreferences()
	{
		return this.timePreferences;
	}
	
	public void setTimePreferences(TimePreferences timePreferences)
	{
		this.timePreferences = timePreferences;
	}
	
	/**************Methods that model the class behavior******************/
	
	public TimePreference[] getTimePreferencesByDay(DayOfTheWeek dayOfTheWeek) {
		return timePreferences.getPreferencesByDay(dayOfTheWeek);
	}

	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, TimePreference[] timePreferences) {
		this.timePreferences.setPreferencesByDay(dayOfTheWeek, timePreferences);
	}
	
	public TimePreference getPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, int slotNr) {
		return timePreferences.getPreferencesByDayAndTime(dayOfTheWeek, slotNr);
	}

	public void setPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, TimePreference timePref,
			int slotNr) 
	{
		this.timePreferences.setPreferencesByDayAndTime(dayOfTheWeek, timePref, slotNr);
	}
	
	/********************Overridden methods************************/
	@Override
	public String toString() {
		return String.format("%s %s %s", this.academicTitle, this.firstName, this.lastName);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Instructor);
		if (equals) {
			Instructor other = (Instructor) o;
			equals = ((this.id == other.getId()) && 
					(this.firstName.equals(other.getFirstName())) && 
					(this.lastName.equals(other.getLastName())) && 
					(this.academicTitle.equals(other.getAcademicTitle())) && 
					(this.timePreferences.equals(other.getTimePreferences()))
					);
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Inctructor:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
