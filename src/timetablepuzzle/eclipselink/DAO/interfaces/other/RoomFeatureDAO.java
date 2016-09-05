package timetablepuzzle.eclipselink.DAO.interfaces.other;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.other.RoomFeature;

public interface RoomFeatureDAO extends DAO<RoomFeature,Integer>{
	public List<RoomFeature> GetAll();
}
