package formula.stateFormula;

import java.util.LinkedList;

import model.State;

public class BoolProp extends StateFormula {
    public final boolean value;

    public BoolProp(boolean value) {
        this.value = value;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        String stringValue = (value) ? "True" : "False";
        buffer.append(" " + stringValue + " ");
    }

    @Override
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> path) {
    	if (constraint.holdsInLeaf(s))
    		return value;
    	else
    		return false;
    }

	@Override
	public boolean holdsIn(State s) {
		return value;
	}

	@Override
	public boolean holdsInLeaf(State s) {
		return value;
	}

	@Override
	public StateFormula childConstraint(State s) {
		return this;
	}

}
