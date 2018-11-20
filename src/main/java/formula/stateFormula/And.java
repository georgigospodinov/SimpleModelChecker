package formula.stateFormula;

import java.util.LinkedList;
import java.util.List;

import model.State;

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
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> path) {
        return left.isValidIn(s, constraint, path) && right.isValidIn(s, constraint, path);
    }

	@Override
	public boolean holdsIn(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean holdsInLeaf(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateFormula fromHere(State s) {
		// TODO Auto-generated method stub
		return null;
	}

}