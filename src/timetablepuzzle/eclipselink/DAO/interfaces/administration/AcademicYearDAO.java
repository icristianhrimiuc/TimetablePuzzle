package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.AcademicYear;

public interface AcademicYearDAO extends DAO<AcademicYear,Integer>{
	public List<AcademicYear> GetAll();
	public void Update(int academicYearId, AcademicYear academicYear);
}
