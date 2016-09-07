package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.SubjectAreaDAO;
import timetablepuzzle.entities.administration.SubjectArea;
import timetablepuzzle.entities.administration.Curricula.Term;

public class SubjectAreaJPADAOService extends JPADAO<SubjectArea,Integer> implements SubjectAreaDAO{
	@Override
	public List<SubjectArea> GetAll(){
		TypedQuery<SubjectArea> query = entityManager.createQuery("SELECT s FROM SubjectArea s", SubjectArea.class);
		List<SubjectArea> listSubjectAreas = query.getResultList();
	    if (listSubjectAreas == null) {
	        System.out.println("No subjectAreas found.");
	    } 

	    return listSubjectAreas;
	}

	@Override
	public void Update(int subjectAreaId, SubjectArea newSubjectArea) {
		SubjectArea existingSubjectArea = this.entityManager.find(SubjectArea.class, subjectAreaId);
		if(existingSubjectArea != null)
		{
			this.entityManager.getTransaction().begin();
			existingSubjectArea.setName(newSubjectArea.getName());
			existingSubjectArea.setCurriculaToStudyByTerm(Term.FIRST, newSubjectArea.getCurriculaToStudyByTerm(Term.FIRST));
			existingSubjectArea.setCurriculaToStudyByTerm(Term.SECOND, newSubjectArea.getCurriculaToStudyByTerm(Term.SECOND));
			existingSubjectArea.setCurriculaToStudyByTerm(Term.THIRD, newSubjectArea.getCurriculaToStudyByTerm(Term.THIRD));
			this.entityManager.getTransaction().commit();
		}
	}
}
