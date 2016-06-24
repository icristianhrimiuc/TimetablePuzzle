package timetablepuzzle.eclipselink.entities.administration;

import java.util.Date;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;

public class AcademicSession {

	private int _externalId;
	

	private String _name;
	
	private Date _sessionBegins;
	
	private Date _classesEnd;
	
	private Date _examsBegin;
	
	private Date _sessionEnds;
	
	private Solution _solution;
	
	private boolean _hasSolution;
	
	public AcademicSession()
	{
		this(0, "NoName", new Date(), new Date(), new Date(), new Date(), null);
	}
	
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
}