package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.administration.AcademicYearDAO;
import timetablepuzzle.eclipselink.DAO.services.administration.AcademicYearJPADAOService;
import timetablepuzzle.eclipselink.entities.E;

@Entity
@Table(name="academic_years")
public class AcademicYear extends E{
	/***********Static fields*************/
	public static AcademicYearDAO academicYearDAO = new AcademicYearJPADAOService();
	/***********Regular properties*************/
	@Column(name="year_period")
	private String _yearPeriod;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=AcademicSession.class)
	@JoinTable(name="academicyear_academicsessions",
    joinColumns=
         @JoinColumn(name="academicyear_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="academicsession_external_id"))
	private List<AcademicSession> _academicSessions;
	
	/**
	 * Default constructor
	 */
	public AcademicYear()
	{
		this(0, "NoYearPeriod", new ArrayList<AcademicSession>());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param yearPeriod
	 * @param academicSessions
	 */
	public AcademicYear(int externalId,String yearPeriod,
			List<AcademicSession> academicSessions)
	{
		_externalId = externalId;
		set_yearPeriod(yearPeriod);
		_academicSessions = academicSessions;
	}
	
	/********************Getters and setters****************/
	public String get_yearPeriod() {
		return _yearPeriod;
	}

	public void set_yearPeriod(String _yearPeriod) {
		this._yearPeriod = _yearPeriod;
	}

	public List<AcademicSession> get_academicSessions() {
		return _academicSessions;
	}
	
	/*******************Methods that model the class behavior*******************/
}
