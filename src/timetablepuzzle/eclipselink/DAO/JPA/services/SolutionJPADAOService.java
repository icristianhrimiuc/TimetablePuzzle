package timetablepuzzle.eclipselink.DAO.JPA.services;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.interfaces.SolutionDAO;
import timetablepuzzle.entities.Solution;

public class SolutionJPADAOService extends JPADAO<Solution,Integer> implements SolutionDAO{
	@Override
	public List<Solution> GetAll(){
		TypedQuery<Solution> query = entityManager.createQuery("SELECT s FROM Solution s", Solution.class);
		List<Solution> listSolutions = query.getResultList();
	    if (listSolutions == null) {
	        System.out.println("No solutions found.");
	    } 

	    return listSolutions;
	}

	@Override
	public void Update(int solutionId, Solution newSolution) {
		Solution existingSolution = this.entityManager.find(Solution.class, solutionId);
		if(existingSolution != null)
		{
			this.entityManager.getTransaction().begin();
			existingSolution.setName(newSolution.getName());
			this.entityManager.getTransaction().commit();
		}
	}
}
