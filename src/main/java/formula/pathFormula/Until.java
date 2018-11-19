package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }
    
 
	
    @Override
    public boolean exists(State s, LinkedList<State> visited) {
    	if (rightActions != null && rightActions.size() == 0) // no acceptable path
    		return false;
        if (visited.contains(s)) // cycle detection
        	return false;
        
        if (rightActions == null && right.isValidIn(s)) { //in final state
            visited.push(s);
            return true;
        }
        if (!this.left.isValidIn(s)) // current state invalid
            return false;
        visited.push(s);
                
        // try right first
        for (TransitionTo t : s.getTransitions()) {
            if (rightActions == null || t.isIn(rightActions)) {
            	if (right.isValidIn(t.getTrg())) {
            		visited.push(t.getTrg());
            		return true;
            	}
            }
        }

        // try left on failure
        for (TransitionTo t : s.getTransitions()) {
            if (leftActions == null || t.isIn(leftActions)) {
            	if (exists(t.getTrg(), visited))
            		return true;
            }
        }
        
        // no path found
        visited.pop();
        return false;
    }
    
    @Override
    public boolean forAll(State s) {
    	// TODO Auto-generated method stub
    	return false;
    }

    // FIXME: These two prune methods implement DFS. They might cause a StackOverflow if there is a loop in the model.
    // TODO: In a way, these are the only two methods left to do.

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        if (rightActions == null && right.isValidIn(s)) {
            return new LinkedHashMap<>();
        }
        if (!left.isValidIn(s)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }

        LinkedList<TransitionTo> ts = s.getTransitions();
        LinkedHashMap<TransitionTo, State> toRemove = new LinkedHashMap<>();
        for (TransitionTo t : ts) {
            State trg = t.getTrg();
            if (rightActions == null || t.isIn(rightActions)) {
                // A recognized transition makes right false, so we need to remove it.
                if (!right.isValidIn(trg)) {
                    toRemove.put(t, s);
                }
                // Skip the rest of this loop.
                continue;
            }
            // The transition is not recognized as part of rightActions.

            if (leftActions == null || t.isIn(leftActions)) {
                // TODO: should prune can cause infinite recursion (in a loop).
                LinkedHashMap<TransitionTo, State> subToRemove = left.shouldPrune(trg);

                // A recognized transition makes left false, so we need to remove it.
                if (subToRemove == null) {
                    toRemove.put(t, s);
                }
                // Otherwise, all of the returned transitions should be removed.
                else toRemove.putAll(subToRemove);
            }

            // The transition is part of neither leftActions nor rightActions.
            else toRemove.put(t, s);
        }

        return toRemove;
    }

    private LinkedHashMap<TransitionTo, State> bfs_should_prune(State s) {
        if (rightActions == null && right.isValidIn(s)) {
            return new LinkedHashMap<>();
        }
        if (!left.isValidIn(s)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }

        LinkedHashMap<TransitionTo, State> toRemove = new LinkedHashMap<>();
        LinkedHashSet<State> visited = new LinkedHashSet<>();
        LinkedList<State> queue = new LinkedList<>();
        queue.addLast(s);

        while (!queue.isEmpty()) {
            State n = queue.pollFirst();
            if (n == null || visited.contains(n))
                continue;
            visited.add(n);

            LinkedList<TransitionTo> ts = n.getTransitions();
            for (TransitionTo t : ts) {
                State trg = t.getTrg();
                if (rightActions == null || t.isIn(rightActions)) {

                    // A recognized right transition makes right false, so we need to remove it.
                    if (!right.isValidIn(trg)) {
                        toRemove.put(t, n);
                    }
                    // Skip the rest of this loop.
                    continue;
                }

                if (leftActions == null || t.isIn(leftActions)) {
                    // A recognized left transition makes left false, so we need to remove it.
                    if (!left.isValidIn(trg)) {
                        toRemove.put(t, n);
                    }
                    else queue.addLast(trg);
                }

                // The transition is part of neither leftActions nor rightActions.
                else toRemove.put(t, n);
            }
        }

        return toRemove;
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        LinkedList<TransitionTo> ts = s.getTransitions();
        LinkedHashMap<TransitionTo, State> toSave = new LinkedHashMap<>();
        if (rightActions == null && right.isValidIn(s)) {
            for (TransitionTo t : ts) {
                toSave.put(t, s);
            }
            return toSave;
        }
        if (!left.isValidIn(s)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }


        for (TransitionTo t : ts) {
            State trg = t.getTrg();
            if (rightActions == null || t.isIn(rightActions)) {
                // A recognized transition makes right true, so we need to save it.
                if (right.isValidIn(trg)) {
                    toSave.put(t, s);
                }
                // Skip the rest of this loop.
                continue;
            }
            // The transition is not recognized as part of rightActions.

            if (leftActions == null || t.isIn(leftActions)) {
                // TODO: should not prune can cause infinite recursion (in a loop).
                LinkedHashMap<TransitionTo, State> subToSave = left.shouldNotPrune(trg);
                // All of the returned transitions should be saved.
                if (subToSave != null) {
                    toSave.putAll(subToSave);
                    toSave.put(t, s);
                }
            }
        }

        return toSave;
    }
}
