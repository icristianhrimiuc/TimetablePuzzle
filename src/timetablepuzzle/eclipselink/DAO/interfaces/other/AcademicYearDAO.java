package timetablepuzzle.eclipselink.DAO.interfaces.other;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.other.AcademicYear;

public interface AcademicYearDAO extends DAO<AcademicYear,Integer>{
	public List<AcademicYear> GetAll();
	public void Update(int academicYearId, AcademicYear academicYear);
}
