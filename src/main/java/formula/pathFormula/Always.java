package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.*;

public class Always extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Always(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEn);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public boolean exists(State s, LinkedList<State> path) {
    	if (!stateFormula.isValidIn(s))
            return false;
    	// check for an always true cycle
    	if (path.contains(s)) {
    		path.push(s);
    		return true;
    	}
		path.push(s);
    	// end of path
    	if (s.getTransitions().isEmpty())
    		return true;
    	// search branches
        for (TransitionTo t : s.getTransitions()) {
            if (actions == null || t.isIn(actions)) {
                if (exists(t.getTrg(), path))
                    return true;
            }
        }
        // no path found
        path.pop();
        return false;
    }

	@Override
	public boolean forAll(State s, LinkedList<State> path) {
		/*
		StateFormula neg = new Not(stateFormula);
		Eventually e = new Eventually(neg, actions, actions);
		return !e.exists(s, path);
		*/
		if (!stateFormula.isValidIn(s))  {
			path.push(s);
            return false;
		}
		if (path.contains(s))
			return true;
		path.add(s);
        
		for (TransitionTo t : s.getTransitions()) {
            if (actions == null || t.isIn(actions)) {
            	if (! forAll(t.getTrg(), path))
                    return false;            	
            }
        }
		path.pop();
        return true;
   }
}

