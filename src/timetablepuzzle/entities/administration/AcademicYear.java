package timetablepuzzle.entities.administration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import timetablepuzzle.entities.inputdata.StudentGroup;

@Entity
@Table(name = "academic_years")
public class AcademicYear {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "parent_studentgroup_id", unique = false, nullable = false)
	private StudentGroup parentStudentGroup;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "first_academicsession_id", unique = false, nullable = false)
	private AcademicSession firstAcademicSession;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "second_academicsession_id", unique = false, nullable = false)
	private AcademicSession secondAcademicSession;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "third_academicsession_id", unique = false, nullable = true)
	private AcademicSession thirdAcademicSession;

	public AcademicYear() {
		this(0, "NoName", new StudentGroup(), new AcademicSession(), new AcademicSession());
	}

	public AcademicYear(int id, String name, StudentGroup parentStudentGroup, AcademicSession firstAcademicSession,
			AcademicSession secondAcademicSession) {
		this.id = id;
		setName(name);
		setParentStudentGroup(parentStudentGroup);
		setFirstAcademicSession(firstAcademicSession);
		setSecondAcademicSession(secondAcademicSession);
		setThirdAcademicSession(null);
	}

	/******************** Getters and setters ****************/
	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StudentGroup getParentStudentGroup() {
		return parentStudentGroup;
	}

	public void setParentStudentGroup(StudentGroup parentStudentGroup) {
		this.parentStudentGroup = parentStudentGroup;
	}

	public AcademicSession getFirstAcademicSession() {
		return firstAcademicSession;
	}

	public void setFirstAcademicSession(AcademicSession firstAcademicSession) {
		this.firstAcademicSession = firstAcademicSession;
	}

	public AcademicSession getSecondAcademicSession() {
		return secondAcademicSession;
	}

	public void setSecondAcademicSession(AcademicSession secondAcademicSession) {
		this.secondAcademicSession = secondAcademicSession;
	}

	public AcademicSession getThirdAcademicSession() {
		return thirdAcademicSession;
	}

	public void setThirdAcademicSession(AcademicSession thirdAcademicSession) {
		this.thirdAcademicSession = thirdAcademicSession;
	}

	/************************
	 * Methods that model the class behavior
	 ***********************/
	public List<AcademicSession> getAcademicSessions() {
		List<AcademicSession> academicSessions = new ArrayList<AcademicSession>();
		academicSessions.add(this.firstAcademicSession);
		academicSessions.add(this.secondAcademicSession);
		if (thirdAcademicSession != null) {
			academicSessions.add(this.thirdAcademicSession);
		}

		return academicSessions;
	}

	/************************
	 * Overridden methods
	 ************************/
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof AcademicYear);
		if (equals) {
			AcademicYear other = (AcademicYear) o;
			equals = ((this.id == other.getId()) && (this.name.equals(other.getName()))
					&& (this.firstAcademicSession.equals(other.getFirstAcademicSession()))
					&& (this.secondAcademicSession.equals(other.getSecondAcademicSession())));
			if (this.thirdAcademicSession != null) {
				equals &= (this.thirdAcademicSession.equals(other.thirdAcademicSession));
			}
		}
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("AcademicYear:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
