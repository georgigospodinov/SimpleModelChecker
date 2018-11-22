package model;

import java.util.Set;

public class TransitionTo {

    private final State src;
    private final State trg;
    private final String[] actions;

    public TransitionTo(State src, State target, String[] actions) {
        this.src = src;
        this.trg = target;
        this.actions = actions;
    }

    public TransitionTo(State target) {
        this(null, target, null);
    }

    public State getTrg() {
        return trg;
    }
    public State getSrc() {
        return src;
    }

    public String[] getActions() {
        return actions;
    }

    public boolean isIn(Set<String> acts) {
        if (actions == null)
            return false;
        if (acts == null)
        	return true;

        for (String action : actions) {
            if (acts.contains(action))
                return true;
        }
        return false;
    }

    @Override
	public String toString() {
		if (actions == null)
			return "Init: " + trg.getName();
		else
			return "  ->  " + trg.getName();
	}
}
