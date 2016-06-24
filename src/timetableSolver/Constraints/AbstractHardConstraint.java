package timetableSolver.Constraints;

import timetablepuzzle.eclipselink.entities.inputdata.Solution;
import timetablepuzzle.eclipselink.entities.inputdata.Class;


public abstract class AbstractHardConstraint {
	
	public abstract boolean IsValidCombination(Solution solution, Class selClass, int dayNTime);
}
