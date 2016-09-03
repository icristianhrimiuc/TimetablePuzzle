package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences;

public interface TimePreferencesDAO extends DAO<TimePreferences,Integer>{
	public List<TimePreferences> GetAll();
	public void Update(int timePreferencesId, TimePreferences newTimePreferences);
}
