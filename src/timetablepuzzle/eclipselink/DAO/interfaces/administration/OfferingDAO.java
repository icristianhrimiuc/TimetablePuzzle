package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.Offering;

public interface OfferingDAO extends DAO<Offering,Integer>{
	public List<Offering> GetAll();
	public void Update(int offeringId, Offering newOffering);
}
