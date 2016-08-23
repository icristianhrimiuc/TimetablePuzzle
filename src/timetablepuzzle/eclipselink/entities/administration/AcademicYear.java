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
}
