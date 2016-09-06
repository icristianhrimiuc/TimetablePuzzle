package timetablepuzzle.eclipselink.DAO.JPA.services.inputdata;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.InstructorDAO;
import timetablepuzzle.entities.inputdata.Instructor;

public class InstructorJPADAOService extends JPADAO<Instructor,Integer> implements InstructorDAO{
	@Override
	public List<Instructor> GetAll() {
		TypedQuery<Instructor> query = entityManager.createQuery("SELECT i FROM Instructor i", Instructor.class);
		List<Instructor> listInstructors = query.getResultList();
	    if (listInstructors == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listInstructors;
	}

	@Override
	public void Update(int instructorId, Instructor newInstructor) {
		Instructor existingInstructor = this.entityManager.find(Instructor.class, instructorId);
		if(existingInstructor != null)
		{
			this.entityManager.getTransaction().begin();
			existingInstructor.setFirstName(newInstructor.getFirstName());
			existingInstructor.setLastName(newInstructor.getLastName());
			existingInstructor.setAcademicTitle(newInstructor.getAcademicTitle());
			existingInstructor.setTimePreferences(newInstructor.getTimePreferences());
			this.entityManager.getTransaction().commit();
		}
	}
}
