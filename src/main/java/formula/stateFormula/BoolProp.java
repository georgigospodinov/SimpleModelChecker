package formula.stateFormula;

import model.Path;
import model.TransitionTo;

public class BoolProp extends StateFormula {
    /**
     * A global constant to facilitate method calls and reduce object creation overhead.
     */
    public static final BoolProp TRUE = new BoolProp(true);
    /** A global constant to facilitate method calls and reduce object creation overhead.*/
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

    /**
     * Overwrites the inherited {@link StateFormula#holdsIn(TransitionTo)} method,
     * so that the program does not get into an infinite loop between
     * {@link StateFormula#holdsIn(TransitionTo)} and {@link StateFormula#isValidIn(TransitionTo, Path, StateFormula)}.
     *
     * @param t the {@link TransitionTo} made
     * @return this {@link BoolProp#value}
     */
    @Override
    public boolean holdsIn(TransitionTo t) {
        return value;
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return this;
    }

}
