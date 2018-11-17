package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO: must satisfy constraint
        // We might be able to do that by having a method that returns all paths, for which a StateFormula is true.
        for (State initState : model.getInitStates()) {
            if (query.isValidIn(initState))
                return true;
        }

        return false;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
