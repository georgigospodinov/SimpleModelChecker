package formula.stateFormula;

import model.Path;
import model.TransitionTo;

public class And extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public And(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" && ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        return constraint.holdsIn(t) && left.isValidIn(t, p, constraint) && right.isValidIn(t, p, constraint);
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return new And(left.childConstraint(t), right.childConstraint(t));
    }

}