package timetablepuzzle.entities.inputdata;

import javax.persistence.*;

import timetablepuzzle.entities.other.Building;
import timetablepuzzle.entities.other.RoomType;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.entities.other.TimePreferences.*;

@Entity
@Table(name = "rooms")
public class Room {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "capacity")
	private int capacity;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, targetEntity = RoomType.class, optional = false)
	@JoinColumn(name = "roomtype_id", nullable = false, updatable = false)
	private RoomType type;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "building", nullable = false, updatable = false)
	private Building building;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "timepreferences_id", unique = true, nullable = false, updatable = false)
	private TimePreferences timePreferences;

	public Room() {
		this(0, "NoName", "NoCode", 0, new RoomType(), new Building(), new TimePreferences());
	}

	public Room(int id, String name, String code, int capacity, RoomType type, Building building,
			TimePreferences timePreferences) {
		this.id = id;
		setName(name);
		setCode(code);
		setCapacity(capacity);
		setType(type);
		setBuilding(building);
		this.timePreferences = timePreferences;
	}

	/*********** Getters and setters for almost all the fields **************/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public Building getBuilding() {
		return this.building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public TimePreferences getTimePreferences() {
		return this.timePreferences;
	}

	public void setTimePreferences(TimePreferences timePreferences) {
		this.timePreferences = timePreferences;
	}

	/*********** Methods that model the class behavior ***************/
	public TimePreference[] getPreferencesByDay(DayOfTheWeek dayOfTheWeek) {
		return this.timePreferences.getPreferencesByDay(dayOfTheWeek);
	}

	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, TimePreference[] _preferences) {
		this.timePreferences.setPreferencesByDay(dayOfTheWeek, _preferences);
	}

	public TimePreference getPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, int slotNr) {
		return this.timePreferences.getPreferencesByDayAndTime(dayOfTheWeek, slotNr);
	}

	public void setPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, TimePreference _preferences, int slotNr) {
		this.timePreferences.setPreferencesByDayAndTime(dayOfTheWeek, _preferences, slotNr);
	}

	/******************** Overridden methods ************************/
	@Override
	public String toString() {
		return String.format("%s (%s)", this.name, this.code);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Room);
		if (equals) {
			Room other = (Room) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName()))
					&& (this.code.equals(other.getCode())) && (this.capacity == other.getCapacity())
					&& (this.type.equals(other.getType())) && (this.building.equals(other.getBuilding()))
					&& (this.timePreferences.equals(other.getTimePreferences())));
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Room:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
