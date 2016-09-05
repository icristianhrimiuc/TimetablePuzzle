package timetablepuzzle.eclipselink.DAO.JPA.services.other;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPA.services.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.BuildingDAO;
import timetablepuzzle.entities.other.Building;

public class BuildingJPADAOService extends JPADAO<Building,Integer> implements BuildingDAO{
	@Override
	public List<Building> GetAll(){
		TypedQuery<Building> query = entityManager.createQuery("SELECT b FROM Building b", Building.class);
		List<Building> listBuildings = query.getResultList();
	    if (listBuildings == null) {
	        System.out.println("No building found.");
	    } 

	    return listBuildings;
	}

	@Override
	public void Update(int buildingId, Building newBuilding) {
		Building existingBuilding = this.entityManager.find(Building.class, buildingId);
		if(existingBuilding != null)
		{
			this.entityManager.getTransaction().begin();
			existingBuilding.setName(newBuilding.getName());
			existingBuilding.setAbbreviation(newBuilding.getAbbreviation());
			existingBuilding.setLocation(newBuilding.getLocation());
			this.entityManager.getTransaction().commit();
		}
	}
}
