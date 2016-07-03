package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="departments")
public class Department{
	/***********Regular properties*************/
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	@Column(name="name")
	private String _name;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=YearOfStudy.class)
	@JoinTable(name="department_yearsofstudy",
    joinColumns=
         @JoinColumn(name="department_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="yearofstudy_external_id"))
	private List<YearOfStudy> _yearsOfStudy; 
	
	/**
	 * Default constructor
	 */
	public Department()
	{
		this(0, "NoName", new ArrayList<YearOfStudy>());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param name
	 * @param areas
	 */
	public Department(int externalId, String name, List<YearOfStudy> yearsOfStudy)
	{
		_externalId = externalId;
		set_name(name);
		_yearsOfStudy = yearsOfStudy;
	}
	
	/********************Getters and setters****************/
	
	public int get_externalId() {
		return _externalId;
	}
	
	
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}
	
	public List<YearOfStudy> get_yearsOfStudy()
	{
		return this._yearsOfStudy;
	}
	
	/*******************Methods that model the class behavior*******************/
}
