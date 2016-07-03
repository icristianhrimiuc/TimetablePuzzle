package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="classes")
public class Class{
	/***********Regular properties*************/
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	@Column(name="course_title")
	private String _courseTitle;
	
	@Column(name="courseAbbreviation")
	private String _courseAbbreviation;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="meeting", nullable=false, updatable=false)
	private Offering _meeting;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="room", nullable=false, updatable=false)
	private Room _assignedRoom;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="instructor", nullable=false, updatable=false)
	private Instructor _assignedInstructor;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="student_group", nullable=false, updatable=false)
	private StudentGroup _assignedParentStGroup;
	
	@Column(name="isFixed")
	private boolean _fixed;
	
	@Transient
	private List<StudentGroup> _assignedStudentGroups;
	
	/**
	 * DefaultConstructor
	 */
	public Class()
	{
		this(0,"NoTitle", "NoAbbreviation", new Offering(), new Room(),
				new Instructor(), new StudentGroup());
	}
	
	/**
	 * Parameterized constructor for creating a brand new class
	 * @param courseTitle
	 * @param courseAbbreviation
	 * @param meeting
	 * @param assignedRoomPos
	 * @param assignedInstructorPos
	 */
	public Class(String courseTitle, String courseAbbreviation, Offering meeting,
			Room assignedRoom, Instructor assignedInstructor,
			StudentGroup assignedParentStGroup)
	{
		this(0,courseTitle,courseAbbreviation,meeting,assignedRoom,
				assignedInstructor,assignedParentStGroup);
	}
	
	/**
	 * Parameterized constructor for creating a existing class stored in the database
	 * @param externalId
	 * @param courseTitle
	 * @param courseAbbreviation
	 * @param meeting
	 * @param assignedRoomPos
	 * @param assignedInstructorPos
	 * @param assignedStudentGroups
	 */
	public Class(int externalId, String courseTitle, String courseAbbreviation, Offering meeting,
			Room assignedRoom, Instructor assignedInstructor, StudentGroup assignedParentStGroup)
	{
		_externalId = externalId;
		set_courseTitle(courseTitle);
		set_courseAbbreviation(courseAbbreviation);
		set_meeting(meeting);
		set_assignedRoom(assignedRoom);
		set_assignedInstructor(assignedInstructor);
		set_assignedParentStGroup(assignedParentStGroup);
	}

	/********************Getters and setters********************/
	
	public int get_externalId() {
		return _externalId;
	}
	
	public String get_courseTitle() {
		return _courseTitle;
	}

	public void set_courseTitle(String _courseTitle) {
		this._courseTitle = _courseTitle;
	}

	public String get_courseAbbreviation() {
		return _courseAbbreviation;
	}

	public void set_courseAbbreviation(String _courseAbbreviation) {
		this._courseAbbreviation = _courseAbbreviation;
	}

	public Offering get_meeting() {
		return _meeting;
	}

	public void set_meeting(Offering _meeting) {
		this._meeting = _meeting;
	}

	public Room get_assignedRoom() {
		return this._assignedRoom;
	}

	public void set_assignedRoom(Room _assignedRoom) {
		if(this._meeting.get_rooms().contains(_assignedRoom))
		{
			this._assignedRoom = _assignedRoom;
		}
	}

	public Instructor get_assignedInstructor() {
		return _assignedInstructor;
	}

	public void set_assignedInstructor(Instructor _assignedInstructor) {
		if(this._meeting.GetInstructors().contains(_assignedInstructor))
		{
			this._assignedInstructor = _assignedInstructor;
		}
	}
	
	public StudentGroup get_assignedParentStGroup()
	{
		return this._assignedParentStGroup;
	}
	
	public void set_assignedParentStGroup(StudentGroup assignedParentStGroup)
	{
		this._assignedParentStGroup = assignedParentStGroup;
		this._assignedStudentGroups = new ArrayList<StudentGroup>();
		DiscoverStudentGroups(assignedParentStGroup);
	}

	public List<StudentGroup> get_assignedStudentGroups() {
		return _assignedStudentGroups;
	}

	public boolean is_fixed() {
		return _fixed;
	}

	public void set_fixed(boolean _fixed) {
		this._fixed = _fixed;
	}
	
	/**************Methods that model the class behavior***************/
	/**
	 * Return the id of the assigned room
	 * @return
	 */
	public int getAssignedRoomId() {
		return this._assignedRoom.get_externalId();
	}
	
	/**
	 * Return the id of the assigned instructor
	 * @return
	 */
	public int getAssignedInstructorId() {
		return this._assignedInstructor.get_externalId();
	}
	
	/**
	 * Return the ids of all assigned student groups
	 * @return
	 */
	public List<Integer> getAssignedStudentGroupsIds()
	{
		List<Integer> stGroupsIds = new ArrayList<Integer>();
		// For each component student group, get it's id
		for(StudentGroup stGroup : this._assignedStudentGroups)
		{
			stGroupsIds.add(stGroup.get_externalId());
		}
		
		return stGroupsIds;
	}	
	
	public void DiscoverStudentGroups(StudentGroup stGroup)
	{
		if(stGroup.get_composingGroups().isEmpty())
		{
			this._assignedStudentGroups.add(stGroup);
		}else{
			for(StudentGroup componentGroup : stGroup.get_composingGroups())
			{
				DiscoverStudentGroups(componentGroup);
			}
		}
	}
}
