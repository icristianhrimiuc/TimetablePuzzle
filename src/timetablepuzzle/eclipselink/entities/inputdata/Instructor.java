package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.LinkedList;
import java.util.List;

import timetablepuzzle.eclipselink.entities.administration.TimePreferences;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.Day;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.TimePref;

public class Instructor {
	private int _externalId;
	private String _name;
	private String _position;
	private TimePreferences _preferences;
	private List<Class> _classAssignments;
	
	public Instructor(int externalId, String name, String position, TimePreferences preferences)
	{
		_externalId = externalId;
		set_name(name);
		set_position(position);
		_preferences = preferences;
		set_classAssignments(new LinkedList<Class>());
	}

	public int get_externalId() {
		return _externalId;
	}

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

	public List<Class> get_classAssignments() {
		return _classAssignments;
	}

	public void set_classAssignments(List<Class> _classAssignments) {
		this._classAssignments = _classAssignments;
	}
}
