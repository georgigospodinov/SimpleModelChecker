package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO: must satisfy constraint
        System.out.println(query);

        // check true for all init states
        for (State initState : model.getInitStates()) {
            TransitionTo t = new TransitionTo(initState);
            Path p = new Path();
            if (!query.isValidIn(t, p, constraint))
                return false;
            System.out.println(p.getSequence());
        }

        return true;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
