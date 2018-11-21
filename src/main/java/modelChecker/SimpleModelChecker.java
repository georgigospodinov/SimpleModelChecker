package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO: must satisfy constraint
        System.out.println(query);

    	LinkedList<State> stateList = new LinkedList<>();
        // check true for all init states
        for (State initState : model.getInitStates()) {
            TransitionTo t = new TransitionTo(initState);
            if (!query.isValidIn(t, constraint, stateList))
                return false;
        }

        return true;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
