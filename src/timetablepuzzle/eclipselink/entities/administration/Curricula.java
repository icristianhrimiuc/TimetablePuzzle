package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.administration.CurriculaDAO;
import timetablepuzzle.eclipselink.DAO.services.administration.CurriculaJPADAOService;
import timetablepuzzle.eclipselink.entities.E;
import timetablepuzzle.eclipselink.entities.administration.SubjectArea.Term;
import timetablepuzzle.eclipselink.entities.administration.YearOfStudy.Year;
import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;

@Entity
@Table(name="curriculas")
public class Curricula extends E{
	/***********Static fields*************/
	public static CurriculaDAO curriculaDAO = new CurriculaJPADAOService();
	/***********Regular properties*************/
	@Column(name="name")
	private String _name;
	
	@Column(name="year")
	private Year _year;
	
	@Column(name="term")
	private Term _term;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=CourseOffering.class)
	@JoinTable(name="curriculas_courseOfferings",
    joinColumns=
         @JoinColumn(name="curricula_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="courseOffering_external_id"))
	private List<CourseOffering> _courses;
	
	/**
	 * Default constructor
	 */
	public Curricula()
	{
		this(0,"NoName", Year.UNASSIGNED, Term.UNASSIGNED, new ArrayList<CourseOffering>());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param courses
	 */
	public Curricula(int externalId, String name, Year year, Term term,  List<CourseOffering> courses)
	{
		_externalId = externalId;
		set_name(name);
		set_year(year);
		set_term(term);
		_courses = courses;
	}
	
	/********************Getters and setters****************/	
	public String get_name()
	{
		return this._name;
	}
	
	public void set_name(String name)
	{
		this._name = name;
	}

	public Year get_year() {
		return _year;
	}

	public void set_year(Year _year) {
		this._year = _year;
	}

	public Term get_term() {
		return _term;
	}

	public void set_term(Term _term) {
		this._term = _term;
	}

	public List<CourseOffering> get_courses() {
		return _courses;
	}
	/*******************Methods that model the class behavior*******************/
}
