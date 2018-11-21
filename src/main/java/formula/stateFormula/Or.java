package formula.stateFormula;

import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;

public class Or extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public Or(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" || ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> path) {
        return constraint.holdsIn(t) && (left.isValidIn(t, constraint, path) || right.isValidIn(t, constraint, path));
    }

    @Override
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        if (!constraint.holdsIn(t)) {
            return false;
        }
        if (left.isValidIn(t, p, constraint) || right.isValidIn(t, p, constraint))
            return true;
        p.push(t);
        return false;
    }

    @Override
    public boolean holdsIn(TransitionTo t) {
        return left.holdsIn(t) || right.holdsIn(t);
    }

    @Override
    public boolean holdsInLeaf(TransitionTo t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return new Or(left.childConstraint(t), right.childConstraint(t));
    }

}