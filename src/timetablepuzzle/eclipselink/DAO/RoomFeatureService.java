package timetablepuzzle.eclipselink.DAO;

import javax.persistence.EntityManager;

import timetablepuzzle.eclipselink.entities.administration.RoomFeature;

//This class might be deprecated
public class RoomFeatureService {
	private static RoomFeatureService _singleton;
	private static DatabaseConnection _dbConnection;
	
	/**
	 * Private constructor to enable the singleton behavior
	 */
	public RoomFeatureService()
	{
		_dbConnection = DatabaseConnection.GetInstance();
	}
	
	/****************Methods that model the class behavior*************************/
	/**
	 * Method that allows singleton behavior
	 * @param persistenceUnitName
	 * @return
	 */
    public static RoomFeatureService GetInstance()
    {
        if(_singleton == null)
        {
                _singleton = new RoomFeatureService();
        }

        return _singleton;
    }
    
    /**
     * Create a brand new room feature
     * @param feature
     */
    public void CreateRoomFeature(RoomFeature roomFeature)
    {
    	EntityManager entityManager = _dbConnection.get_entityManager();
    	entityManager.getTransaction().begin();
    	
    	entityManager.persist(roomFeature);
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }
}
