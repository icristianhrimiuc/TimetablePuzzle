package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="departments")
public class Department{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=YearOfStudy.class)
	@JoinTable(name="department_yearsofstudy",
    joinColumns=
         @JoinColumn(name="department_id"),
    inverseJoinColumns=
         @JoinColumn(name="yearofstudy_id"))
	private List<YearOfStudy> yearsOfStudy; 

	public Department()
	{
		this(0, "NoName", new ArrayList<YearOfStudy>());
	}

	public Department(int id, String name, List<YearOfStudy> yearsOfStudy)
	{
		this.id = id;
		setName(name);
		this.yearsOfStudy = yearsOfStudy;
	}
	
	/********************Getters and setters****************/
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<YearOfStudy> getYearsOfStudy()
	{
		return this.yearsOfStudy;
	}
	
	/*******************Methods that model the class behavior*******************/
}
