package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.administration.RoomFeature;

public interface RoomFeatureDAO extends DAO<RoomFeature,Integer>{
	public List<RoomFeature> GetAll();
}
