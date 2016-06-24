package timetablepuzzle.eclipselink.entities.administration;

import java.util.List;

import timetablepuzzle.eclipselink.entities.inputdata.StudentGroup;

public class YearOfStudy {
	private int _externalId;
	private String _year;
	private List<StudyDepartment> _departments;
	private StudentGroup _students;
	
	public YearOfStudy(int externalId, String year, 
			List<StudyDepartment> departments, StudentGroup students)
	{
		_externalId = externalId;
		set_year(year);
		set_departments(departments);
		set_students(students);
	}

	public int get_externalId() {
		return _externalId;
	}

	public String get_year() {
		return _year;
	}

	public void set_year(String _year) {
		this._year = _year;
	}

	public List<StudyDepartment> get_departments() {
		return _departments;
	}

	public void set_departments(List<StudyDepartment> _departments) {
		this._departments = _departments;
	}

	public StudentGroup get_students() {
		return _students;
	}

	public void set_students(StudentGroup _students) {
		this._students = _students;
	}
}
