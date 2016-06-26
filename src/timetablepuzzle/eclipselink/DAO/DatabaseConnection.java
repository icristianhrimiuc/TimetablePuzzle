package timetablepuzzle.eclipselink.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//This class might be deprecated
public class DatabaseConnection {
	private static DatabaseConnection _singleton = null;
	private static String _persistenceUnitName;
	private static EntityManagerFactory _emFactory;
	private static EntityManager _entityManager;
	
	/**
	 * Parameterized private constructor to enable singleton behavior
	 * @param persistenceUnitName
	 */
	private DatabaseConnection(String persistenceUnitName)
	{
		_persistenceUnitName = persistenceUnitName;
		_emFactory = Persistence.createEntityManagerFactory(_persistenceUnitName);
		_entityManager = _emFactory.createEntityManager();
	}
	
	/**************Getters and setters*******************/
	public String get_persistenceUnitName()
	{
		return _persistenceUnitName;
	}
	
	public EntityManagerFactory get_emFactory()
	{
		return _emFactory;
	}
	
	public EntityManager get_entityManager() {
		return _entityManager;
	}
	
	/***************Methods that model the class behavior*****************/
	/**
	 * Method that allows singleton behavior
	 * Gets the default persistence unit, the 
	 * one that goes by the name "TimetablePuzzle"
	 * @return
	 */
	public static DatabaseConnection GetInstance()
	{
		return GetInstance("TimetablePuzzle");
	}
	
	/**
	 * Method that allows singleton behavior
	 * @param persistenceUnitName
	 * @return
	 */
    public static DatabaseConnection GetInstance(String persistenceUnitName)
    {
        if(_singleton == null || !_persistenceUnitName.equals(persistenceUnitName))
        {
                _singleton = new DatabaseConnection(persistenceUnitName);
        }

        return _singleton;
    } 
}
