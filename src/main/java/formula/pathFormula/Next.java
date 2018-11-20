package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class Next extends PathFormula {
	//TODO replace 
	StateFormula constraint = new BoolProp(true);
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Next(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NEXT_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

	@Override
	public boolean exists(State s, LinkedList<State> path) {
        for (TransitionTo t : s.getTransitions()) {
            if (actions == null || t.isIn(actions)) {
                if (stateFormula.isValidIn(t.getTrg(), constraint)) {
                    path.push(t.getTrg());
                	return true;
                }
            }
        }
        return false;
	}

	@Override
	public boolean forAll(State s, LinkedList<State> path) {
		if (actions != null && actions.isEmpty())
			return false;
		int paths = 0;
        for (TransitionTo t : s.getTransitions()) {
            if (actions == null || t.isIn(actions)) {
            	paths++;
                if (! stateFormula.isValidIn(t.getTrg(), constraint)) {
                    path.push(t.getTrg());
                	return false;
                }
            }
        }
        // We need at least one valid path
        return paths > 0;
	}
}
