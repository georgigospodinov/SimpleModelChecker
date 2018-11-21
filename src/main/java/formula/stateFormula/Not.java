package formula.stateFormula;

import formula.FormulaParser;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;

public class Not extends StateFormula {
    public final StateFormula stateFormula;

    public Not(StateFormula stateFormula) {
        this.stateFormula = stateFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NOT_TOKEN);
        buffer.append("(");
        stateFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> path) {
        return constraint.holdsIn(t) && !stateFormula.isValidIn(t, constraint, path);
    }

    @Override
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        if (!constraint.holdsIn(t)) {
            return false;
        }
        if (stateFormula.isValidIn(t, p, constraint)) {
            p.push(t);
            return false;
        }
        return true;
    }

    @Override
    public boolean holdsIn(TransitionTo t) {
        return isValidIn(t, new BoolProp(true), new LinkedList<State>());
    }

    @Override
    public boolean holdsInLeaf(TransitionTo t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return new Not(stateFormula.childConstraint(t));
    }

}
