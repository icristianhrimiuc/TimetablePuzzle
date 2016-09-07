package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.YearOfStudy;

public interface YearOfStudyDAO extends DAO<YearOfStudy,Integer>{
	public List<YearOfStudy> GetAll();
	public void Update(int yearOfStudyId, YearOfStudy newYearOfStudy);
}
