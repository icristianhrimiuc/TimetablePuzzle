package timetablepuzzle.eclipselink.DAO.interfaces.inputdata;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.inputdata.Room;

public interface RoomDAO extends DAO<Room,Integer>{
	public List<Room> GetAll();
	public void Update(int roomId, Room newRoom);
}
