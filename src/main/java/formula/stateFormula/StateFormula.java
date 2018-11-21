package formula.stateFormula;

import formula.pathFormula.PathFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import static formula.stateFormula.BoolProp.TRUE;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    /**
     * Determines if this {@link StateFormula} is valid in the target {@link State} of the given {@link TransitionTo}.
     * And if it is abiding the constraint.
     * Uses the given {@link Path} p to produce a trace. This path gets passed to calls to
     * {@link PathFormula#exists(TransitionTo, Path, StateFormula)} and
     * {@link PathFormula#forAll(TransitionTo, Path, StateFormula)}.
     *
     * @param t          the {@link TransitionTo} that was made before this call
     * @param p          path so far (before that transition)
     * @param constraint constraint to abide to
     * @return true iff this {@link StateFormula} is valid in {@link TransitionTo#trg}
     *
     * @see PathFormula#exists(TransitionTo, Path, StateFormula)
     * @see PathFormula#forAll(TransitionTo, Path, StateFormula)
     */
    public abstract boolean isValidIn(TransitionTo t, Path p, StateFormula constraint);

    /**
     * Determines if this {@link StateFormula} is valid in the given {@link State}, abiding the constraint.
     * This method calls {@link StateFormula#isValidIn(TransitionTo, Path, StateFormula)} and is equivalent to
     * 'isValidIn(new Transition(s), new Path(), constraint)'.
     *
     * @param s          the {@link State} where this {@link StateFormula} is to be checked
     * @param constraint the {@link StateFormula} representing the constraint which must hold true
     * @return true iff this {@link StateFormula} is valid in the given {@link State} s and abides the constraint
     *
     * @see Path
     */
    public boolean isValidIn(State s, StateFormula constraint) {
        TransitionTo t = new TransitionTo(s);
        return isValidIn(t, new Path(), constraint);
    }


    /**
     * Determines if this {@link StateFormula} is satisfied in the target {@link State} of the given {@link TransitionTo}.
     * This method ignores constraints and does not produce any trace.
     * It is used to check the constraint.
     * This method is equivalent to 'isValidIn(t, new Path(), TRUE)'.
     *
     * @param t the {@link TransitionTo} made
     * @return true iff this {@link StateFormula} is valid in {@link TransitionTo#trg}
     * @see Path
     * @see BoolProp#TRUE
     */
    public boolean holdsIn(TransitionTo t) {
        return isValidIn(t, new Path(), TRUE);
    }

    /**
     * Given the last {@link TransitionTo}, this method determines what part of the constraint remains to be satisfied.
     * This method returns {@link BoolProp#FALSE} if the constraint is not satisfied.
     * In that case, calling {@link StateFormula#holdsIn(TransitionTo)} on the child constraint will return false.
     *
     * @param t the {@link TransitionTo} that was made
     * @return a {@link StateFormula} representing the constraint that needs to be satisfied.
     */
    public abstract StateFormula childConstraint(TransitionTo t);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
