package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.entities.inputData.Instructor;
import timetablepuzzle.entities.inputData.Room;

@Entity
@Table(name="offerings")
public class Offering{
	public static enum OfferingType{LECTURE,SEMINARY,LABORATORY,GYM,UNASSIGNED};
	public static enum DatePattern{FULL_TERM,EVEN_WEEKS,ODD_WEEKS,FIRST_HALF,SECOND_HALF}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
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
	
	@JoinColumn(name="date_pattern", nullable=false, updatable=false)
	@Column(name="date_pattern", nullable=false)
	@Enumerated(EnumType.STRING)
	private DatePattern datePattern;
	
	@Column(name="nroftimeslots")
	private int nrOfTimeSlots;
	
	@Column(name="nrofgroupslots")
	private int nrOfGroupSlots;

	public Offering()
	{
		this(0,"NoName",OfferingType.UNASSIGNED,new ArrayList<Room>(),
				new ArrayList<InstructorMeetings>(), DatePattern.FULL_TERM,0,0);
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
	
	public void setRooms(List<Room> rooms){
		this.rooms = rooms;
	}

	public List<InstructorMeetings> getNrOfMeetingsPerInstructor() {
		return nrOfMeetingsPerInstructor;
	}
	
	public void setNrOfMeetingsPerInstructor(List<InstructorMeetings> nrOfMeetingsPerInstructor){
		this.nrOfMeetingsPerInstructor = nrOfMeetingsPerInstructor;
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
