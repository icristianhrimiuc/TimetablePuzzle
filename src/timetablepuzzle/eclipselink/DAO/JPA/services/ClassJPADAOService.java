package timetablepuzzle.eclipselink.DAO.JPA.services;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.interfaces.ClassDAO;
import timetablepuzzle.entities.Class;

public class ClassJPADAOService extends JPADAO<Class,Integer> implements ClassDAO{
	@Override
	public List<Class> GetAll() {
		TypedQuery<Class> query = entityManager.createQuery("SELECT c FROM Class c", Class.class);
		List<Class> listClasses = query.getResultList();
	    if (listClasses == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listClasses;
	}

	@Override
	public void Update(int classId, Class newClass) {
		Class existingClass = this.entityManager.find(Class.class, classId);
		if(existingClass != null)
		{
			this.entityManager.getTransaction().begin();
			existingClass.setCourseTitle(newClass.getCourseTitle());
			existingClass.setCourseAbbreviation(newClass.getCourseAbbreviation());
			existingClass.setDepartmentName(newClass.getDepartmentName());
			existingClass.setCollegeYear(newClass.getCollegeYear());
			existingClass.setSubjectAreaName(newClass.getSubjectAreaName());
			existingClass.setTerm(newClass.getTerm());
			existingClass.setOffering(newClass.getOffering());
			existingClass.setAssignedRoom(newClass.getAssignedRoom());
			existingClass.setAssignedInstructor(newClass.getAssignedInstructor());
			existingClass.setAssignedParentStudentGroup(newClass.getAssignedParentStudentGroup());
			this.entityManager.getTransaction().commit();
		}
	}
}
