package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.User;

public interface UserDAO extends DAO<User,Integer>{
	public User findByUsername(String username);
	public List<User> GetAll();
	public void Update(int UserId, User newUser);
}
