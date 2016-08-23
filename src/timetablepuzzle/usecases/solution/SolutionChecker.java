package timetablepuzzle.usecases.solution;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;

import java.util.List;

import timetablepuzzle.eclipselink.entities.inputdata.Class;

public class SolutionChecker {
	protected Solution solution;

	public SolutionChecker(Solution solution) {
		this.solution = solution;
	}

	public boolean IsValidAssignment(Class aClass, int startDayAndTimeSlot) {
		int endDayAndTimeSlot = startDayAndTimeSlot + aClass.GetClassDuration();
		int nrOfTimeSlotsPerDay = this.solution.getTimeslotPattern().getNrOfTimeSlotsPerDay();
		boolean isValidAssignment = (endDayAndTimeSlot <= nrOfTimeSlotsPerDay);
		
		if (isValidAssignment) {
			for (int dayAndTimeSlot = startDayAndTimeSlot; dayAndTimeSlot < endDayAndTimeSlot; dayAndTimeSlot++) {
				if(!AreResourcesFree(aClass, dayAndTimeSlot))
				{
					isValidAssignment = false;
					break;
				}
			}
		}
		
		return isValidAssignment;
	}

	/**
	 * Check if all resources are free
	 * @param aClass
	 * @param dayAndTimeSlot
	 * @return
	 */
	private boolean AreResourcesFree(Class aClass, int dayAndTimeSlot) {
		return this.solution.IsRoomFree(aClass.getAssignedRoomId(), dayAndTimeSlot) && 
				this.solution.IsInstructorFree(aClass.getAssignedInstructorId(), dayAndTimeSlot) &&
				IsParentStudentGroupFree(aClass.getAssignedStudentGroupsIds(), dayAndTimeSlot);
	}
	
	/**
	 * Check if all child student groups are free
	 * @param childStudentGroupsIds
	 * @param dayAndTimeSlot
	 * @return
	 */
	private boolean IsParentStudentGroupFree(List<Integer> childStudentGroupsIds, int dayAndTimeSlot)
	{
		boolean isStudentGroupFree = true;
		for(Integer studentGroupId : childStudentGroupsIds)
		{
			if(!this.solution.IsStudentGroupFree(studentGroupId, dayAndTimeSlot))
			{
				isStudentGroupFree = false;
				break;
			}
		}
		
		return isStudentGroupFree;
	}
}
