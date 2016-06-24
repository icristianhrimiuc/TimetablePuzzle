package timetablepuzzle.eclipselink.entities.administration;

import java.util.LinkedList;
import java.util.List;

public class DatePattern {
	private int _externalId;
	private String _name;
	private List<String> _usedDates;
	
	/**
	 * Create and initialize a date pattern with given parameters 
	 * @param externalId
	 * @param name
	 */
	public DatePattern(int externalId, String name)
	{
		_externalId = externalId;
		set_name(name);
		_usedDates = new LinkedList<String>();
	}
	
	public void AddDateInterval(String dateInterval)
	{
		_usedDates.add(dateInterval);
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
}
