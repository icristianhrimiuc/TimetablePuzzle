package timetablepuzzle.usecases.solution;

import java.util.List;

import timetablepuzzle.eclipselink.entities.inputdata.Class;
import timetablepuzzle.eclipselink.entities.inputdata.Solution;

public class SolutionAssignManager {
	public static enum Message {
		CLASS_NOT_FOUND, UNASSIGNED, ASIGGN_SUCCESSFULL, ROOM_IS_UNAVAILABLE, INSTRUCTOR_IS_UNAVAILABLE, STUDENTGROUP_IS_UNAVAILABLE
	};
	
	private Solution solution;
	
	public SolutionAssignManager(Solution solution)
	{
		this.solution = solution;
	}

	public Message Assign(Class aClass, int dayAndTimeSlot) {
		// Set parameters
		Message status = Message.UNASSIGNED;
		int classId = aClass.getId();
		int roomId = aClass.getAssignedRoomId();
		int instructorId = aClass.getAssignedInstructorId();
		List<Integer> stGroupsIds = aClass.getAssignedStudentGroupsIds();
		// Check to see if the variable belongs to this solution
		if (solution.IsClassInSolution(classId)) {
			// Check to see if any of the necessary resources are occupied
			if (solution.IsRoomFree(roomId, dayAndTimeSlot)) {
				if (solution.IsInstructorFree(instructorId, dayAndTimeSlot)) {
					for (Integer stGroupId : stGroupsIds) {
						if (solution.IsStudentGroupFree(stGroupId, dayAndTimeSlot)) {
							// One or more of the student groups
							// that were supposed to attend the given
							// class are occupied at the given time
							status = Message.STUDENTGROUP_IS_UNAVAILABLE;
						}
					}
				} else {
					// The instructor for the given class is occupied at the
					// given time
					status = Message.INSTRUCTOR_IS_UNAVAILABLE;
				}
			} else {
				// The room for the given class is occupied at the given time
				status = Message.ROOM_IS_UNAVAILABLE;
			}
		} else {
			// The variable is not a unassigned variable of this solution
			// Display a proper warning message
			status = Message.CLASS_NOT_FOUND;
		}

		// Check the status
		if (status == Message.UNASSIGNED) {
			// Assign the variable to the value
			// For the entire length of the class
			for (int i = 0; i < aClass.GetClassDuration(); i++) {
				solution.AssignClassToRoom(roomId, classId, dayAndTimeSlot+i);
				solution.AssignClassToInstructor(instructorId, classId, dayAndTimeSlot+i);
				for (Integer stGroupId : stGroupsIds) {
					solution.AssignClassToStudentGroup(stGroupId, classId, dayAndTimeSlot+i);
				}
			}
			solution.SetAssignedDayAndTimeSlot(classId, dayAndTimeSlot);
			status = Message.ASIGGN_SUCCESSFULL;
		}

		return status;
	}
}
