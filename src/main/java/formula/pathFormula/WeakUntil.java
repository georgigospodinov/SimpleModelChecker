package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

import static formula.stateFormula.BoolProp.TRUE;

public class WeakUntil extends PathFormula {
    //TODO replace
    StateFormula constraint = new BoolProp(true);

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

        if (rightActions == null && right.isValidIn(t, p, this.constraint)) {
            p.push(t);
            return true;
        }

        if (!left.isValidIn(t, p, this.constraint))
            return false;

        p.push(t);
        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (rightActions == null || transition.isIn(rightActions)) {
                if (right.isValidIn(transition, p, this.constraint)) {
                    p.push(transition);
                    return true;
                }
            }
        }

        int onwards = 0;
        for (TransitionTo transition : current.getTransitions()) {
            if (leftActions == null || transition.isIn(leftActions)) {
                onwards++;
                if (exists(transition, p, TRUE))
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
            p.pop();
            return false;
        }
    }

    @Override
    public boolean forAll(TransitionTo t, Path p, StateFormula constraint) {
        if (p.contains(t)) {
            return true;
        }

        if (rightActions == null && right.isValidIn(t, p, this.constraint))
            return true;

        p.push(t);
        if (!left.isValidIn(t, p, this.constraint)) {
            return false;
        }

        LinkedList<TransitionTo> checkLeft = new LinkedList<>();
        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (rightActions == null || transition.isIn(rightActions)) {
                if (!right.isValidIn(transition, p, this.constraint)) {
                    checkLeft.push(transition);
                }
            }
            else checkLeft.push(transition);
        }

        for (TransitionTo transition : checkLeft) {
            if (leftActions == null || transition.isIn(leftActions)) {
                if (!forAll(transition, p, TRUE)) {
                    return false;
                }
            }
        }

        p.pop();
        return true;
    }
}
