package formula.pathFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;

public abstract class PathFormula {

    /**
     * Null pointer used by the pruning methods.
     */
    public static final LinkedHashMap<TransitionTo, State> THIS_STATE_MAKES_ME_FALSE = null;

    public abstract void writeToBuffer(StringBuilder buffer);
    
    public abstract boolean pathFrom(State s);

    /**
     * Finds all transitions that make this formula invalid.
     * Returns a {@link java.util.LinkedHashMap} of all transitions that should be removed.
     * In the map, keys are {@link TransitionTo} and values are their source {@link State}s.
     * <p>
     * An empty map means that no transitions should be removed. (All transitions are acceptable.)
     * Null means that this formula is invalid in the given state. (Don't care about transitions.)
     */
    public abstract LinkedHashMap<TransitionTo, State> shouldPrune(State s);

    /**
     * Finds all transitions that make this formula true.
     * Returns a {@link java.util.LinkedHashMap} of all transitions that should not be removed.
     * In the map, keys are {@link TransitionTo} and values are their source {@link State}s.
     * <p>
     * An empty map means that all transitions should be removed. (No transitions are acceptable.)
     * Null means that this formula is invalid in the given state. (Don't care about transitions.)
     */
    public abstract LinkedHashMap<TransitionTo, State> shouldNotPrune(State s);
}
