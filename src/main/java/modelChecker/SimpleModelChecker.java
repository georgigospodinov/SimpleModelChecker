package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO: must satisfy constraint
//        LinkedHashMap<TransitionTo, State> toRemove = constraint.shouldPrune(model.getInitStates().iterator().next());
        // This map now contains all the transitions that need to be removed, so that the model fits the constraint. (In theory)
//        Set<TransitionTo> ts = toRemove.keySet();
//        for (TransitionTo t : ts) {
//            State s = toRemove.get(t);
//            s.removeTransition(t);
//        }
        // We might be able to do that by having a method that returns all paths, for which a StateFormula is true.
        System.out.println(query);
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
