package timetablepuzzle.eclipselink.DAO.interfaces.other;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.other.RoomType;

public interface RoomTypeDAO extends DAO<RoomType,Integer>{
	public List<RoomType> GetAll();
	public void Update(int roomTypeId, RoomType newRoomType);
}
