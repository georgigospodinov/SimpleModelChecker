package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.Path;
import model.TransitionTo;

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
        return constraint.holdsIn(t) && pathFormula.exists(t, p);
    }

    @Override
    public boolean holdsIn(TransitionTo t) {
        return isValidIn(t, new Path(), new BoolProp(true));
    }

    @Override
    public boolean holdsInLeaf(TransitionTo t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        return new BoolProp(holdsIn(t));
    }

}
