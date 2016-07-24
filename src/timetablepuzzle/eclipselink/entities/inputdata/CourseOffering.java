package timetablepuzzle.eclipselink.entities.inputdata;

import javax.persistence.*;

@Entity
@Table(name="course_offerings")
public class CourseOffering{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="abbreviation")
	private String abbreviation;
	
	@Column(name="title")
	private String title;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="lecture_offering_id", unique=true, nullable=false, updatable=false)
	private Offering lecture;
	
	@OneToOne(cascade=CascadeType.ALL,optional=false)
    @JoinColumn(name="individualMeetings_offering_id", unique=true, nullable=false, updatable=false)
	private Offering individualMeetings;

	public CourseOffering()
	{
		this(0,"NoAbbreviation","NoTitle",new Offering(), new Offering());
	}

	public CourseOffering(int id, String abbreviation,
			String title, Offering lecture, Offering individualMeetings)
	{
		this.id = id;
		setAbbreviation(abbreviation);
		setTitle(title);
		setLecture(lecture);
		setIndividualMeetings(individualMeetings);
	}
	
	/******************Getters and Setters****************/
	public int getId() {
		return this.id;
	}
	
	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Offering getLecture() {
		return this.lecture;
	}

	public void setLecture(Offering lecture) {
		this.lecture = lecture;
	}

	public Offering getIndividualMeetings() {
		return individualMeetings;
	}
	
	public void setIndividualMeetings(Offering individualMeetings)
	{
		this.individualMeetings = individualMeetings;
	}
	/***************Methods that model the class behavior***************/
}
