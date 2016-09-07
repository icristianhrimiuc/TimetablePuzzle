package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.FacultyDAO;
import timetablepuzzle.entities.administration.Faculty;

public class FacultyJPADAOService extends JPADAO<Faculty,Integer> implements FacultyDAO{
	@Override
	public List<Faculty> GetAll(){
		TypedQuery<Faculty> query = entityManager.createQuery("SELECT f FROM Faculty f", Faculty.class);
		List<Faculty> listFaculties = query.getResultList();
	    if (listFaculties == null) {
	        System.out.println("No faculties found.");
	    } 

	    return listFaculties;
	}

	@Override
	public void Update(int facultyId, Faculty newFaculty) {
		Faculty existingFaculty = this.entityManager.find(Faculty.class, facultyId);
		if(existingFaculty != null)
		{
			this.entityManager.getTransaction().begin();
			existingFaculty.setName(newFaculty.getName());
			existingFaculty.setAcademicYears(newFaculty.getAcademicYears());
			existingFaculty.setDepartments(newFaculty.getDepartments());
			this.entityManager.getTransaction().commit();
		}
	}
}
