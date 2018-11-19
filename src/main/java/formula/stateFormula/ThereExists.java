package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.*;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;

    public ThereExists(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(State s) {
        if (pathFormula instanceof Always) {
            return validAlways(s, (Always) pathFormula);
        }
        else if (pathFormula instanceof Eventually) {
            return validEventually(s, (Eventually) pathFormula);
        }
        else if (pathFormula instanceof Until) {
            return validUntil(s, (Until) pathFormula);
        }
        else if (pathFormula instanceof Next) {
            return validNext(s, (Next) pathFormula);
        }
        else throw new IllegalArgumentException("Unhandled case: " + pathFormula.getClass());
    }

    private boolean validAlwaysRecursive(State s, Always path, LinkedHashSet<State> visited) {
        if (!path.stateFormula.isValidIn(s)) {
            return false;
        }

        visited.add(s);
        Set<String> actions = path.getActions();
        boolean flag = true;
        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            // Skip invalid transitions.
            if (actions != null && !t.isIn(actions)) continue;

            State next = t.getTrg();
            // Skip visited states.
            if (visited.contains(next)) continue;

            if (validAlwaysRecursive(next, path, visited))
                return true;

                // There is a valid transition, that makes this pathFormula false.
            else flag = false;
        }

        return flag;
    }

    private boolean validAlways(State s, Always path) {
        return validAlwaysRecursive(s, path, new LinkedHashSet<State>());
    }

    private boolean validUntilRecursive(State s, Until path, LinkedHashSet<State> visited) {
        Set<String> leftActions = path.getLeftActions();
        Set<String> rightActions = path.getRightActions();
        if (rightActions == null && path.right.isValidIn(s)) {
            return true;
        }
        if (!path.left.isValidIn(s)) {
            return false;
        }

        visited.add(s);

        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            State target = t.getTrg();
            if (visited.contains(target)) {
                // Loop detected.
                return false;
            }
            if (rightActions == null) {
                if (leftActions == null) {
                    // No actions on either side, if right is accepted, all good.
                    if (path.right.isValidIn(target)) {
                        return true;
                    }
                    // If left recurse to success, all good.
                    if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                        return true;
                    }
                }
                else {
                    // Actions on the left but not on the right.
                    if (t.isIn(leftActions)) {
                        // If left recurses to success, all is good.
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            return true;
                        }
                    }
                    // Transition t is on the right, if it leads to a state where right is valid, all is good.
                    else if (path.right.isValidIn(target)) {
                        return true;
                    }
                }  // else (leftActions != null)
            }  // if rightActions == null
            else {  // There are actions on the right.
                boolean inRight = t.isIn(rightActions);
                if (leftActions == null) {  // No actions on the left.
                    if (inRight) {
                        // If right is true after a rightAction, all is good.
                        if (path.right.isValidIn(target)) {
                            return true;
                        }
                    }
                    else {
                        // If left recurses to success, all is good.
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            return true;
                        }
                    }
                }
                else {  // Actions on both sides.
                    boolean inLeft = t.isIn(leftActions);
                    if (inRight && inLeft) {
                        // If right is true after a rightAction, all is good.
                        if (path.right.isValidIn(target)) {
                            return true;
                        }
                        // If left recurses to success, all is good.
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            return true;
                        }
                    }
                    if (inRight && !inLeft) {
                        if (path.right.isValidIn(target)) {
                            return true;
                        }
                    }
                    if (!inRight && inLeft) {
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            return true;
                        }
                    }
                }  // else (leftActions != null)
            }  // else (rightActions != null)
        }  // for

        return false;
    }

    private boolean validUntil(State s, Until path) {
        return validUntilRecursive(s, path, new LinkedHashSet<State>());
    }

    private boolean validNext(State s, Next path) {
        LinkedList<TransitionTo> ts = s.getTransitions();
        Set<String> actions = path.getActions();
        for (TransitionTo t : ts) {
            if (actions == null || t.isIn(actions)) {
                if (path.stateFormula.isValidIn(t.getTrg()))
                    return true;
            }
        }

        return false;
    }

    private boolean validEventually(State s, Eventually path) {
        Until u = new Until(new BoolProp(true), path.stateFormula, path.getLeftActions(), path.getRightActions());
        return validUntil(s, u);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        // Each PathFormula implements this in its own way.
        return pathFormula.shouldPrune(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        // Each PathFormula implements this in its own way.
        return pathFormula.shouldNotPrune(s);
    }
}
