package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.administration.TimePreferences.*;

@Entity
@Table(name="solutions")
public class Solution{
	public static enum Message{VARIABLE_NOT_FOUND,UNASSIGNED,ASIGGN_SUCCESSFULL,ROOM_IS_UNAVAILABLE,
		INSTRUCTOR_IS_UNAVAILABLE,STUDENTGROUP_IS_UNAVAILABLE};

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
		
	// Operations on ElementCollections are always cascaded.
	@ElementCollection
    @MapKeyColumn(name="room_id")
	@Column(name="roomassignments")
	@CollectionTable(
	      name="solution_roomassignments",
	      joinColumns=@JoinColumn(name="solution_id")
	)
	private Map<Integer,String> roomsAssignments;
	
	@ElementCollection
    @MapKeyColumn(name="instructor_id")
	@Column(name="instructorassignments")
	@CollectionTable(
	      name="solution_instructorassignments",
	      joinColumns=@JoinColumn(name="solution_id")
	)
	private Map<Integer,String> instructorsAssignments;
	
	@ElementCollection
    @MapKeyColumn(name="studentgroup_id")
	@Column(name="studentgroupassignments")
	@CollectionTable(
	      name="solution_studentgroupassignments",
	      joinColumns=@JoinColumn(name="solution_id")
	)
	private Map<Integer,String> studentsAssignments;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable
	(
	    name="unassigned_classes",
	    joinColumns={ @JoinColumn(name="solution_id", referencedColumnName="id") },
	    inverseJoinColumns={ @JoinColumn(name="unassigned_class_id", referencedColumnName="id", unique=true) }
	)
	private List<Class> unassignedClasses;

	@ElementCollection
    @MapKeyColumn(name="class_id")
	@Column(name="nrofremovals")
	@CollectionTable(
	      name="solution_nrofremovals",
	      joinColumns=@JoinColumn(name="solution_id")
	)
	private Map<Integer,Integer> nrOfRemovals;
	
	@Column(name="nrofclasses")
	private int nrOfClasses;
	
	@Column(name="nroftimeslotsperday")
	private int nrOfTimeSlotsPerDay;
	
	@Column(name="nrOfdays")
	private int nrOfDays;
	
	@Transient
	private HashMap<Integer,Class[]> roomsTimetable;
	
	@Transient
	private HashMap<Integer,Class[]> instructorsTimetable;
	
	@Transient
	private HashMap<Integer,Class[]> studentsTimetable;
	
	@Transient
	private HashMap<Class,Integer> assignedClasses;

	public Solution()
	{
		this(0, 0, new ArrayList<Class>(),
				new HashMap<Integer, Class[]>(),
				new HashMap<Integer, Class[]>(),
				new HashMap<Integer, Class[]>());
	}

	public Solution(int id, int nrOfTimeSlotsPerDay,List<Class> unassignedClasses,
			HashMap<Integer,Class[]> roomsTimetable,
			HashMap<Integer,Class[]> instructorsTimetable, 
			HashMap<Integer,Class[]> studentsTimetable)
	{
		this.id = id;
		this.roomsTimetable = roomsTimetable;
		this.instructorsTimetable = instructorsTimetable;
		this.studentsTimetable = studentsTimetable;
		this.unassignedClasses = unassignedClasses;
		this.nrOfRemovals = new HashMap<Integer,Integer>();
		AllocateMemoryForClasses(unassignedClasses);
		FindAssignedClasses();
		ResetTheNumberOfRemovals();
		// Since the union of the unassignedClasses set and the assignedClasses set
		// must be the empty set, the total number of classes is equal to the size of
		// HashMap storing the number of removals
		setNrOfClasses(nrOfRemovals.size());
		setNrOfTimeSlotsPerDay(nrOfTimeSlotsPerDay);
		setNrOfDays(nrOfDays);
	}

	private void ResetTheNumberOfRemovals() {
		for(Class uClass : this.unassignedClasses)
		{
			nrOfRemovals.put(uClass.getId(), 0);
		}
		for(Class aClass : this.assignedClasses.keySet())
		{
			nrOfRemovals.put(aClass.getId(), 0);
		}
	}
	/***********************Getter and Setter********************/
	public int getId() {
		return this.id;
	}
		
	@SuppressWarnings("unchecked")
	public HashMap<Integer,Class[]> getRoomsTimetable() {
		return (HashMap<Integer,Class[]>)roomsTimetable.clone();
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer,Class[]> getInstructorsTimetable() {
		return (HashMap<Integer,Class[]>)instructorsTimetable.clone();
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer, Class[]> getStudentsTimetable() {
		return (HashMap<Integer,Class[]>)studentsTimetable.clone();
	}

	public int getNrOfTimeSlotsPerDay() {
		return this.nrOfTimeSlotsPerDay;
	}

	public void setNrOfTimeSlotsPerDay(int nrOfTimeSlotsPerDay) {
		this.nrOfTimeSlotsPerDay = nrOfTimeSlotsPerDay;
	}

	public List<Class> getUnassignedClasses() {
		return this.unassignedClasses;
	}

	public HashMap<Class,Integer> getAssignedClasses() {
		return this.assignedClasses;
	}
	
	public int getNrOfRemovals(int classId)
	{
		return this.nrOfRemovals.get(classId);
	}

	public int getNrOfClasses() {
		return this.nrOfClasses;
	}

	public void setNrOfClasses(int nrOfClasses) {
		this.nrOfClasses = nrOfClasses;
	}

	public int getNrOfDays() {
		return this.nrOfDays;
	}

	public void setNrOfDays(int nrOfDays) {
		this.nrOfDays = nrOfDays;
	}
	
	/**
	 * Gets a list of classes and starts allocating memory for 
	 * the records in all the timetable for the rooms, instructors
	 *  and studentGroups found
	 * @param classes
	 */
	private void AllocateMemoryForClasses(List<Class> classes)
	{
		for(Class oneClass : classes)
		{
			// Allocate memory for the room
			int roomId = oneClass.getAssignedRoomId();
			if(!this.roomsTimetable.containsKey(roomId))
			{
				this.roomsTimetable.put(roomId,
						new Class[this.nrOfTimeSlotsPerDay]);
			}
			// Allocate memory for the instructor
			int instructorId = oneClass.getAssignedInstructorId();
			if(!this.instructorsTimetable.containsKey(instructorId))
			{
				this.instructorsTimetable.put(instructorId,
						new Class[this.nrOfTimeSlotsPerDay]);
			}
			// Allocate memory for the student groups
			List<Integer> stGroupsIds = oneClass.getAssignedStudentGroupsIds();
			for(Integer stGroupId : stGroupsIds)
			{
				if(!this.studentsTimetable.containsKey(stGroupId))
				{
					this.studentsTimetable.put(stGroupId,
							new Class[this.nrOfTimeSlotsPerDay]);
				}
			}			
		}
	}
	
	/**
	 * Finds the assigned classes by searching the rooms timetable.
	 * It then stores the class found and it's index representing the 
	 * start slot position for the class.
	 */
	private void FindAssignedClasses()
	{
		this.assignedClasses = new HashMap<Class,Integer>();
		
		for(Class[] roomClasses : this.roomsTimetable.values())
		{
			// For each room
			for(int i=0; i< this.nrOfTimeSlotsPerDay; i++)
			{
				// For each class
				if(roomClasses[i] != null)
				{
					// If it exists, save it and it's index
					this.assignedClasses.put(roomClasses[i], i);
				}
			}
		}
	}
	
	/* Public methods to model the behavior of the class */
	/**
	 * Assigns a given variable(or class) to a given value(or place in time)
	 * @param variable
	 * @param value
	 * @return
	 */
	public Message Assign(Class variable, int value)
	{
		// Set parameters
		Message status = Message.UNASSIGNED;
		int roomId = variable.getAssignedRoomId();
		int instructorId = variable.getAssignedInstructorId();
		List<Integer> stGroupsIds = variable.getAssignedStudentGroupsIds();
		// Check to see if the variable belongs to this solution
		if(this.unassignedClasses.contains(variable))
		{
			// Check to see if any of the necessary resources are occupied
			if(this.roomsTimetable.get(roomId)[value] == null)
			{
				if(this.instructorsTimetable.get(instructorId)[value] == null)
				{
					for(Integer stGroupId : stGroupsIds)
					{
						if(this.studentsTimetable.get(stGroupId)[value] != null)
						{
							// One or more of the student groups
							// that were supposed to attend the given
							// class are occupied at the given time
							status = Message.STUDENTGROUP_IS_UNAVAILABLE;
						}
					}
				}else{
					// The instructor for the given class is occupied at the given time
					status = Message.INSTRUCTOR_IS_UNAVAILABLE;
				}
			}else{
				// The room for the given class is occupied at the given time
				status = Message.ROOM_IS_UNAVAILABLE;
			}
		}else{
			// The variable is not a unassigned variable of this solution
			// Display a proper warning message
			status  = Message.VARIABLE_NOT_FOUND;
		}
		
		// Check the status
		if(status == Message.UNASSIGNED)
		{
			// Assign the variable to the value
			// For the entire length of the class
			for(int  i=0; i<variable.getOffering().getNrOfTimeSlots(); i++)
			{
				this.roomsTimetable.get(roomId)[value + i] = variable;
				this.instructorsTimetable.get(instructorId)[value + i] = variable;
				for(Integer stGroupId : stGroupsIds)
				{
					this.studentsTimetable.get(stGroupId)[value + i] = variable;
				}
			}
			this.unassignedClasses.remove(variable);
			this.assignedClasses.put(variable, value);
			status = Message.ASIGGN_SUCCESSFULL;
		}
		
		return status;
	}

	public int Unassign(List<Class> classes)
	{
		int nrOfUnassignedClasses = 0;
		
		for(Class oneClass : classes)
		{
			if(this.assignedClasses.containsKey(oneClass))
			{
				int roomId = oneClass.getAssignedRoomId();
				int instructorId = oneClass.getAssignedInstructorId();
				List<Integer> stGroupsIds = oneClass.getAssignedStudentGroupsIds();
				int dayNTime = this.assignedClasses.get(oneClass);
				// Unassigns the class from all the tables
				for(int  i=0; i<oneClass.getOffering().getNrOfTimeSlots(); i++)
				{
					this.roomsTimetable.get(roomId)[dayNTime] = null;
					this.instructorsTimetable.get(instructorId)[dayNTime] = null;
					for(Integer stGroupId : stGroupsIds)
					{
						this.studentsTimetable.get(stGroupId)[dayNTime] = null;
					}
				}
				this.unassignedClasses.add(oneClass);
				// This line increments the number of removals for this one class
				int nrOfRemovals = this.nrOfRemovals.get(oneClass.getId());
				nrOfRemovals++;
				this.nrOfRemovals.replace(oneClass.getId(), nrOfRemovals);
				nrOfUnassignedClasses++;
			}	
		}
		
		return nrOfUnassignedClasses;
	}
	
	public int GetDomainSize(Class uClass, boolean conflicting)
	{
		Class[] rmClasses = this.roomsTimetable.get(uClass.getAssignedRoomId());
		Class[] instrClasses = this.instructorsTimetable.get(uClass.getAssignedInstructorId());
		List<Integer> stGrpsIds = uClass.getAssignedStudentGroupsIds();
		List<Class[]> stGrpsClasses = new ArrayList<Class[]>();
		for(int stGrId : stGrpsIds)
		{
			stGrpsClasses.add(this.studentsTimetable.get(stGrId));
		}
		
		// Calculate the domain size, depending on the mode
		// This will memorize if a time of day is eligible as a start position
		boolean[] checksConflicting = new boolean[nrOfDays*nrOfTimeSlotsPerDay];
		
		// First, calculate which slot is free according to all the conditions 
		// For each day
		for(int i=0; i < nrOfDays; i++)
		{
			// Get room and instructor preferences for the day
			TimePreference[] rmTimePref = uClass.getAssignedRoom().getPreferencesByDay(DayOfTheWeek.values()[i]);
			TimePreference[] instrTimePref = uClass.getAssignedInstructor().getTimePreferencesByDay(DayOfTheWeek.values()[i]);
			
			// For each time of the day
			for(int j=0; j< this.nrOfTimeSlotsPerDay; j++)
			{
				// Check is the room is free, if no check if the class occupying it is fixed
				// Then check if the room access is allowed at that time and day
				if((rmClasses[i*nrOfDays+j] == null || ( conflicting && !rmClasses[i*nrOfDays+j].isFixed()))
						&& (rmTimePref[i*nrOfDays+j] != TimePreference.PROHIBITED || 
						(!conflicting && rmTimePref[i*nrOfDays+j] != TimePreference.STRONGLY_DISCOURAGED)))
				{
					// Check is the instructor is free, if no check if the class occupying it is fixed
					// Then check if the instructor works at that time and day
					if((instrClasses[i*nrOfDays+j] == null || ( conflicting && !instrClasses[i*nrOfDays+j].isFixed()))
							&& (instrTimePref[i*nrOfDays+j] != TimePreference.PROHIBITED ||
							(!conflicting && instrTimePref[i*nrOfDays+j] != TimePreference.STRONGLY_DISCOURAGED)))
					{
						boolean stGrpsCheck = true;
						// Check if any of the student groups are busy, if yes then check to see if the 
						// class occupying them is fixed
						for(Class[] stGrpClasses : stGrpsClasses)
						{
							if(!(stGrpClasses[i*nrOfDays+j] == null || ( conflicting && !stGrpClasses[i*nrOfDays+j].isFixed())))
							{
								stGrpsCheck = false;
								break;
							}									
						}
						
						if(stGrpsCheck)
						{
							// Everything checks out, this is a valid domain start position
							checksConflicting[i*nrOfDays+j] = true;
						}
					}
				}						
			}
		}

		// Second, count the number of valid domain start positions. One valid domain start
		// position is a star positions that has a number of free slots after it equal
		// to the class length
		// For each day
		int domainSize = 0;
		for(int i = 0; i < nrOfDays; i++)
		{
			// For each time of day
			for(int j=0; j< nrOfTimeSlotsPerDay; j++)
			{
				boolean isValidStartPos = true;
				// For the length of the class, check if the time slots are free
				for(int k=j; k< j+uClass.getOffering().getNrOfTimeSlots(); k++)
				{
					isValidStartPos &= checksConflicting[i*nrOfDays+k];
				}
				// Memorize if the start position is a valid one 
				checksConflicting[i*nrOfDays+j] = isValidStartPos;
				if(isValidStartPos)domainSize++;
			}
		}
		
		return domainSize;
	}

	public Map<Integer,String> get_roomsAssignments() {
		return roomsAssignments;
	}

	public void set_roomsAssignments(Map<Integer,String> _roomsAssignments) {
		this.roomsAssignments = _roomsAssignments;
	}

	public Map<Integer,String> get_instructorsAssignments() {
		return instructorsAssignments;
	}

	public void set_instructorsAssignments(Map<Integer,String> _instructorsAssignments) {
		this.instructorsAssignments = _instructorsAssignments;
	}

	public Map<Integer,String> get_studentsAssignments() {
		return studentsAssignments;
	}

	public void set_studentsAssignments(Map<Integer,String> _studentsAssignments) {
		this.studentsAssignments = _studentsAssignments;
	}
}
