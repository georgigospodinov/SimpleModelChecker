package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;

import java.util.HashSet;
import java.util.Set;

public class Always extends WeakUntil {
    public final StateFormula stateFormula;

    public Always(StateFormula stateFormula, Set<String> actions) {
    	super(stateFormula, new BoolProp(false), actions, new HashSet<String>());
        this.stateFormula = stateFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }
}

