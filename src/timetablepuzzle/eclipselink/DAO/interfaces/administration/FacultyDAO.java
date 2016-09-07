package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.Faculty;

public interface FacultyDAO extends DAO<Faculty,Integer>{
	public List<Faculty> GetAll();
	public void Update(int facultyId, Faculty newFaculty);
}
