package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

import timetablepuzzle.usecases.solution.TimeslotPattern;

@Entity
@Table(name="time_preferences")
public class TimePreferences{
	public static enum DayOfTheWeek{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY};	
	public static enum TimePreference{PROHIBITED,STRONGLY_DISCOURAGED,DISCOURAGED,
		NEUTRAL,PREFFERED,STRONGLY_PREFFERED,REQUIRED};

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="monday")
	private String monPreferences;
	
	@Column(name="tuesday")
	private String tuePreferences;
	
	@Column(name="wednesday")
	private String wedPreferences;
	
	@Column(name="thursday")
	private String thuPreferences;
	
	@Column(name="friday")
	private String friPreferences;
	
	@Transient
	private TimePreference[] monday;
	
	@Transient
	private TimePreference[] tuesday;
	
	@Transient
	private TimePreference[] wednesday;
	
	@Transient
	private TimePreference[] thursday;
	
	@Transient
	private TimePreference[] friday;

	public TimePreferences()
	{
		this(0, TimeslotPattern.GenerateFreeDay(), TimeslotPattern.GenerateFreeDay(), TimeslotPattern.GenerateFreeDay(),
				TimeslotPattern.GenerateFreeDay(), TimeslotPattern.GenerateFreeDay());
	}

	public TimePreferences(int id, String monPreferences, String tuePreferences,
			String wedPreferences, String thuPreferences, String friPreferences)
	{
		this.id = id;
		setMonPreferences(monPreferences);
		setTuePreferences(tuePreferences);
		setWedPreferences(wedPreferences);
		setThuPreferences(thuPreferences);
		setFriPreferences(friPreferences);
	}
	
	/*********Getters and setters*********/
	public int getId() {
		return this.id;
	}
	
	public String getMonPreferences()
	{
		return this.monPreferences;
	}
	
	public void setMonPreferences(String monPreferences)
	{
		this.monPreferences = monPreferences;
		setPreferencesByDay(DayOfTheWeek.MONDAY,monPreferences);
	}

	public String getTuePreferences()
	{
		return this.tuePreferences;
	}
	
	public void setTuePreferences(String tuePreferences)
	{
		this.tuePreferences = tuePreferences;
		setPreferencesByDay(DayOfTheWeek.TUESDAY,tuePreferences);
	}

	public String getWedPreferences()
	{
		return this.wedPreferences;
	}
	
	public void setWedPreferences(String wedPreferences)
	{
		this.wedPreferences = wedPreferences;
		setPreferencesByDay(DayOfTheWeek.WEDNESDAY,wedPreferences);
	}

	public String getThuPreferences()
	{
		return this.thuPreferences;
	}
	
	public void setThuPreferences(String thuPreferences)
	{
		this.thuPreferences = thuPreferences;
		setPreferencesByDay(DayOfTheWeek.THURSDAY,thuPreferences);
	}

	public String getFriPreferences()
	{
		return this.friPreferences;
	}
	
	public void setFriPreferences(String friPreferences)
	{
		this.friPreferences = friPreferences;
		setPreferencesByDay(DayOfTheWeek.FRIDAY,friPreferences);
	}

	/***************Methods that model the class behavior*******************/
	public TimePreference[] getPreferencesByDay(DayOfTheWeek dayOfTheWeek) {
		TimePreference[] day;
		switch(dayOfTheWeek)
		{
		case MONDAY:
			day = this.monday;
			break;
		case TUESDAY:
			day = this.tuesday;
			break;
		case WEDNESDAY:
			day = this.wednesday;
			break;
		case THURSDAY: 
			day = this.thursday;
			break;
		case FRIDAY:
			day = this.friday;
			break;
		default:
			day = null;
			break;			
		}
		
		return day;
	}

	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, String dayPreferences) {
		String[] preferences = dayPreferences.split(TimeslotPattern.TimeSlotsSeparator);
		setPreferencesByDay(dayOfTheWeek, DecodeDayPreferences(preferences));
	}
	
	private TimePreference[] DecodeDayPreferences(String[] preferences)
	{
		TimePreference[] timePreferences = new TimePreference[TimeslotPattern.NrOfTimeSlotsPerDay];
		for(int i=0; i<preferences.length; i++)
		{
			timePreferences[i] = getTimePreferenceFromString(preferences[i]);
		}
		
		return timePreferences;
	}

	private TimePreference getTimePreferenceFromString(String timePreference) {
		return TimePreference.values()[Integer.parseInt(timePreference)];
	}
	
	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, TimePreference[] timePreferences)
	{
		if(timePreferences.length == TimeslotPattern.NrOfTimeSlotsPerDay)
		{
			switch(dayOfTheWeek)
			{
			case MONDAY:
				this.monday = timePreferences;
				break;
			case TUESDAY:
				this.tuesday = timePreferences;
				break;
			case WEDNESDAY:
				this.wednesday = timePreferences;
				break;
			case THURSDAY: 
				this.thursday = timePreferences;
				break;
			case FRIDAY:
				this.friday = timePreferences;
				break;
			default:
				break;			
			}
		}
	}
	
	public TimePreference getPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek,int slotNr)
	{
		return getPreferencesByDay(dayOfTheWeek)[slotNr];
	}
	
	public void setPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, TimePreference timePref, int slotNr)
	{
		TimePreference[] timePrefs = getPreferencesByDay(dayOfTheWeek);
		timePrefs[slotNr] = timePref;
	}

	@Override
	public String toString() {
		return String.format("%s-%s-%s-%s-%s", this.monPreferences, this.tuePreferences,
				this.wedPreferences, this.thuPreferences, this.friPreferences);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof TimePreferences);
		if (equals) {
			TimePreferences other = (TimePreferences) o;
			equals = ((this.id == other.getId()) && 
					(this.monPreferences.equals(other.getMonPreferences())) && 
					(this.tuePreferences.equals(other.getTuePreferences())) && 
					(this.wedPreferences.equals(other.getWedPreferences())) && 
					(this.thuPreferences.equals(other.getThuPreferences())) &&
					(this.friPreferences.equals(other.getFriPreferences())) 
					);
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("TimePreferences:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
