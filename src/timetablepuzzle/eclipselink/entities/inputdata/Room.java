package timetablepuzzle.eclipselink.entities.inputdata;

import timetablepuzzle.eclipselink.entities.administration.*;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.*;

public class Room {
	private int _externalId;
	private String _code;
	private String _name;
	private RoomType _type;
	private int _capacity;
	private TimePreferences _preferences;
	private Building _building;
	
	/**
	 * Constructor for creating an existing room with info from the database
	 * @param externalId
	 * @param code
	 * @param name
	 * @param type
	 * @param capacity
	 * @param preferences
	 * @param building
	 */
	public Room(int externalId, String code, String name, RoomType type,
			int capacity, TimePreferences preferences, Building building)
	{
		_externalId = externalId;
		set_code(code);
		set_name(name);
		set_type(type);
		set_capacity(capacity);
		_preferences = preferences;
		set_building(building);
	}

	/***********Getters and setters for almost all the fields**************/
	public int get_externalId() {
		return _externalId;
	}

	public String get_code() {
		return _code;
	}

	public void set_code(String _code) {
		this._code = _code;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public RoomType get_type() {
		return _type;
	}

	public void set_type(RoomType _type) {
		this._type = _type;
	}

	public int get_capacity() {
		return _capacity;
	}

	public void set_capacity(int _capacity) {
		this._capacity = _capacity;
	}

	public TimePref[] get_preferencesByDay(Day dayOfTheWeek) {
		return _preferences.GetPreferencesByDay(dayOfTheWeek);
	}

	public void set_preferencesByDay(Day dayOfTheWeek, TimePref[] _preferences) {
		this._preferences.SetPreferencesByDay(dayOfTheWeek, _preferences);
	}
	
	public TimePref get_preferencesByTime(Day dayOfTheWeek, int slotNr) {
		return _preferences.GetPreferencesByTime(dayOfTheWeek, slotNr);
	}

	public void set_preferencesByTime(Day dayOfTheWeek, TimePref _preferences, int slotNr) {
		this._preferences.SetPreferencesByTime(dayOfTheWeek, _preferences, slotNr);
	}

	public Building get_building() {
		return _building;
	}

	public void set_building(Building _building) {
		this._building = _building;
	}
	/***********Functions that model the class behavior***************/
}
