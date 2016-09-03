package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.TimePreferencesDAO;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences;

public class TimePreferencesJPADAOService extends JPADAO<TimePreferences,Integer> implements TimePreferencesDAO{
	@Override
	public List<TimePreferences> GetAll() {
		TypedQuery<TimePreferences> query = entityManager.createQuery("SELECT t FROM TimePreferences t", TimePreferences.class);
		List<TimePreferences> listTimePreferences = query.getResultList();
	    if (listTimePreferences == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listTimePreferences;
	}

	@Override
	public void Update(int timePreferencesId, TimePreferences newTimePreferences) {
		TimePreferences existingTimePreference = this.entityManager.find(TimePreferences.class, timePreferencesId);
		if(existingTimePreference != null)
		{
			this.entityManager.getTransaction().begin();
			existingTimePreference.setMonPreferences(newTimePreferences.getMonPreferences());
			existingTimePreference.setTuePreferences(newTimePreferences.getTuePreferences());
			existingTimePreference.setWedPreferences(newTimePreferences.getWedPreferences());
			existingTimePreference.setThuPreferences(newTimePreferences.getThuPreferences());
			existingTimePreference.setFriPreferences(newTimePreferences.getFriPreferences());			
			this.entityManager.getTransaction().commit();
		}
	}
}
