package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class WeakUntil extends PathFormula {
	//TODO replace 
	StateFormula constraint = new BoolProp(true);
	
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
    public boolean exists(TransitionTo t, LinkedList<State> visited, LinkedList<State> basePath) {
        State s = t.getTrg();
        if (visited.contains(s)) { // cycle detection
            visited.push(s);
            return true;
        }

        LinkedList<State> fullPath = new LinkedList<>();
        fullPath.addAll(visited);
        fullPath.addAll(basePath);
        if (rightActions == null && right.isValidIn(t, constraint, fullPath)) { //in final state
            visited.push(s);
            return true;
        }
        fullPath = new LinkedList<>();
        fullPath.addAll(visited);
        fullPath.addAll(basePath);
        if (!this.left.isValidIn(t, constraint, fullPath)) // current state invalid
            return false;
        visited.push(s);

        // try right first
        for (TransitionTo tran : s.getTransitions()) {
            if (rightActions == null || tran.isIn(rightActions)) {
                fullPath = new LinkedList<>();
                fullPath.addAll(visited);
                fullPath.addAll(basePath);
                if (right.isValidIn(tran, constraint, fullPath)) {
                    visited.push(tran.getTrg());
                    return true;
                }
            }
        }

        // try left on failure
        int onwards = 0;
        for (TransitionTo tran : s.getTransitions()) {
            if (leftActions == null || tran.isIn(leftActions)) {
                onwards++;
                fullPath = new LinkedList<>();
                fullPath.addAll(visited);
                fullPath.addAll(basePath);
                if (exists(tran, visited, fullPath))
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
    public boolean forAll(TransitionTo t, LinkedList<State> visited, LinkedList<State> basePath) {
        // Loop detected before final state
        State s = t.getTrg();
        if (visited.contains(s)) {
            return true;
        }
        // in final state

        LinkedList<State> fullPath = new LinkedList<>();
        fullPath.addAll(visited);
        fullPath.addAll(basePath);
        if (rightActions == null && right.isValidIn(t, constraint, fullPath))
            return true;
        // in invalid state
        if (!left.isValidIn(t, constraint, fullPath)) {
            visited.push(s);
            return false;
        }
        visited.push(s);

        // check transitions to the right
        // only check left on failed branches
        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        for (TransitionTo tran : s.getTransitions()) {
            if (rightActions == null || tran.isIn(rightActions)) {
                // TODO: GREGOR!!! Check that this looks/works as expected.
                if (!right.isValidIn(tran, constraint, fullPath)) {
                    checkLeft.push(tran);
                    fullPath = new LinkedList<State>();
                    fullPath.addAll(visited);
                    fullPath.addAll(basePath);
                }
            }
            checkLeft.push(tran);
        }

        // then check left 
        for (TransitionTo tran : checkLeft) {
            if (leftActions == null || tran.isIn(leftActions)) {
                if (!forAll(tran, visited, basePath))
                    return false;
            }
        }

        // all paths if any accepted
        // weak until accepts dead ends so no need to count
        visited.pop();
        return true;
    }

}
