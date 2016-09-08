package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.inputdata.StudentGroup;

@Entity
@Table(name = "years_of_study")
public class YearOfStudy {
	public static enum CollegeYear {
		FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, UNASSIGNED;

		@Override
		public String toString() {
			String name = this.name();
			name = name.replace('_', ' ');
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

			return name;
		}
	};

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "college_year")
	@Enumerated(EnumType.STRING)
	private CollegeYear collegeYear;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, targetEntity = SubjectArea.class)
	@JoinTable(name = "yearofstudy_subjectAreas", joinColumns = @JoinColumn(name = "yearofstudy_id"), inverseJoinColumns = @JoinColumn(name = "subjectarea_id"))
	private List<SubjectArea> subjectAreas;

	@Transient
	private HashMap<Term, List<CourseOffering>> commonCoursesByTerm;

	public YearOfStudy() {
		this(0, "NoName", CollegeYear.UNASSIGNED, new ArrayList<SubjectArea>());
	}

	public YearOfStudy(int id, String name, CollegeYear collegeYear, List<SubjectArea> subjectAreas) {
		this.id = id;
		setName(name);
		setCollegeYear(collegeYear);
		setSubjectAreas(subjectAreas);
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

	public CollegeYear getCollegeYear() {
		return collegeYear;
	}

	public void setCollegeYear(CollegeYear collegeYear) {
		this.collegeYear = collegeYear;
	}

	public List<SubjectArea> getSubjectAreas() {
		return this.subjectAreas;
	}

	/*******************
	 * Methods that model the class behavior
	 *******************/
	public void setSubjectAreas(List<SubjectArea> areas) {
		this.subjectAreas = areas;
		this.commonCoursesByTerm = new HashMap<Term, List<CourseOffering>>();
		for (Term term : Curricula.Term.values()) {
			this.commonCoursesByTerm.put(term, determineCommonCoursesByTerm(term));
		}
	}

	private List<CourseOffering> determineCommonCoursesByTerm(Term term) {
		HashMap<CourseOffering, Integer> countedCourses = new HashMap<CourseOffering, Integer>();
		for (SubjectArea area : this.subjectAreas) {
			for (CourseOffering course : area.getCurriculaToStudyByTerm(term).getCourses()) {
				incrementNrOfAppearances(countedCourses, course);
			}
		}

		return extractCommonCourses(countedCourses);
	}

	private void incrementNrOfAppearances(HashMap<CourseOffering, Integer> countCourses, CourseOffering course) {
		if (!countCourses.containsKey(course)) {
			countCourses.put(course, 1);
		}
		countCourses.replace(course, countCourses.get(course) + 1);
	}

	private List<CourseOffering> extractCommonCourses(HashMap<CourseOffering, Integer> countedCourses) {
		List<CourseOffering> commonCourses = new ArrayList<CourseOffering>();
		int nrOfSubjectAreas = this.subjectAreas.size();
		for (CourseOffering course : countedCourses.keySet()) {
			if (countedCourses.get(course) == nrOfSubjectAreas) {
				commonCourses.add(course);
			}
		}

		return commonCourses;
	}

	public List<Class> getClasses(Term term, StudentGroup parentStudentGroup, String departmentName,
			CollegeYear collegeYear) {
		List<Class> classes = new ArrayList<Class>();
		if (parentStudentGroup != null) {
			List<StudentGroup> studentGroups = parentStudentGroup.getComposingGroups();
			if (studentGroups.size() == this.subjectAreas.size()) {
				for (StudentGroup studentGroup : studentGroups) {
					SubjectArea subjectArea = getSubjectAreaByName(studentGroup.getCode());
					if (subjectArea != null) {
						classes.addAll(subjectArea.getClasses(term, studentGroup, departmentName, collegeYear,
								subjectArea.getName()));
					}
				}
			}
		}

		return classes;
	}

	private SubjectArea getSubjectAreaByName(String name) {
		SubjectArea searchedSubjectArea = null;
		for (SubjectArea subjectArea : this.subjectAreas) {
			if (subjectArea.getName().toLowerCase().equals(name.toLowerCase())) {
				searchedSubjectArea = subjectArea;
				break;
			}
		}

		return searchedSubjectArea;
	}

	/*******************
	 * Overridden methods
	 *******************/
	@Override
	public String toString() {
		return String.format("College year %s", this.collegeYear.name().toLowerCase());
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof YearOfStudy);
		if (equals) {
			YearOfStudy other = (YearOfStudy) o;
			equals = ((this.id == other.getId()) && (this.collegeYear.equals(other.getCollegeYear())));

			for (SubjectArea subjectArea : other.getSubjectAreas()) {
				equals &= this.subjectAreas.contains(subjectArea);
				if (!equals)
					break;
			}
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("YearOfStudy:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
