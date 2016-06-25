package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.inputdata.CourseOffering;
import timetablepuzzle.eclipselink.entities.inputdata.StudentGroup;

@Entity
@Table(name="years_of_study")
public class YearOfStudy {
	/***********Static fields*************/
	public static enum Year{FIRST,SECOND,THIRD,FOURTH,FIFTH,SIXTH,UNASSIGNED};
	/***********Regular fields*************/
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int _externalId;
	
	@Column(name="year")
	private Year _year;
	
	@OneToMany(targetEntity=SubjectArea.class)
	@JoinTable(name="yearofstudy_subjectAreas",
    joinColumns=
         @JoinColumn(name="yearofstudy_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="subjectrea_external_id"))
	private List<SubjectArea> _areas;
	
	@OneToOne
	@JoinColumn(name="student_group")
	private StudentGroup _students;
	
	@Transient
	private List<CourseOffering> _commonCourses;
	
	/**
	 * Default constructor
	 */
	public YearOfStudy()
	{
		this(0, Year.UNASSIGNED, new ArrayList<SubjectArea>(), new StudentGroup());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param year
	 * @param areas
	 * @param students
	 */
	public YearOfStudy(int externalId, Year year, 
			List<SubjectArea> areas, StudentGroup students)
	{
		_externalId = externalId;
		set_year(year);
		setAreas(areas);
		set_students(students);
	}
	
	/********************Getters and setters****************/
	public int get_externalId() {
		return _externalId;
	}

	public Year get_year() {
		return _year;
	}

	public void set_year(Year _year) {
		this._year = _year;
	}
	
	public List<SubjectArea> get_areas()
	{
		return this._areas;
	}

	public StudentGroup get_students() {
		return _students;
	}

	public void set_students(StudentGroup _students) {
		this._students = _students;
	}
	
	/*******************Methods that model the class behavior*******************/
	/**
	 * Set the subject areas
	 * This also checks for all the common courses
	 * @param areas
	 */
	public void setAreas(List<SubjectArea> areas) {
		this._areas = areas;
		HashMap<CourseOffering, int[]> countCourses = new HashMap<CourseOffering,int[]>();
		for(SubjectArea area : this._areas)
		{
			for(Curricula term : area.get_studyTerms())
			{
				for(CourseOffering course : term.get_courses())
				{
					if(!countCourses.containsKey(course))
					{
						countCourses.put(course, new int[1]);
					}
					// increment the number of appearances for this course
					countCourses.get(course)[0]++;
				}
			}
		}
		
		// Check to see what courses are common
		this._commonCourses = new ArrayList<CourseOffering>();
		int nrOfSubjectAreas = this._areas.size();
		for(CourseOffering course : countCourses.keySet())
		{
			if(countCourses.get(course)[0] == nrOfSubjectAreas)
			{
				this._commonCourses.add(course);
			}
		}
	}
}
