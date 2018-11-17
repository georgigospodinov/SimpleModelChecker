package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;
import java.util.Set;

public class Next extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Next(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NEXT_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

	@Override
	public boolean pathFrom(State s) {
        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            if (actions == null || t.isIn(actions)) {
                State next = t.getTrg();
                // At least one next state makes the stateFormula true.
                if (stateFormula.isValidIn(next))
                    return true;
            }
        }
        return false;
	}
}
