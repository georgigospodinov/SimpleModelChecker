package model;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 
 * */
public class State {
    private boolean init;
    private String name;
    private String [] label;
    private LinkedList<TransitionTo> trans = new LinkedList<>();
	
    public void setLabels(String[] labels) {
    	this.label = labels;
    }
    
    /**
     * Is state an initial state
     * @return boolean init 
     * */
    public boolean isInit() {
	return init;
    }
	
    /**
     * Returns the name of the state
     * @return String name 
     * */
    public String getName() {
	return name;
    }
	
    /**
     * Returns the labels of the state
     * @return Array of string labels
     * */
    public String[] getLabel() {
	return label;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            String otherName = (String) obj;
            return this.name.equals(otherName);
        }

        if (!(obj instanceof State))
            return false;

        State other = (State) obj;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return name + (init ? "* " : "  ") + Arrays.toString(label);
    }

	public void addTransition(State trg, String[] actions) {
		trans.add(new TransitionTo(trg, actions));		
	}
	
	public LinkedList<TransitionTo> getTransitions() {
		return trans;
	}
	
}
