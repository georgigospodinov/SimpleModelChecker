package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.State;


import java.util.LinkedList;

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
    	LinkedList<State> path = new LinkedList<>();
        return pathFormula.forAll(s, path); 
    }
/*
    private boolean validEventually(State s, Eventually path) {
        //Until u = new Until(new BoolProp(true), path.stateFormula, path.getLeftActions(), path.getRightActions());
        return false;// validUntil(s, u);
    }
*/
}
