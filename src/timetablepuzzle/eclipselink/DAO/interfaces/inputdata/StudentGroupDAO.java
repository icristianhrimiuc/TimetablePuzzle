package timetablepuzzle.eclipselink.DAO.interfaces.inputdata;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.inputdata.StudentGroup;

public interface StudentGroupDAO extends DAO<StudentGroup,Integer>{
	public List<StudentGroup> GetAll();
	public void Update(int studentGroupId, StudentGroup newStudentGroup);
}
