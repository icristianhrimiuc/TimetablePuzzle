package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="faculties")
public class Faculty{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=Department.class)
	@JoinTable(name="faculty_departments",
    joinColumns=
         @JoinColumn(name="faculty_id"),
    inverseJoinColumns=
         @JoinColumn(name="department_id"))
	private List<Department> departments;

	public Faculty()
	{
		this(0,"NoName", new ArrayList<Department>());
	}

	public Faculty(int id, String name, List<Department> departments)
	{
		this.id = id;
		setName(name);
		this.departments = departments;
	}
	
	/********************Getters and setters****************/
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartments() {
		return this.departments;
	}
	
	/*******************Methods that model the class behavior*******************/
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Faculty);
		if (equals) {
			Faculty other = (Faculty) o;
			equals = ((this.id == other.getId()) && 
					(this.name.equals(other.getName()))
					);
			
			for(Department department : other.getDepartments()){
				equals &= this.departments.contains(department);
				if(equals)break;
			}
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Faculty:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
