package timetablepuzzle.eclipselink.DAO.interfaces.inputdata;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.inputdata.InstructorMeetings;

public interface InstructorMeetingsDAO extends DAO<InstructorMeetings,Integer>{
	public List<InstructorMeetings> GetAll();
	public void Update(int instructorMeetingsId, InstructorMeetings newInstructorMeetings);
}
