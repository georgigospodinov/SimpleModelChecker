package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class SimpleModelChecker implements ModelChecker {

    private LinkedList<State> currentStates;
    private LinkedHashSet<State> visitedStates;

    public void allPossibleTransitions(Model m, State s) {

    }

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO Auto-generated method stub
        currentStates = model.getInitStates();
        visitedStates = new LinkedHashSet<>(currentStates);
        return false;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
