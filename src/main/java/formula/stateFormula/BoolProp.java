package formula.stateFormula;

import model.Path;
import model.TransitionTo;

public class BoolProp extends StateFormula {
    public static final BoolProp TRUE = new BoolProp(true);
    public static final BoolProp FALSE = new BoolProp(false);

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
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        return constraint.holdsIn(t) && value;
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
