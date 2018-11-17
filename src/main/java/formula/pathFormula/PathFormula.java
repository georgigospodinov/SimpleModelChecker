package formula.pathFormula;

import model.State;

public abstract class PathFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    /**
     * This is a stub method,
     * so that we can check the {@link formula.stateFormula.StateFormula#isValidIn(State)} method.
     */
    public abstract boolean skipPathSymbol(State s);
    
    public abstract boolean pathFrom(State s);
}
