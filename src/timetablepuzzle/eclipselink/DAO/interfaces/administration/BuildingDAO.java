package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.administration.Building;

public interface BuildingDAO extends DAO<Building,Integer>{
	public List<Building> GetAll();
	public void Update(int buildingId, Building building);
}
