package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.*;

public class Always extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Always(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEn);
        stateFormula.writeToBuffer(buffer);
    }

    private boolean recursivePathFrom(State s, HashSet<State> visited) {
        if (!stateFormula.isValidIn(s))
            return false;

        visited.add(s);

        boolean noRecursivePathMakesMeFalse = true;
        /* This comment attempts to explain the flag above. Read the loop before reading this comment.
         *
         * Assume that we are not going to find a transition which leads to an invalid state.
         * If every valid transition leads to a visited state, then all is OK.
         * The loop below will skip invalid transitions and visited states.
         * The loop will set the boolean flag noRecursivePathMakesMeFalse to false
         *     iff the recursion finds a path that makes the formula false.
         *
         * This flag will be returned if the loop completes.
         * The loop completes under any one of these conditions:
         *     1. No transition is in the actions set.
         *     2. All next states are visited.
         *     3. All recursions return false.
         * Note: the loop does not complete if it does find a path, so this method will return true.
         *
         * If the loop ends due to 1 or 2, than the flag noRecursivePathMakesMeFalse has remained true.
         * We return true because either there is valid cycle or we have reached a final state.
         *
         * Under condition 3, the flag noRecursivePathMakesMeFalse will have been set to false.
         * We return false because all recursions have failed (which means that at least one recursion has failed).
         *
         * TODO is this well explained?
         * */

        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            // Skip invalid transitions.
            if (actions != null && !t.isIn(actions)) continue;

            State next = t.getTrg();
            // Skip visited states.
            if (visited.contains(next)) continue;

            if (recursivePathFrom(next, visited))
                return true;

                // There is a valid transition, that makes this pathFormula false.
            else noRecursivePathMakesMeFalse = false;
        }

        return noRecursivePathMakesMeFalse;
    }

    @Override
    public boolean exists(State s, LinkedList<State> path) {
    	if (!stateFormula.isValidIn(s))
            return false;
    	// check for an always true cycle
    	if (path.contains(s)) {
    		path.push(s);
    		return true;
    	}
		path.push(s);
    	// end of path
    	if (s.getTransitions().isEmpty())
    		return true;
    	// search branches
        for (TransitionTo t : s.getTransitions()) {
            if (actions == null || t.isIn(actions)) {
                if (exists(t.getTrg(), path))
                    return true;
            }
        }
        // no path found
        path.pop();
        return false;
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        if (!stateFormula.isValidIn(s)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }

        LinkedHashMap<TransitionTo, State> toRemove = new LinkedHashMap<>();
        LinkedHashSet<State> visited = new LinkedHashSet<>();
        LinkedList<State> queue = new LinkedList<>();
        queue.add(s);
        // Breadth First Search.
        while (!queue.isEmpty()) {
            State n = queue.pollFirst();
            // Skip states that are null or visited.
            if (n == null || visited.contains(n))
                continue;

            visited.add(n);
            LinkedList<TransitionTo> ts = n.getTransitions();
            for (TransitionTo t : ts) {
                if (actions == null || t.isIn(actions)) {
                    State trg = t.getTrg();
                    // Note: no need to call shouldPrune because BFS iterates all the states.
                    // A recognized transition makes the formula false, so we need to remove it.
                    if (!stateFormula.isValidIn(trg))
                        toRemove.put(t, n);
                        // The target state is ok, so we should check it/ recurse from it.
                    else queue.addLast(trg);
                }
                // The transition is not recognized, so we need to remove it.
                else toRemove.put(t, n);
            }  // for
        }  // while
        return toRemove;
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        if (!stateFormula.isValidIn(s)) {
            return THIS_STATE_MAKES_ME_FALSE;
        }

        LinkedHashMap<TransitionTo, State> toSave = new LinkedHashMap<>();
        LinkedHashSet<State> visited = new LinkedHashSet<>();
        LinkedList<State> queue = new LinkedList<>();
        queue.add(s);
        // Breadth First Search.
        while (!queue.isEmpty()) {
            State n = queue.pollFirst();
            // Skip states that are null or visited.
            if (n == null || visited.contains(n))
                continue;

            visited.add(n);
            LinkedList<TransitionTo> ts = n.getTransitions();
            for (TransitionTo t : ts) {
                if (actions == null || t.isIn(actions)) {
                    State trg = t.getTrg();
                    // Note: no need to call shouldPrune because BFS iterates all the states.
                    // A recognized transition makes the formula true, so we need to save it.
                    if (stateFormula.isValidIn(trg)) {
                        toSave.put(t, n);
                        queue.addLast(n);
                    }
                }
            }  // for
        }  // while
        return toSave;
    }

	@Override
	public boolean forAll(State s) {
		// TODO Auto-generated method stub
		return false;
	}
}
