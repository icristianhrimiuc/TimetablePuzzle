package timetablepuzzle.usecases.solution;

import java.util.List;

import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.Solution;

public class SolutionUnassignManager {
	
	public Solution solution;
	
	public SolutionUnassignManager(Solution solution)
	{
		this.solution = solution;
	}
	
	public void Unassign(Class aClass) {
		int classId = aClass.getId();
		
		if (solution.IsClassInSolution(classId)) {
			int roomId = aClass.getAssignedRoomId();
			int instructorId = aClass.getAssignedInstructorId();
			List<Integer> stGroupsIds = aClass.getAssignedStudentGroupsIds();
			int dayAndTime = solution.GetAssignedDayAndTimeSlot(classId);
			// Unassigns the class from all the tables
			for (int i = 0; i < aClass.GetClassDuration(); i++) {
				solution.AssignClassToRoom(roomId, TimeslotPattern.FreeTimeSlot, dayAndTime);
				solution.AssignClassToInstructor(instructorId, TimeslotPattern.FreeTimeSlot, dayAndTime);
				for (Integer stGroupId : stGroupsIds) {
				
					solution.AssignClassToStudentGroup(stGroupId, TimeslotPattern.FreeTimeSlot, dayAndTime);
				}
			}
			solution.SetAssignedDayAndTimeSlot(classId, TimeslotPattern.UnassignedTimeSlot);
			solution.IncrementNrOfRemovals(classId);
		}
	}
}
