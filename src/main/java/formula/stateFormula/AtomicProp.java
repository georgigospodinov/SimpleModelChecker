package formula.stateFormula;

import java.util.LinkedList;
import java.util.List;

import model.State;

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
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> path) {
        String[] labels = s.getLabel();
        for (String l : labels) {
            if (label.equals(l))
                return true;
        }
        return false;
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
