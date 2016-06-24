package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;

public class Class {
	private int _externalId;
	private String _courseTitle;
	private String _courseAbbreviation;
	private Offering _meeting;
	private int _assignedRoomPos;
	private int _assignedInstructorPos;
	private List<StudentGroup> _assignedStudentGroups;
	private boolean _fixed;
	
	/**
	 * Constructor for creating a brand new class
	 * @param courseTitle
	 * @param courseAbbreviation
	 * @param meeting
	 * @param assignedRoomPos
	 * @param assignedInstructorPos
	 */
	public Class(String courseTitle, String courseAbbreviation, Offering meeting,
			int assignedRoomPos, int assignedInstructorPos,
			List<StudentGroup> assignedStudentGroups)
	{
		this(0,courseTitle,courseAbbreviation,meeting,assignedRoomPos,
				assignedInstructorPos,assignedStudentGroups);
	}
	
	/**
	 * Constructor for creating a existing class stored in the database
	 * @param externalId
	 * @param courseTitle
	 * @param courseAbbreviation
	 * @param meeting
	 * @param assignedRoomPos
	 * @param assignedInstructorPos
	 * @param assignedStudentGroups
	 */
	public Class(int externalId, String courseTitle, String courseAbbreviation,
			Offering meeting, int assignedRoomPos, int assignedInstructorPos,
			List<StudentGroup> assignedStudentGroups)
	{
		_externalId = externalId;
		set_courseTitle(courseTitle);
		set_courseAbbreviation(courseAbbreviation);
		set_meeting(meeting);
		set_assignedRoom(assignedRoomPos);
		set_assignedInstructorPos(assignedInstructorPos);
		set_assignedStudentGroups(assignedStudentGroups);
	}

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
		return this._meeting.get_rooms().get(this._assignedRoomPos);
	}

	public void set_assignedRoom(int _assignedRoomPos) {
		this._assignedRoomPos = _assignedRoomPos;
	}

	public int get_assignedInstructorPos() {
		return _assignedInstructorPos;
	}

	public void set_assignedInstructorPos(int _assignedInstructorPos) {
		this._assignedInstructorPos = _assignedInstructorPos;
	}

	public List<StudentGroup> get_assignedStudentGroups() {
		return _assignedStudentGroups;
	}

	public void set_assignedStudentGroups(List<StudentGroup> _assignedStudentGroups) {
		this._assignedStudentGroups = _assignedStudentGroups;
	}

	public boolean is_fixed() {
		return _fixed;
	}

	public void set_fixed(boolean _fixed) {
		this._fixed = _fixed;
	}
	
	/* Public methods that model the class behavior */
	public Room getAssignedRoom() {
		return this._meeting.get_rooms().get(this._assignedRoomPos);
	}

	public Instructor getAssignedInstructor() {
		return this._meeting.get_instructors().get(this._assignedInstructorPos);
	}
	
	public int getAssignedRoomId() {
		return this._meeting.get_rooms().get(this._assignedRoomPos).get_externalId();
	}
	
	public int getAssignedInstructorId() {
		return this._meeting.get_instructors().get(this._assignedInstructorPos).get_externalId();
	}
	
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
	
	
}
