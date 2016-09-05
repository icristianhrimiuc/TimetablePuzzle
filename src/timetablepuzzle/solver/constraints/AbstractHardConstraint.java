package timetablepuzzle.solver.constraints;

import timetablepuzzle.usecases.solution.SolutionChecker;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.inputData.Class;


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
