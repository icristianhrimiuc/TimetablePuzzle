package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.E;

@Entity
@Table(name="student_groups")
public class StudentGroup extends E{	
	@Column(name="code")
	private String _code;
	
	@Column(name="name")
	private String _name;
	
	@OneToMany(targetEntity=StudentGroup.class)
	@JoinTable(name="stGroup_stGroup",
    joinColumns=
         @JoinColumn(name="parent_stGroup_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="child_stGroup_external_id"))
	private List<StudentGroup> _composingGroups;
	
	@Column(name="nrofstudents")
	private int _nrOfStudents;
	
	/**
	 * Default constructor
	 */
	public StudentGroup()
	{
		this(0,"NoCode","NoName",new ArrayList<StudentGroup>());
	}
	
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
		_composingGroups = composingGroups;
	}

	/*****************Getters and setters**************/
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

	public int get_nrOfStudents() {
		return _nrOfStudents;
	}

	public void set_nrOfStudents(int _nrOfStudents) {
		this._nrOfStudents = _nrOfStudents;
	}
}
