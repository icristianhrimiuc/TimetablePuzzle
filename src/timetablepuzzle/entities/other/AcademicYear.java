package timetablepuzzle.entities.other;

import javax.persistence.*;

@Entity
@Table(name = "academic_years")
public class AcademicYear {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "first_academicsession_id", unique = false, nullable = false)
	private AcademicSession firstAcademicSession;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "second_academicsession_id", unique = false, nullable = false)
	private AcademicSession secondAcademicSession;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "third_academicsession_id", unique = false, nullable = true)
	private AcademicSession thirdAcademicSession;

	public AcademicYear() {
		this(0, "NoName", new AcademicSession(), new AcademicSession());
	}

	public AcademicYear(int id, String name, AcademicSession firstAcademicSession,
			AcademicSession secondAcademicSession) {
		this.id = id;
		this.name = name;
		this.firstAcademicSession = firstAcademicSession;
		this.secondAcademicSession = secondAcademicSession;
	}

	/******************** Getters and setters ****************/
	public int getId(){
		return this.id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	/******************** Methods that model the class behavior*******************/
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof AcademicYear);
		if (equals) {
			AcademicYear other = (AcademicYear) o;
			equals = (
					(this.id == other.getId()) && 
					(this.name.equals(other.getName())) &&
					(this.firstAcademicSession.equals(other.getFirstAcademicSession())) &&
					(this.secondAcademicSession.equals(other.getSecondAcademicSession())) &&
					(this.thirdAcademicSession.equals(other.thirdAcademicSession))
					);
		}
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("AcademicYear:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
