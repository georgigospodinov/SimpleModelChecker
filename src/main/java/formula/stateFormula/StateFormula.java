package formula.stateFormula;

import model.Path;
import model.State;
import model.TransitionTo;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    // formula is true and does not breach the constraint

//    protected boolean isValidInInternal(TransitionTo t, Path p) {
//        throw new UnsupportedOperationException("Not yet implemented!");
//    }
//

    /**
     * TODO
     * On fail, add the given transition t to Path p.
     *
     * @param t          transition just made
     * @param p          path so far (before that transition)
     * @param constraint constraint to check
     * @return true if all ok else false
     */
    public abstract boolean isValidIn(TransitionTo t, Path p, StateFormula constraint);

    public boolean isValidIn(State s, StateFormula constraint) {
        TransitionTo t = new TransitionTo(s);
        return isValidIn(t, new Path(), constraint);
    }

    // constraint is satisfied in this state
    public abstract boolean holdsIn(TransitionTo t);
    
    // constraint is satisfied in this state as an end state
    public abstract boolean holdsInLeaf(TransitionTo t);

    /**
     * Given the current state, what is the constraint from here onwards
     * TODO javadoc
     */
    public abstract StateFormula childConstraint(TransitionTo t);
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
