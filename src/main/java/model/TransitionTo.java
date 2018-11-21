package model;

import java.util.Arrays;
import java.util.Set;

public class TransitionTo {

    private final State trg;
    private final String[] actions;

    public TransitionTo(State target, String[] actions) {
        this.trg = target;
        this.actions = actions;
    }

    public TransitionTo(State target) {
        this(target, null);
    }

    public State getTrg() {
        return trg;
    }

    public boolean isIn(Set<String> acts) {
        if (actions == null)
            return false;

        for (String action : actions) {
            if (acts.contains(action))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "{trg=" + trg +
                ", actions=" + Arrays.toString(actions) +
                "}";
    }
}
