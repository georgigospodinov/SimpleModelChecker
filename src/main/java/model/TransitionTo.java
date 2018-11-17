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
		for (String allowedAct: acts) {
			for (String act: actions) {
				if (act.equals(allowedAct))
					return true;
			}
		}
		return false;
	}

}
