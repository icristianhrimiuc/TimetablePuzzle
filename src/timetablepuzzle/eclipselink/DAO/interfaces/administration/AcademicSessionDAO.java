package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.AcademicSession;

public interface AcademicSessionDAO extends DAO<AcademicSession,Integer> {
	public List<AcademicSession> GetAll();
	public void Update(int academicSessionId, AcademicSession academicSession);
}
