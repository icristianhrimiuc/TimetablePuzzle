package timetablepuzzle.eclipselink.DAO.JPA.services.inputdata;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.StudentGroupDAO;
import timetablepuzzle.entities.inputdata.StudentGroup;

public class StudentGroupJPADAOService extends JPADAO<StudentGroup,Integer> implements StudentGroupDAO{
	@Override
	public List<StudentGroup> GetAll() {
		TypedQuery<StudentGroup> query = entityManager.createQuery("SELECT s FROM StudentGroup s", StudentGroup.class);
		List<StudentGroup> listStudentGroups = query.getResultList();
	    if (listStudentGroups == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listStudentGroups;
	}

	@Override
	public void Update(int studentGroupId, StudentGroup newStudentGroup) {
		StudentGroup existingStudentGroup = this.entityManager.find(StudentGroup.class, studentGroupId);
		if(existingStudentGroup != null)
		{
			this.entityManager.getTransaction().begin();
			existingStudentGroup.setName(newStudentGroup.getName());
			existingStudentGroup.setCode(newStudentGroup.getCode());
			existingStudentGroup.setNrOfStudents(newStudentGroup.getNrOfStudents());
			existingStudentGroup.setComposingGroups(newStudentGroup.getComposingGroups());
			this.entityManager.getTransaction().commit();
		}
	}
}
