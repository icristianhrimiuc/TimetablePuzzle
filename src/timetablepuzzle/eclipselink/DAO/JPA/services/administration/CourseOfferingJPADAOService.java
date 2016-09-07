package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CourseOfferingDAO;
import timetablepuzzle.entities.administration.CourseOffering;

public class CourseOfferingJPADAOService extends JPADAO<CourseOffering,Integer> implements CourseOfferingDAO{
	@Override
	public List<CourseOffering> GetAll(){
		TypedQuery<CourseOffering> query = entityManager.createQuery("SELECT c FROM CourseOffering c", CourseOffering.class);
		List<CourseOffering> listCourseOfferings = query.getResultList();
	    if (listCourseOfferings == null) {
	        System.out.println("No courseOfferings found.");
	    } 

	    return listCourseOfferings;
	}

	@Override
	public void Update(int courseOfferingId, CourseOffering newCourseOffering) {
		CourseOffering existingCourseOffering = this.entityManager.find(CourseOffering.class, courseOfferingId);
		if(existingCourseOffering != null)
		{
			this.entityManager.getTransaction().begin();
			existingCourseOffering.setTitle(newCourseOffering.getTitle());
			existingCourseOffering.setAbbreviation(newCourseOffering.getAbbreviation());
			existingCourseOffering.setLecture(newCourseOffering.getLecture());
			existingCourseOffering.setIndividualMeetings(newCourseOffering.getIndividualMeetings());
			this.entityManager.getTransaction().commit();
		}
	}	
}
