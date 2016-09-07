package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.Curricula;

public interface CurriculaDAO extends DAO<Curricula,Integer>{
	public List<Curricula> GetAll();
	public void Update(int curriculaId, Curricula newCurricula);
}
