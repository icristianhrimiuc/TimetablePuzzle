package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="time_preferences")
public class TimePreferences{
	public static enum DayOfTheWeek{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY};	
	public static enum TimePreference{PROHIBITED,STRONGLY_DISCOURAGED,DISCOURAGED,
		NEUTRAL,PREFFERED,STRONGLY_PREFFERED,REQUIRED};

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="timeslots_perday")
	private int nrOfTimeSlotsPerDay;
	
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
		this(0, 12, "3,3,3,3,3,3,3,3,3,3,3,3", "3,3,3,3,3,3,3,3,3,3,3,3",
				"3,3,3,3,3,3,3,3,3,3,3,3", "3,3,3,3,3,3,3,3,3,3,3,3", 
				"3,3,3,3,3,3,3,3,3,3,3,3");
	}

	public TimePreferences(int id, int nrOfTimeSlotsPerDay, String monPrefences, String tuePrefences,
			String wedPrefences, String thuPrefences, String friPrefences)
	{
		this.id = id;
		setNrOfTimeSlotsPerDay(nrOfTimeSlotsPerDay);
		setMonPrefences(monPrefences);
		setTuePrefences(tuePrefences);
		setWedPrefences(wedPrefences);
		setThuPrefences(thuPrefences);
		setFriPrefences(friPrefences);
	}
	
	/*********Getters and setters*********/
	public int getId() {
		return id;
	}
	
	public String getMonPrefences()
	{
		return this.monPreferences;
	}
	
	public void setMonPrefences(String monPreferences)
	{
		this.monPreferences = monPreferences;
		setPreferencesByDay(DayOfTheWeek.MONDAY,monPreferences);
	}

	public String getTuePrefences()
	{
		return this.tuePreferences;
	}
	
	public void setTuePrefences(String tuePrefences)
	{
		this.tuePreferences = tuePrefences;
		setPreferencesByDay(DayOfTheWeek.TUESDAY,tuePrefences);
	}

	public String getWedPref()
	{
		return this.wedPreferences;
	}
	
	public void setWedPrefences(String wedPrefences)
	{
		this.wedPreferences = wedPrefences;
		setPreferencesByDay(DayOfTheWeek.WEDNESDAY,wedPrefences);
	}

	public String getThuPrefences()
	{
		return this.thuPreferences;
	}
	
	public void setThuPrefences(String thuPrefences)
	{
		this.thuPreferences = thuPrefences;
		setPreferencesByDay(DayOfTheWeek.THURSDAY,thuPrefences);
	}

	public String getFriPreferences()
	{
		return this.friPreferences;
	}
	
	public void setFriPrefences(String friPreferences)
	{
		this.friPreferences = friPreferences;
		setPreferencesByDay(DayOfTheWeek.FRIDAY,friPreferences);
	}
	
	public int getNrOfTimeSlotsPerDay() {
		return nrOfTimeSlotsPerDay;
	}

	public void setNrOfTimeSlotsPerDay(int nrOfTimeSlotsPerDay) {
		this.nrOfTimeSlotsPerDay = nrOfTimeSlotsPerDay;
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
		setPreferencesByDay(dayOfTheWeek, DecodeDayPreferences(dayPreferences.split(",")));
	}
	
	private TimePreference[] DecodeDayPreferences(String[] preferences)
	{
		TimePreference[] timePreferences = new TimePreference[this.nrOfTimeSlotsPerDay];
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
		if(timePreferences.length == this.nrOfTimeSlotsPerDay)
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
}
