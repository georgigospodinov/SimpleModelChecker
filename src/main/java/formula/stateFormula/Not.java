package formula.stateFormula;

import java.util.LinkedList;
import java.util.List;

import formula.FormulaParser;
import model.State;

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
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> path) {
        return !stateFormula.isValidIn(s, constraint, path);
    }

	@Override
	public boolean holdsIn(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean holdsInLeaf(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateFormula fromHere(State s) {
		// TODO Auto-generated method stub
		return null;
	}

}
