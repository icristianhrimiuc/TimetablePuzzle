package timetablepuzzle.eclipselink.DAO.interfaces.inputdata;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.inputdata.Offering;

public interface OfferingDAO extends DAO<Offering,Integer>{
	public List<Offering> GetAll();
	public void Update(int offeringId, Offering newOffering);
}
