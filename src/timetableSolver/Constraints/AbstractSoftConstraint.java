package timetableSolver.Constraints;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;
import timetablepuzzle.eclipselink.entities.inputdata.Class;

public abstract class AbstractSoftConstraint {
	private int _pointsOfPenaltyPerViolation;

	public int get_pointsOfPenaltyPerViolation() {
		return _pointsOfPenaltyPerViolation;
	}

	public void set_pointsOfPenaltyPerViolation(int _pointsOfPenaltyPerViolation) {
		this._pointsOfPenaltyPerViolation = _pointsOfPenaltyPerViolation;
	}
	
	public abstract long CalculatePenaltyPoints(Solution solution);
	
	public abstract long GetPenaltyPointsForVariable(Solution solution, Class selClass);
}
