package formula.stateFormula;

import model.Path;
import model.TransitionTo;

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
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        if (!constraint.holdsIn(t)) {
            return false;
        }
        String[] labels = t.getTrg().getLabel();
        for (String l : labels) {
            if (label.equals(l))
                return true;
        }
        return false;
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
    	String[] labels = t.getSrc().getLabel();
        for (String l : labels) {
            if (label.equals(l))
                return new BoolProp(true);
        }
        return new BoolProp(false);
        //return new BoolProp(holdsIn(t));
    }

}
