package timetablepuzzle.eclipselink.entities.inputdata;

import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
import timetablepuzzle.eclipselink.DAO.services.inputdata.RoomJPADAOService;
import timetablepuzzle.eclipselink.entities.E;
import timetablepuzzle.eclipselink.entities.administration.*;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.*;
import javax.persistence.*;

@Entity
@Table(name="rooms")
public class Room extends E{
	/***********Static fields*************/
	public static RoomDAO roomDAO = new RoomJPADAOService();
	/***********Regular properties*************/	
	@Column(name="code")
	private String _code;
	
	@Column(name="name")
	private String _name;
	
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=RoomType.class,optional=false)
	@JoinColumn(name="room_type", nullable=false, updatable=false)
	private RoomType _type;
	
	@Column(name="capacity")
	private int _capacity;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="time_preferences", unique=true, nullable=false, updatable=false)
	private TimePreferences _preferences;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="building",nullable=false,updatable=false)
	private Building _building;
	
	/**
	 * DefaultConstructor
	 */
	public Room()
	{
		this(0,"NoCode","NoName",new RoomType(),0,new TimePreferences(),new Building());
	}
	
	/**
	 * Parameterized constructor for creating an existing room 
	 * with info from the database
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
