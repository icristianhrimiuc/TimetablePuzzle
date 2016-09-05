package timetablepuzzle.eclipselink.DAO.interfaces.administration;

import java.util.List;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.entities.administration.InstructorMeetings;

public interface InstructorMeetingsDAO extends DAO<InstructorMeetings,Integer>{
	public List<InstructorMeetings> GetAll();
	public void Update(int instructorMeetingsId, InstructorMeetings newInstructorMeetings);
}
