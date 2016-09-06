package timetablepuzzle.eclipselink.DAO.JPA.services.inputdata;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
import timetablepuzzle.entities.inputdata.Room;

public class RoomJPADAOService extends JPADAO<Room,Integer> implements RoomDAO{
	@Override
	public List<Room> GetAll() {
		TypedQuery<Room> query = entityManager.createQuery("SELECT r FROM Room r", Room.class);
		List<Room> listRooms = query.getResultList();
	    if (listRooms == null) {
			LOGGER.log(Level.WARNING, "No {0} was found when calling GetAll(). ", new Object[]{this.entityClass});
	    } 

	    return listRooms;
	}

	@Override
	public void Update(int roomId, Room newRoom) {
		Room existingRoom = this.entityManager.find(Room.class, roomId);
		if(existingRoom != null)
		{
			this.entityManager.getTransaction().begin();
			existingRoom.setName(newRoom.getName());
			existingRoom.setCode(newRoom.getCode());
			existingRoom.setCapacity(newRoom.getCapacity());
			existingRoom.setType(newRoom.getType());
			existingRoom.setBuilding(newRoom.getBuilding());
			existingRoom.setTimePreferences(newRoom.getTimePreferences());
			this.entityManager.getTransaction().commit();
		}
	}
}
