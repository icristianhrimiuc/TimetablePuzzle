package timetablepuzzle.eclipselink.entities.administration;

import java.util.Date;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;

@Entity
@Table(name="academic_sessions")
public class AcademicSession{
	/***********Regular properties*************/
	// Empty abstract class to be inherited by all entities 
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="name")
	private String name;

	// When the session starts, the classes also start
    @Temporal(TemporalType.DATE)
	@Column(name="session_start_date")
	private Date sessionStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="classes_end_date")
	private Date classesEndDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="exams_start_date")
	private Date examsStartDate;
	
	// When the session ends, the exams also end
	@Temporal(TemporalType.DATE)
	@Column(name="session_end_date")
	private Date sessionEndDate;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="accepted_solution_id", unique=true)
	private Solution acceptedSolution;
	
	public AcademicSession()
	{
		this(0, "NoName", new Date(), new Date(), new Date(), new Date(), new Solution());
	}
	
	public AcademicSession(int id, String name,
			Date sessionStartDate, Date classesEndDate, Date examsStartDate, Date sessionEndDate,
			Solution acceptedSolution)
	{
		this.id = id;
		setName(name);
		setSessionStartDate(sessionStartDate);
		setClassesEndDate(classesEndDate);
		setExamsStartDate(examsStartDate);
		setSessionEndDate(sessionEndDate);
		setAcceptedSolution(acceptedSolution);
	}
	
	/***********************Getters and setters*******************/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSessionStartDate() {
		return sessionStartDate;
	}

	public void setSessionStartDate(Date sessionStartDate) {
		this.sessionStartDate = sessionStartDate;
	}

	public Date getClassesEndDate() {
		return classesEndDate;
	}

	public void setClassesEndDate(Date classesEndDate) {
		this.classesEndDate = classesEndDate;
	}

	public Date getExamsStartDate() {
		return examsStartDate;
	}

	public void setExamsStartDate(Date examsStartDate) {
		this.examsStartDate = examsStartDate;
	}

	public Date getSessionEndDate() {
		return sessionEndDate;
	}

	public void setSessionEndDate(Date sessionEndDate) {
		this.sessionEndDate = sessionEndDate;
	}

	public Solution getAcceptedSolution() {
		return acceptedSolution;
	}

	public void setAcceptedSolution(Solution acceptedSolution) {
		this.acceptedSolution = acceptedSolution;
	}

	public boolean hasSolution() {
		return this.acceptedSolution != null;
	}
	
	/*******************Methods that model the class behavior*******************/
}