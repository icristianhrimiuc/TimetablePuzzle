package timetablepuzzle.entities.administration;

import javax.persistence.*;

import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.Offering;

@Entity
@Table(name="instructor_meeetings")
public class InstructorMeetings{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nrOfMeetings")
	private int nrOfMeetings;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="instructor_id",updatable = false,insertable = false,referencedColumnName="id")
	private Instructor instructor;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="offering_id",updatable = false,insertable = false,referencedColumnName="id")
	private Offering offering;

	public InstructorMeetings()
	{
		this(0,0,new Instructor(),new Offering());
	}

	public InstructorMeetings(int id, int nrOfMeetings, Instructor instructor, Offering offering)
	{
		this.id = id;
		this.setNrOfMeetings(nrOfMeetings);
		this.setInstructor(instructor);
		this.setOffering(offering);
	}
	
	/***************Getters and setters****************/
	public int getId() {
		return this.id;
	}

	public int getNrOfMeetings() {
		return this.nrOfMeetings;
	}

	public void setNrOfMeetings(int nrOfMeetings) {
		this.nrOfMeetings = nrOfMeetings;
	}
	
	public Instructor getInstructor() {
		return this.instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	
	public Offering getOffering()
	{
		return this.offering;
	}
	
	public void setOffering(Offering offering)
	{
		this.offering = offering;
	}
	
	/*******************Methods that model the class behavior*******************/
	@Override
	public String toString() {
		return String.format("%s %s %d", this.instructor, this.offering, this.nrOfMeetings);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof InstructorMeetings);
		if (equals) {
			InstructorMeetings other = (InstructorMeetings) o;
			equals = ((this.id == other.getId()) && 
					(this.nrOfMeetings == other.getNrOfMeetings()) &&
					(this.instructor.equals(other.getInstructor())) && 
					(this.offering.equals(other.getOffering())) 
					);
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("InstructorMeetings:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}