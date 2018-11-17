package modelChecker;

import formula.machines.FormulaMachine;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.Transition;

import java.util.LinkedHashSet;

public class SimpleModelChecker implements ModelChecker {

    private LinkedHashSet<State> currentStates;
    private State currentState;
    private LinkedHashSet<State> visitedStates;
    private FormulaMachine constraintMachine, queryMachine;

    public void makeRandomTransitionOnCurrentState(Model m) {
        Transition t = m.getTransitionsFrom(currentState).iterator().next();
        constraintMachine.transition(t.getActions()[0]);
        queryMachine.transition(t.getActions()[0]);
        currentState = m.getState(t.getTarget());
    }

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

    public void checkCurrentStates(StateFormula constraint, StateFormula query) {
        for (State s : currentStates) {
            System.out.println("In state " + s);
            System.out.println("Constraint " + constraint + " is " + constraint.isValidIn(s));
            System.out.println("Query " + query + " is " + query.isValidIn(s));
            System.out.println();
        }
        System.out.println("In state " + currentState);
        System.out.println(constraintMachine.isAccepting(currentState));
        System.out.println(queryMachine.isAccepting(currentState));
        System.out.println();
    }

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        currentStates = model.getInitStates();
        currentState = currentStates.iterator().next();
        visitedStates = new LinkedHashSet<>(currentStates);
        constraintMachine = FormulaMachine.createFormulaMachine(constraint);
        queryMachine = FormulaMachine.createFormulaMachine(query);
        checkCurrentStates(constraint, query);
        // TODO: Do we need to repeat this call until we run out of states?
        makeAllTransitions(model);
        makeRandomTransitionOnCurrentState(model);
        checkCurrentStates(constraint, query);
        return false;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
