package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.UserDAO;
import timetablepuzzle.entities.administration.User;

public class UserJPADAOService  extends JPADAO<User,Integer> implements UserDAO{
	@Override
	public User findByUsername(String username)
	{
		TypedQuery<User> query =
			      entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			  List<User> results = query.getResultList();
			  
		if(results.size() == 1)
		{
			// Either there is no user with this user name, or there are too many
			return results.get(0);
		}
		// Some error occurred, there can only be a user with this user name
		return null;
	}
	
	@Override
	public List<User> GetAll() {
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
		List<User> listUsers = query.getResultList();
	    if (listUsers == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listUsers;
	}

	@Override
	public void Update(int userId, User newUser) {
		User existingUser = this.entityManager.find(User.class, userId);
		if(existingUser != null)
		{
			this.entityManager.getTransaction().begin();
			existingUser.setFirstName(newUser.getFirstName());
			existingUser.setLastName(newUser.getLastName());
			existingUser.setUsername(newUser.getUsername());
			existingUser.setPasswordToken(newUser.getPasswordToken());
			existingUser.setUserType(newUser.getUserType());
			this.entityManager.getTransaction().commit();
		}
	}
}
