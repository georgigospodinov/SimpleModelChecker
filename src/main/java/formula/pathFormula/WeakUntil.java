package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class WeakUntil extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

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
    public boolean exists(State s, LinkedList<State> visited) {
        if (visited.contains(s)) { // cycle detection 
            visited.push(s);
            return true;
        }
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
        int onwards = 0;
        for (TransitionTo t : s.getTransitions()) {
            if (leftActions == null || t.isIn(leftActions)) {
                onwards++;
                if (exists(t.getTrg(), visited))
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
        if (onwards == 0) // || (leftActions != null && leftActions.isEmpty()) ||s.getTransitions().isEmpty())
            //dead end
            return true;
        else {
            visited.pop();
            return false;
        }
    }

    @Override
    public boolean forAll(State s, LinkedList<State> visited) {
        // Loop detected before final state
        if (visited.contains(s)) {
            return true;
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
        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        for (TransitionTo t : s.getTransitions()) {
            if (rightActions == null || t.isIn(rightActions)) {
                if (!right.isValidIn(t.getTrg()))
                    checkLeft.push(t);
            }
            checkLeft.push(t);
        }

        // then check left 
        for (TransitionTo t : checkLeft) {
            if (leftActions == null || t.isIn(leftActions)) {
                if (!forAll(t.getTrg(), visited))
                    return false;
            }
        }

        // all paths if any accepted
        // weak until accepts dead ends so no need to count
        visited.pop();
        return true;
    }

}
