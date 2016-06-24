package timetablepuzzle.eclipselink.entities.administration;

public class TimePreferences {
	/***********Static fields*************/
	/**Days of the week**/
	public enum Day{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY};
	/**public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;*/
	/**Time preferences**/
	public enum TimePref{PROHIBITED,STRONGLY_DISCOURAGED,DISCOURAGED,
		NEUTRAL,PREFFERED,STRONGLY_PREFFERED,REQUIRED};
	/*public static final int PROHIBITED = -3;
	public static final int STRONGLY_DISCOURAGED = -2;
	public static final int DISCOURAGED = -1;
	public static final int NEUTRAL = 0;
	public static final int PREFFERED = 1;
	public static final int STRONGLY_PREFFERED = 2;
	public static final int REQUIRED = 3;*/
	/***********Dynamic properties*********/
	private int _externalId;
	private TimePref[] _monday;
	private TimePref[] _tuesday;
	private TimePref[] _wednesday;
	private TimePref[] _thursday;
	private TimePref[] _friday;
	private int _nrOfTimeSlotsPerDay;
	
	/**
	 * Creating a brand new time preference
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 */
	public TimePreferences(TimePref[] monday, TimePref[] tuesday,
			TimePref[] wednesday, TimePref[] thursday, TimePref[] friday)
	{
		this(0, monday, tuesday, wednesday, thursday, friday);
	}
	
	/**
	 * Create a existing time preference from the database
	 * @param externalId
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 */
	public TimePreferences(int externalId, TimePref[] monday, TimePref[] tuesday,
			TimePref[] wednesday, TimePref[] thursday, TimePref[] friday)
	{
		_externalId = externalId;
		SetPreferencesByDay(Day.MONDAY,monday);
		SetPreferencesByDay(Day.TUESDAY,tuesday);
		SetPreferencesByDay(Day.WEDNESDAY,wednesday);
		SetPreferencesByDay(Day.THURSDAY,thursday);
		SetPreferencesByDay(Day.FRIDAY,friday);
		set_nrOfTimeSlotsPerDay(12);
	}
	
	/*********Getters and setters*********/
	public int get_externalId() {
		return _externalId;
	}	
	
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

	public void SetPreferencesByDay(Day dayOfTheWeek, TimePref[] timePreferences) {
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
	
	public TimePref GetPreferencesByTime(Day dayOfTheWeek,int slotNr)
	{
		return GetPreferencesByDay(dayOfTheWeek)[slotNr];
	}
	
	public void SetPreferencesByTime(Day dayOfTheWeek, TimePref timePref, int slotNr)
	{
		TimePref[] timePrefs = GetPreferencesByDay(dayOfTheWeek);
		timePrefs[slotNr] = timePref;
	}

	public int get_nrOfTimeSlotsPerDay() {
		return _nrOfTimeSlotsPerDay;
	}

	public void set_nrOfTimeSlotsPerDay(int _nrOfTimeSlotsPerDay) {
		this._nrOfTimeSlotsPerDay = _nrOfTimeSlotsPerDay;
	}
	/**********Functions that model the class behavior*****************/
}
