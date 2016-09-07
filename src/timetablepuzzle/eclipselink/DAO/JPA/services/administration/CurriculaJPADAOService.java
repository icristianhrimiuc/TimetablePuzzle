package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CurriculaDAO;
import timetablepuzzle.entities.administration.Curricula;

public class CurriculaJPADAOService extends JPADAO<Curricula,Integer> implements CurriculaDAO{
	@Override
	public List<Curricula> GetAll(){
		TypedQuery<Curricula> query = entityManager.createQuery("SELECT c FROM Curricula c", Curricula.class);
		List<Curricula> listCurriculas = query.getResultList();
	    if (listCurriculas == null) {
	        System.out.println("No curriculas found.");
	    } 

	    return listCurriculas;
	}

	@Override
	public void Update(int curriculaId, Curricula newCurricula) {
		Curricula existingCurricula = this.entityManager.find(Curricula.class, curriculaId);
		if(existingCurricula != null)
		{
			this.entityManager.getTransaction().begin();
			existingCurricula.setName(newCurricula.getName());
			existingCurricula.setYear(newCurricula.getYear());
			existingCurricula.setTerm(newCurricula.getTerm());
			existingCurricula.setCourses(newCurricula.getCourses());
			this.entityManager.getTransaction().commit();
		}
	}
}
