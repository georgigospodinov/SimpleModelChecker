package formula.stateFormula;

import formula.FormulaParser;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;

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
    public boolean isValidIn(State s) {
        return !stateFormula.isValidIn(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        return stateFormula.shouldNotPrune(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        return stateFormula.shouldPrune(s);
    }
}
