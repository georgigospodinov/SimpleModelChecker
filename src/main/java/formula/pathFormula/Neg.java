package formula.pathFormula;

import formula.FormulaParser;
import model.State;

public class Neg extends PathFormula {
    public final PathFormula pathFormula;

    public Neg(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NOT_TOKEN);
        pathFormula.writeToBuffer(buffer);
    }

    @Override
    public boolean skipPathSymbol(State s) {
        return !pathFormula.skipPathSymbol(s);
    }
}
