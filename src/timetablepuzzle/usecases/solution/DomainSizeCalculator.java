package timetablepuzzle.usecases.solution;

import java.util.ArrayList;
import java.util.List;

import timetablepuzzle.eclipselink.entities.administration.TimePreferences.DayOfTheWeek;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.TimePreference;
import timetablepuzzle.eclipselink.entities.inputdata.Class;
import timetablepuzzle.eclipselink.entities.inputdata.Instructor;
import timetablepuzzle.eclipselink.entities.inputdata.Room;
import timetablepuzzle.eclipselink.entities.inputdata.Solution;

public class DomainSizeCalculator {

	private Solution solution;
	private int nrOfDays;
	private int nrOfTimeSlotsPerDay;

	public DomainSizeCalculator(Solution solution) {
		this.solution = solution;
		this.nrOfDays = this.solution.getTimeslotPattern().getNrOfDays();
		this.nrOfTimeSlotsPerDay = this.solution.getTimeslotPattern().getNrOfTimeSlotsPerDay();
	}

	public int GetNonConflictingDomainSize(Class aClass) {
		boolean[] validTimeSlots = GetValidTimeSlots(aClass, false);
		int aClassLength = aClass.getOffering().getNrOfTimeSlots();
		List<Integer> validStartTimeSlots = GetValidStartTimeslots(validTimeSlots, aClassLength);

		return validStartTimeSlots.size();
	}

	public int GetConflictingDomainSize(Class aClass) {
		boolean[] validTimeSlots = GetValidTimeSlots(aClass, true);
		int aClassLength = aClass.getOffering().getNrOfTimeSlots();
		List<Integer> validStartTimeSlots = GetValidStartTimeslots(validTimeSlots, aClassLength);

		return validStartTimeSlots.size();
	}

	private boolean[] GetValidTimeSlots(Class aClass, boolean conflicting) {
		Room room = aClass.getAssignedRoom();
		Instructor instructor = aClass.getAssignedInstructor();
		List<Integer> stGroupsIds = aClass.getAssignedStudentGroupsIds();
		// This will memorize if a time slot passed all conditions
		boolean[] validTimeSlots = new boolean[nrOfDays * nrOfTimeSlotsPerDay];
		for (int dayAndTimeSlot = 0; dayAndTimeSlot < nrOfDays * nrOfTimeSlotsPerDay; dayAndTimeSlot++) {
			validTimeSlots[dayAndTimeSlot] = IsRoomValid(room, dayAndTimeSlot, conflicting)
					&& IsInstructorValid(instructor, dayAndTimeSlot, conflicting)
					&& AreStudentGroupsValid(stGroupsIds, dayAndTimeSlot, conflicting);
		}

		return validTimeSlots;
	}

	/**
	 * Check is the room is free, then check if the room access is allowed at
	 * that time and day
	 * 
	 * @param room
	 * @param day
	 * @param slotNr
	 * @return
	 */
	private boolean IsRoomValid(Room room, int dayAndTimeSlot, boolean conflicting) {
		int slotNr = dayAndTimeSlot % nrOfDays;
		DayOfTheWeek day = DayOfTheWeek.values()[dayAndTimeSlot / nrOfDays];
		TimePreference roomTimePreference = room.getPreferencesByDayAndTime(day, slotNr);
		return ((this.solution.IsRoomFree(room.getId(), dayAndTimeSlot)
				|| (conflicting && !this.solution.IsRoomClassFixed(room.getId(), dayAndTimeSlot)))
				&& (roomTimePreference != TimePreference.PROHIBITED
						|| (!conflicting && roomTimePreference != TimePreference.STRONGLY_DISCOURAGED)));
	}

	/**
	 * Check is the instructor is free, then check if the instructor works at
	 * that time and day
	 * 
	 * @param instructor
	 * @param day
	 * @param slotNr
	 * @return
	 */
	private boolean IsInstructorValid(Instructor instructor, int dayAndTimeSlot, boolean conflicting) {
		int slotNr = dayAndTimeSlot % nrOfDays;
		DayOfTheWeek day = DayOfTheWeek.values()[dayAndTimeSlot / nrOfDays];
		TimePreference instructorTimePreference = instructor.getPreferencesByDayAndTime(day, slotNr);
		return ((this.solution.IsInstructorFree(instructor.getId(), dayAndTimeSlot)
				|| (conflicting && !this.solution.IsInstructorClassFixed(instructor.getId(), dayAndTimeSlot))
						&& (instructorTimePreference != TimePreference.PROHIBITED
								|| (!conflicting && instructorTimePreference != TimePreference.STRONGLY_DISCOURAGED))));
	}

	/**
	 * Check if any of the student groups is busy
	 * 
	 * @param stGroupsIds
	 * @param day
	 * @param slotNr
	 * @return
	 */
	private boolean AreStudentGroupsValid(List<Integer> stGroupsIds, int dayAndTimeSlot, boolean conflicting) {
		boolean isStGroupValid = true;
		for (Integer stGroupId : stGroupsIds) {
			if (!this.solution.IsStudentGroupFree(stGroupId, dayAndTimeSlot)
					&& !(conflicting && !this.solution.IsIStudentGroupClassFixed(stGroupId, dayAndTimeSlot))) {
				isStGroupValid = false;
				break;
			}
		}

		return isStGroupValid;
	}

	private List<Integer> GetValidStartTimeslots(boolean[] validTimeSlots, int classLength) {
		List<Integer> validStartTimeSlots = new ArrayList<Integer>();

		// For each day
		for (int day = 0; day < nrOfDays; day++) {
			// No need to iterate till the end of the day, the last possible
			// start time slot is enough
			for (int timeSlot = 0; timeSlot < (nrOfTimeSlotsPerDay - classLength); timeSlot++) {
				// For the length of the class, check if the time slots are free
				boolean isValidStartPos = true;
				for (int k = timeSlot; k < timeSlot + classLength; k++) {
					isValidStartPos &= validTimeSlots[day * nrOfDays + k];
				}
				if (isValidStartPos) {
					// Memorize if the start position as a valid one
					validStartTimeSlots.add(day * nrOfDays + timeSlot);
				}
			}
		}

		return validStartTimeSlots;
	}
}
