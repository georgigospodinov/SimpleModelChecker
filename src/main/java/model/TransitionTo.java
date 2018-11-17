package model;

import java.util.Set;

public class TransitionTo {

    private State trg;
    private String[] actions;

    public TransitionTo(State trg, String[] actions) {
        this.trg = trg;
        this.actions = actions;
    }

    public State getTrg() {
        return trg;
    }

    public boolean isIn(Set<String> acts) {
        for (String action : actions) {
            if (acts.contains(action))
                return true;
        }
        return false;
    }

}
