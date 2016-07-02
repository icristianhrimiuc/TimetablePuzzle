package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.administration.User;

public interface UserDAO extends DAO {
	public User findByUsername(String username);
}
