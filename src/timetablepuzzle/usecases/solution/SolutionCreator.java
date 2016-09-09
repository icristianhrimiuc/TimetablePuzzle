package timetablepuzzle.usecases.solution;

import java.util.ArrayList;
import java.util.List;

import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.Solution;

public class SolutionCreator {
	private List<Class> classes;
	private Solution solution;
	
	public SolutionCreator(String name, List<Class> classes)
	{
		this.classes = classes;
		this.solution = new Solution(0,name, this.classes);
		InitializeRoomAssignments();
		InitializeInstructorAssignments();
		InitializeStudentGroupsAssignments();
		InitializeAssignedDayAndTimeSlots();
		InitializeNumberOfRemovals();
	}
	
	public Solution CreateNewSolution()
	{
		return solution;
	}
	
	private void InitializeRoomAssignments() {
		List<Integer> roomsIds = new ArrayList<Integer>();
		for (Class aClass : this.classes) {
			int roomId = aClass.getAssignedRoomId();
			if (!roomsIds.contains(roomId)) {
				roomsIds.add(roomId);
			}
		}
		for(Integer roomId : roomsIds)
		{
			this.solution.AssignWeekToRoom(roomId, TimeslotPattern.GenerateFreeWeek());
		}
	}
	
	private void InitializeInstructorAssignments() {
		List<Integer> instructorsIds = new ArrayList<Integer>();
		for (Class aClass : this.classes) {
			int instructorId = aClass.getAssignedInstructorId();
			if (!instructorsIds.contains(instructorId)) {
				instructorsIds.add(instructorId);
			}
		}
		for(Integer instructorId : instructorsIds)
		{
			this.solution.AssignWeekToInstructor(instructorId, TimeslotPattern.GenerateFreeWeek());
		}
	}
	
	private void InitializeStudentGroupsAssignments() {
		List<Integer> studentGroupsIds = new ArrayList<Integer>();
		for (Class aClass : this.classes) {
			studentGroupsIds.addAll(aClass.getAssignedStudentGroupsIds());			
		}
		
		for(Integer studentGroupId : studentGroupsIds)
		{
			this.solution.AssignWeekToStudentGroup(studentGroupId, TimeslotPattern.GenerateFreeWeek());
		}
	}

	private void InitializeAssignedDayAndTimeSlots() {
		for (Class aClass : this.classes) {
			this.solution.SetAssignedDayAndTimeSlot(aClass.getId(), TimeslotPattern.UnassignedTimeSlot);
		}
	}

	private void InitializeNumberOfRemovals() {
		for (Class aClass : this.classes) {
			this.solution.ResetNrOfRemovals(aClass.getId());
		}
	}
}
