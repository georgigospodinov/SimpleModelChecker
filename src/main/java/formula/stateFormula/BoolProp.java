package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedList;

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
	public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> path) {
		if (constraint.holdsInLeaf(t))
    		return value;
    	else
    		return false;
    }

	@Override
	public boolean holdsIn(TransitionTo t) {
		return value;
	}

	@Override
	public boolean holdsInLeaf(TransitionTo t) {
		return value;
	}

	@Override
	public StateFormula childConstraint(TransitionTo t) {
		return this;
	}

}
