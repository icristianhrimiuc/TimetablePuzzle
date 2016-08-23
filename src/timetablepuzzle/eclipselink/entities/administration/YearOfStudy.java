package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.administration.SubjectArea.Term;
import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;
import timetablepuzzle.eclipselink.entities.inputdata.StudentGroup;

@Entity
@Table(name="years_of_study")
public class YearOfStudy{
	public static enum CollegeYear{FIRST,SECOND,THIRD,FOURTH,FIFTH,SIXTH,UNASSIGNED};

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@Column(name="college_year")
	private CollegeYear collegeYear;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=SubjectArea.class)
	@JoinTable(name="yearofstudy_subjectAreas",
    joinColumns=
         @JoinColumn(name="yearofstudy_id"),
    inverseJoinColumns=
         @JoinColumn(name="subjectarea_id"))
	private List<SubjectArea> subjectAreas;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="student_group")
	private StudentGroup students;
	
	@Transient
	private HashMap<Term, List<CourseOffering>> commonCoursesByTerm;

	public YearOfStudy()
	{
		this(0, CollegeYear.UNASSIGNED, new ArrayList<SubjectArea>(), new StudentGroup());
	}

	public YearOfStudy(int id, CollegeYear collegeYear, 
			List<SubjectArea> subjectAreas, StudentGroup students)
	{
		this.id = id;
		setCollegeYear(collegeYear);
		setSubjectAreas(subjectAreas);
		setStudents(students);
	}
	
	/********************Getters and setters****************/
	public int getId() {
		return id;
	}
	
	public CollegeYear getCollegeYear() {
		return collegeYear;
	}

	public void setCollegeYear(CollegeYear collegeYear) {
		this.collegeYear = collegeYear;
	}
	
	public List<SubjectArea> getSubjectAreas()
	{
		return this.subjectAreas;
	}

	public StudentGroup getStudents() {
		return students;
	}

	public void setStudents(StudentGroup students) {
		this.students = students;
	}
	
	/*******************Methods that model the class behavior*******************/
	public void setSubjectAreas(List<SubjectArea> areas) {
		this.subjectAreas = areas;
		for(Term term : SubjectArea.Term.values())
		{
			this.commonCoursesByTerm.put(term, determineCommonCoursesByTerm(term));
		}
	}

	private List<CourseOffering> determineCommonCoursesByTerm(Term term) {
		HashMap<CourseOffering, Integer> countedCourses = new HashMap<CourseOffering,Integer>();
		for(SubjectArea area : this.subjectAreas)
		{
			for(CourseOffering course : area.getCurriculaToStudyByTerm(term).getCourses())
			{
				incrementNrOfAppearances(countedCourses, course);
			}
		}
		
		return extractCommonCourses(countedCourses);
	}

	private void incrementNrOfAppearances(HashMap<CourseOffering, Integer> countCourses,
			CourseOffering course) {
		if(!countCourses.containsKey(course))
		{
			countCourses.put(course,1);
		}
		countCourses.replace(course, countCourses.get(course)+1);
	}

	private List<CourseOffering> extractCommonCourses(HashMap<CourseOffering, Integer> countedCourses) {
		List<CourseOffering> commonCourses= new ArrayList<CourseOffering>();
		int nrOfSubjectAreas = this.subjectAreas.size();
		for(CourseOffering course : countedCourses.keySet())
		{
			if(countedCourses.get(course) == nrOfSubjectAreas)
			{
				commonCourses.add(course);
			}
		}
		
		return commonCourses;
	}
}
