package formula.stateFormula;

import formula.FormulaParser;
import model.Path;
import model.TransitionTo;

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
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
    	return constraint.holdsIn(t) && !stateFormula.isValidIn(t, p, constraint);
    }
    

    public boolean holdsIn(TransitionTo t) {
        return !stateFormula.holdsIn(t);
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return new Not(stateFormula.childConstraint(t));
    }

}
