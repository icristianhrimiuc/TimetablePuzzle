package timetablepuzzle.eclipselink.entities.administration;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timeslotPatterns")
public class TimeslotPattern {
	public static final int DefaultNrOfDays = 5;
	public static final int DefaultNrOfTimeSlotsPerDay = 12;
	public static final String DefaultTimeSlotsSeparator = ";";
	public static final int UnassignedTimeSlot = -1;
	public static final int FreeTimeSlot = 0;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nrOfdays")
	private int nrOfDays;
	
	@Column(name = "nrOfTimeSlotsPerDay")
	private int nrOfTimeSlotsPerDay;
	
	@Column(name = "timeSlotsSeparator")
	private String timeSlotsSeparator;
	
	public TimeslotPattern()
	{
		this(DefaultNrOfDays, DefaultNrOfTimeSlotsPerDay);
	}
	
	public TimeslotPattern(int nrOfDays, int nrOfTimeSlotsPerDay)
	{
		this(0, DefaultNrOfDays, DefaultNrOfTimeSlotsPerDay, DefaultTimeSlotsSeparator);
	}
	
	public TimeslotPattern(int id, int nrOfDays, int nrOfTimeSlotsPerDay, String timeSlotSeparator)
	{
		this.nrOfDays = nrOfDays;
		this.nrOfTimeSlotsPerDay = nrOfTimeSlotsPerDay;
		this.timeSlotsSeparator = timeSlotSeparator;
	}
	
	/*********************** Getter and Setter ********************/
	public int getId() {
		return id;
	}
	
	public int getNrOfDays() {
		return nrOfDays;
	}

	public int getNrOfTimeSlotsPerDay() {
		return nrOfTimeSlotsPerDay;
	}

	public void setNrOfTimeSlotsPerDay(int nrOfTimeSlotsPerDay) {
		this.nrOfTimeSlotsPerDay = nrOfTimeSlotsPerDay;
	}

	public String getTimeSlotSeparator() {
		return timeSlotsSeparator;
	}
	
	/*********************** Methods that model the class behavior ********************/
	public boolean IsValidDayAndTimeSlot(int dayAndTimeSlot)
	{
		return dayAndTimeSlot < (this.nrOfDays*this.nrOfTimeSlotsPerDay);
	}
	
	public boolean IsValidWeek(String weekAssignments)
	{
		return weekAssignments.split(timeSlotsSeparator).length == this.nrOfDays*this.nrOfTimeSlotsPerDay;
	}

	public String GenerateFreeWeek() {
		String[] freeDay = new String[nrOfDays*nrOfTimeSlotsPerDay];
		Arrays.fill(freeDay, FreeTimeSlot);

		return ConvertToWeekAssignments(freeDay);
	}
	
	public String ConvertToWeekAssignments(String[] classesIds)
	{
		String week = "";
		for(String classId : classesIds)
		{
			week += classId + this.timeSlotsSeparator;
		}
		
		return week;
	}
	
	public String[] ConvertToArrayAssignments(String classesIds)
	{
		return classesIds.split(this.timeSlotsSeparator);
	}
	
	public boolean IsFirstTimeslotOfTheDay(int dayAndTimeSlot){
		return dayAndTimeSlot%this.nrOfTimeSlotsPerDay == 0;
	}
}
