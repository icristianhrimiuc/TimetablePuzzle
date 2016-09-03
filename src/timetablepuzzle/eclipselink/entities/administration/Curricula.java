package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;

@Entity
@Table(name="curriculas")
public class Curricula{
	public static enum Term{FIRST,SECOND,THIRD,UNASSIGNED};
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="year")
	@Enumerated(EnumType.STRING)
	private CollegeYear year;
	
	@Column(name="term")
	@Enumerated(EnumType.STRING)
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
	@Override
	public String toString() {
		String yearName = String.format("%s year", this.year);
		String termName = String.format("%s term", this.term.name().toLowerCase());
		return String.format("%s(%s-%s)", this.name, yearName, termName);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Curricula);
		if (equals) {
			Curricula other = (Curricula) o;
			equals = ((this.id == other.getId()) && 
					(this.name.equals(other.getName())) && 
					(this.year.equals(other.getYear())) && 
					(this.term.equals(other.getTerm()))
					);
			
			for(CourseOffering course : other.courses){
				equals &= this.courses.contains(course);
				if(!equals) break;
			}
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Curricula:%s:%s", (Integer.toString(this.id)), this.toString()).hashCode();
	}
}
