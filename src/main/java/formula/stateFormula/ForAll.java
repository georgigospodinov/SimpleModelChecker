package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.Neg;
import formula.pathFormula.PathFormula;
import model.State;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;

    public ForAll(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(State s) {
        Not n = new Not(new ThereExists(new Neg(pathFormula)));
        return n.isValidIn(s);
    }
}
