package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="academic_years")
public class AcademicYear{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="year_period")
	private String yearPeriod;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=AcademicSession.class)
	@JoinTable(name="academicyear_academicsessions",
    joinColumns=
         @JoinColumn(name="academicyear_id"),
    inverseJoinColumns=
         @JoinColumn(name="academicsession_id"))
	private List<AcademicSession> academicSessions;
	
	public AcademicYear()
	{
		this(0, "NoYearPeriod", new ArrayList<AcademicSession>());
	}
	
	public AcademicYear(int id,String yearPeriod,
			List<AcademicSession> academicSessions)
	{
		this.id = id;
		setYearPeriod(yearPeriod);
		this.academicSessions = academicSessions;
	}
	
	/********************Getters and setters****************/
	public int getId() {
		return this.id;
	}
	
	public String getYearPeriod() {
		return yearPeriod;
	}

	public void setYearPeriod(String yearPeriod) {
		this.yearPeriod = yearPeriod;
	}

	public List<AcademicSession> getAcademicSessions() {
		return academicSessions;
	}
	
	/*******************Methods that model the class behavior*******************/
	@Override
	public String toString() {
		return String.format("Academic year %s", this.yearPeriod);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof AcademicYear);
		if (equals) {
			AcademicYear other = (AcademicYear) o;
			equals = (
					(this.id == other.getId()) && 
					(this.yearPeriod.equals(other.getYearPeriod()))
					);
			for(AcademicSession academicSession : other.getAcademicSessions()){
				equals &= this.academicSessions.contains(academicSession);
				if(!equals)break;
			}
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("AcademicYear:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
