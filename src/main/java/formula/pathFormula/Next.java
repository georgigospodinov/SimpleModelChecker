package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
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
	public boolean exists(State s, LinkedList<State> path) {
        for (TransitionTo t : s.getTransitions()) {
            if (actions == null || t.isIn(actions)) {
                if (stateFormula.isValidIn(t.getTrg())) {
                    path.push(t.getTrg());
                	return true;
                }
            }
        }
        return false;
	}

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        if (!this.exists(s, null)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }

        LinkedHashMap<TransitionTo, State> toRemove = new LinkedHashMap<>();
        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            if (actions == null || t.isIn(actions)) {
                State trg = t.getTrg();
                // A recognized transition makes the formula false, so we need to remove it.
                if (!stateFormula.isValidIn(trg))
                    toRemove.put(t, s);
            }
            // The transition is not recognized, so we need to remove it.
            else toRemove.put(t, s);
        }
        return toRemove;
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        if (!this.exists(s, null)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }

        LinkedHashMap<TransitionTo, State> toSave = new LinkedHashMap<>();
        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            if (actions == null || t.isIn(actions)) {
                State trg = t.getTrg();
                // A recognized transition makes the formula true, so we need to save it.
                if (stateFormula.isValidIn(trg))
                    toSave.put(t, s);
            }
        }
        return toSave;
    }

	@Override
	public boolean forAll(State s) {
		// TODO Auto-generated method stub
		return false;
	}
}
