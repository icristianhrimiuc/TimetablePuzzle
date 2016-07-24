package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.administration.DatePattern;
import timetablepuzzle.eclipselink.entities.administration.InstructorMeetings;

@Entity
@Table(name="offerings")
public class Offering{
	public static enum OfferingType{LECTURE,SEMINARY,LABORATORY,GYM,UNASSIGNED};

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	private OfferingType type;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=Room.class)
	@JoinTable(name="offering_rooms",
    joinColumns=
         @JoinColumn(name="offering_id"),
    inverseJoinColumns=
         @JoinColumn(name="room_id"))
	private List<Room> rooms;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=InstructorMeetings.class)
	@JoinTable(name="offering_instructormeetings",
    joinColumns=
         @JoinColumn(name="offering_id"),
    inverseJoinColumns=
         @JoinColumn(name="instructormeetings_id"))
	private List<InstructorMeetings> nrOfMeetingsPerInstructor;
	
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=DatePattern.class,optional=false)
	@JoinColumn(name="date_pattern", nullable=false, updatable=false)
	private DatePattern datePattern;
	
	@Column(name="nroftimeslots")
	private int nrOfTimeSlots;
	
	@Column(name="nrofgroupslots")
	private int nrOfGroupSlots;

	public Offering()
	{
		this(0,"NoName",OfferingType.UNASSIGNED,new ArrayList<Room>(),
				new ArrayList<InstructorMeetings>(),new DatePattern(),0,0);
	}

	public Offering(int id, String name, OfferingType type,
			List<Room> rooms, List<InstructorMeetings> nrOfMeetingsPerInstructor,
			DatePattern datePattern, int nrOfTimeSlots, int nrOfGroupSlots)
	{
		this.id = id;
		setName(name);
		setType(type);
		this.rooms = rooms;
		this.nrOfMeetingsPerInstructor = nrOfMeetingsPerInstructor;
		setDatePattern(datePattern);
		setNrOfTimeSlots(nrOfTimeSlots);
		setNrOfGroupSlots(nrOfGroupSlots);
	}
	
	/******************Getters and Setters****************/
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public OfferingType getType()
	{
		return this.type;
	}
	
	public void setType(OfferingType type)
	{
		this.type = type;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public List<InstructorMeetings> getNrOfMeetingsPerInstructor() {
		return nrOfMeetingsPerInstructor;
	}

	public DatePattern getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(DatePattern datePattern) {
		this.datePattern = datePattern;
	}

	public int getNrOfTimeSlots() {
		return this.nrOfTimeSlots;
	}

	public void setNrOfTimeSlots(int nrOfTimeSlots) {
		this.nrOfTimeSlots = nrOfTimeSlots;
	}

	public int getNrOfGroupSlots() {
		return this.nrOfGroupSlots;
	}

	public void setNrOfGroupSlots(int nrOfGroupSlots) {
		this.nrOfGroupSlots = nrOfGroupSlots;
	}
	
	/***************Methods that model the class behavior***************/
	public List<Instructor> getInstructors()
	{
		List<Instructor> instructors = new ArrayList<Instructor>();
		for(InstructorMeetings instrMeeting : this.nrOfMeetingsPerInstructor)
		{
			instructors.add(instrMeeting.getInstructor());
		}
		
		return instructors;
	}
}
