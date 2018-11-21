package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class SimpleModelChecker implements ModelChecker {

    private LinkedList<LinkedList<String>> traces;

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        System.out.println(query);

        // check true for all init states
        LinkedHashSet<State> initStates = model.getInitStates();
        traces = new LinkedList<>();

        for (State initState : initStates) {
            TransitionTo t = new TransitionTo(initState);
            Path p = new Path();
            boolean invalid = !query.isValidIn(t, p, constraint);
            LinkedList<String> seq = p.getSequence();
            System.out.println(seq);
            traces.addLast(seq);
            if (invalid)
                return false;
        }

        return true;
    }

    @Override
    public String[] getTrace() {
        LinkedList<String> first = traces.peekFirst();
        String[] t = new String[first.size()];
        return first.toArray(t);
    }

}
