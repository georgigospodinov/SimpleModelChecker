package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
	public boolean pathFrom(State s) {
        return recursivePathFrom(s, new HashSet<State>());
	}

}
