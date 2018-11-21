package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;

import java.util.Set;

public class Eventually extends Until {

    public Eventually(StateFormula stateFormula, Set<String> leftActions, Set<String> rightActions) {
        super(new BoolProp(true), stateFormula, leftActions, rightActions);
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.EVENTUALLY_TOKEN);
        right.writeToBuffer(buffer);
    }

}
