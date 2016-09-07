package timetablepuzzle.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;

@Entity
@Table(name = "classes")
public class Class {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// These fields are used when displaying a solution to the user
	@Column(name = "course_title")
	private String courseTitle;

	@Column(name = "course_abbreviation")
	private String courseAbbreviation;

	@Column(name = "department_name")
	private String departmentName;

	@Column(name = "college_year")
	@Enumerated(EnumType.STRING)
	private CollegeYear collegeYear;

	@Column(name = "subject_area_name")
	private String subjectAreaName;

	@Column(name = "term")
	@Enumerated(EnumType.STRING)
	private Term term;

	// These fields are used by the solver
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "offering_id", nullable = false, updatable = false)
	private Offering offering;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "assigned_room_id", nullable = false, updatable = false)
	private Room assignedRoom;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "assigned_instructor_id", nullable = false, updatable = false)
	private Instructor assignedInstructor;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "assigned_studentgroup_id", nullable = false, updatable = false)
	private StudentGroup assignedParentStudentGroup;

	@Transient
	private List<StudentGroup> assignedStudentGroups;

	public Class() {
		this(0, "NoTitle", "NoAbbreviation", "NoDepartment", CollegeYear.UNASSIGNED, "NoSubjectArea", Term.UNASSIGNED,
				new Offering(), new Room(), new Instructor(), new StudentGroup());
	}

	public Class(int id, String courseTitle, String courseAbbreviation, String departmentName, CollegeYear collegeYear,
			String subjectAreaName, Term term, Offering offering, Room assignedRoom, Instructor assignedInstructor,
			StudentGroup assignedParentStudentGroup) {
		this.id = id;
		setCourseTitle(courseTitle);
		setCourseAbbreviation(courseAbbreviation);
		setDepartmentName(departmentName);
		setCollegeYear(collegeYear);
		setSubjectAreaName(subjectAreaName);
		setTerm(term);
		setOffering(offering);
		setAssignedRoom(assignedRoom);
		setAssignedInstructor(assignedInstructor);
		setAssignedParentStudentGroup(assignedParentStudentGroup);
	}

	/******************** Getters and setters ********************/

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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public CollegeYear getCollegeYear() {
		return collegeYear;
	}

	public void setCollegeYear(CollegeYear collegeYear) {
		this.collegeYear = collegeYear;
	}

	public String getSubjectAreaName() {
		return subjectAreaName;
	}

	public void setSubjectAreaName(String subjectAreaName) {
		this.subjectAreaName = subjectAreaName;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
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
		this.assignedRoom = assignedRoom;
	}

	public Instructor getAssignedInstructor() {
		return this.assignedInstructor;
	}

	public void setAssignedInstructor(Instructor assignedInstructor) {
		this.assignedInstructor = assignedInstructor;
	}

	public StudentGroup getAssignedParentStudentGroup() {
		return this.assignedParentStudentGroup;
	}

	public void setAssignedParentStudentGroup(StudentGroup assignedParentStudentGroup) {
		this.assignedParentStudentGroup = assignedParentStudentGroup;
		this.assignedStudentGroups = new ArrayList<StudentGroup>();
		ExtractChildStudentGroups(assignedParentStudentGroup);
	}

	public List<StudentGroup> getAssignedStudentGroups() {
		return this.assignedStudentGroups;
	}

	/************** Methods that model the class behavior ***************/
	public int getAssignedRoomId() {
		return this.assignedRoom.getId();
	}

	public int getAssignedInstructorId() {
		return this.assignedInstructor.getId();
	}

	public List<Integer> getAssignedStudentGroupsIds() {
		List<Integer> stGroupsIds = new ArrayList<Integer>();
		for (StudentGroup stGroup : this.assignedStudentGroups) {
			stGroupsIds.add(stGroup.getId());
		}

		return stGroupsIds;
	}

	public void ExtractChildStudentGroups(StudentGroup parentStudentGroup) {
		if (parentStudentGroup.getComposingGroups().isEmpty()) {
			this.assignedStudentGroups.add(parentStudentGroup);
		} else {
			for (StudentGroup componentGroup : parentStudentGroup.getComposingGroups()) {
				ExtractChildStudentGroups(componentGroup);
			}
		}
	}

	public int GetClassDuration() {
		return this.offering.getNrOfTimeSlots();
	}

	/******************** Overridden methods ************************/
	@Override
	public String toString() {
		return String.format("%s (%s)", this.courseTitle, this.courseAbbreviation);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Class);
		if (equals) {
			Class other = (Class) o;
			equals = ((this.id == other.getId()) && (this.courseTitle.equals(other.getCourseTitle()))
					&& (this.courseAbbreviation.equals(other.getCourseAbbreviation()))
					&& (this.offering.equals(other.getOffering()))
					&& (this.assignedRoom.equals(other.getAssignedRoom()))
					&& (this.assignedInstructor.equals(other.getAssignedInstructor()))
					&& (this.assignedParentStudentGroup.equals(other.getAssignedParentStudentGroup())));
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Room:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
