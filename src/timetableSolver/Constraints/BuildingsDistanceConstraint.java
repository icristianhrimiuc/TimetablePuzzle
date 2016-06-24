package timetableSolver.Constraints;

import java.util.HashMap;
import java.util.List;

import timetablepuzzle.eclipselink.entities.administration.Location;
import timetablepuzzle.eclipselink.entities.inputdata.Class;
import timetablepuzzle.eclipselink.entities.inputdata.Solution;

public class BuildingsDistanceConstraint extends AbstractHardConstraint {
	// The maximum allowed distance between two consecutive classes locations
	private int _maxDistanceBetweenBuildings;
	
	/**
	 * Default constructor
	 */
	public BuildingsDistanceConstraint()
	{
		_maxDistanceBetweenBuildings = 300;
	}
	
	
	/**
	 * Checks if a given class can be assigned to a given 
	 * dayNTime without violating this constraint
	 * @param solution
	 * @param selClass
	 * @param dayNTime
	 */
	public boolean IsValidCombination(Solution solution, Class selClass, int dayNTime)
	{
		// Caution: this function assumes that the user verified that the selClass fits from the 
		// time slot given for the extent of it's length
		return CheckInstructorSchedule(solution, selClass, dayNTime) &&
				CheckStudentGroupsSchedule(solution, selClass, dayNTime);
	}
	
	/**
	 * Check constraint for the instructors schedule
	 * @param solution
	 * @param selClass
	 * @param dayNTime
	 * @return
	 */
	private boolean CheckInstructorSchedule(Solution solution, Class selClass, int dayNTime)
	{
		boolean flag = true;
		// Check constraint for the instructors schedule
		Location selClassLocation = selClass.get_assignedRoom().get_building().get_location();
		int selClassLength = selClass.get_meeting().get_nrOfTimeSlots();
		int timeOfDay = dayNTime%solution.get_nrOfDays();
		int instrId = selClass.getAssignedInstructorId();
		Class[] instrClasses = solution.get_instructorsTimetable().get(instrId);
		// Helper variables
		Class beforeClass;
		Location beforeClassLocation;
		Class nextClass;
		Location nextClassLocation;
		if(timeOfDay >= 0 && 
				timeOfDay < (solution.get_nrOfTimeSlotsPerDay() - selClassLength))
		{
			// Then it is the first class of the day, or something between the first and the last
			// (first exclusive)Get the next class, if one exists
			nextClass = instrClasses[dayNTime + selClassLength];
			if(nextClass != null)
			{
				nextClassLocation = nextClass.get_assignedRoom().get_building().get_location();
				if(selClassLocation.distanceTo(nextClassLocation) > _maxDistanceBetweenBuildings)
				{
					flag = false;
				}
			}
		}
		if(timeOfDay > selClassLength && timeOfDay <= (solution.get_nrOfTimeSlotsPerDay() - selClassLength))
		{
			// Then it is the last class of the day, or something between the first and the last
			// (last exclusive)Get the before class, if one exists
			beforeClass = instrClasses[dayNTime - 1];
			if(beforeClass != null)
			{
				beforeClassLocation = beforeClass.get_assignedRoom().get_building().get_location();
				if(selClassLocation.distanceTo(beforeClassLocation) > _maxDistanceBetweenBuildings)
				{
					flag = false;
				}					
			}
		}
		
		return flag;
	}
	
	private boolean CheckStudentGroupsSchedule(Solution solution, Class selClass, int dayNTime)
	{
		boolean flag = true;
		// Check constraint for the studentGroup schedule
		Location selClassLocation = selClass.get_assignedRoom().get_building().get_location();
		int selClassLength = selClass.get_meeting().get_nrOfTimeSlots();
		int timeOfDay = dayNTime%solution.get_nrOfDays();
		List<Integer> stGrpsIds = selClass.getAssignedStudentGroupsIds();
		HashMap<Integer,Class[]> studentsTimetable = solution.get_studentsTimetable();
		Class[] stGrpClasses;
		for(Integer stGrpId : stGrpsIds)
		{
			stGrpClasses = studentsTimetable.get(stGrpId);
			// Helper variables
			Class beforeClass;
			Location beforeClassLocation;
			Class nextClass;
			Location nextClassLocation;
			if(timeOfDay >= 0 && 
					timeOfDay < (solution.get_nrOfTimeSlotsPerDay() - selClassLength))
			{
				// Then it is the first class of the day, or something between the first and the last
				// (first exclusive)Get the next class, if one exists
				nextClass = stGrpClasses[dayNTime + selClassLength];
				if(nextClass != null)
				{
					nextClassLocation = nextClass.get_assignedRoom().get_building().get_location();
					if(selClassLocation.distanceTo(nextClassLocation) > _maxDistanceBetweenBuildings)
					{
						flag = false;
					}
				}
			}
			if(timeOfDay > selClassLength && timeOfDay <= (solution.get_nrOfTimeSlotsPerDay() - selClassLength))
			{
				// Then it is the last class of the day, or something between the first and the last
				// (last exclusive)Get the before class, if one exists
				beforeClass = stGrpClasses[dayNTime - 1];
				if(beforeClass != null)
				{
					beforeClassLocation = beforeClass.get_assignedRoom().get_building().get_location();
					if(selClassLocation.distanceTo(beforeClassLocation) > _maxDistanceBetweenBuildings)
					{
						flag = false;
					}					
				}
			}
		}
				
		
		return flag;
	}
}
