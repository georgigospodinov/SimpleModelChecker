package formula.stateFormula;

import model.State;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    public abstract boolean isValidIn(State s);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
