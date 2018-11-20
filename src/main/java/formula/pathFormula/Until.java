package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class Until extends PathFormula {
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
    public boolean exists(State s, LinkedList<State> visited) {
        if (rightActions != null && rightActions.size() == 0) // no acceptable path
            return false;
        if (visited.contains(s)) // cycle detection
            return false;

        if (rightActions == null && right.isValidIn(s)) { //in final state
            visited.push(s);
            return true;
        }
        if (!this.left.isValidIn(s)) // current state invalid
            return false;
        visited.push(s);

        // try right first
        for (TransitionTo t : s.getTransitions()) {
            if (rightActions == null || t.isIn(rightActions)) {
                if (right.isValidIn(t.getTrg())) {
                    visited.push(t.getTrg());
                    return true;
                }
            }
        }

        // try left on failure
        for (TransitionTo t : s.getTransitions()) {
            if (leftActions == null || t.isIn(leftActions)) {
                if (exists(t.getTrg(), visited))
                    return true;
            }
        }

        // no path found
        visited.pop();
        return false;
    }

    @Override
    public boolean forAll(State s, LinkedList<State> visited) {
        // Loop detected before final state
        if (visited.contains(s)) {
            visited.push(s);
            return false;
        }
        // in final state
        if (rightActions == null && right.isValidIn(s))
            return true;
        // in invalid state
        if (!left.isValidIn(s)) {
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
                if (!right.isValidIn(t.getTrg()))
                    checkLeft.push(t);
                else
                    passing++;
            }
            checkLeft.push(t);
        }

        // then check left 
        for (TransitionTo t : checkLeft) {
            if (leftActions == null || t.isIn(leftActions)) {
                if (!forAll(t.getTrg(), visited))
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
