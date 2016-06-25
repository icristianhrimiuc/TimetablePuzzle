package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.*;

public class Solution {
	/* Constant variables*/
	public enum Message{VARIABLE_NOT_FOUND,UNASSIGNED,ASIGGN_SUCCESSFULL,ROOM_IS_UNAVAILABLE,
		INSTRUCTOR_IS_UNAVAILABLE,STUDENTGROUP_IS_UNAVAILABLE};
/*	public static final int VARIABLE_NOT_FOUND = -2;
	public static final int UNASSIGNED = -1;
	public static final int ASIGGN_SUCCESSFULL = 0;
	public static final int ROOM_IS_UNAVAILABLE = 1;
	public static final int INSTRUCTOR_IS_UNAVAILABLE = 2;
	public static final int STUDENTGROUP_IS_UNAVAILABLE = 3;*/
	/* Private properties */
	private int _externalId;
	private HashMap<Integer,Class[]> _roomsTimetable;
	private HashMap<Integer,Class[]> _instructorsTimetable;
	private HashMap<Integer, Class[]> _studentsTimetable;
	private List<Class> _unassignedClasses;
	private HashMap<Class,Integer> _assignedClasses;
	private HashMap<Integer,int[]> _nrOfRemovals;
	private int _nrOfClasses;
	private int _nrOfTimeSlotsPerDay;
	private int _nrOfDays;
	
	/**
	 * Constructor for creating a brand new solution
	 * @param nrOfTimeSlotsPerDay
	 */
	public Solution(int nrOfTimeSlotsPerDay, List<Class> unassignedClasses)
	{
		this(0, nrOfTimeSlotsPerDay, unassignedClasses,
				new HashMap<Integer, Class[]>(),
				new HashMap<Integer, Class[]>(),
				new HashMap<Integer, Class[]>());
		AllocateMemoryForClasses(this._unassignedClasses);
	}
	
	/** 
	 * Constructor for creating a partially or totally solution
	 * from informations stored in the database
	 * @param externalId
	 * @param nrOfTimeSlotsPerDay
	 * @param unassignedClasses
	 * @param assignedClasses
	 */
	public Solution(int externalId, int nrOfTimeSlotsPerDay,List<Class> unassignedClasses,
			HashMap<Integer,Class[]> roomsTimetable,
			HashMap<Integer,Class[]> instructorsTimetable, 
			HashMap<Integer,Class[]> studentsTimetable)
	{
		_externalId = externalId;
		_roomsTimetable = roomsTimetable;
		_instructorsTimetable = instructorsTimetable;
		_studentsTimetable = studentsTimetable;
		_unassignedClasses = unassignedClasses;
		FindAssignedClasses();
		_nrOfRemovals = new HashMap<Integer,int[]>();
		// Set the nrOfRemovals for the unassigned classes to 0
		for(Class uClass : _unassignedClasses)
		{
			// This is safe, because java sets it by default to zero
			_nrOfRemovals.put(uClass.get_externalId(), new int[1]);
		}
		// Set the number of removals for the assigned classes to 0
		for(Class aClass : _assignedClasses.keySet())
		{
			// This is safe, because java sets it by default to zero
			_nrOfRemovals.put(aClass.get_externalId(), new int[1]);
		}
		// Since the union of the unassignedClasses set and the assignedClasses set
		// must be the empty set, the total number of classes is equal to the size of
		// HashMap storing the number of removals
		set_nrOfClasses(_nrOfRemovals.size());
		set_nrOfTimeSlotsPerDay(nrOfTimeSlotsPerDay);
		set_nrOfDays(5);
	}

	/* Getter and Setter methods for almost all the fields of the class*/
	public int get_externalId() {
		return _externalId;
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer,Class[]> get_roomsTimetable() {
		return (HashMap<Integer,Class[]>)_roomsTimetable.clone();
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer,Class[]> get_instructorsTimetable() {
		return (HashMap<Integer,Class[]>)_instructorsTimetable.clone();
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer, Class[]> get_studentsTimetable() {
		return (HashMap<Integer,Class[]>)_studentsTimetable.clone();
	}

	public int get_nrOfTimeSlotsPerDay() {
		return _nrOfTimeSlotsPerDay;
	}

	public void set_nrOfTimeSlotsPerDay(int _nrOfTimeSlotsPerDay) {
		this._nrOfTimeSlotsPerDay = _nrOfTimeSlotsPerDay;
	}

	public List<Class> get_unassignedClasses() {
		return _unassignedClasses;
	} 

	public HashMap<Class,Integer> get_assignedClasses() {
		return _assignedClasses;
	}
	
	public int get_nrOfRemovals(int classId)
	{
		return this._nrOfRemovals.get(classId)[0];
	}

	public int get_nrOfClasses() {
		return _nrOfClasses;
	}

	public void set_nrOfClasses(int _nrOfClasses) {
		this._nrOfClasses = _nrOfClasses;
	}

	public int get_nrOfDays() {
		return _nrOfDays;
	}

	public void set_nrOfDays(int _nrOfDays) {
		this._nrOfDays = _nrOfDays;
	}
	
	/* Private methods to help simplify the code */
	
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
			if(!this._roomsTimetable.containsKey(roomId))
			{
				this._roomsTimetable.put(roomId,
						new Class[this._nrOfTimeSlotsPerDay]);
			}
			// Allocate memory for the instructor
			int instructorId = oneClass.getAssignedInstructorId();
			if(!this._instructorsTimetable.containsKey(instructorId))
			{
				this._instructorsTimetable.put(instructorId,
						new Class[this._nrOfTimeSlotsPerDay]);
			}
			// Allocate memory for the student groups
			List<Integer> stGroupsIds = oneClass.getAssignedStudentGroupsIds();
			for(Integer stGroupId : stGroupsIds)
			{
				if(!this._studentsTimetable.containsKey(stGroupId))
				{
					this._studentsTimetable.put(stGroupId,
							new Class[this._nrOfTimeSlotsPerDay]);
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
		for(Class[] roomClasses : this._roomsTimetable.values())
		{
			// For each room
			for(int i=0; i< this._nrOfTimeSlotsPerDay; i++)
			{
				// For each class
				if(roomClasses[i] != null)
				{
					// If it exists, save it and it's index
					this._assignedClasses.put(roomClasses[i], i);
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
		if(this._unassignedClasses.contains(variable))
		{
			// Check to see if any of the necessary resources are occupied
			if(this._roomsTimetable.get(roomId)[value] == null)
			{
				if(this._instructorsTimetable.get(instructorId)[value] == null)
				{
					for(Integer stGroupId : stGroupsIds)
					{
						if(this._studentsTimetable.get(stGroupId)[value] != null)
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
			for(int  i=0; i<variable.get_meeting().get_nrOfTimeSlots(); i++)
			{
				this._roomsTimetable.get(roomId)[value + i] = variable;
				this._instructorsTimetable.get(instructorId)[value + i] = variable;
				for(Integer stGroupId : stGroupsIds)
				{
					this._studentsTimetable.get(stGroupId)[value + i] = variable;
				}
			}
			this._unassignedClasses.remove(variable);
			this._assignedClasses.put(variable, value);
			status = Message.ASIGGN_SUCCESSFULL;
		}
		
		return status;
	}

	public int Unassign(List<Class> classes)
	{
		int nrOfUnassignedClasses = 0;
		
		for(Class oneClass : classes)
		{
			if(this._assignedClasses.containsKey(oneClass))
			{
				int roomId = oneClass.getAssignedRoomId();
				int instructorId = oneClass.getAssignedInstructorId();
				List<Integer> stGroupsIds = oneClass.getAssignedStudentGroupsIds();
				int dayNTime = this._assignedClasses.get(oneClass);
				// Unassigns the class from all the tables
				for(int  i=0; i<oneClass.get_meeting().get_nrOfTimeSlots(); i++)
				{
					this._roomsTimetable.get(roomId)[dayNTime] = null;
					this._instructorsTimetable.get(instructorId)[dayNTime] = null;
					for(Integer stGroupId : stGroupsIds)
					{
						this._studentsTimetable.get(stGroupId)[dayNTime] = null;
					}
				}
				this._unassignedClasses.add(oneClass);
				// This line increments the number of removals for this one class
				this._nrOfRemovals.get(oneClass.get_externalId())[0]++;
				nrOfUnassignedClasses++;
			}			
		}
		
		return nrOfUnassignedClasses;
	}
	
	public int GetDomainSize(Class uClass, boolean conflicting)
	{
		Class[] rmClasses = this._roomsTimetable.get(uClass.getAssignedRoomId());
		Class[] instrClasses = this._instructorsTimetable.get(uClass.getAssignedInstructorId());
		List<Integer> stGrpsIds = uClass.getAssignedStudentGroupsIds();
		List<Class[]> stGrpsClasses = new ArrayList<Class[]>();
		for(int stGrId : stGrpsIds)
		{
			stGrpsClasses.add(this._studentsTimetable.get(stGrId));
		}
		
		// Calculate the domain size, depending on the mode
		// This will memorize if a time of day is eligible as a start position
		boolean[] checksConflicting = new boolean[_nrOfDays*_nrOfTimeSlotsPerDay];
		
		// First, calculate which slot is free according to all the conditions 
		// For each day
		for(int i=0; i < _nrOfDays; i++)
		{
			// Get room and instructor preferences for the day
			TimePref[] rmTimePref = uClass.get_assignedRoom().get_preferencesByDay(Day.values()[i]);
			TimePref[] instrTimePref = uClass.get_assignedInstructor().get_timePreferencesByDay(Day.values()[i]);
			
			// For each time of the day
			for(int j=0; j< this._nrOfTimeSlotsPerDay; j++)
			{
				// Check is the room is free, if no check if the class occupying it is fixed
				// Then check if the room access is allowed at that time and day
				if((rmClasses[i*_nrOfDays+j] == null || ( conflicting && !rmClasses[i*_nrOfDays+j].is_fixed()))
						&& (rmTimePref[i*_nrOfDays+j] != TimePref.PROHIBITED || 
						(!conflicting && rmTimePref[i*_nrOfDays+j] != TimePref.STRONGLY_DISCOURAGED)))
				{
					// Check is the instructor is free, if no check if the class occupying it is fixed
					// Then check if the instructor works at that time and day
					if((instrClasses[i*_nrOfDays+j] == null || ( conflicting && !instrClasses[i*_nrOfDays+j].is_fixed()))
							&& (instrTimePref[i*_nrOfDays+j] != TimePref.PROHIBITED ||
							(!conflicting && instrTimePref[i*_nrOfDays+j] != TimePref.STRONGLY_DISCOURAGED)))
					{
						boolean stGrpsCheck = true;
						// Check if any of the student groups are busy, if yes then check to see if the 
						// class occupying them is fixed
						for(Class[] stGrpClasses : stGrpsClasses)
						{
							if(!(stGrpClasses[i*_nrOfDays+j] == null || ( conflicting && !stGrpClasses[i*_nrOfDays+j].is_fixed())))
							{
								stGrpsCheck = false;
								break;
							}									
						}
						
						if(stGrpsCheck)
						{
							// Everything checks out, this is a valid domain start position
							checksConflicting[i*_nrOfDays+j] = true;
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
		for(int i = 0; i < _nrOfDays; i++)
		{
			// For each time of day
			for(int j=0; j< _nrOfTimeSlotsPerDay; j++)
			{
				boolean isValidStartPos = true;
				// For the length of the class, check if the time slots are free
				for(int k=j; k< j+uClass.get_meeting().get_nrOfTimeSlots(); k++)
				{
					isValidStartPos &= checksConflicting[i*_nrOfDays+k];
				}
				// Memorize if the start position is a valid one 
				checksConflicting[i*_nrOfDays+j] = isValidStartPos;
				if(isValidStartPos)domainSize++;
			}
		}
		
		return domainSize;
	}
}
