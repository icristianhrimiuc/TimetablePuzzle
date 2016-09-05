package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.User;

public interface UserDAO extends DAO<User,Integer>{
	public User findByUsername(String username);
}
