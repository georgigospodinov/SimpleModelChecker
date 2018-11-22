package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;


public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    public final Set<String> leftActions;
    public final Set<String> rightActions;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean exists(TransitionTo t, Path p, StateFormula constraint) {
    	if (rightActions != null && rightActions.size() == 0) // no acceptable path
            return false;
        
        if (p.contains(t))
            return false;

        if (!constraint.holdsIn(t)) {
        	return false;
        }
        
        if (rightActions == null && right.isValidIn(t, p, constraint)) {
            p.push(t);
            return true;
        }

        if (!left.isValidIn(t, p, constraint)) {
            return false;
        }

        p.push(t);

        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (transition.isIn(rightActions)) {
                if (right.isValidIn(transition, p, constraint)) {
                    p.push(transition);
                    return true;
                }
            }
        }

        for (TransitionTo transition : current.getTransitions()) {
            if (transition.isIn(leftActions)) {
                if (exists(transition, p, constraint.childConstraint(transition)))
                    return true;
            }
        }

        p.pop();
        return false;
    }

    @Override
    public boolean forAll(TransitionTo t, Path p, StateFormula constraint) {
    	try {
    		return forAllInternal(t, p, constraint);
    	} catch (ConstraintBreachException e) {
    		// no acceptable path
    		return false;
    	}
    	
    }
    
    private boolean forAllInternal(TransitionTo t, Path p, StateFormula constraint) throws ConstraintBreachException {
        // Loop detection
        if (p.contains(t)) {
            return false;
        }
        
        if (!constraint.holdsIn(t)) {
    		throw new ConstraintBreachException();
        }

        if (rightActions == null && right.isValidIn(t, new Path(), constraint)) {
            return true;
        }

        p.push(t);
        if (!left.isValidIn(t, new Path(), constraint)) {
            return false;
        }

        int passing = 0;
        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (transition.isIn(rightActions)) {
                if (!right.isValidIn(transition, new Path(), constraint)) {
                    checkLeft.push(transition);
                }
                else passing++;
            }
            else checkLeft.push(transition);
        }

        for (TransitionTo transition : checkLeft) {
            if (transition.isIn(leftActions)) {
            	try {
	                if (!forAllInternal(transition, p, constraint.childConstraint(transition))) {
	                    return false;
	                }
	                passing++;
            	}
            	catch (ConstraintBreachException e) {
            		// don't fail because of constraint breaches
            	}
            }
        }

        if (passing > 0) {
            p.pop();
            return true;
        }
        //no passing paths, Strong until fails.
        else return false;
    }
    
    
}
