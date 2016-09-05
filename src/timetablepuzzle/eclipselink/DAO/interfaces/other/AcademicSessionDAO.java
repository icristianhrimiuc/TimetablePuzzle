package timetablepuzzle.eclipselink.DAO.interfaces.other;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.other.AcademicSession;

public interface AcademicSessionDAO extends DAO<AcademicSession,Integer> {
	public List<AcademicSession> GetAll();
	public void Update(int academicSessionId, AcademicSession academicSession);
}
