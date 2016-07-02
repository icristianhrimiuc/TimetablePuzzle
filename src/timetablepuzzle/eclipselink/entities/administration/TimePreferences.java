package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.administration.TimePreferencesDAO;
import timetablepuzzle.eclipselink.DAO.services.administration.TimePreferencesJPADAOService;
import timetablepuzzle.eclipselink.entities.E;

@Entity
@Table(name="time_preferences")
public class TimePreferences extends E{
	/***********Static fields*************/
	/**Days of the week**/
	public static enum Day{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY};	
	public static enum TimePref{PROHIBITED,STRONGLY_DISCOURAGED,DISCOURAGED,
		NEUTRAL,PREFFERED,STRONGLY_PREFFERED,REQUIRED};
	public static TimePreferencesDAO timePreferencesDAO = new TimePreferencesJPADAOService();
	/***********Regular properties*************/	
	@Column(name="monday")
	private String _monPref;
	
	@Column(name="tuesday")
	private String _tuePref;
	
	@Column(name="wednesday")
	private String _wedPref;
	
	@Column(name="thursday")
	private String _thuPref;
	
	@Column(name="friday")
	private String _friPref;
	
	@Column(name="nrOfTimeSlotsPerDay")
	private int _nrOfTimeSlotsPerDay;
	
	@Transient
	private TimePref[] _monday;
	
	@Transient
	private TimePref[] _tuesday;
	
	@Transient
	private TimePref[] _wednesday;
	
	@Transient
	private TimePref[] _thursday;
	
	@Transient
	private TimePref[] _friday;
	
	/**
	 * Default constructor
	 */
	public TimePreferences()
	{
		this(0, "3,3,3,3,3,3,3,3,3,3,3,3", "3,3,3,3,3,3,3,3,3,3,3,3",
				"3,3,3,3,3,3,3,3,3,3,3,3", "3,3,3,3,3,3,3,3,3,3,3,3", 
				"3,3,3,3,3,3,3,3,3,3,3,3", 12);
	}
	
	/**
	 * Parameterized constructor.Creating a brand new time preference
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 */
	public TimePreferences(String monPref, String tuePref,
			String wedPref, String thuPref, String friPref, int nrOfTimeSlotsPerDay)
	{
		this(0, monPref, tuePref, wedPref, thuPref, friPref, nrOfTimeSlotsPerDay);
	}
	
	/**
	 * Parameterized constructor.Create a existing time preference from the database
	 * @param externalId
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 */
	public TimePreferences(int externalId,String monPref, String tuePref,
			String wedPref, String thuPref, String friPref, int nrOfTimeSlotsPerDay)
	{
		_externalId = externalId;
		set_nrOfTimeSlotsPerDay(nrOfTimeSlotsPerDay);
		set_monPref(monPref);
		set_tuePref(tuePref);
		set_wedPref(wedPref);
		set_thuPref(thuPref);
		set_friPref(friPref);
	}
	
	/*********Getters and setters*********/
	public String get_monPref()
	{
		return this._monPref;
	}
	
	public void set_monPref(String monPref)
	{
		this._monPref = monPref;
		SetPreferencesByDay(Day.MONDAY,monPref);
	}

	public String get_tuePref()
	{
		return this._tuePref;
	}
	
	public void set_tuePref(String tuePref)
	{
		this._tuePref = tuePref;
		SetPreferencesByDay(Day.TUESDAY,tuePref);
	}

	public String get_wedPref()
	{
		return this._wedPref;
	}
	
	public void set_wedPref(String wedPref)
	{
		this._wedPref = wedPref;
		SetPreferencesByDay(Day.WEDNESDAY,wedPref);
	}

	public String get_thuPref()
	{
		return this._thuPref;
	}
	
	public void set_thuPref(String thuPref)
	{
		this._thuPref = thuPref;
		SetPreferencesByDay(Day.THURSDAY,thuPref);
	}

	public String get_friPref()
	{
		return this._friPref;
	}
	
	public void set_friPref(String friPref)
	{
		this._friPref = friPref;
		SetPreferencesByDay(Day.FRIDAY,friPref);
	}
	
	public int get_nrOfTimeSlotsPerDay() {
		return _nrOfTimeSlotsPerDay;
	}

	public void set_nrOfTimeSlotsPerDay(int nrOfTimeSlotsPerDay) {
		this._nrOfTimeSlotsPerDay = nrOfTimeSlotsPerDay;
	}

	/**********Functions that model the class behavior*****************/
	/**
	 * Gets an array that contains time preferences
	 * @param dayOfTheWeek
	 * @return
	 */
	public TimePref[] GetPreferencesByDay(Day dayOfTheWeek) {
		TimePref[] day;
		switch(dayOfTheWeek)
		{
		case MONDAY:
			day = this._monday;
			break;
		case TUESDAY:
			day = this._tuesday;
			break;
		case WEDNESDAY:
			day = this._wednesday;
			break;
		case THURSDAY: 
			day = this._thursday;
			break;
		case FRIDAY:
			day = this._friday;
			break;
		default:
			day = null;
			break;			
		}
		
		return day;
	}

	/**
	 * Sets time preferences from a string of values
	 * @param dayOfTheWeek
	 * @param dayPrefs
	 */
	public void SetPreferencesByDay(Day dayOfTheWeek, String dayPrefs) {
		TimePref[] timePreferences = new TimePref[this._nrOfTimeSlotsPerDay];
		// Split dayPrefs by separator
		String[] prefs = dayPrefs.split(",");
		// Create a TimePref array
		for(int i=0; i<prefs.length; i++)
		{
			timePreferences[i] = TimePref.values()[Integer.parseInt(prefs[i])];
		}
		// Assign the time preferences
		SetPreferencesByDay(dayOfTheWeek, timePreferences);
		
	}
	
	/**
	 * Sets time preferences with an array
	 * @param dayOfTheWeek
	 * @param timePreferences
	 */
	public void SetPreferencesByDay(Day dayOfTheWeek, TimePref[] timePreferences)
	{
		// Assign it to the day given
		if(timePreferences.length == this._nrOfTimeSlotsPerDay)
		{
			switch(dayOfTheWeek)
			{
			case MONDAY:
				this._monday = timePreferences;
				break;
			case TUESDAY:
				this._tuesday = timePreferences;
				break;
			case WEDNESDAY:
				this._wednesday = timePreferences;
				break;
			case THURSDAY: 
				this._thursday = timePreferences;
				break;
			case FRIDAY:
				this._friday = timePreferences;
				break;
			default:
				break;			
			}
		}
	}
	
	/**
	 * Gets a preference by day and time of day
	 * @param dayOfTheWeek
	 * @param slotNr
	 * @return
	 */
	public TimePref GetPreferencesByTime(Day dayOfTheWeek,int slotNr)
	{
		return GetPreferencesByDay(dayOfTheWeek)[slotNr];
	}
	
	/**
	 * Sets a preference by day and time of day
	 * @param dayOfTheWeek
	 * @param timePref
	 * @param slotNr
	 */
	public void SetPreferencesByTime(Day dayOfTheWeek, TimePref timePref, int slotNr)
	{
		TimePref[] timePrefs = GetPreferencesByDay(dayOfTheWeek);
		timePrefs[slotNr] = timePref;
	}
}
