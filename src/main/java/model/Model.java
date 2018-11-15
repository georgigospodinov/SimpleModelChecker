package model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * A model is consist of states and transitions
 */
public class Model {
    private State[] states;
    private Transition[] transitions;

    public static Model parseModel(String filePath) throws IOException {
        Gson gson = new Gson();
        Model model = gson.fromJson(new FileReader(filePath), Model.class);
        for (Transition t : model.transitions) {
            System.out.println(t);
        }
        return model;
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
     * Returns the list of transitions
     * 
     * @return list of transition for the given model
     */
    public Transition[] getTransitions() {
        return transitions;
    }

    public LinkedList<State> getInitStates() {
        LinkedList<State> inits = new LinkedList<>();
        for (State s : states) {
            if (s.isInit())
                inits.addLast(s);
        }

        return inits;
    }

    public void getTransitionsFrom(State s) {
        for (Transition t : transitions) {
            // TODO: Compare states to strings?
            if (t.getSource().equals(s.getName())) {
                // TODO: THIS THING
            }
        }
    }

}
