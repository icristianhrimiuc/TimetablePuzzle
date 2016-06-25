package timetablepuzzle.eclipselink.entities.administration;

import java.util.Date;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;

@Entity
@Table(name="academic_sessions")
public class AcademicSession {
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int _externalId;	

	@Column(name="name")
	private String _name;

    @Temporal(TemporalType.DATE)
	@Column(name="session_begins")
	private Date _sessionBegins;
	
	@Temporal(TemporalType.DATE)
	@Column(name="classes_end")
	private Date _classesEnd;
	
	@Temporal(TemporalType.DATE)
	@Column(name="exams_begin")
	private Date _examsBegin;
	
	@Temporal(TemporalType.DATE)
	@Column(name="session_ends")
	private Date _sessionEnds;
	
	@OneToOne
	@JoinColumn(name="solution", unique=true)
	private Solution _solution;
	
	@Transient
	private boolean _hasSolution;
	
	/**
	 * Default constructor
	 */
	public AcademicSession()
	{
		this(0, "NoName", new Date(), new Date(), new Date(), new Date(), null);
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param name
	 * @param sessionBegins
	 * @param classesEnd
	 * @param examsBegin
	 * @param sessionEnds
	 * @param solution
	 */
	public AcademicSession(int externalId, String name,
			Date sessionBegins, Date classesEnd, Date examsBegin, Date sessionEnds,
			Solution solution)
	{
		_externalId = externalId;
		set_name(name);
		set_sessionBegins(sessionBegins);
		set_classesEnd(classesEnd);
		set_examsBegin(examsBegin);
		set_sessionEnds(sessionEnds);
		set_solution(solution);
	}
	
	/***********************Getters and setters*******************/
	public int get_externalId() {
		return _externalId;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public Date get_sessionBegins() {
		return _sessionBegins;
	}

	public void set_sessionBegins(Date _sessionBegins) {
		this._sessionBegins = _sessionBegins;
	}

	public Date get_classesEnd() {
		return _classesEnd;
	}

	public void set_classesEnd(Date _classesEnd) {
		this._classesEnd = _classesEnd;
	}

	public Date get_examsBegin()
	{
		return _examsBegin;
	}

	public void set_examsBegin(Date _examsBegin) {
		this._examsBegin = _examsBegin;
	}

	public Date get_sessionEnds() {
		return _sessionEnds;
	}

	public void set_sessionEnds(Date _sessionEnds) {
		this._sessionEnds = _sessionEnds;
	}
	
	public Solution get_solution() {
		return _solution;
	}

	public void set_solution(Solution solution) {
		
		if(solution != null)
		{
			this._hasSolution = true;
		}else{
			this._hasSolution = false;
		}
		this._solution = solution;
	}

	public boolean get_hasSolution() {
		return _hasSolution;
	}
	
	/*******************Methods that model the class behavior*******************/
}