package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.administration.RoomType;

public interface RoomTypeDAO extends DAO<RoomType,Integer>{
	public List<RoomType> GetAll();
	public void Update(int roomTypeId, RoomType newRoomType);
}
