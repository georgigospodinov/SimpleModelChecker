package formula.stateFormula;

import model.State;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    // formula is true and does not breach the constraint
    public abstract boolean isValidIn(State s, StateFormula constraint);

    // constraint is satisfied in this state
    public abstract boolean holdsIn(State s);
    
    // constraint is satisfied in this state as an end state
    public abstract boolean holdsInLeaf(State s);
    
    /*
     * Given the current state, what is the constraint from here onwards
     * 
     */
    public abstract StateFormula fromHere(State s);
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }
    
    

}
