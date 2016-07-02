package timetablepuzzle.spring.windows;

import timetableSolver.PasswordAuthentication;
import timetablepuzzle.eclipselink.entities.administration.User;

public class Program {

   public static void main( String[ ] args ) {
	   
      User user = new User();
      PasswordAuthentication passAuth = new PasswordAuthentication();
      char[] password = {'a','d','m','i','n'};
      user.set_username("admin");
      user.set_token(passAuth.hash(password));
      user.set_userType(User.UserType.ADMIN);
      user.set_firstName("Johny");
      user.set_lastName("Test");
      
      User.userDAO.persist( user );
   }
}
