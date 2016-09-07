package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.DepartmentDAO;
import timetablepuzzle.entities.administration.Department;

public class DepartmentJPADAOService extends JPADAO<Department,Integer> implements DepartmentDAO{
	@Override
	public List<Department> GetAll(){
		TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d", Department.class);
		List<Department> listYearsOfStudys = query.getResultList();
	    if (listYearsOfStudys == null) {
	        System.out.println("No departments found.");
	    } 

	    return listYearsOfStudys;
	}

	@Override
	public void Update(int departmentId, Department newDepartment) {
		Department existingDepartment = this.entityManager.find(Department.class, departmentId);
		if(existingDepartment != null)
		{
			this.entityManager.getTransaction().begin();
			existingDepartment.setName(newDepartment.getName());
			existingDepartment.setYearsOfStudy(newDepartment.getYearsOfStudy());
			this.entityManager.getTransaction().commit();
		}
	}
}
