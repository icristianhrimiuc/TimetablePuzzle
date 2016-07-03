package timetablepuzzle.eclipselink.entities.inputdata;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.E;

@Entity
@Table(name="course_offerings")
public class CourseOffering extends E{
	/***********Regular properties*************/	
	@Column(name="abbreviation")
	private String _abbreviation;
	
	@Column(name="title")
	private String _title;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="lecture_offering", unique=true, nullable=false, updatable=false)
	private Offering _lecture;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="individualMeetings_offering", unique=true, nullable=false, updatable=false)
	private Offering _individualMeetings;
	
	/**
	 * Default constructor
	 */
	public CourseOffering()
	{
		this(0,"NoAbbreviation","NoTitle",new Offering(), new Offering());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param abbreviation
	 * @param title
	 * @param lecture
	 * @param individualMeetings
	 */
	public CourseOffering(int externalId, String abbreviation,
			String title, Offering lecture, Offering individualMeetings)
	{
		_externalId = externalId;
		set_abbreviation(abbreviation);
		set_title(title);
		set_lecture(lecture);
		set_individualMeetings(individualMeetings);
	}
	
	/******************Getters and Setters****************/
	public String get_abbreviation() {
		return _abbreviation;
	}

	public void set_abbreviation(String _abbreviation) {
		this._abbreviation = _abbreviation;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public Offering get_lecture() {
		return _lecture;
	}

	public void set_lecture(Offering _lecture) {
		this._lecture = _lecture;
	}

	public Offering get_individualMeetings() {
		return _individualMeetings;
	}
	
	public void set_individualMeetings(Offering individualMeetings)
	{
		this._individualMeetings = individualMeetings;
	}
	/***************Methods that model the class behavior***************/
}
