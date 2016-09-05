package timetablepuzzle.entities.inputData;

import javax.persistence.*;

import timetablepuzzle.entities.other.Building;
import timetablepuzzle.entities.other.RoomType;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.entities.other.TimePreferences.*;

@Entity
@Table(name="rooms")
public class Room{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=RoomType.class,optional=false)
	@JoinColumn(name="roomtype_id", nullable=false, updatable=false)
	private RoomType type;
	
	@Column(name="capacity")
	private int capacity;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="timepreferences_id", unique=true, nullable=false, updatable=false)
	private TimePreferences preferences;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="building",nullable=false,updatable=false)
	private Building building;

	public Room()
	{
		this(0,"NoCode","NoName",new RoomType(),0,new TimePreferences(),new Building());
	}

	public Room(int id, String code, String name, RoomType type,
			int capacity, TimePreferences preferences, Building building)
	{
		this.id = id;
		setCode(code);
		setName(name);
		setType(type);
		setCapacity(capacity);
		this.preferences = preferences;
		setBuilding(building);
	}

	/***********Getters and setters for almost all the fields**************/
	public int getId() {
		return this.id;
	}
	
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public TimePreference[] getPreferencesByDay(DayOfTheWeek dayOfTheWeek) {
		return this.preferences.getPreferencesByDay(dayOfTheWeek);
	}

	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, TimePreference[] _preferences) {
		this.preferences.setPreferencesByDay(dayOfTheWeek, _preferences);
	}
	
	public TimePreference getPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, int slotNr) {
		return this.preferences.getPreferencesByDayAndTime(dayOfTheWeek, slotNr);
	}

	public void setPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, TimePreference _preferences, int slotNr) {
		this.preferences.setPreferencesByDayAndTime(dayOfTheWeek, _preferences, slotNr);
	}

	public Building getBuilding() {
		return this.building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	/***********Functions that model the class behavior***************/
}
