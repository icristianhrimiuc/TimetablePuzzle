package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.List;

import timetablepuzzle.eclipselink.entities.administration.DatePattern;

public class Offering {
	/*********Static fields***********/
	public static final int LECTURE= 1;
	public static final int SEMINARY = 2;
	public static final int LABORATORY = 3;
	public static final int GYM = 4;
	/************Dynamic Properties**********/
	private int _externalId;
	private String _name;
	private int _type;
	private List<Room> _rooms;
	private List<Instructor> _instructors;
	private List<Integer> _nrOfMeetingsPerInstructor;
	private DatePattern _datePattern;
	private int _nrOfTimeSlots;
	private int _nrOfGroupSlots;
	
	public Offering(int externalId, String name,int type,
			List<Room> rooms, List<Instructor> instructors,
			List<Integer> nrOfMeetingsPerInstructor, DatePattern datePattern, 
			int nrOfTimeSlots, int nrOfGroupSlots)
	{
		_externalId = externalId;
		set_name(name);
		set_type(type);
		set_rooms(rooms);
		set_instructors(instructors);
		set_nrOfMeetingsPerInstructor(nrOfMeetingsPerInstructor);
		set_datePattern(datePattern);
		set_nrOfTimeSlots(nrOfTimeSlots);
		set_nrOfGroupSlots(nrOfGroupSlots);
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
	
	public int get_type()
	{
		return this._type;
	}
	
	public void set_type(int type)
	{
		this._type = type;
	}

	public List<Room> get_rooms() {
		return _rooms;
	}

	public void set_rooms(List<Room> _rooms) {
		this._rooms = _rooms;
	}

	public List<Instructor> get_instructors() {
		return _instructors;
	}

	public void set_instructors(List<Instructor> _instructors) {
		this._instructors = _instructors;
	}

	public List<Integer> get_nrOfMeetingsPerInstructor() {
		return _nrOfMeetingsPerInstructor;
	}

	public void set_nrOfMeetingsPerInstructor(List<Integer> _nrOfMeetingsPerInstructor) {
		this._nrOfMeetingsPerInstructor = _nrOfMeetingsPerInstructor;
	}

	public DatePattern get_datePattern() {
		return _datePattern;
	}

	public void set_datePattern(DatePattern _datePattern) {
		this._datePattern = _datePattern;
	}

	public int get_nrOfTimeSlots() {
		return _nrOfTimeSlots;
	}

	public void set_nrOfTimeSlots(int _nrOfTimeSlots) {
		this._nrOfTimeSlots = _nrOfTimeSlots;
	}

	public int get_nrOfGroupSlots() {
		return _nrOfGroupSlots;
	}

	public void set_nrOfGroupSlots(int _nrOfGroupSlots) {
		this._nrOfGroupSlots = _nrOfGroupSlots;
	}
}
