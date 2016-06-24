package timetablepuzzle.eclipselink.entities.administration;

import java.util.List;

public class AcademicYear {
	private int _externalId;
	private String _schoolYear;
	private List<AcademicSession> _academicSessions;
	private List<YearOfStudy> _yearsOfStudy;
	
	public AcademicYear(int externalId,String schoolYear,
			List<AcademicSession> academicSessions, List<YearOfStudy> yearsOfStudy)
	{
		_externalId = externalId;
		set_schoolYear(schoolYear);
		set_academicSessions(academicSessions);
		set_yearsOfStudy(yearsOfStudy);
	}

	public int get_externalId() {
		return _externalId;
	}

	public String get_schoolYear() {
		return _schoolYear;
	}

	public void set_schoolYear(String _schoolYear) {
		this._schoolYear = _schoolYear;
	}

	public List<AcademicSession> get_academicSessions() {
		return _academicSessions;
	}

	public void set_academicSessions(List<AcademicSession> _academicSessions) {
		this._academicSessions = _academicSessions;
	}

	public List<YearOfStudy> get_yearsOfStudy() {
		return _yearsOfStudy;
	}

	public void set_yearsOfStudy(List<YearOfStudy> _yearsOfStudy) {
		this._yearsOfStudy = _yearsOfStudy;
	}	
}
