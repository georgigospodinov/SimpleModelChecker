package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedList;

public class AtomicProp extends StateFormula {
    public final String label;

    public AtomicProp(String label) {
        this.label = label;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(" " + label + " ");
    }

    @Override
    public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> path) {
        if (!constraint.holdsIn(t))
    		return false;
        String[] labels = t.getTrg().getLabel();
        for (String l : labels) {
            if (label.equals(l)) 
                return true;
        }        
        return false;
    }

	@Override
    public boolean holdsIn(TransitionTo t) {
        return isValidIn(t, new BoolProp(true), new LinkedList<State>());
	}

	@Override
    public boolean holdsInLeaf(TransitionTo t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public StateFormula childConstraint(TransitionTo t) {
        return new BoolProp(holdsIn(t));
	}

}
