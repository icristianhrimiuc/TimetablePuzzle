package timetablepuzzle.usecases.solution;

import java.util.Arrays;

public final class TimeslotPattern {
	// Number of teaching days per week
	public static final int NrOfDays = 5;
	// Number of teaching time slots per day
	public static final int NrOfTimeSlotsPerDay = 12;
	public static final String TimeSlotsSeparator = ";";
	public static final int UnassignedTimeSlot = -1;
	public static final int FreeTimeSlot = 0;
	
	/*********************** Methods that model the class behavior ********************/
	public static boolean IsValidDayAndTimeSlot(int dayAndTimeSlot)
	{
		return dayAndTimeSlot < (NrOfDays*NrOfTimeSlotsPerDay);
	}
	
	public static boolean IsValidWeek(String weekAssignments)
	{
		return weekAssignments.split(TimeSlotsSeparator).length == NrOfDays*NrOfTimeSlotsPerDay;
	}

	public static String GenerateFreeWeek() {
		String[] freeWeek = new String[NrOfDays*NrOfTimeSlotsPerDay];
		Arrays.fill(freeWeek, Integer.toString(UnassignedTimeSlot));

		return ConvertToStringAssignments(freeWeek);
	}
	
	public static String GenerateFreeDay() {
		String[] freeDay = new String[NrOfTimeSlotsPerDay];
		Arrays.fill(freeDay, Integer.toString(FreeTimeSlot));

		return ConvertToStringAssignments(freeDay);
	}
	
	public static String ConvertToStringAssignments(String[] classesIds)
	{
		String week = "";
		for(String classId : classesIds)
		{
			week += classId + TimeSlotsSeparator;
		}
		
		return week;
	}
	
	public static String[] ConvertToArrayAssignments(String classesIds)
	{
		return classesIds.split(TimeSlotsSeparator);
	}
	
	public static boolean IsFirstTimeslotOfTheDay(int dayAndTimeSlot){
		return dayAndTimeSlot%NrOfTimeSlotsPerDay == 0;
	}
}
