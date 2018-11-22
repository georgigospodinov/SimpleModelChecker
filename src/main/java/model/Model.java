package model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;

/**
 * A model is consist of states and transitions
 */
public class Model {
    private State[] states;
    private Transition[] transitions;

    public static Model parseModel(String filePath) throws IOException {
        Gson gson = new Gson();
        // This fills in the arrays of states and transitions.
        Model model = gson.fromJson(new FileReader(filePath), Model.class);
        model.build();
        return model;
    }

    /**
     * Builds relations between {@link State}s and {@link TransitionTo}s, as need for optimization.
     */
    private void build() {
        for (Transition t : transitions) {
            State src = getState(t.getSource());
            State trg = getState(t.getTarget());
            src.addTransition(trg, t.getActions());
        }
    }

    /**
     * Returns the list of the states
     *
     * @return list of state for the given model
     */
    public State[] getStates() {
        return states;
    }

    /**
     * Returns the {@link State} object with the given name.
     *
     * @param name the name of the state
     * @return the object representing that state
     */
    public State getState(String name) {
        for (State s : states)
            if (s.getName().equals(name))
                return s;

        return null;
    }

    /**
     * Returns the list of transitions
     *
     * @return list of transition for the given model
     */
    public Transition[] getTransitions() {
        return transitions;
    }

    /**
     * Returns a {@link LinkedHashSet} of all the {@link State} objects in the model that represent initial states.
     *
     * @return all initial states in the model
     */
    public LinkedHashSet<State> getInitStates() {
        LinkedHashSet<State> inits = new LinkedHashSet<>();
        for (State s : states) {
            if (s.isInit())
                inits.add(s);
        }

        return inits;
    }

    /**
     * Returns a {@link LinkedHashSet} of all the {@link Transition}s in the model that originate from the given {@link State}.
     *
     * @param s the state of origin
     * @return the transitions leaving that state
     */
    public LinkedHashSet<Transition> getTransitionsFrom(State s) {
        LinkedHashSet<Transition> possibleTransitions = new LinkedHashSet<>();
        for (Transition t : transitions) {
            // TODO: Compare states to strings?
//            if (s.equals(t.getSource())) { // Java complains about inconvertible types (this may have warning in the compilation).
            if (t.getSource().equals(s.getName())) {
                possibleTransitions.add(t);
            }
        }
        return possibleTransitions;
    }

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("States:\n");
        for (State s : states) {
        	sb.append("\t" + s + "\n");
        }
        sb.append("Transitions:\n");
        for (Transition t : transitions) {
        	sb.append("\t" + t + "\n");
        }
        return sb.toString();
    }
}
