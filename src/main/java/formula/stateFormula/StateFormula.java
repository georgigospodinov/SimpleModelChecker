package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedList;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    // formula is true and does not breach the constraint
    public abstract boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> path);

    public boolean isValidIn(State s, StateFormula constraint) {
        TransitionTo t = new TransitionTo(s, null);
        return isValidIn(t, constraint, new LinkedList<State>());
    }

    // constraint is satisfied in this state
    public abstract boolean holdsIn(TransitionTo t);
    
    // constraint is satisfied in this state as an end state
    public abstract boolean holdsInLeaf(TransitionTo t);

    /**
     * Given the current state, what is the constraint from here onwards
     * TODO
     */
    public abstract StateFormula childConstraint(TransitionTo t);
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }
    
    

}
