package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.List;

public class StudentGroup {
	private int _externalId;
	private String _code;
	private String _name;
	private List<StudentGroup> _composingGroups;
	private int _nrOfStudents;
	
	/**
	 * Create and initialize a new student group with the given parameters
	 * @param externalId
	 * @param code
	 * @param name
	 */
	public StudentGroup(int externalId, String code, String name, List<StudentGroup> composingGroups)
	{
		_externalId = externalId;
		set_code(code);
		set_name(name);
		set_composingGroups(composingGroups);
	}

	public int get_externalId() {
		return _externalId;
	}

	public String get_code() {
		return _code;
	}

	public void set_code(String _code) {
		this._code = _code;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public List<StudentGroup> get_composingGroups() {
		return _composingGroups;
	}

	public void set_composingGroups(List<StudentGroup> _composingGroups) {
		this._composingGroups = _composingGroups;
	}

	public int get_nrOfStudents() {
		return _nrOfStudents;
	}

	public void set_nrOfStudents(int _nrOfStudents) {
		this._nrOfStudents = _nrOfStudents;
	}	
}
