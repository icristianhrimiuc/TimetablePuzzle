package timetablepuzzle.eclipselink.DAO.JPA.services.inputdata;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.OfferingDAO;
import timetablepuzzle.entities.inputdata.Offering;

public class OfferingJPADAOService extends JPADAO<Offering,Integer> implements OfferingDAO{
	@Override
	public List<Offering> GetAll() {
		TypedQuery<Offering> query = entityManager.createQuery("SELECT o FROM Offering o", Offering.class);
		List<Offering> listOfferings = query.getResultList();
	    if (listOfferings == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listOfferings;
	}

	@Override
	public void Update(int offeringId, Offering newOffering) {
		Offering existingOffering = this.entityManager.find(Offering.class, offeringId);
		if(existingOffering != null)
		{
			this.entityManager.getTransaction().begin();
			existingOffering.setName(newOffering.getName());
			existingOffering.setNrOfTimeSlots(newOffering.getNrOfTimeSlots());
			existingOffering.setType(newOffering.getType());
			existingOffering.setDatePattern(newOffering.getDatePattern());
			existingOffering.setNrOfMeetingsPerInstructor(newOffering.getNrOfMeetingsPerInstructor());
			this.entityManager.getTransaction().commit();
		}
	}
}
