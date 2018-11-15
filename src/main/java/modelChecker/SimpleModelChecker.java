package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.Transition;

import java.util.LinkedHashSet;

public class SimpleModelChecker implements ModelChecker {

    private LinkedHashSet<State> currentStates;
    private LinkedHashSet<State> visitedStates;

    public void makeTransitionsFrom(Model m, State s) {
        LinkedHashSet<Transition> transitions = m.getTransitionsFrom(s);
        // TODO What do we do with the action of a transition? t.getAction();
        for (Transition t : transitions) {
            State next = m.getState(t.getTarget());
            // Ignore visited states.
            if (visitedStates.contains(next))
                continue;
            // This line may cause ConcurrentModificationException if this method is executed on currentStates.
            currentStates.add(next);
        }
    }

    public void makeAllTransitions(Model m) {
        // Place all current states in a new collection, so that we don't get ConcurrentModificationException when calling makeTransitionsFrom.
        // TODO: There probably is a more optimal way to this.
        LinkedHashSet<State> currents = new LinkedHashSet<>(currentStates);
        currentStates.clear();
        for (State current : currents) {
            makeTransitionsFrom(m, current);
        }
    }

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        currentStates = model.getInitStates();
        visitedStates = new LinkedHashSet<>(currentStates);
        System.out.println("current states = " + currentStates);
        // TODO: Do we need to repeat this call until we run out of states?
        makeAllTransitions(model);
        System.out.println("current states = " + currentStates);
        return false;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
