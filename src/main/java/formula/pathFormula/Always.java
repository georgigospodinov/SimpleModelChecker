package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;

import java.util.*;

//public class Always extends PathFormula {
public class Always extends WeakUntil {
    public final StateFormula stateFormula;
    //private Set<String> actions;

    public Always(StateFormula stateFormula, Set<String> actions) {
    	super(stateFormula, new BoolProp(false), actions, new HashSet<String>());
        this.stateFormula = stateFormula;
        //this.actions = actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }
}

