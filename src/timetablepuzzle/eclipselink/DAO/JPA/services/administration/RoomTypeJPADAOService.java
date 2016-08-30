package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.RoomTypeDAO;
import timetablepuzzle.eclipselink.entities.administration.RoomType;

public class RoomTypeJPADAOService extends JPADAO<RoomType,Integer> implements RoomTypeDAO{

	@Override
	public List<RoomType> GetAll() {
		TypedQuery<RoomType> query = entityManager.createQuery("SELECT b FROM RoomType b", RoomType.class);
		List<RoomType> listRoomTypes = query.getResultList();
	    if (listRoomTypes == null) {
	        System.out.println("No room types found.");
	    } 

	    return listRoomTypes;
	}

	@Override
	public void Update(int roomTypeId, RoomType newRoomType) {
		RoomType existingRoomType = this.entityManager.find(RoomType.class, roomTypeId);
		if(existingRoomType != null)
		{
			this.entityManager.getTransaction().begin();
			existingRoomType.setName(newRoomType.getName());
			existingRoomType.setMinCapacity(newRoomType.getMinCapacity());
			existingRoomType.setMaxCapacity(newRoomType.getMaxCapacity());
			existingRoomType.setRoomFeatures(newRoomType.getRoomFeatures());
			this.entityManager.getTransaction().commit();
		}
	}
}
