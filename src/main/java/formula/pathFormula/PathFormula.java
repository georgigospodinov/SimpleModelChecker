package formula.pathFormula;

import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import model.TransitionTo;

public abstract class PathFormula {

    public abstract void writeToBuffer(StringBuilder buffer);

    /**
     * Checks that at least one path exists that satisfies this {@link PathFormula}.
     * The sequence of {@link TransitionTo}s that make up that path are NOT saved.
     * The {@link Path} argument is used internally for recursive checks.
     * This method returns true if and only if a path exists and it abides the given constraint.
     * The check starts at the target {@link State} of the given {@link TransitionTo}.
     *
     * @param t          the {@link TransitionTo} that was made before this call
     * @param p          the {@link Path} so far (before transition t)
     * @param constraint constraint to abide to
     * @return true iff there is a path that satisfies this {@link PathFormula} and it abides the constraint
     */
    public abstract boolean exists(TransitionTo t, Path p, StateFormula constraint);

    /**
     * Checks if all paths after the given {@link TransitionTo} satisfy this {@link PathFormula}.
     * These paths all abide the constraint.
     * The check starts at the target {@link State} of the given {@link TransitionTo}.
     * The given {@link Path} is used to store a sequence of {@link TransitionTo}s that invalidate this {@link PathFormula}.
     * This will happen only if the method returns false. Otherwise it returns true.
     *
     * @param t the {@link TransitionTo} that was made before this call
     * @param p the {@link Path} so far (before transition t)
     * @param constraint constraint to abide to
     * @return true iff all paths satisfy this {@link PathFormula} and they abide the constraint
     */
    public abstract boolean forAll(TransitionTo t, Path p, StateFormula constraint);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
