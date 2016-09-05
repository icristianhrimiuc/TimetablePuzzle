package timetablepuzzle.eclipselink.DAO.interfaces.other;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.other.Building;

public interface BuildingDAO extends DAO<Building,Integer>{
	public List<Building> GetAll();
	public void Update(int buildingId, Building building);
}
