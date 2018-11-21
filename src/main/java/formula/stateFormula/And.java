package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedList;

public class And extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public And(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" && ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> path) {
        return constraint.holdsIn(t) && left.isValidIn(t, constraint, path) && right.isValidIn(t, constraint, path);
    }

	@Override
    public boolean holdsIn(TransitionTo t) {
        return left.holdsIn(t) && right.holdsIn(t);
    }

    public boolean holdsInLeaf(TransitionTo t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public StateFormula childConstraint(TransitionTo t) {
        return new And(left.childConstraint(t), right.childConstraint(t));
	}

}