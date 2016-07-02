package timetablepuzzle.eclipselink.DAO.services.administration;

import java.util.List;
import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.UserDAO;
import timetablepuzzle.eclipselink.entities.administration.User;

public class UserJPADAOService  extends JPADAO implements UserDAO{
	public User findByUsername(String username)
	{
		TypedQuery<User> query =
			      entityManager.createQuery("SELECT u FROM Users u WHERE u.username = :username", User.class);
			  List<User> results = query.getResultList();
			  
		if(results.size() == 1)
		{
			// Either there is no user with this user name, or there are too many
			return results.get(0);
		}
		// Some error occurred, there can only be a user with this user name
		return null;
	}
}
