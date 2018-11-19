package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.*;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.LinkedList;

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
    public boolean isValidIn(State s) {
    	LinkedList<State> path = new LinkedList<>();
        return pathFormula.exists(s, path); 
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        // Each PathFormula implements this in its own way.
        return pathFormula.shouldPrune(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        // Each PathFormula implements this in its own way.
        return pathFormula.shouldNotPrune(s);
    }
}
