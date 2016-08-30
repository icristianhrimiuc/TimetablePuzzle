package timetablepuzzle.eclipselink.DAO.JPA.services.administration;

import java.util.List;

import javax.persistence.TypedQuery;

import timetablepuzzle.eclipselink.DAO.JPADAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.RoomFeatureDAO;
import timetablepuzzle.eclipselink.entities.administration.RoomFeature;

public class RoomFeatureJPADAOService extends JPADAO<RoomFeature,Integer> implements RoomFeatureDAO{
	@Override
	public List<RoomFeature> GetAll(){
		TypedQuery<RoomFeature> query = entityManager.createQuery("SELECT b FROM RoomFeature b", RoomFeature.class);
		List<RoomFeature> listRoomFeature = query.getResultList();
	    if (listRoomFeature == null) {
	        System.out.println("No room features found.");
	    } 

	    return listRoomFeature;
	}
}
