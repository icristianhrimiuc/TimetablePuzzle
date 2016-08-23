package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.administration.SubjectArea.Term;
import timetablepuzzle.eclipselink.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;

@Entity
@Table(name="curriculas")
public class Curricula{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="year")
	private CollegeYear year;
	
	@Column(name="term")
	private Term term;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=CourseOffering.class)
	@JoinTable(name="curriculas_courseOfferings",
    joinColumns=
         @JoinColumn(name="curricula_id"),
    inverseJoinColumns=
         @JoinColumn(name="courseOffering_id"))
	private List<CourseOffering> courses;

	public Curricula()
	{
		this(0,"NoName", CollegeYear.UNASSIGNED, Term.UNASSIGNED, new ArrayList<CourseOffering>());
	}

	public Curricula(int id, String name, CollegeYear year, Term term,  List<CourseOffering> courses)
	{
		this.id = id;
		setName(name);
		setYear(year);
		setTerm(term);
		this.courses = courses;
	}
	
	/********************Getters and setters****************/	
	public int getId() {
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public CollegeYear getYear() {
		return year;
	}

	public void setYear(CollegeYear year) {
		this.year = year;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public List<CourseOffering> getCourses() {
		return courses;
	}
	/*******************Methods that model the class behavior*******************/
}
