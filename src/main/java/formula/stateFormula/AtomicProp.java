package formula.stateFormula;

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
    public boolean isValidIn(State s) {
        String[] labels = s.getLabel();
        for (String l : labels) {
            if (label.equals(l))
                return true;
        }
        return false;
    }

}
