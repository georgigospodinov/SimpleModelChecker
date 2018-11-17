package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;

import java.util.Set;

public class Always extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Always(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEn);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public boolean skipPathSymbol(State s) {
        return stateFormula.isValidIn(s);
    }

}
