package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class WeakUntil extends PathFormula {

    public final StateFormula left;
    public final StateFormula right;
    public final Set<String> leftActions;
    public final Set<String> rightActions;

    public WeakUntil(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
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
        buffer.append(" " + FormulaParser.WEAK_UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean exists(TransitionTo t, Path p, StateFormula constraint) {
        if (p.contains(t)) {
            return true;
        }

        if (rightActions == null && right.isValidIn(t, p, constraint)) {
            p.push(t);
            return true;
        }

        if (!left.isValidIn(t, p, constraint))
            return false;

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

        int onwards = 0;
        for (TransitionTo transition : current.getTransitions()) {
            if (transition.isIn(leftActions)) {
                onwards++;
                if (exists(transition, p, constraint.childConstraint(transition)))
                    return true;
            }
        }

        // cannot step to end state
        // did not find an onwards path
        // if this is a dead end, accept
        /*
         * Dead end:
         * 	No transitions
         * 	No allowed left transitions
         */

        //onwards: number of transitions from s in leftActions
        if (onwards == 0) 
            //dead end
            return true;
        else {
            p.pop();
            return false;
        }
    }

    @Override
    public boolean forAll(TransitionTo t, Path p, StateFormula constraint) {
    	try {
    		return forAllInternal(t, p, constraint);
    	} catch (ConstraintBreachException e) {
    		// no failing path
    		return true;
    	}
    }

    public boolean forAllInternal(TransitionTo t, Path p, StateFormula constraint) throws ConstraintBreachException {
        if (p.contains(t)) {
            return true;
        }
        
        if (!constraint.holdsIn(t)) {
    		throw new ConstraintBreachException();
        }
        
        if (rightActions == null && right.isValidIn(t, new Path(), constraint))
            return true;

        p.push(t);
        if (!left.isValidIn(t, new Path(), constraint)) {
            return false;
        }

        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (transition.isIn(rightActions)) {
                if (!right.isValidIn(transition, new Path(), constraint)) {
                    checkLeft.push(transition);
                }
            }
            else checkLeft.push(transition);
        }

        for (TransitionTo transition : checkLeft) {
            if (transition.isIn(leftActions)) {
            	try {
	                if (!forAllInternal(transition, p, constraint.childConstraint(transition))) {
	                    return false;
	                }
            	} catch (ConstraintBreachException e) {
            		// don't fail
            	}
            	
            }
        }

        p.pop();
        return true;
    }
}
