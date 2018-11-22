package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.Path;
import model.TransitionTo;

import static formula.stateFormula.BoolProp.TRUE;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;

    public ThereExists(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
    	return constraint.holdsIn(t) && pathFormula.exists(t, new Path(), constraint);
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return new BoolProp(pathFormula.exists(new TransitionTo(t.getSrc()), new Path(), TRUE));
    }

}
