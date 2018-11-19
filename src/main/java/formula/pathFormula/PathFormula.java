package formula.pathFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public abstract class PathFormula {

    /**
     * Null pointer used by the pruning methods.
     */
    public static final LinkedHashMap<TransitionTo, State> THIS_STATE_MAKES_ME_FALSE = null;

    public abstract void writeToBuffer(StringBuilder buffer);
    public abstract boolean exists(State s, LinkedList<State> path);
	public abstract boolean forAll(State s, LinkedList<State> path);
	
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }


}
