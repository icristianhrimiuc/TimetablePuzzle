package timetablepuzzle.eclipselink.entities.administration;

import java.util.List;

import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;

public class Curricula {
	private int _externalId;
	private List<CourseOffering> _courses;
	
	public Curricula(int externalId, List<CourseOffering> courses)
	{
		_externalId = externalId;
		set_courses(courses);
	}

	public int get_externalId() {
		return _externalId;
	}

	public List<CourseOffering> get_courses() {
		return _courses;
	}

	public void set_courses(List<CourseOffering> _courses) {
		this._courses = _courses;
	}
}
