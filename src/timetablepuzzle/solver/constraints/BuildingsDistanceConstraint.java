package timetablepuzzle.solver.constraints;

import java.util.List;

import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.inputData.Class;
import timetablepuzzle.entities.other.Location;
import timetablepuzzle.usecases.solution.TimeslotPattern;

public class BuildingsDistanceConstraint extends AbstractHardConstraint {
	public static final int DefaultMaxDistanceBetweenBuildings = 300;

	private int maxDistanceBetweenBuildings;

	public BuildingsDistanceConstraint(Solution solution) {
		super(solution);
		this.maxDistanceBetweenBuildings = DefaultMaxDistanceBetweenBuildings;
	}

	/**
	 * Checks if a given class can be assigned to a given dayAndTimeSlot without
	 * violating this constraint
	 * 
	 * @param solution
	 * @param selClass
	 * @param dayNTime
	 */
	public boolean IsValidAssignment(Class aClass, int dayAndTimeSlot) {
		return this.checker.IsValidAssignment(aClass, dayAndTimeSlot) && CanInstructorTakeClass(aClass, dayAndTimeSlot)
				&& CanStudentGroupsTakeClass(aClass, dayAndTimeSlot);
	}

	private boolean CanInstructorTakeClass(Class aClass, int dayAndTimeSlot) {
		Location aClassLocation = aClass.getAssignedRoom().getBuilding().getLocation();
		int instructorId = aClass.getAssignedInstructorId();
		int previousDayAndTimeSlot = dayAndTimeSlot - 1;
		int nextDayAndTimeSlot = dayAndTimeSlot + aClass.GetClassDuration();

		return IsValidInstructorTimeSlot(aClassLocation, instructorId, previousDayAndTimeSlot)
				&& IsValidInstructorTimeSlot(aClassLocation, instructorId, nextDayAndTimeSlot);
	}

	private boolean IsValidInstructorTimeSlot(Location aClassLocation, int instructorId, int dayAndTimeSlot) {
		return TimeslotPattern.IsFirstTimeslotOfTheDay(dayAndTimeSlot)
				|| this.solution.IsInstructorFree(instructorId, dayAndTimeSlot)
				|| IsValidDistance(aClassLocation, GetInstructorClassLocation(instructorId, dayAndTimeSlot));
	}

	private boolean IsValidDistance(Location firstLocation, Location secondLocation) {
		return firstLocation.DistanceTo(secondLocation) <= this.maxDistanceBetweenBuildings;
	}

	private Location GetInstructorClassLocation(int instructorId, int dayAndTimeSlot) {
		int classId = this.solution.GetInstructorAssignment(instructorId, dayAndTimeSlot);
		Class aClass = this.solution.GetClassById(classId);

		return aClass.getAssignedRoom().getBuilding().getLocation();
	}

	private boolean CanStudentGroupsTakeClass(Class aClass, int dayAndTimeSlot) {
		Location aClassLocation = aClass.getAssignedRoom().getBuilding().getLocation();
		List<Integer> studentGroupsIds = aClass.getAssignedStudentGroupsIds();
		int previousDayAndTimeSlot = dayAndTimeSlot - 1;
		int nextDayAndTimeSlot = dayAndTimeSlot + aClass.GetClassDuration();
		boolean canStudentGroupsTakeClass = true;
		for(Integer studentGroupId : studentGroupsIds)
		{
			if(!IsValidStudentGroupTimeSlot(aClassLocation, studentGroupId, previousDayAndTimeSlot)
					|| !IsValidStudentGroupTimeSlot(aClassLocation, studentGroupId, nextDayAndTimeSlot))
			{
				canStudentGroupsTakeClass = false;
				break;
			}
		}
		
		return canStudentGroupsTakeClass;
	}

	private boolean IsValidStudentGroupTimeSlot(Location aClassLocation, int studentGroupId, int dayAndTimeSlot) {;
		return TimeslotPattern.IsFirstTimeslotOfTheDay(dayAndTimeSlot)
				|| this.solution.IsStudentGroupFree(studentGroupId, dayAndTimeSlot)
				|| IsValidDistance(aClassLocation, GetStudentGroupClassLocation(studentGroupId, dayAndTimeSlot));
	}

	private Location GetStudentGroupClassLocation(int studentGroupId, int dayAndTimeSlot) {
		int classId = this.solution.GetStudentGroupAssignment(studentGroupId, dayAndTimeSlot);
		Class aClass = this.solution.GetClassById(classId);

		return aClass.getAssignedRoom().getBuilding().getLocation();
	}
}
