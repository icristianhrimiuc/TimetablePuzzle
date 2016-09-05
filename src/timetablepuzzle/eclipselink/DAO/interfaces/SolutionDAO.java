package timetablepuzzle.eclipselink.DAO.interfaces;

import java.util.List;

import timetablepuzzle.entities.Solution;

public interface SolutionDAO extends DAO<Solution,Integer>{
	public List<Solution> GetAll();
	public void Update(int solutionId, Solution solution);
}
