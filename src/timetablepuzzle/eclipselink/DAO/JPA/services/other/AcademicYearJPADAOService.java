package timetablepuzzle.eclipselink.DAO.JPA.services.other;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.AcademicYearDAO;
import timetablepuzzle.entities.other.AcademicYear;

public class AcademicYearJPADAOService extends JPADAO<AcademicYear,Integer> implements AcademicYearDAO{
	@Override
	public List<AcademicYear> GetAll(){
		TypedQuery<AcademicYear> query = entityManager.createQuery("SELECT a FROM AcademicYear a", AcademicYear.class);
		List<AcademicYear> listAcademicYears = query.getResultList();
	    if (listAcademicYears == null) {
	        System.out.println("No academicYears found.");
	    } 

	    return listAcademicYears;
	}

	@Override
	public void Update(int academicYearId, AcademicYear newAcademicYear) {
		AcademicYear existingAcademicYear = this.entityManager.find(AcademicYear.class, academicYearId);
		if(existingAcademicYear != null)
		{
			this.entityManager.getTransaction().begin();
			existingAcademicYear.setName(newAcademicYear.getName());
			existingAcademicYear.setFirstAcademicSession(newAcademicYear.getFirstAcademicSession());
			existingAcademicYear.setSecondAcademicSession(newAcademicYear.getSecondAcademicSession());
			existingAcademicYear.setThirdAcademicSession(newAcademicYear.getThirdAcademicSession());
			this.entityManager.getTransaction().commit();
		}
	}
}
