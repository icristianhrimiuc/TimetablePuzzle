package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.inputdata.StudentGroup;

@Entity
@Table(name = "faculties")
public class Faculty {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, targetEntity = AcademicYear.class)
	@JoinTable(name = "faculty_academicyears", joinColumns = @JoinColumn(name = "faculty_id"), inverseJoinColumns = @JoinColumn(name = "academicyear_id"))
	private List<AcademicYear> academicYears;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, targetEntity = Department.class)
	@JoinTable(name = "faculty_departments", joinColumns = @JoinColumn(name = "faculty_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
	private List<Department> departments;

	public Faculty() {
		this(0, "NoName", new ArrayList<AcademicYear>(), new ArrayList<Department>());
	}

	public Faculty(int id, String name, List<AcademicYear> academicYears, List<Department> departments) {
		this.id = id;
		setName(name);
		setAcademicYears(academicYears);
		setDepartments(departments);
	}

	/******************** Getters and setters ****************/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AcademicYear> getAcademicYears() {
		return academicYears;
	}

	public void setAcademicYears(List<AcademicYear> academicYears) {
		this.academicYears = academicYears;
	}

	public List<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(List<Department> departments) {

	}

	/*******************
	 * Methods that model the class behavior
	 *******************/
	public void addAcademicYear(AcademicYear acdemicYear) {
		this.academicYears.add(acdemicYear);
	}

	public void addDepartment(Department department) {
		this.departments.add(department);
	}

	public List<Class> getClasses(Term term, StudentGroup parentStudentGroup) {
		List<Class> classes = new ArrayList<Class>();
		if (parentStudentGroup != null) {
			List<StudentGroup> studentGroups = parentStudentGroup.getComposingGroups();
			if (studentGroups.size() == this.departments.size()) {
				for (StudentGroup studentGroup : studentGroups) {
					Department department = getDepartmentByName(studentGroup.getCode());
					if (department != null) {
						classes.addAll(department.getClasses(term, studentGroup, department.getName()));
					}
				}
			}

		}

		return classes;
	}

	private Department getDepartmentByName(String name) {
		Department searchedDepartment = null;
		for (Department department : this.departments) {
			if (department.getName().toLowerCase().equals(name.toLowerCase())) {
				searchedDepartment = department;
				break;
			}
		}

		return searchedDepartment;
	}

	/*******************
	 * Overridden methods
	 *******************/
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Faculty);
		if (equals) {
			Faculty other = (Faculty) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName())));

			for (AcademicYear academicYear : other.getAcademicYears()) {
				equals &= this.academicYears.contains(academicYear);
				if (equals)
					break;
			}

			for (Department department : other.getDepartments()) {
				equals &= this.departments.contains(department);
				if (equals)
					break;
			}
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Faculty:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
