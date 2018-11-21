package formula.pathFormula;

import formula.stateFormula.StateFormula;
import model.Path;
import model.TransitionTo;

public abstract class PathFormula {

    public abstract void writeToBuffer(StringBuilder buffer);

    //    /**
//     * Checks that at least one path exists that satisfies the formula.
//     * The sequence of {@link State}s that make up that path are stacked in the given {@link LinkedList}.
//     * After this method returns, you can iterate the list in descending order with {@link LinkedList#descendingIterator()}
//     * to obtain the correct sequence of {@link State}s.
//     * <p>
//     * Inner calls to this method use the same {@link LinkedList} as a stack.
//     * They push and pop {@link State}s as needed.
//     *
//     * @param t    the {@link State} from which this path should start
//     * @param path the stack to which {@link State}s are pushed to make up a path that satisfies this {@link PathFormula}
//     * @return true if there is a path, in which case the path argument contains a path
//     * false if there isn't such a path, in which case the path argument is empty
//     */
    public abstract boolean exists(TransitionTo t, Path p, StateFormula constraint);

    public abstract boolean forAll(TransitionTo t, Path p, StateFormula constraint);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
