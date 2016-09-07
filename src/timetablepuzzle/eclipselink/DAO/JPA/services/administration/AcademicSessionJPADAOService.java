package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.AcademicSessionDAO;
import timetablepuzzle.entities.administration.AcademicSession;

public class AcademicSessionJPADAOService extends JPADAO<AcademicSession,Integer> implements AcademicSessionDAO{
	@Override
	public List<AcademicSession> GetAll(){
		TypedQuery<AcademicSession> query = entityManager.createQuery("SELECT a FROM AcademicSession a", AcademicSession.class);
		List<AcademicSession> listAcademicSessions = query.getResultList();
	    if (listAcademicSessions == null) {
	        System.out.println("No academicSessions found.");
	    } 

	    return listAcademicSessions;
	}

	@Override
	public void Update(int academicSessionId, AcademicSession newAcademicSession) {
		AcademicSession existingAcademicSession = this.entityManager.find(AcademicSession.class, academicSessionId);
		if(existingAcademicSession != null)
		{
			this.entityManager.getTransaction().begin();
			existingAcademicSession.setName(newAcademicSession.getName());
			existingAcademicSession.setSessionStartDate(newAcademicSession.getSessionStartDate());
			existingAcademicSession.setClassesEndDate(newAcademicSession.getClassesEndDate());
			existingAcademicSession.setExamsStartDate(newAcademicSession.getExamsStartDate());
			existingAcademicSession.setSessionEndDate(newAcademicSession.getSessionEndDate());
			existingAcademicSession.setAcceptedSolution(newAcademicSession.getAcceptedSolution());
			this.entityManager.getTransaction().commit();
		}
	}	
}
