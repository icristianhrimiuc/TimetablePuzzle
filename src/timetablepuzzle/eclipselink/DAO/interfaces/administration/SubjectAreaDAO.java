package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.SubjectArea;

public interface SubjectAreaDAO extends DAO<SubjectArea,Integer>{
	public List<SubjectArea> GetAll();
	public void Update(int subjectAreaId, SubjectArea newSubjectArea);
}
