package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.E;
import timetablepuzzle.eclipselink.entities.administration.DatePattern;
import timetablepuzzle.eclipselink.entities.administration.InstructorMeetings;

@Entity
@Table(name="offerings")
public class Offering extends E{
	/*********Static fields***********/
	public static enum OfferingType{LECTURE,SEMINARY,LABORATORY,GYM,UNASSIGNED};
	/************Regular Properties**********/	
	@Column(name="name")
	private String _name;
	
	@Column(name="type")
	private OfferingType _type;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=Room.class)
	@JoinTable(name="offering_rooms",
    joinColumns=
         @JoinColumn(name="offering_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="room_external_id"))
	private List<Room> _rooms;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=InstructorMeetings.class)
	@JoinTable(name="offering_instructormeetings",
    joinColumns=
         @JoinColumn(name="offering_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="instructormeetings_external_id"))
	private List<InstructorMeetings> _nrOfMeetingsPerInstructor;
	
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=DatePattern.class,optional=false)
	@JoinColumn(name="date_pattern", nullable=false, updatable=false)
	private DatePattern _datePattern;
	
	@Column(name="nroftimeslots")
	private int _nrOfTimeSlots;
	
	@Column(name="nrofgroupslots")
	private int _nrOfGroupSlots;
	
	/**
	 * DefaultConstructor
	 */
	public Offering()
	{
		this(0,"NoName",OfferingType.UNASSIGNED,new ArrayList<Room>(),
				new ArrayList<InstructorMeetings>(),new DatePattern(),0,0);
	}
	
	/**
	 * Parameterized constructor. Creates a class with data from the database
	 * @param externalId
	 * @param name
	 * @param type
	 * @param rooms
	 * @param instructors
	 * @param nrOfMeetingsPerInstructor
	 * @param datePattern
	 * @param nrOfTimeSlots
	 * @param nrOfGroupSlots
	 */
	public Offering(int externalId, String name, OfferingType type,
			List<Room> rooms, List<InstructorMeetings> nrOfMeetingsPerInstructor,
			DatePattern datePattern, int nrOfTimeSlots, int nrOfGroupSlots)
	{
		_externalId = externalId;
		set_name(name);
		set_type(type);
		_rooms = rooms;
		_nrOfMeetingsPerInstructor = nrOfMeetingsPerInstructor;
		set_datePattern(datePattern);
		set_nrOfTimeSlots(nrOfTimeSlots);
		set_nrOfGroupSlots(nrOfGroupSlots);
	}
	/******************Getters and Setters****************/
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}
	
	public OfferingType get_type()
	{
		return this._type;
	}
	
	public void set_type(OfferingType type)
	{
		this._type = type;
	}

	public List<Room> get_rooms() {
		return _rooms;
	}

	public List<InstructorMeetings> get_nrOfMeetingsPerInstructor() {
		return _nrOfMeetingsPerInstructor;
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
	/***************Methods that model the class behavior***************/
	/**
	 * Return a list of all the possible instructors
	 * @return
	 */
	public List<Instructor> GetInstructors()
	{
		List<Instructor> instructors = new ArrayList<Instructor>();
		for(InstructorMeetings instrMeeting : this._nrOfMeetingsPerInstructor)
		{
			instructors.add(instrMeeting.get_instructor());
		}
		
		return instructors;
	}
}
