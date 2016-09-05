package timetablepuzzle.eclipselink.DAO.interfaces.other;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.other.TimePreferences;

public interface TimePreferencesDAO extends DAO<TimePreferences,Integer>{
	public List<TimePreferences> GetAll();
	public void Update(int timePreferencesId, TimePreferences newTimePreferences);
}
