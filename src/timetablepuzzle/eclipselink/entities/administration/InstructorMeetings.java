package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.inputdata.Instructor;
import timetablepuzzle.eclipselink.entities.inputdata.Offering;

@Entity
@Table(name="instructor_meeetings")
public class InstructorMeetings{
	/***********Regular properties*************/
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="instructor",updatable = false,insertable = false,referencedColumnName="external_id")
	private Instructor _instructor;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="offering",updatable = false,insertable = false,referencedColumnName="external_id")
	private Offering _offering;
	
	@Column(name="nrOfMeetings")
	private int _nrOfMeetings;
	
	/**
	 * Default constructor
	 */
	public InstructorMeetings()
	{
		this(0,new Instructor(),new Offering(),0);
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param instructor
	 * @param nrOfMeetings
	 */
	public InstructorMeetings(int externalId, Instructor instructor, Offering offering, int nrOfMeetings)
	{
		this._externalId = externalId;
		this.set_instructor(instructor);
		this.set_offering(offering);
		this.set_nrOfMeetings(nrOfMeetings);
	}
	
	/***************Getters and setters****************/
	
	public int get_externalId() {
		return _externalId;
	}
	
	public Instructor get_instructor() {
		return _instructor;
	}

	public void set_instructor(Instructor _instructor) {
		this._instructor = _instructor;
	}
	
	public Offering get_offering()
	{
		return this._offering;
	}
	
	public void set_offering(Offering offering)
	{
		this._offering = offering;
	}

	public int get_nrOfMeetings() {
		return _nrOfMeetings;
	}

	public void set_nrOfMeetings(int _nrOfMeetings) {
		this._nrOfMeetings = _nrOfMeetings;
	}	
}