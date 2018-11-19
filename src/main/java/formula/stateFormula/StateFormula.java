package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static formula.pathFormula.PathFormula.THIS_STATE_MAKES_ME_FALSE;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    public abstract boolean isValidIn(State s);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

    /**
     * Finds all transitions that make this formula invalid.
     * Returns a {@link java.util.LinkedHashMap} of all transitions that should be removed.
     * In the map, keys are {@link TransitionTo} and values are their source {@link State}s.
     * <p>
     * An empty map means that no transitions should be removed. (All transitions are acceptable.)
     * Null means that this formula is invalid in the given state. (Don't care about transitions.)
     */
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        if (!this.isValidIn(s))
            return THIS_STATE_MAKES_ME_FALSE;
        return new LinkedHashMap<>();
    }

    /**
     * Finds all transitions that make this formula true.
     * Returns a {@link java.util.LinkedHashMap} of all transitions that should not be removed.
     * In the map, keys are {@link TransitionTo} and values are their source {@link State}s.
     * <p>
     * An empty map means that all transitions should be removed. (No transitions are acceptable.)
     * Null means that this formula is invalid in the given state. (Don't care about transitions.)
     */
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        if (!this.isValidIn(s))
            return THIS_STATE_MAKES_ME_FALSE;
        LinkedHashMap<TransitionTo, State> toSave = new LinkedHashMap<>();
        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            toSave.put(t, s);
        }
        return toSave;
    }
    
}
