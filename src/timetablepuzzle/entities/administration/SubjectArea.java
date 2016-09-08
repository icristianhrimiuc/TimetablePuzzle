package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.entities.inputdata.StudentGroup;

@Entity
@Table(name = "subject_areas")
public class SubjectArea {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "abbreviation")
	private String abbreviation;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "firstterm_curricula_id", unique = true, nullable = false)
	private Curricula firstTermCurricula;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "secondterm_curricula_id", unique = true, nullable = false)
	private Curricula secondTermCurricula;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "thirdterm_curricula_id", unique = true, nullable = true)
	private Curricula thirdTermCurricula;

	public SubjectArea() {
		this(0, "NoName", "NoAbbreviation", new Curricula(), new Curricula());
	}

	public SubjectArea(int id, String name, String abbreviation, Curricula firstTermCurricula, Curricula secondTermCurricula) {
		this.id = id;
		setName(name);
		setAbbreviation(abbreviation);
		setCurriculaToStudyByTerm(Term.FIRST, firstTermCurricula);
		setCurriculaToStudyByTerm(Term.SECOND, secondTermCurricula);
	}

	/******************** Getters and setters ****************/
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Curricula getCurriculaToStudyByTerm(Term term) {
		Curricula curriculaToStudy = new Curricula();
		switch (term) {
		case FIRST:
			curriculaToStudy = this.firstTermCurricula;
			break;
		case SECOND:
			curriculaToStudy = this.secondTermCurricula;
			break;
		case THIRD:
			curriculaToStudy = this.thirdTermCurricula;
			break;
		default:
			break;
		}

		return curriculaToStudy;
	}

	public void setCurriculaToStudyByTerm(Term term, Curricula curriculaToStudy) {
		switch (term) {
		case FIRST:
			this.firstTermCurricula = curriculaToStudy;
			break;
		case SECOND:
			this.secondTermCurricula = curriculaToStudy;
			break;
		case THIRD:
			this.thirdTermCurricula = curriculaToStudy;
			break;
		default:
			break;
		}
	}

	/**********************
	 * Methods that model the class behavior
	 ******************/
	public List<Class> getClasses(Term term, StudentGroup parentStudentGroup, String departmentName,
			CollegeYear collegeYear, String subjectAreaName) {
		List<Class> classes = new ArrayList<Class>();
		if (parentStudentGroup != null) {
			Curricula curricula = getCurriculaToStudyByTerm(term);
			if (curricula.getId() != 0) {
				classes = curricula.getClasses(term, parentStudentGroup, departmentName, collegeYear, subjectAreaName);
			}
		}

		return classes;
	}

	/**********************
	 * Overridden methods
	 ******************/
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof SubjectArea);
		if (equals) {
			SubjectArea other = (SubjectArea) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName()))
					&& (this.firstTermCurricula.equals(other.getCurriculaToStudyByTerm(Term.FIRST)))
					&& (this.secondTermCurricula.equals(other.getCurriculaToStudyByTerm(Term.SECOND)))
					&& (this.thirdTermCurricula.equals(other.getCurriculaToStudyByTerm(Term.THIRD))));
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("SubjectArea:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
