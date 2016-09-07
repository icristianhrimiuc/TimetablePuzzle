package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.YearOfStudyDAO;
import timetablepuzzle.entities.administration.YearOfStudy;

public class YearOfStudyJPADAOService extends JPADAO<YearOfStudy,Integer> implements YearOfStudyDAO{
	@Override
	public List<YearOfStudy> GetAll(){
		TypedQuery<YearOfStudy> query = entityManager.createQuery("SELECT y FROM YearOfStudy y", YearOfStudy.class);
		List<YearOfStudy> listYearsOfStudys = query.getResultList();
	    if (listYearsOfStudys == null) {
	        System.out.println("No yearsOfStudys found.");
	    } 

	    return listYearsOfStudys;
	}

	@Override
	public void Update(int yearOfStudyId, YearOfStudy newYearOfStudy) {
		YearOfStudy existingYearOfStudy = this.entityManager.find(YearOfStudy.class, yearOfStudyId);
		if(existingYearOfStudy != null)
		{
			this.entityManager.getTransaction().begin();
			existingYearOfStudy.setName(newYearOfStudy.getName());
			existingYearOfStudy.setCollegeYear(newYearOfStudy.getCollegeYear());
			existingYearOfStudy.setSubjectAreas(newYearOfStudy.getSubjectAreas());
			this.entityManager.getTransaction().commit();
		}
	}
}
