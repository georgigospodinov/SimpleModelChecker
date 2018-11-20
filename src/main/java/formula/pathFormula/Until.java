package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
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
    public boolean exists(State s, LinkedList<State> visited, LinkedList<State> basePath) {
        if (rightActions != null && rightActions.size() == 0) // no acceptable path
            return false;
        if (visited.contains(s)) // cycle detection
            return false;

        LinkedList<State> fullPath = new LinkedList<State>();
        fullPath.addAll(visited);
        fullPath.addAll(basePath);
        if (rightActions == null && right.isValidIn(s, constraint, fullPath)) { //in final state
            visited.push(s);
            return true;
        }
        if (!this.left.isValidIn(s, constraint, fullPath)) // current state invalid
            return false;
        visited.push(s);

        // try right first
        for (TransitionTo t : s.getTransitions()) {
            if (rightActions == null || t.isIn(rightActions)) {
                if (right.isValidIn(t.getTrg(), constraint, fullPath)) {
                    visited.push(t.getTrg());
                    return true;
                }
            }
        }

        // try left on failure
        for (TransitionTo t : s.getTransitions()) {
            if (leftActions == null || t.isIn(leftActions)) {
                if (exists(t.getTrg(), visited, basePath))
                    return true;
            }
        }

        // no path found
        visited.pop();
        return false;
    }

    @Override
    public boolean forAll(State s, LinkedList<State> visited, LinkedList<State> basePath) {
        // Loop detected before final state
        if (visited.contains(s)) {
            visited.push(s);
            return false;
        }
        // in final state
        LinkedList<State> fullPath = new LinkedList<State>();
        fullPath.addAll(visited);
        fullPath.addAll(basePath);
        if (rightActions == null && right.isValidIn(s, constraint, fullPath))
            return true;
        // in invalid state
        if (!left.isValidIn(s, constraint, fullPath)) {
            visited.push(s);
            return false;
        }
        visited.push(s);

        // check transitions to the right
        // only check left on failed branches
        int passing = 0;
        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        for (TransitionTo t : s.getTransitions()) {
            if (rightActions == null || t.isIn(rightActions)) {
                if (!right.isValidIn(t.getTrg(), constraint, fullPath))
                    checkLeft.push(t);
                else
                    passing++;
            }
            checkLeft.push(t);
        }

        // then check left 
        for (TransitionTo t : checkLeft) {
            if (leftActions == null || t.isIn(leftActions)) {
                if (!forAll(t.getTrg(), visited, basePath))
                    return false;
                passing++;
            }
        }

        if (passing > 0) {
            // there are passing paths and none failing
            visited.pop();
            return true;
        }
        else
            //no passing paths
            //fail as strong until
            return false;

    }

}
