package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="classes")
public class Class{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="course_title")
	private String courseTitle;
	
	@Column(name="course_abbreviation")
	private String courseAbbreviation;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="offering_id", nullable=false, updatable=false)
	private Offering offering;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="assigned_room_id", nullable=false, updatable=false)
	private Room assignedRoom;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="assigned_instructor_id", nullable=false, updatable=false)
	private Instructor assignedInstructor;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="assigned_studentgroup_id", nullable=false, updatable=false)
	private StudentGroup assignedParentStudentGroup;
	
	@Transient
	private List<StudentGroup> assignedStudentGroups;

	public Class()
	{
		this(0,"NoTitle", "NoAbbreviation", new Offering(), new Room(),
				new Instructor(), new StudentGroup());
	}

	public Class(int id, String courseTitle, String courseAbbreviation, Offering offering,
			Room assignedRoom, Instructor assignedInstructor, StudentGroup assignedParentStudentGroup)
	{
		this.id = id;
		setCourseTitle(courseTitle);
		setCourseAbbreviation(courseAbbreviation);
		setOffering(offering);
		setAssignedRoom(assignedRoom);
		setAssignedInstructor(assignedInstructor);
		setAssignedParentStudentGroup(assignedParentStudentGroup);
	}

	/********************Getters and setters********************/
	
	public int getId() {
		return this.id;
	}
	
	public String getCourseTitle() {
		return this.courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseAbbreviation() {
		return this.courseAbbreviation;
	}

	public void setCourseAbbreviation(String courseAbbreviation) {
		this.courseAbbreviation = courseAbbreviation;
	}

	public Offering getOffering() {
		return this.offering;
	}

	public void setOffering(Offering offering) {
		this.offering = offering;
	}

	public Room getAssignedRoom() {
		return this.assignedRoom;
	}

	public void setAssignedRoom(Room assignedRoom) {
		if(this.offering.getRooms().contains(assignedRoom))
		{
			this.assignedRoom = assignedRoom;
		}
	}

	public Instructor getAssignedInstructor() {
		return this.assignedInstructor;
	}

	public void setAssignedInstructor(Instructor assignedInstructor) {
		if(this.offering.getInstructors().contains(assignedInstructor))
		{
			this.assignedInstructor = assignedInstructor;
		}
	}
	
	public StudentGroup getAssignedParentStudentGroup()
	{
		return this.assignedParentStudentGroup;
	}
	
	public void setAssignedParentStudentGroup(StudentGroup assignedParentStudentGroup)
	{
		this.assignedParentStudentGroup = assignedParentStudentGroup;
		this.assignedStudentGroups = new ArrayList<StudentGroup>();
		ExtractChildStudentGroups(assignedParentStudentGroup);
	}

	public List<StudentGroup> getAssignedStudentGroups() {
		return this.assignedStudentGroups;
	}
	
	/**************Methods that model the class behavior***************/
	public int getAssignedRoomId() {
		return this.assignedRoom.getId();
	}
	
	public int getAssignedInstructorId() {
		return this.assignedInstructor.getId();
	}
	
	public List<Integer> getAssignedStudentGroupsIds()
	{
		List<Integer> stGroupsIds = new ArrayList<Integer>();
		for(StudentGroup stGroup : this.assignedStudentGroups)
		{
			stGroupsIds.add(stGroup.getId());
		}
		
		return stGroupsIds;
	}
	
	public void ExtractChildStudentGroups(StudentGroup parentStudentGroup)
	{
		if(parentStudentGroup.getComposingGroups().isEmpty())
		{
			this.assignedStudentGroups.add(parentStudentGroup);
		}else{
			for(StudentGroup componentGroup : parentStudentGroup.getComposingGroups())
			{
				ExtractChildStudentGroups(componentGroup);
			}
		}
	}
	
	public int GetClassDuration()
	{
		return this.offering.getNrOfTimeSlots();
	}
}
