package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class SimpleModelChecker implements ModelChecker {

    private LinkedList<String> trace;

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        System.out.println(query);

        // check true for all init states
        LinkedHashSet<State> initStates = model.getInitStates();
        trace = new LinkedList<>();
        for (State initState : initStates) {
            TransitionTo t = new TransitionTo(initState);
            Path p = new Path();
            boolean invalid = !query.isValidIn(t, p, constraint);
            if (invalid) {
                trace = p.getSequence();
                return false;
            }
        }

        return true;
    }

    @Override
    public String[] getTrace() {
        String[] t = new String[trace.size()];
        return trace.toArray(t);
    }

}
