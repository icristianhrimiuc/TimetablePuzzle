package timetablepuzzle.entities.other;

import java.util.Arrays;

import javax.persistence.*;

import timetablepuzzle.usecases.solution.TimeslotPattern;

@Entity
@Table(name = "time_preferences")
public class TimePreferences {
	public static enum DayOfTheWeek {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	};

	public static enum TimePreference {
		PROHIBITED, STRONGLY_DISCOURAGED, DISCOURAGED, NEUTRAL, PREFFERED, STRONGLY_PREFFERED, REQUIRED
	};

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "monday", nullable = false)
	private String monPreferences;

	@Column(name = "tuesday", nullable = false)
	private String tuePreferences;

	@Column(name = "wednesday", nullable = false)
	private String wedPreferences;

	@Column(name = "thursday", nullable = false)
	private String thuPreferences;

	@Column(name = "friday", nullable = false)
	private String friPreferences;

	@Transient
	private TimePreference[] monday;

	@Transient
	private TimePreference[] tuesday;

	@Transient
	private TimePreference[] wednesday;

	@Transient
	private TimePreference[] thursday;

	@Transient
	private TimePreference[] friday;

	public TimePreferences() {
		this(0, "NoName", generateSamePreferenceAllDay(TimePreference.NEUTRAL),
				generateSamePreferenceAllDay(TimePreference.NEUTRAL),
				generateSamePreferenceAllDay(TimePreference.NEUTRAL),
				generateSamePreferenceAllDay(TimePreference.NEUTRAL),
				generateSamePreferenceAllDay(TimePreference.NEUTRAL));
	}

	public TimePreferences(int id, String name, String monPreferences, String tuePreferences, String wedPreferences,
			String thuPreferences, String friPreferences) {
		this.id = id;
		this.setName(name);
		setMonPreferences(monPreferences);
		setTuePreferences(tuePreferences);
		setWedPreferences(wedPreferences);
		setThuPreferences(thuPreferences);
		setFriPreferences(friPreferences);
	}

	/********* Getters and setters *********/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMonPreferences() {
		return this.monPreferences;
	}

	public void setMonPreferences(String monPreferences) {
		this.monPreferences = monPreferences;
		this.monday = decodeDayPreferences(monPreferences);
	}

	private void setMonday(TimePreference[] monday) {
		this.monday = monday;
		this.monPreferences = codeDayPreferences(this.monday);
	}

	private void setMondayPreferenceByTime(TimePreference preference, int slotNr) {
		this.monday[slotNr] = preference;
		this.monPreferences = codeDayPreferences(this.monday);
	}

	public String getTuePreferences() {
		return this.tuePreferences;
	}

	public void setTuePreferences(String tuePreferences) {
		this.tuePreferences = tuePreferences;
		this.tuesday = decodeDayPreferences(tuePreferences);
	}

	private void setTuesday(TimePreference[] tuesday) {
		this.tuesday = tuesday;
		this.tuePreferences = codeDayPreferences(this.tuesday);
	}

	private void setTuesdayPreferenceByTime(TimePreference preference, int slotNr) {
		this.tuesday[slotNr] = preference;
		this.tuePreferences = codeDayPreferences(this.tuesday);
	}

	public String getWedPreferences() {
		return this.wedPreferences;
	}

	public void setWedPreferences(String wedPreferences) {
		this.wedPreferences = wedPreferences;
		setPreferencesByDay(DayOfTheWeek.WEDNESDAY, wedPreferences);
	}

	private void setWednesday(TimePreference[] wednesday) {
		this.wednesday = wednesday;
		this.wedPreferences = codeDayPreferences(this.wednesday);
	}

	private void setWednesdayPreferenceByTime(TimePreference preference, int slotNr) {
		this.wednesday[slotNr] = preference;
		this.wedPreferences = codeDayPreferences(this.wednesday);
	}

	public String getThuPreferences() {
		return this.thuPreferences;
	}

	public void setThuPreferences(String thuPreferences) {
		this.thuPreferences = thuPreferences;
		setPreferencesByDay(DayOfTheWeek.THURSDAY, thuPreferences);
	}

	private void setThursday(TimePreference[] thursday) {
		this.thursday = thursday;
		this.thuPreferences = codeDayPreferences(this.thursday);
	}

	private void setThursdayPreferenceByTime(TimePreference preference, int slotNr) {
		this.thursday[slotNr] = preference;
		this.thuPreferences = codeDayPreferences(this.thursday);
	}

	public String getFriPreferences() {
		return this.friPreferences;
	}

	public void setFriPreferences(String friPreferences) {
		this.friPreferences = friPreferences;
		setPreferencesByDay(DayOfTheWeek.FRIDAY, friPreferences);
	}

	private void setFriday(TimePreference[] friday) {
		this.friday = friday;
		this.friPreferences = codeDayPreferences(this.friday);
	}

	private void setFridayPreferenceByTime(TimePreference preference, int slotNr) {
		this.friday[slotNr] = preference;
		this.friPreferences = codeDayPreferences(this.friday);
	}

	/*************** Methods that model the class behavior *******************/
	public TimePreference[] getPreferencesByDay(DayOfTheWeek dayOfTheWeek) {
		TimePreference[] day;
		switch (dayOfTheWeek) {
		case MONDAY:
			day = this.monday;
			break;
		case TUESDAY:
			day = this.tuesday;
			break;
		case WEDNESDAY:
			day = this.wednesday;
			break;
		case THURSDAY:
			day = this.thursday;
			break;
		case FRIDAY:
			day = this.friday;
			break;
		default:
			day = null;
			break;
		}

		return day;
	}

	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, String dayPreferences) {
		setPreferencesByDay(dayOfTheWeek, decodeDayPreferences(dayPreferences));
	}

	private static TimePreference[] decodeDayPreferences(String stringPreferences) {
		String[] stringArray = stringPreferences.split(TimeslotPattern.TimeSlotsSeparator);
		TimePreference[] arrayPreferences = new TimePreference[TimeslotPattern.NrOfTimeSlotsPerDay];
		for (int i = 0; i < stringArray.length; i++) {
			arrayPreferences[i] = getTimePreferenceFromString(stringArray[i]);
		}

		return arrayPreferences;
	}

	private static String codeDayPreferences(TimePreference[] arrayPreferences) {
		String stringPreferences = "";
		for (TimePreference preference : arrayPreferences) {
			stringPreferences += Integer.toString(preference.ordinal());
			stringPreferences += TimeslotPattern.TimeSlotsSeparator;
		}

		return stringPreferences;
	}

	private static TimePreference getTimePreferenceFromString(String timePreference) {
		return TimePreference.values()[Integer.parseInt(timePreference)];
	}

	public void setPreferencesByDay(DayOfTheWeek dayOfTheWeek, TimePreference[] timePreferences) {
		if (timePreferences.length == TimeslotPattern.NrOfTimeSlotsPerDay) {
			switch (dayOfTheWeek) {
			case MONDAY:
				setMonday(timePreferences);
				break;
			case TUESDAY:
				setTuesday(timePreferences);
				break;
			case WEDNESDAY:
				setWednesday(timePreferences);
				break;
			case THURSDAY:
				setThursday(timePreferences);
				break;
			case FRIDAY:
				setFriday(timePreferences);
				break;
			default:
				break;
			}
		}
	}

	public TimePreference getPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, int slotNr) {
		return getPreferencesByDay(dayOfTheWeek)[slotNr];
	}

	public void setPreferencesByDayAndTime(DayOfTheWeek dayOfTheWeek, TimePreference preference, int slotNr) {
		switch (dayOfTheWeek) {
		case MONDAY:
			setMondayPreferenceByTime(preference, slotNr);
			break;
		case TUESDAY:
			setTuesdayPreferenceByTime(preference, slotNr);
			break;
		case WEDNESDAY:
			setWednesdayPreferenceByTime(preference, slotNr);
			break;
		case THURSDAY:
			setThursdayPreferenceByTime(preference, slotNr);
			break;
		case FRIDAY:
			setFridayPreferenceByTime(preference, slotNr);
			break;
		default:
			break;
		}
	}

	public void incrementPreferenceByDayAndTime(DayOfTheWeek dayOfTheWeek, int slotNr) {
		TimePreference currentPreference = getPreferencesByDayAndTime(dayOfTheWeek, slotNr);
		setPreferencesByDayAndTime(dayOfTheWeek, getNextPreference(currentPreference), slotNr);
	}

	public static TimePreference getNextPreference(TimePreference currentPreference) {
		TimePreference nextPreference = TimePreference.NEUTRAL;

		if (currentPreference.ordinal() < (TimePreference.values().length - 1)) {
			nextPreference = TimePreference.values()[currentPreference.ordinal() + 1];
		} else {
			nextPreference = TimePreference.values()[0];
		}

		return nextPreference;
	}

	public static String generateSamePreferenceAllDay(TimePreference timePreference) {
		String[] samePreferenceDay = new String[TimeslotPattern.NrOfTimeSlotsPerDay];
		Arrays.fill(samePreferenceDay, Integer.toString(timePreference.ordinal()));

		return convertToStringAssignments(samePreferenceDay);
	}

	public static String convertToStringAssignments(String[] classesIds) {
		String week = "";
		for (String classId : classesIds) {
			week += classId + TimeslotPattern.TimeSlotsSeparator;
		}

		return week;
	}

	public static String getTimePreferenceNameByIndex(int index) {
		String name = TimePreference.values()[index].name();
		name = name.replace('_', ' ');
		name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

		return name;
	}

	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof TimePreferences);
		if (equals) {
			TimePreferences other = (TimePreferences) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName()))
					&& (this.monPreferences.equals(other.getMonPreferences()))
					&& (this.tuePreferences.equals(other.getTuePreferences()))
					&& (this.wedPreferences.equals(other.getWedPreferences()))
					&& (this.thuPreferences.equals(other.getThuPreferences()))
					&& (this.friPreferences.equals(other.getFriPreferences())));
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("TimePreferences:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
