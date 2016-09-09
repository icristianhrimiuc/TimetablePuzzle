package timetablepuzzle.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import timetablepuzzle.usecases.solution.TimeslotPattern;

@Entity
@Table(name = "solutions")
public class Solution {
	public static int ResourceIsNotInSolution = -2;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@ElementCollection
	@MapKeyColumn(name = "room_id")
	@Column(name = "roomassignments")
	@CollectionTable(name = "solution_roomassignments", joinColumns = @JoinColumn(name = "solution_id"))
	private Map<Integer, String> roomsAssignments;

	@ElementCollection
	@MapKeyColumn(name = "instructor_id")
	@Column(name = "instructorassignments")
	@CollectionTable(name = "solution_instructorassignments", joinColumns = @JoinColumn(name = "solution_id"))
	private Map<Integer, String> instructorsAssignments;

	@ElementCollection
	@MapKeyColumn(name = "studentgroup_id")
	@Column(name = "studentgroupassignments")
	@CollectionTable(name = "solution_studentgroupassignments", joinColumns = @JoinColumn(name = "solution_id"))
	private Map<Integer, String> studentGroupssAssignments;

	@ElementCollection
	@MapKeyColumn(name = "class_id")
	@Column(name = "dayAndTimeSlot")
	@CollectionTable(name = "solution_assignedClasses", joinColumns = @JoinColumn(name = "solution_id"))
	private Map<Integer, Integer> assignedDayAndTimeSlot;

	@ElementCollection
	@MapKeyColumn(name = "class_id")
	@Column(name = "nrofremovals")
	@CollectionTable(name = "solution_nrofremovals", joinColumns = @JoinColumn(name = "solution_id"))
	private Map<Integer, Integer> nrOfRemovals;

	@ElementCollection
	@MapKeyColumn(name = "class_id")
	@Column(name = "isFixed")
	@CollectionTable(name = "solution_fixedClasses", joinColumns = @JoinColumn(name = "solution_id"))
	private Map<Integer, Boolean> fixedClasses;

	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, targetEntity = Class.class)
	@JoinTable(name = "solution_classes", joinColumns = @JoinColumn(name = "solution_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
	private List<Class> listOfClasses;

	@Transient
	private Map<Integer, Class> classes;

	public Solution() {
		this(0, "No Name", new ArrayList<Class>());
	}

	public Solution(int id, String name, List<Class> classes) {
		this.id = id;
		this.name = name;
		this.listOfClasses = classes;
		this.classes = ConvertListToMapById(classes);
	}

	private Map<Integer, Class> ConvertListToMapById(List<Class> listOfClasses) {
		Map<Integer, Class> classes = new HashMap<Integer, Class>();
		for (Class aClass : listOfClasses) {
			int aClassId = aClass.getId();
			if (!classes.containsKey(aClassId)) {
				classes.put(aClassId, aClass);
			}
		}

		return classes;
	}

	/*********************** Getter and Setter ********************/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNrOfRemovals(Integer classId) {
		return this.nrOfRemovals.get(classId);
	}

	/***********************
	 * Methods that model the class behavior
	 ********************/
	/************* Room assignment methods **************/
	public boolean IsRoomFree(int roomId, int dayAndTimeSlot) {
		return GetRoomAssignment(roomId, dayAndTimeSlot) == TimeslotPattern.FreeTimeSlot;
	}

	public int GetRoomAssignment(int roomId, int dayAndTimeSlot) {
		if (IsRoomInSolution(roomId) && TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			return Integer.parseInt(GetRoomAssignments(roomId)[dayAndTimeSlot]);
		}

		return ResourceIsNotInSolution;
	}

	public boolean IsRoomInSolution(int roomId) {
		return this.roomsAssignments.containsKey(roomId);
	}

	private String[] GetRoomAssignments(int roomId) {
		return TimeslotPattern.ConvertToArrayAssignments(this.roomsAssignments.get(roomId));
	}

	public void AssignClassToRoom(int roomId, int classId, int dayAndTimeSlot) {
		if (IsRoomInSolution(roomId) && IsClassInSolution(classId)
				&& TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			String[] weekAssignments = GetRoomAssignments(roomId);
			weekAssignments[dayAndTimeSlot] = Integer.toString(classId);
			this.roomsAssignments.replace(roomId, TimeslotPattern.ConvertToStringAssignments(weekAssignments));
		}
	}

	public void AssignWeekToRoom(int roomId, String weekAssignments) {
		if (IsRoomInSolution(roomId) && TimeslotPattern.IsValidWeek(weekAssignments)) {
			this.roomsAssignments.replace(roomId, weekAssignments);
		}
	}

	public Set<Integer> GetAllRoomsIds() {
		return this.roomsAssignments.keySet();
	}

	/************* Instructor assignment methods **************/
	public boolean IsInstructorFree(int instructorId, int dayAndTimeSlot) {
		return GetInstructorAssignment(instructorId, dayAndTimeSlot) == TimeslotPattern.FreeTimeSlot;
	}

	public int GetInstructorAssignment(int instructorId, int dayAndTimeSlot) {
		if (IsInstructorInSolution(instructorId) && TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			return Integer.parseInt(GetInstructorAssignments(instructorId)[dayAndTimeSlot]);
		}

		return -2;
	}

	public boolean IsInstructorInSolution(int instructorId) {
		return this.instructorsAssignments.containsKey(instructorId);
	}

	private String[] GetInstructorAssignments(int instructorId) {
		return TimeslotPattern.ConvertToArrayAssignments(this.instructorsAssignments.get(instructorId));
	}

	public void AssignClassToInstructor(int instructorId, int classId, int dayAndTimeSlot) {
		if (IsInstructorInSolution(instructorId) && IsClassInSolution(classId)
				&& TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			String[] weekAssignments = GetInstructorAssignments(instructorId);
			weekAssignments[dayAndTimeSlot] = Integer.toString(classId);
			this.instructorsAssignments.replace(instructorId,
					TimeslotPattern.ConvertToStringAssignments(weekAssignments));
		}
	}

	public void AssignWeekToInstructor(int instructorId, String weekAssignments) {
		if (IsInstructorInSolution(instructorId) && TimeslotPattern.IsValidWeek(weekAssignments)) {
			this.instructorsAssignments.replace(instructorId, weekAssignments);
		}
	}

	public Set<Integer> GetAllInstructorsIds() {
		return this.instructorsAssignments.keySet();
	}

	/************* StudentGroup assignment methods **************/
	public boolean IsStudentGroupFree(int studentGroupId, int dayAndTimeSlot) {
		return GetStudentGroupAssignment(studentGroupId, dayAndTimeSlot) == TimeslotPattern.FreeTimeSlot;
	}

	public int GetStudentGroupAssignment(int studentGroupId, int dayAndTimeSlot) {
		if (IsStudentGroupInSolution(studentGroupId) && TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			return Integer.parseInt(GetStudentGroupAssignments(studentGroupId)[dayAndTimeSlot]);
		}

		return -2;
	}

	public boolean IsStudentGroupInSolution(int studentGroupId) {
		return this.studentGroupssAssignments.containsKey(studentGroupId);
	}

	private String[] GetStudentGroupAssignments(int studentGroupId) {
		return TimeslotPattern.ConvertToArrayAssignments(this.studentGroupssAssignments.get(studentGroupId));
	}

	public void AssignClassToStudentGroup(int studentGroupId, int classId, int dayAndTimeSlot) {
		if (IsStudentGroupInSolution(studentGroupId) && IsClassInSolution(classId)
				&& TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			String[] weekAssignments = GetStudentGroupAssignments(studentGroupId);
			weekAssignments[dayAndTimeSlot] = Integer.toString(classId);
			this.studentGroupssAssignments.replace(studentGroupId,
					TimeslotPattern.ConvertToStringAssignments(weekAssignments));
		}
	}

	public void AssignWeekToStudentGroup(int studentGroupId, String weekAssignments) {
		if (IsStudentGroupInSolution(studentGroupId) && TimeslotPattern.IsValidWeek(weekAssignments)) {
			this.studentGroupssAssignments.replace(studentGroupId, weekAssignments);
		}
	}

	public Set<Integer> GetAllStudentGroupsIds() {
		return this.studentGroupssAssignments.keySet();
	}

	/************* Class assignment methods **************/
	public int GetAssignedDayAndTimeSlot(int classId) {
		if (IsClassInSolution(classId)) {
			return this.assignedDayAndTimeSlot.get(classId);
		}

		return ResourceIsNotInSolution;
	}

	public void SetAssignedDayAndTimeSlot(int classId, int dayAndTimeSlot) {
		if (IsClassInSolution(classId) && TimeslotPattern.IsValidDayAndTimeSlot(dayAndTimeSlot)) {
			this.assignedDayAndTimeSlot.replace(classId, dayAndTimeSlot);
		}
	}

	public boolean HasAsssignedDayAndTimeSlot(int classId) {
		return GetAssignedDayAndTimeSlot(classId) != TimeslotPattern.UnassignedTimeSlot;
	}

	public List<Class> GetAssignedClasses() {
		List<Class> assignedClasses = new ArrayList<Class>();
		for (Integer classId : this.assignedDayAndTimeSlot.keySet()) {
			if (this.assignedDayAndTimeSlot.get(classId) != TimeslotPattern.FreeTimeSlot) {
				assignedClasses.add(this.classes.get(classId));
			}
		}

		return assignedClasses;
	}

	public List<Class> GetUnassignedClasses() {
		List<Class> unassignedClasses = new ArrayList<Class>();
		for (Integer classId : this.assignedDayAndTimeSlot.keySet()) {
			if (this.assignedDayAndTimeSlot.get(classId) == TimeslotPattern.FreeTimeSlot) {
				unassignedClasses.add(this.classes.get(classId));
			}
		}

		return unassignedClasses;
	}

	/************* NrOfRemovals methods **************/
	public int GetNrOfRemovals(int classId) {
		if (IsClassInSolution(classId)) {
			return this.nrOfRemovals.get(classId);
		}

		return -2;
	}

	public void IncrementNrOfRemovals(int classId) {
		if (IsClassInSolution(classId)) {
			int nrOfRemovals = GetNrOfRemovals(classId);
			nrOfRemovals++;
			this.nrOfRemovals.replace(classId, nrOfRemovals);
		}
	}

	public void ResetNrOfRemovals(int classId) {
		if (IsClassInSolution(classId)) {
			this.nrOfRemovals.replace(classId, 0);
		}
	}

	/************* Classes methods **************/
	public boolean IsClassInSolution(int classId) {
		return this.classes.containsKey(classId);
	}

	public boolean IsClassFixed(int classId) {
		if (IsClassInSolution(classId)) {
			return this.fixedClasses.get(classId);
		}

		return false;
	}

	public boolean IsRoomClassFixed(int roomId, int dayAndTimeSlot) {
		if (IsRoomInSolution(roomId)) {
			int classId = GetRoomAssignment(roomId, dayAndTimeSlot);
			return this.fixedClasses.get(classId);
		}

		return false;
	}

	public boolean IsInstructorClassFixed(int instructorId, int dayAndTimeSlot) {
		if (IsInstructorInSolution(instructorId)) {
			int classId = GetInstructorAssignment(instructorId, dayAndTimeSlot);
			return this.fixedClasses.get(classId);
		}

		return false;
	}

	public boolean IsIStudentGroupClassFixed(int studentGroupId, int dayAndTimeSlot) {
		if (IsStudentGroupInSolution(studentGroupId)) {
			int classId = GetStudentGroupAssignment(studentGroupId, dayAndTimeSlot);
			return this.fixedClasses.get(classId);
		}

		return false;
	}

	/************************ Classes methods ****************************/
	public Class GetClassById(int classId) {
		return this.classes.get(classId);
	}

	public int GetNrOfCLassesInSolution() {
		return this.listOfClasses.size();
	}

	/************************** Override methods *****************************/

	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Solution);
		if (equals) {
			Solution other = (Solution) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName())));
			// TODO Create a proper Equals function
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Solution:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
