package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.CourseOffering;

public interface CourseOfferingDAO extends DAO<CourseOffering,Integer>{
	public List<CourseOffering> GetAll();
	public void Update(int courseOfferingId, CourseOffering newCourseOffering);
}
