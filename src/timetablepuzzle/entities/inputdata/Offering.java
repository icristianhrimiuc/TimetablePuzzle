package timetablepuzzle.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "offerings")
public class Offering {
	public static enum OfferingType {
		LECTURE, SEMINARY, LABORATORY, GYM, UNASSIGNED;

		@Override
		public String toString() {
			String name = this.name();
			name = name.replace('_', ' ');
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

			return name;
		}
	};

	public static enum DatePattern {
		FULL_TERM, EVEN_WEEKS, ODD_WEEKS, FIRST_HALF, SECOND_HALF;
		
		@Override
		public String toString() {
			String name = this.name();
			name = name.replace('_', ' ');
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

			return name;
		}
	};

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "nroftimeslots")
	private int nrOfTimeSlots;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private OfferingType type;

	@JoinColumn(name = "date_pattern", nullable = false, updatable = false)
	@Column(name = "date_pattern", nullable = false)
	@Enumerated(EnumType.STRING)
	private DatePattern datePattern;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = InstructorMeetings.class)
	@JoinTable(name = "offering_instructormeetings", joinColumns = @JoinColumn(name = "offering_id"), inverseJoinColumns = @JoinColumn(name = "instructormeetings_id"))
	private List<InstructorMeetings> nrOfMeetingsPerInstructor;

	public Offering() {
		this(0, "NoName", 0, OfferingType.UNASSIGNED, DatePattern.FULL_TERM, new ArrayList<InstructorMeetings>());
	}

	public Offering(int id, String name, int nrOfTimeSlots, OfferingType type, DatePattern datePattern,
			List<InstructorMeetings> nrOfMeetingsPerInstructor) {
		this.id = id;
		setName(name);
		setNrOfTimeSlots(nrOfTimeSlots);
		setType(type);
		setDatePattern(datePattern);
		setNrOfMeetingsPerInstructor(nrOfMeetingsPerInstructor);
	}

	/****************** Getters and Setters ****************/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNrOfTimeSlots() {
		return this.nrOfTimeSlots;
	}

	public void setNrOfTimeSlots(int nrOfTimeSlots) {
		this.nrOfTimeSlots = nrOfTimeSlots;
	}

	public OfferingType getType() {
		return this.type;
	}

	public void setType(OfferingType type) {
		this.type = type;
	}

	public DatePattern getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(DatePattern datePattern) {
		this.datePattern = datePattern;
	}

	public List<InstructorMeetings> getNrOfMeetingsPerInstructor() {
		return nrOfMeetingsPerInstructor;
	}

	public void setNrOfMeetingsPerInstructor(List<InstructorMeetings> nrOfMeetingsPerInstructor) {
		this.nrOfMeetingsPerInstructor = nrOfMeetingsPerInstructor;
	}

	/************************
	 * Methods that model the class behavior
	 ********************/
	public List<Instructor> getInstructors() {
		List<Instructor> instructors = new ArrayList<Instructor>();
		for (InstructorMeetings instrMeeting : this.nrOfMeetingsPerInstructor) {
			instructors.add(instrMeeting.getInstructor());
		}

		return instructors;
	}

	public int getTotalNumberOfMeetings() {
		int totalNrOfMeetings = 0;
		for (InstructorMeetings nrOfMeetingsPerInstructor : this.getNrOfMeetingsPerInstructor()) {
			totalNrOfMeetings += nrOfMeetingsPerInstructor.getNrOfMeetings();
		}

		return totalNrOfMeetings;
	}

	/**********************
	 * Overridden methods
	 ********************/
	@Override
	public String toString() {
		return String.format("%s (%s-%s)", this.name, this.type.name().toLowerCase(), this.datePattern.name().toLowerCase());
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Offering);
		if (equals) {
			Offering other = (Offering) o;
			equals = ((this.id == other.getId()) && 
					(this.name == other.getName()) && 
					(this.nrOfTimeSlots == other.getNrOfTimeSlots()) && 
					(this.type.equals(other.getType())) && 
					(this.datePattern.equals(other.getDatePattern()))
					);
			
			for (InstructorMeetings nrOfMeetingsPerInstructor : other.getNrOfMeetingsPerInstructor()) {
				equals &= this.nrOfMeetingsPerInstructor.contains(nrOfMeetingsPerInstructor);
				if (!equals)
					break;
			}
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Offering:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
