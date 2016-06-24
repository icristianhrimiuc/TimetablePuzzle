package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;

import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;

public class StudyDepartment {
	private int _externalId;
	private String _name;
	private List<SubjectArea> _areas;
	private List<CourseOffering> _commonCourses;
	
	public StudyDepartment(int externalId, String name, List<SubjectArea> areas)
	{
		_externalId = externalId;
		set_name(name);
		set_areas(areas);
		_commonCourses = new ArrayList<CourseOffering>();
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

	public List<SubjectArea> get_areas() {
		return _areas;
	}

	public void set_areas(List<SubjectArea> _areas) {
		this._areas = _areas;
	}

	public List<CourseOffering> get_commonCourses() {
		return _commonCourses;
	}
}
