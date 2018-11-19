package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.*;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;

    public ForAll(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
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

    private boolean validNext(State s, Next path) {
        LinkedList<TransitionTo> ts = s.getTransitions();
        Set<String> actions = path.getActions();
        for (TransitionTo t : ts) {
            if (actions == null || t.isIn(actions)) {
                if (!path.stateFormula.isValidIn(t.getTrg()))
                    return false;
            }
            else return false;
        }

        return true;
    }

    private boolean validAlwaysRecursive(State s, Always path, LinkedHashSet<State> visited) {
        if (!path.stateFormula.isValidIn(s)) {
            return false;
        }

        visited.add(s);
        Set<String> actions = path.getActions();
        LinkedList<TransitionTo> ts = s.getTransitions();
        for (TransitionTo t : ts) {
            if (actions == null || t.isIn(actions)) {
                State next = t.getTrg();
                // Skip visited states.
                if (visited.contains(next))
                    continue;

                if (!validAlwaysRecursive(next, path, visited))
                    return false;
            }
            // There should be no invalid transitions.
            return false;
        }

        return true;
    }

    private boolean validAlways(State s, Always path) {
        if (!path.stateFormula.isValidIn(s))
            return false;

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
        boolean atLeastOneSucceeds = false;
        for (TransitionTo t : ts) {
            State target = t.getTrg();
            if (visited.contains(target)) {
                // Loop detected.
                return false;
            }
            if (rightActions == null) {
                if (leftActions == null) {
                    // No actions on either side, if right is accepted, all good. Otherwise, left must recurse to a valid.
                    if (path.right.isValidIn(target)) {
                        atLeastOneSucceeds = true;
                    }
                    else if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                        atLeastOneSucceeds = true;
                    }
                    else return false;
                }
                else {
                    // Actions on the left but not on the right. Recurse if the transition is on the left.
                    if (t.isIn(leftActions)) {
                        // Then left must recurse to a valid.
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            atLeastOneSucceeds = true;
                        }
                        else return false;
                    }
                    // Transition t is on the right, it must lead to a state where right is valid.
                    else if (path.right.isValidIn(target)) {
                        atLeastOneSucceeds = true;
                    }
                    else return false;
                }  // else (leftActions != null)
            }  // if rightActions == null
            else {  // There are actions on the right.
                boolean inRight = t.isIn(rightActions);
                if (leftActions == null) {  // No actions on the left.
                    if (inRight) {
                        // Right must be true after a rightAction.
                        if (path.right.isValidIn(target)) {
                            atLeastOneSucceeds = true;
                        }
                        else return false;
                    }
                    else {
                        // Left must recurse to an acceptable path.
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            atLeastOneSucceeds = true;
                        }
                        else return false;
                    }
                }
                else {  // Actions on both sides.
                    boolean inLeft = t.isIn(leftActions);
                    if (inRight && inLeft) {
                        // Both are possible, If not right, than the left must recurse to a right.
                        if (path.right.isValidIn(target)) {
                            atLeastOneSucceeds = true;
                        }
                        else if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            atLeastOneSucceeds = true;
                        }
                        else return false;
                    }
                    if (inRight && !inLeft) {
                        if (path.right.isValidIn(target)) {
                            atLeastOneSucceeds = true;
                        }
                        else return false;
                    }
                    if (!inRight && inLeft) {
                        if (validUntilRecursive(target, path, new LinkedHashSet<>(visited))) {
                            atLeastOneSucceeds = true;
                        }
                        else return false;
                    }
                    if (!inRight && !inLeft) {
                        // invalid transition TODO do we return false here? Think of:
                        // A(E(Ga1 p) a3 U a4 E(Ga2 q)), aka:
                        // ForAll( (Exists Always a1 p)   a3 Until a4   (Exists Always a2 q) )
                        // ForAll paths, there is a path where we always take action 1 and p is true
                        //               while we take action 3 until we take action 4
                        //               at which point there is a path where  we always take action 2 and q is true
                        //   a1 is not recognized by the until (which looks for a3 and a4).
                        return false;
                    }
                }  // else (leftActions != null)
            }  // else (rightActions != null)
        }  // for

        return atLeastOneSucceeds;
    }

    private boolean validUntil(State s, Until path) {
        return validUntilRecursive(s, path, new LinkedHashSet<State>());
    }

    private boolean validEventually(State s, Eventually path) {
        Until u = new Until(new BoolProp(true), path.stateFormula, path.getLeftActions(), path.getRightActions());
        return validUntil(s, u);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        /* ForAll requires that all paths are acceptable,
         * so if there is even one path that does not satisfy the forAll
         * we need to say that this fails.
         * Removing invalid paths and leaving only valid ones is to be done in 'ThereExists'.
         */
        return super.shouldPrune(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        /* ForAll requires that all paths are acceptable,
         * so if there is even one path that does not satisfy the forAll
         * we need to say that this fails.
         * Removing invalid paths and leaving only valid ones is to be done in 'ThereExists'.
         */
        return super.shouldNotPrune(s);
    }
}
