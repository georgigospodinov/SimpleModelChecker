package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class Until extends PathFormula {
	//TODO replace 
	StateFormula constraint = new BoolProp(true);
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

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
    public boolean exists(TransitionTo t, Path p) {
        if (rightActions != null && rightActions.size() == 0) // no acceptable path
            return false;

        if (p.contains(t))
            return false;

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
            if (rightActions == null || transition.isIn(rightActions)) {
                if (right.isValidIn(transition, p, constraint)) {
                    p.push(transition);
                    return true;
                }
            }
        }

        for (TransitionTo transition : current.getTransitions()) {
            if (leftActions == null || transition.isIn(leftActions)) {
                if (exists(transition, p))
                    return true;
            }
        }

        p.pop();
        return false;
    }

    @Override
    public boolean forAll(TransitionTo t, Path p) {
        // Loop detection
        if (p.contains(t)) {
            p.push(t);
            return false;
        }

        if (rightActions == null && right.isValidIn(t, p, constraint)) {
            return true;
        }

        if (left.isValidIn(t, p, constraint)) {
            return false;
        }
        p.push(t);

        int passing = 0;
        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (rightActions == null || transition.isIn(rightActions)) {
                if (!right.isValidIn(transition, p, constraint)) {
                    checkLeft.push(transition);
                }
                else passing++;
            }
            else checkLeft.push(transition);
        }

        for (TransitionTo transition : checkLeft) {
            if (leftActions == null || transition.isIn(leftActions)) {
                if (!forAll(transition, p)) {
                    return false;
                }
                passing++;
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
