package timetablepuzzle.eclipselink.DAO.interfaces;

import java.util.List;

import timetablepuzzle.entities.Class;

public interface ClassDAO extends DAO<Class,Integer>{
	public List<Class> GetAll();
	public void Update(int classId, Class newClass);
}
