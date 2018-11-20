package formula.pathFormula;

import model.State;

import java.util.LinkedList;
import java.util.List;

public abstract class PathFormula {

    public abstract void writeToBuffer(StringBuilder buffer);

    // TODO: Do we need to return true or false? The path argument says it all.
    /*
    Sample code:
    protected LinkedList<State> exists(State s, LinkedList path) {
        if (path == null)
            path = new LinkedList<>();

        ...
    }
    public LinkedList<State> exists(State s) {
        return exists(s, null);
    }
     */
    // TODO: should we save transitions instead of states?
    /*
     * Each transition knows its target
     * And we thus avoid confusion if two states are connected with multiple transitions.
     */

    /**
     * Checks that at least one path exists that satisfies the formula.
     * The sequence of {@link State}s that make up that path are stacked in the given {@link LinkedList}.
     * After this method returns, you can iterate the list in descending order with {@link LinkedList#descendingIterator()}
     * to obtain the correct sequence of {@link State}s.
     * <p>
     * Inner calls to this method use the same {@link LinkedList} as a stack.
     * They push and pop {@link State}s as needed.
     *
     * @param s    the {@link State} from which this path should start
     * @param path the stack to which {@link State}s are pushed to make up a path that satisfies this {@link PathFormula}
     * @return true if there is a path, in which case the path argument contains a path
     * false if there isn't such a path, in which case the path argument is empty
     */
    public abstract boolean exists(State s, LinkedList<State> path, LinkedList<State> basePath);
	public abstract boolean forAll(State s, LinkedList<State> path, LinkedList<State> basePath);
	
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
