package timetablepuzzle.solver.constraints;

import timetablepuzzle.usecases.solution.SolutionAssignManager;
import timetablepuzzle.usecases.solution.SolutionUnassignManager;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.inputdata.Class;

public abstract class AbstractSoftConstraint {
	public static final int DefaultNrOfPenaltyPointsPerViolation = 5;
	
	protected Solution solution;
	protected SolutionUnassignManager unassignManager;
	protected SolutionAssignManager assignManager;
	protected int nrOfpenaltyPointsPerViolation;
	
	public AbstractSoftConstraint(Solution solution)
	{
		this.solution = solution;
		this.unassignManager = new SolutionUnassignManager(solution);
		this.assignManager = new SolutionAssignManager(solution);
	}
	
	public Solution getSolution() {
		return solution;
	}	

	public int getNrOfPenaltyPointsPerViolation() {
		return nrOfpenaltyPointsPerViolation;
	}

	public void setNrOfPenaltyPointsPerViolation(int pointsOfPenaltyPerViolation) {
		this.nrOfpenaltyPointsPerViolation = pointsOfPenaltyPerViolation;
	}
	
	public abstract long CalculateTotalNrOfPenaltyPoints();
	
	public abstract long GetNrOfPenaltyPointsForVariable(Solution solution, Class aClass);
}
