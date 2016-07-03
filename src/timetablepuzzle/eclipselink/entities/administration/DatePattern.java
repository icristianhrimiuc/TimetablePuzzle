package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.E;

@Entity
@Table(name="date_patterns")
public class DatePattern extends E{
	/***********Regular properties*************/
	@Column(name="name")
	private String _name;
	
	@Column(name="used_dates")
	private String _usedDates;
	
	/**
	 * Default constructor
	 */
	public DatePattern()
	{
		this(0,"NoName","");
	}
	
	/**
	 * Parameterized constructor. Create and initialize a date pattern with given parameters 
	 * @param externalId
	 * @param name
	 */
	public DatePattern(int externalId, String name, String usedDates)
	{
		_externalId = externalId;
		set_name(name);
		_usedDates = usedDates;
	}
	
	/**************Getters and setters******************/
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}
	
	public String get_usedDates(){
		return this._usedDates;
	}
	
	public void set_usedDates(String usedDates)
	{
		this._usedDates = usedDates;
	}
	
	/************Methods that model the class behavior**************/
	/**
	 * Add a new date interval to the existing usedDates
	 * @param dateInterval
	 */
	public void AddDateInterval(String dateInterval)
	{
		_usedDates = _usedDates + dateInterval + ";";
	}
	
	/**
	 * Returns the usedDates interval of this date pattern
	 * @return
	 */
	public String[] GetUsedDatesIntervals()
	{
		return this._usedDates.split(";");
	}
}
