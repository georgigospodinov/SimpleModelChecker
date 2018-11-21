package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashSet;

public class SimpleModelChecker implements ModelChecker {

    private Path trace;

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // check true for all init states
        LinkedHashSet<State> initStates = model.getInitStates();
        for (State initState : initStates) {
            TransitionTo t = new TransitionTo(initState);
            Path p = new Path();
            boolean invalid = !query.isValidIn(t, p, constraint);
            if (invalid) {
                trace = p;
                if (p.isEmpty())
                	p.push(t);
                return false;
            }
        }
        return true;
    }

    @Override
    public String[] getTrace() {
    	if (trace == null) 
    		return new String[0];
    	else
    		return trace.toTrace();
    }

}
