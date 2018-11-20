package formula.stateFormula;

import java.util.LinkedList;
import java.util.List;

import model.State;

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
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> path) {
        return constraint.holdsIn(s) && left.isValidIn(s, constraint, path) || right.isValidIn(s, constraint, path);
    }

	@Override
	public boolean holdsIn(State s) {
		return left.holdsIn(s) || right.holdsIn(s);
	}

	@Override
	public boolean holdsInLeaf(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateFormula childConstraint(State s) {
		return new Or(left.childConstraint(s), right.childConstraint(s));
	}

}