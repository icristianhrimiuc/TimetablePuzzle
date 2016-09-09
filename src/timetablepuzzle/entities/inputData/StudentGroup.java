package timetablepuzzle.entities.inputdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "student_groups")
public class StudentGroup {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "nrofstudents")
	private int nrOfStudents;

	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},targetEntity = StudentGroup.class)
	@JoinTable(name = "studentgroup_studentgroups", joinColumns = @JoinColumn(name = "parent_studentgroup_id"), inverseJoinColumns = @JoinColumn(name = "child_studentgroup_id"))
	private List<StudentGroup> composingGroups;

	public StudentGroup() {
		this(0, "NoName", "NoCode", 0);
	}

	public StudentGroup(int id, String name, String code, int nrOfStudents) {
		try {
			this.id = id;
			setName(name);
			setCode(code);
			setNrOfStudents(nrOfStudents);
			setComposingGroups(new ArrayList<StudentGroup>());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public StudentGroup(int id, String name, String code, List<StudentGroup> composingGroups) {
		try {
			this.id = id;
			setName(name);
			setCode(code);
			setComposingGroups(composingGroups);
			if (this.composingGroups.isEmpty()) {
				setNrOfStudents(0);
			} else {
				setNrOfStudents(calculaterOfStudents());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***************** Getters and setters **************/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getNrOfStudents() {
		return this.nrOfStudents;
	}

	public void setNrOfStudents(int nrOfStudents) {
		this.nrOfStudents = nrOfStudents;
	}

	public List<StudentGroup> getComposingGroups() {
		return this.composingGroups;
	}

	public void setComposingGroups(List<StudentGroup> composingGroups) {
		this.composingGroups = composingGroups;
	}

	/**************** Methods that model class behavior ******************/
	public int calculaterOfStudents() {
		int totalNrOFStudents = 0;
		for (StudentGroup composingGroup : this.composingGroups) {
			totalNrOFStudents += composingGroup.getNrOfStudents();
		}

		return totalNrOFStudents;
	}

	public List<StudentGroup> getLeafGroups() {
		List<StudentGroup> leafGroups = new ArrayList<StudentGroup>();
		if ((this.composingGroups == null) || (this.composingGroups.isEmpty())) {
			leafGroups.add(this);
		} else {
			for (StudentGroup composingGroup : this.composingGroups) {
				leafGroups.addAll(composingGroup.getLeafGroups());
			}
		}

		return leafGroups;
	}

	public List<StudentGroup> getAllComposingGroupsHierachically() {
		List<StudentGroup> allComposingGroups = new ArrayList<StudentGroup>();
		allComposingGroups.addAll(this.composingGroups);
		for (StudentGroup composingGroup : this.composingGroups) {
			allComposingGroups.addAll(composingGroup.getAllComposingGroupsHierachically());
		}

		return allComposingGroups;
	}

	public String getAllComposingGroupsHierachicallyAsString() {
		String allComposingGroups = this.toString();
		int size = composingGroups.size();

		if (size > 0) {
			allComposingGroups += "{";
			for (int i = 0; i < this.composingGroups.size(); i++) {
				allComposingGroups += composingGroups.get(i).getAllComposingGroupsHierachicallyAsString();
				if (i < (size - 1)) {
					allComposingGroups += ",";
				}
			}
			allComposingGroups += "}";
		}

		return allComposingGroups;
	}

	/******************** Overridden methods ************************/
	@Override
	public String toString() {
		return String.format("(%s)", this.code);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof StudentGroup);
		if (equals) {
			StudentGroup other = (StudentGroup) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName()))
					&& (this.code.equals(other.getCode())) && (this.nrOfStudents == other.getNrOfStudents()));

			for (StudentGroup studentGroup : other.getComposingGroups()) {
				equals &= this.composingGroups.contains(studentGroup);
				if (!equals)
					break;
			}
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Room:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
