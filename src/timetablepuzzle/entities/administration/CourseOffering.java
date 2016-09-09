package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.InstructorMeetings;
import timetablepuzzle.entities.inputdata.Offering;
import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.entities.inputdata.StudentGroup;

@Entity
@Table(name = "course_offerings")
public class CourseOffering {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "abbreviation")
	private String abbreviation;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "lecture_offering_id", unique = false, nullable = false)
	private Offering lecture;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "individualMeetings_offering_id", unique = false, nullable = false)
	private Offering individualMeetings;

	public CourseOffering() {
		this(0, "NoTitle", "NoAbbreviation", new Offering(), new Offering());
	}

	public CourseOffering(int id, String title, String abbreviation, Offering lecture, Offering individualMeetings) {
		this.id = id;
		setTitle(title);
		setAbbreviation(abbreviation);
		setLecture(lecture);
		setIndividualMeetings(individualMeetings);
	}

	/****************** Getters and Setters ****************/
	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Offering getLecture() {
		return this.lecture;
	}

	public void setLecture(Offering lecture) {
		this.lecture = lecture;
	}

	public Offering getIndividualMeetings() {
		return individualMeetings;
	}

	public void setIndividualMeetings(Offering individualMeetings) {
		this.individualMeetings = individualMeetings;
	}

	/*******************
	 * Methods that model the class behavior
	 *******************/
	public List<Class> getClasses(Term term, StudentGroup parentStudentGroup, String departmentName,
			CollegeYear collegeYear, String subjectAreaName) {
		List<Class> classes = new ArrayList<Class>();
		if (parentStudentGroup != null) {
			classes.add(getLectureClass(term, parentStudentGroup, departmentName, collegeYear, subjectAreaName));
			classes.addAll(getIndividialMeetingsClasses(term, parentStudentGroup, departmentName, collegeYear,
					subjectAreaName));
		}

		return classes;
	}

	private Class getLectureClass(Term term, StudentGroup parentStudentGroup, String departmentName,
			CollegeYear collegeYear, String subjectAreaName) {
		Class lectureClass = new Class();

		List<InstructorMeetings> instructorMeetings = this.lecture.getNrOfMeetingsPerInstructor();
		if (instructorMeetings.size() == 1) {
			Room assignedRoom = instructorMeetings.get(0).getRoom();
			Instructor assignedInstructor = instructorMeetings.get(0).getInstructor();

			// Set the data
			lectureClass.setCourseTitle(this.title);
			lectureClass.setCourseAbbreviation(this.abbreviation);
			lectureClass.setDepartmentName(departmentName);
			lectureClass.setCollegeYear(collegeYear);
			lectureClass.setSubjectAreaName(subjectAreaName);
			lectureClass.setTerm(term);
			lectureClass.setOffering(this.lecture);
			lectureClass.setAssignedRoom(assignedRoom);
			lectureClass.setAssignedInstructor(assignedInstructor);
			lectureClass.setAssignedParentStudentGroup(parentStudentGroup);
		}

		return lectureClass;
	}

	private List<Class> getIndividialMeetingsClasses(Term term, StudentGroup parentStudentGroup, String departmentName,
			CollegeYear collegeYear, String subjectAreaName) {
		List<Class> individualMeetingsClasses = new ArrayList<Class>();
		List<StudentGroup> studentGroups = getIndividualStudentGroups(parentStudentGroup);

		if (this.individualMeetings.getTotalNumberOfMeetings() == studentGroups.size() || this.individualMeetings.getTotalNumberOfMeetings() == 0) {
			int studentGroupIndex = 0;
			List<InstructorMeetings> instructorMeetings = this.individualMeetings.getNrOfMeetingsPerInstructor();
			for (InstructorMeetings meetings : instructorMeetings) {
				int nrOfMeetings = meetings.getNrOfMeetings();
				Room assignedRoom = meetings.getRoom();
				Instructor assignedInstructor = meetings.getInstructor();
				for (int i = 0; i < nrOfMeetings; i++) {
					Class individualMeeting = new Class();

					// Set the data
					individualMeeting.setCourseTitle(this.title);
					individualMeeting.setCourseAbbreviation(this.abbreviation);
					individualMeeting.setDepartmentName(departmentName);
					individualMeeting.setCollegeYear(collegeYear);
					individualMeeting.setSubjectAreaName(subjectAreaName);
					individualMeeting.setTerm(term);
					individualMeeting.setOffering(this.individualMeetings);
					individualMeeting.setAssignedRoom(assignedRoom);
					individualMeeting.setAssignedInstructor(assignedInstructor);
					individualMeeting.setAssignedParentStudentGroup(studentGroups.get(studentGroupIndex));

					// Add to classes
					individualMeetingsClasses.add(individualMeeting);
				}
			}
		}

		return null;
	}

	private List<StudentGroup> getIndividualStudentGroups(StudentGroup parentStudentGroup) {
		List<StudentGroup> studentGroups;
		switch (this.individualMeetings.getType()) {
		case SEMINARY:
			studentGroups = getSeminaryGroups(parentStudentGroup);
			break;
		case LABORATORY:
			studentGroups = getLaboratoryGroups(parentStudentGroup);
			break;
		default:
			studentGroups = new ArrayList<StudentGroup>();
			break;
		}

		return studentGroups;
	}

	private List<StudentGroup> getLaboratoryGroups(StudentGroup parentStudentGroup) {
		List<StudentGroup> seminaryGroups = getSeminaryGroups(parentStudentGroup);
		List<StudentGroup> laboratoryGroups = new ArrayList<StudentGroup>();
		for (StudentGroup seminaryGroup : seminaryGroups) {
			laboratoryGroups.addAll(seminaryGroup.getComposingGroups());
		}

		return laboratoryGroups;
	}

	private List<StudentGroup> getSeminaryGroups(StudentGroup parentStudentGroup) {
		return parentStudentGroup.getComposingGroups();
	}

	/*******************
	 * Overridden methods
	 *******************/
	@Override
	public String toString() {
		return String.format("%s(%s)", this.title, this.abbreviation);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof CourseOffering);
		if (equals) {
			CourseOffering other = (CourseOffering) o;
			equals = ((this.id == other.getId()) && 
					(this.title == other.getTitle()) && 
					(this.abbreviation == other.getAbbreviation()) && 
					(this.lecture.equals(other.getLecture())) && 
					(this.individualMeetings.equals(other.getIndividualMeetings()))
					);
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("CourseOffering:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
