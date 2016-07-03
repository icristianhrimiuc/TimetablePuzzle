package timetablepuzzle.eclipselink.entities.inputdata;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.E;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.Day;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.TimePref;

@Entity
@Table(name="instructors")
public class Instructor extends E{
	/***********Regular properties*************/
	@Column(name="name")
	private String _name;
	
	@Column(name="position")
	private String _position;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="time_preferences", unique=true, nullable=false, updatable=false)
	private TimePreferences _timePreferences;
	
	/**
	 * Default Constructor
	 */
	public Instructor()
	{
		this(0,"NoName","NoPosition",new TimePreferences());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param name
	 * @param position
	 * @param preferences
	 */
	public Instructor(int externalId, String name, String position,
			TimePreferences timePreferences)
	{
		_externalId = externalId;
		set_name(name);
		set_position(position);
		set_timePreferences(timePreferences);
	}
	/**********Getters and setters**************/
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_position() {
		return _position;
	}

	public void set_position(String _position) {
		this._position = _position;
	}
	
	public TimePreferences get_timePreferences()
	{
		return this._timePreferences;
	}
	
	public void set_timePreferences(TimePreferences timePreferences)
	{
		this._timePreferences = timePreferences;
	}
	
	public TimePref[] get_timePreferencesByDay(Day dayOfTheWeek) {
		return _timePreferences.GetPreferencesByDay(dayOfTheWeek);
	}

	public void set_preferencesByDay(Day dayOfTheWeek, TimePref[] timePreferences) {
		this._timePreferences.SetPreferencesByDay(dayOfTheWeek, timePreferences);
	}
	
	public TimePref get_timePreferencesByTime(Day dayOfTheWeek, int slotNr) {
		return _timePreferences.GetPreferencesByTime(dayOfTheWeek, slotNr);
	}

	public void set_timePreferencesByTime(Day dayOfTheWeek, TimePref _timePref,
			int slotNr) 
	{
		this._timePreferences.SetPreferencesByTime(dayOfTheWeek, _timePref, slotNr);
	}
	/**************Methods that model the class behavior******************/
}
