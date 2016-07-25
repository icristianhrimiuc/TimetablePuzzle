package timetablepuzzle.eclipselink.entities.inputdata;

import java.util.HashMap;

import javax.persistence.Transient;

public class SolutionObject {

	private Solution solution;
	
	@Transient
	private HashMap<Integer,Class[]> roomsTimetable;
	
	@Transient
	private HashMap<Integer,Class[]> instructorsTimetable;
	
	@Transient
	private HashMap<Integer,Class[]> studentsTimetable;
	
	@Transient
	private HashMap<Class,Integer> assignedClasses;
	
	public SolutionObject(Solution solution)
	{
		this.solution = solution;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}
}
