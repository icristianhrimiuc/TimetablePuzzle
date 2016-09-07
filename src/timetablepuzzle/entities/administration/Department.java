package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.inputdata.Class;
import timetablepuzzle.entities.inputdata.StudentGroup;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, targetEntity = YearOfStudy.class)
	@JoinTable(name = "department_yearsofstudy", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "yearofstudy_id"))
	private List<YearOfStudy> yearsOfStudy;

	public Department() {
		this(0, "NoName", new ArrayList<YearOfStudy>());
	}

	public Department(int id, String name, List<YearOfStudy> yearsOfStudy) {
		this.id = id;
		setName(name);
		this.yearsOfStudy = yearsOfStudy;
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

	public List<YearOfStudy> getYearsOfStudy() {
		return this.yearsOfStudy;
	}
	
	public void setYearsOfStudy(List<YearOfStudy> yearsOfStudy){
		this.yearsOfStudy = yearsOfStudy;
	}

	/*******************
	 * Methods that model the class behavior
	 *******************/
	public List<Class> getClasses(Term term, StudentGroup parentStudentGroup, String departmentName) {
		List<Class> classes = new ArrayList<Class>();
		if (parentStudentGroup != null) {
			List<StudentGroup> studentGroups = parentStudentGroup.getComposingGroups();
			if (studentGroups.size() == this.yearsOfStudy.size()) {
				for (StudentGroup studentGroup : studentGroups) {
					YearOfStudy yearOfStudy = getYearOfStudyByCollegeYear(studentGroup.getCode());
					if (yearOfStudy != null) {
						classes.addAll(yearOfStudy.getClasses(term, studentGroup, departmentName,
								yearOfStudy.getCollegeYear()));
					}
				}
			}
		}

		return classes;
	}

	private YearOfStudy getYearOfStudyByCollegeYear(String collegeYear) {
		YearOfStudy searchedYearOfStudy = null;
		for (YearOfStudy yearOfStudy : this.yearsOfStudy) {
			if (yearOfStudy.getCollegeYear().toString().toLowerCase().equals(collegeYear.toLowerCase())) {
				searchedYearOfStudy = yearOfStudy;
				break;
			}
		}

		return searchedYearOfStudy;
	}

	/*******************
	 * Overridden methodes
	 *******************/
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Department);
		if (equals) {
			Department other = (Department) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName())));

			for (YearOfStudy yearOfStudy : other.getYearsOfStudy()) {
				equals &= this.yearsOfStudy.contains(yearOfStudy);
				if (equals)
					break;
			}
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Department:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
