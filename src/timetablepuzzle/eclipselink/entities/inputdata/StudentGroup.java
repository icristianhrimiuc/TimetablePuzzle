package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="student_groups")
public class StudentGroup{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(targetEntity=StudentGroup.class)
	@JoinTable(name="studentgroup_studentgroups",
    joinColumns=
         @JoinColumn(name="parent_studentgroup_id"),
    inverseJoinColumns=
         @JoinColumn(name="child_studentgroup_id"))
	private List<StudentGroup> composingGroups;
	
	@Column(name="nrofstudents")
	private int nrOfStudents;

	public StudentGroup()
	{
		this(0,"NoCode","NoName",new ArrayList<StudentGroup>());
	}

	public StudentGroup(int id, String code, String name, List<StudentGroup> composingGroups)
	{
		this.id = id;
		setCode(code);
		setName(name);
		this.composingGroups = composingGroups;
	}

	/*****************Getters and setters**************/
	public int getId() {
		return this.id;
	}
	
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StudentGroup> getComposingGroups() {
		return this.composingGroups;
	}

	public int getNrOfStudents() {
		return this.nrOfStudents;
	}

	public void setNrOfStudents(int nrOfStudents) {
		this.nrOfStudents = nrOfStudents;
	}
	/****************Methods that model class behavior******************/
}
