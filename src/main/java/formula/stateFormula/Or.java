package formula.stateFormula;

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
    public boolean isValidIn(State s, StateFormula constraint) {
        return left.isValidIn(s, constraint) || right.isValidIn(s, constraint);
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