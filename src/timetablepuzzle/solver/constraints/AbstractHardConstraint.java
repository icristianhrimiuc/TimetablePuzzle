package timetablepuzzle.solver.constraints;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;
import timetablepuzzle.usecases.solution.SolutionChecker;
import timetablepuzzle.eclipselink.entities.inputdata.Class;


public abstract class AbstractHardConstraint {
	protected Solution solution;
	protected SolutionChecker checker;
	
	public AbstractHardConstraint(Solution solution)
	{
		this.solution = solution;
		this.checker = new SolutionChecker(solution);
	}
	
	public abstract boolean IsValidAssignment(Class selClass, int dayNTime);
}
