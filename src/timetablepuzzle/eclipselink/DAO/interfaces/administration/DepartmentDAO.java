package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.Department;

public interface DepartmentDAO extends DAO<Department,Integer>{
	public List<Department> GetAll();
	public void Update(int departmentId, Department newDepartment);
}
