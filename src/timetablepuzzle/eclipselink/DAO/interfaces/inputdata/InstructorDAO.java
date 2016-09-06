package timetablepuzzle.eclipselink.DAO.interfaces.inputdata;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.inputdata.Instructor;

public interface InstructorDAO extends DAO<Instructor,Integer>{
	public List<Instructor> GetAll();
	public void Update(int instructorId, Instructor newInstructor);
}
