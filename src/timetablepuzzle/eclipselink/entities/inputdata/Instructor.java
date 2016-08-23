package timetablepuzzle.eclipselink.entities.inputdata;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.administration.TimePreferences;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.DayOfTheWeek;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.TimePreference;

@Entity
@Table(name="instructors")
public class Instructor{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="academic_title")
	private String academicTitle;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="timepreferences_id", unique=true, nullable=false, updatable=false)
	private TimePreferences timePreferences;

	public Instructor()
	{
		this(0,"NoName","NoPosition",new TimePreferences());
	}

	public Instructor(int id, String name, String academicTitle,
			TimePreferences timePreferences)
	{
		this.id = id;
		setName(name);
		setAcademicTitle(academicTitle);
		setTimePreferences(timePreferences);
	}
	/**********Getters and setters**************/
	public int getId() {
		return id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	/**************Methods that model the class behavior******************/
}
