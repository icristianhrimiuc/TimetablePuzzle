package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.InstructorMeetingsDAO;
import timetablepuzzle.entities.administration.InstructorMeetings;

public class InstructorMeetingsJPADAOService extends JPADAO<InstructorMeetings,Integer> implements InstructorMeetingsDAO{
	@Override
	public List<InstructorMeetings> GetAll() {
		TypedQuery<InstructorMeetings> query = entityManager.createQuery("SELECT im FROM InstructorMeetings im", InstructorMeetings.class);
		List<InstructorMeetings> listInstructorMeetings = query.getResultList();
	    if (listInstructorMeetings == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listInstructorMeetings;
	}

	@Override
	public void Update(int instructorMeetingsId, InstructorMeetings newInstructorMeetings) {
		InstructorMeetings existingInstructorMeetings = this.entityManager.find(InstructorMeetings.class, instructorMeetingsId);
		if(existingInstructorMeetings != null)
		{
			this.entityManager.getTransaction().begin();
			existingInstructorMeetings.setInstructor(newInstructorMeetings.getInstructor());
			existingInstructorMeetings.setOffering(newInstructorMeetings.getOffering());
			existingInstructorMeetings.setNrOfMeetings(newInstructorMeetings.getNrOfMeetings());
			this.entityManager.getTransaction().commit();
		}
	}
}
