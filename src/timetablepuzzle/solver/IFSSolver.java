package timetablepuzzle.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import timetablepuzzle.eclipselink.entities.inputdata.*;
import timetablepuzzle.eclipselink.entities.inputdata.Class;
import timetablepuzzle.solver.constraints.AbstractHardConstraint;
import timetablepuzzle.solver.constraints.AbstractSoftConstraint;
import timetablepuzzle.usecases.solution.DomainSizeCalculator;
import timetablepuzzle.usecases.solution.SolutionAssignManager;
import timetablepuzzle.usecases.solution.SolutionUnassignManager;

public class IFSSolver {
	/*****************Input data***********************/
    // A partial(feasible) solution
    private Solution solution;
    private SolutionAssignManager assignManager;
    private SolutionUnassignManager unassignManager;
    private DomainSizeCalculator domainSizeCalculator;
    // Hard constraints
    private ArrayList<AbstractHardConstraint> _hardConstraints;
    // Soft constraints
    private ArrayList<AbstractSoftConstraint> _softConstraints;
    /*****************Work data***********************/
    IFSSolver singleton = null;
    // One of the stop conditions
    private long maxIterations;
    // Another stop condition
    private long maxTimeInSeconds;
    // The variable selection coefficients
    private int[] varSelCoeff;
    // The probability for a class to be selected 
    // during the variable selection process
    private double selectionProbability;
    // The size limit that decides when to stop applying the 
    // selectionProbability for the variable selection process
    private int sizeSelLimit;
    // The number of the current iteration
    private long currentIteration;
    // Random numberGenerator;
    private Random rnd;
    // Memorize the maximum number of penalty points that a class can get
    private int maxPPointsForAClass;
    // Memorize the minimum number of penalty points that a class can get
    private int minPPointsForAClass;
    // The maximum percentage of soft constraints that is allowed to be violated
    private double maxPctOfViolatedSC;

    /**
     * Private Constructor to allow the singleton behavior
     * @param maxIterations
     * @param nrOfTimeSlots
     */
    private IFSSolver(long maxIterations, long maxTimeInSeconds, 
    		ArrayList<AbstractHardConstraint> hardContraints, 
    		ArrayList<AbstractSoftConstraint> softConstraints)
    {
        this.maxIterations = maxIterations;
        this.maxTimeInSeconds = maxTimeInSeconds;
        this._hardConstraints = hardContraints;
        this._softConstraints = softConstraints;
        int[] coeff = {1,1,1,1};
        SetVarSelCoeff(coeff);
        selectionProbability = 0.2;
        sizeSelLimit = 30;
        rnd = new Random();
        // Calculate maximum and minimum number of penalty points for the set of soft constraints
        int maxPPoints = 0;
        int minPPoints = _softConstraints.get(0).getNrOfPenaltyPointsPerViolation();
        int curPPoints;
        for(AbstractSoftConstraint softConstraint : _softConstraints)
        {
        	curPPoints = softConstraint.getNrOfPenaltyPointsPerViolation();
        	maxPPoints += curPPoints;
        	if(curPPoints < minPPoints)
        	{
        		minPPoints = curPPoints;
        	}
        }
        this.maxPPointsForAClass = maxPPoints;
        this.minPPointsForAClass = minPPoints;
        SetMaxPctOfViolatedSC(0.1);
    }
    
    /* Getters and setters for the solver fields */
    
	public Solution get_solution() {
		return solution;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<AbstractHardConstraint> get_hardConstraints() {
		return (ArrayList<AbstractHardConstraint>)this._hardConstraints.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<AbstractSoftConstraint> get_softConstraints() {
		return (ArrayList<AbstractSoftConstraint>)this._softConstraints.clone();
	}
	
	public int[] get_varSelCoeff()
	{
		return this.varSelCoeff;
	}
	
	public double get_selectionProbability()
	{
		return this.selectionProbability;
	}

	public double get_maxPctOfViolatedSC() {
		return maxPctOfViolatedSC;
	}

	/* Public methods that model the class behavior */

    /**
     * Returns the single instance of this class
     * @param maxIterations
     * @param nrOfTimeSlots
     * @return
     */
    public IFSSolver GetInstance(long maxIterations, long maxTimeInSeconds,
    		ArrayList<AbstractHardConstraint> hardContraints, 
    		ArrayList<AbstractSoftConstraint> softConstraints)
    {
        if(this.singleton == null ||
        		this.maxIterations != maxIterations ||
        		this.maxTimeInSeconds != maxTimeInSeconds)
        {
                this.singleton = new IFSSolver(maxIterations, maxTimeInSeconds,
                		hardContraints, softConstraints);
        }

        return this.singleton;
    }
	
	/**
	 * Sets the initial solution. From this the solver will iterate 
	 * till it reaches one of the stop conditions
	 * @param _solution
	 * @return
	 */
	public IFSSolver SetInitialSolution(Solution _solution) {
		this.solution = _solution;
		// A new solution has been set, the algorithm starts
		// from iteration 0
		this.currentIteration = 0;
		
		return this;
	}
	
	/**
	 * Set the variable selectionCoefficients
	 * @param coefficients
	 */
	public IFSSolver SetVarSelCoeff(int[] coeff)
	{
		this.varSelCoeff = coeff;
		
		return this;
	}
	
	/**
	 * Set the probability for a variable to be selected
	 * from the set of unassigned classes, when the size is over 
	 * @param selectionProbability
	 */
	public IFSSolver SetSelectionProbability(double selectionProbability)
	{
		this.selectionProbability = selectionProbability;
		
		return this;
	}
	
	/**
	 * Set the maximum percentage of allowed violated soft constraints.
	 * A 100% percentage means that all variables violate all 
	 * soft constraints and take the maximum of penalty points for the violation
	 * @param maxPctOfViolatedSC
	 * @return
	 */
	public IFSSolver SetMaxPctOfViolatedSC(double percent) {
		maxPctOfViolatedSC = maxPPointsForAClass * solution.GetNrOfCLassesInSolution() * percent;
		
		return this;
	}
	
    /**
     * Creates a solution 
     * @return
     */
    public Solution DoSteps(long nrOfSteps)
    {
        long step = 0;
        Solution currentSol = solution;
        Solution bestSol = solution;
        Class variable;

        while(step <= nrOfSteps && CanContinue(currentSol))
        {
        	step++;
        	currentIteration++;
        	variable = SelectVariable(currentSol);
//        	Integer value = SelectValue(currentSol,variable);
//        	List<Class> conflictingVars = currentSol.GetConflictingVariables(variable, value);
//    		currentSol.Unassign(conflictingVars);
//    		currentSol.Assign(variable, value);
//    		if(CompareSolutions(currentSol, bestSol) > 0)
    		{
    			bestSol = currentSol;
    		}
        }

        return bestSol;
    }

    /**
     * Determines if either of the stop conditions was reached
     * @param iteration
     * @return
     */
    private boolean CanContinue(Solution currentSol)
    {
    	boolean canContinue = true; 
    	
        if(currentIteration < maxIterations)
        {
            // Here i should check if the current solution is good enough
            if( solution.GetUnassignedClasses().isEmpty())
            {
                // If there are no more variables to assign and 
                // less than 10% of the soft constraints are violated
                // then the solution is good enough
                if(CheckSoftConstraints(this.solution) <= this.maxPctOfViolatedSC)
                {
                	canContinue = false;
                }
            }
        }

        return canContinue;
    }

    /**
     * Check to see how many soft constraints are violated
     * @return
     */
    private int CheckSoftConstraints(Solution solution)
    {
        int totalPenaltyPoints = 0;

        for (AbstractSoftConstraint softConstraint : this._softConstraints)
        {
            totalPenaltyPoints += softConstraint.CalculateTotalNrOfPenaltyPoints();
        }

        return totalPenaltyPoints;
    }
    
    /**
     * Select the next variable from all the variables depending 
     * on the current state of the system
     * @param current
     * @return
     */
    private Class SelectVariable(Solution currentSol)
    {
    	List<Class> worstClasses = new ArrayList<Class>();
    	double worstClassValue = Double.MAX_VALUE;
    	
        // Check to see if all variables were assigned
    	List<Class> unassignedClasses = currentSol.GetUnassignedClasses();
        if(!unassignedClasses.isEmpty())
        {
            // Select the variables that are involved in the largest number of constraints
            // Calculate the value of the worst variable according to the criteria  
            // multiplied with the corresponding coefficient
        	
        	// Decide whether to keep all the classes, or just a randomly selected part of them 
        	List<Class> selClasses;
        	if(unassignedClasses.size() > this.sizeSelLimit)
        	{
            	selClasses = RandomlySelectClasses(currentSol);
        	}else{
        		selClasses = unassignedClasses;
        	}
        	
        	for(Class selClass : selClasses)
        	{
            	int nrOfRemovals = currentSol.getNrOfRemovals(selClass.getId());
            	// Each class has one dependency to each of the groups that should assist
            	// this is known as parallel dependency
            	int nrOfDependencies = selClass.getAssignedStudentGroups().size();
            	// Check to see if the class is a lecture
            	if(selClass.getOffering().getType() == Offering.OfferingType.LECTURE)
            	{
            		// Each lecture has to be before all the laboratories of the groups 
            		// that should assist take place
            		nrOfDependencies += selClass.getAssignedStudentGroups().size();
            	}
            	int nrOfConflictingPlaces = this.domainSizeCalculator.GetConflictingDomainSize(selClass);
            	int nrOfNonCnflictingPlaces = this.domainSizeCalculator.GetNonConflictingDomainSize(selClass);
            	double classValue = -1 * varSelCoeff[0] * nrOfRemovals + 
            			-1 * varSelCoeff[1] * nrOfDependencies + 
            			varSelCoeff[2] * nrOfConflictingPlaces + 
            			varSelCoeff[3] * nrOfNonCnflictingPlaces;
            	if(classValue < worstClassValue)
            	{
            		worstClasses.clear();
            		worstClasses.add(selClass);
            		worstClassValue = classValue;
            	}else{
            		if(classValue == worstClassValue)
            		{
            			worstClasses.add(selClass);
            		}
            	}
        	}
        }
        else
        {
            // There are no unassigned variables, but there are too many soft constraints violated
            // Select from the assigned variables the one that violates the highest number of soft constraints
            // TO DO: Select from the assigned variables the one that violates the highest number of constraints
        	// Decide whether to keep all the classes, or just a randomly selected part of them 
        	List<Class> assignedClasses = currentSol.GetAssignedClasses();
        	int totalNrOfPPointsPerClass;
        	int maxNrOfPPointsPerClass = 0;
        	
        	for(Class aClass : assignedClasses)
        	{
        		totalNrOfPPointsPerClass = 0;
        		for(AbstractSoftConstraint softConstraint : _softConstraints)
        		{
        			totalNrOfPPointsPerClass += 
        					softConstraint.GetNrOfPenaltyPointsForVariable(currentSol,  aClass);
        		}
        		
        		if(totalNrOfPPointsPerClass > maxNrOfPPointsPerClass)
        		{
        			worstClasses.clear();
        			worstClasses.add(aClass);
        		}else{
        			if(totalNrOfPPointsPerClass == maxNrOfPPointsPerClass){
        				worstClasses.add(aClass);
        			}
        		}       		
        	}
        }

        // Check to see if there are more than one classes
        Class worstClass = null;
        if(worstClasses.size() >= 1)
        {
        	// There are multiple classes with the same value. Select one randomly
        	int rIndex = this.rnd.nextInt(worstClasses.size());
        	worstClass = worstClasses.get(rIndex);
        }else{
        	// There is only one class with. Select it
        	worstClass = worstClasses.get(0);
        }
        
        return worstClass;
    }
    
    /**
     * Randomly select classes from the unassigned classes to 
     * speed up the select variable process when there are too many classes
     * @param currentSol
     * @return
     */
    private List<Class> RandomlySelectClasses(Solution currentSol)
    {
    	List<Class> selClasses = new ArrayList<Class>();
    	for(Class uClass : currentSol.GetUnassignedClasses())
    	{
    		if(this.rnd.nextDouble() <= this.selectionProbability)
    		{
    			selClasses.add(uClass);
    		}
    	}
    	
    	return selClasses;
    }
    
    /**
     * Calculates the number of penalty points one solution has above the other
     * takes into account
     * @param first
     * @param second
     * @return
     */
    private int CompareSolutions(Solution first, Solution second)
    {
    	 return (second.GetUnassignedClasses().size() - first.GetUnassignedClasses().size()) * 
    			this.minPPointsForAClass + 
    			(CheckSoftConstraints(first) - CheckSoftConstraints(second));
    }
}
