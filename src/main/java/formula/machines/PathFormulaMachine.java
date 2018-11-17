package formula.machines;

import formula.pathFormula.*;
import model.State;

import java.util.Set;

public class PathFormulaMachine extends FormulaMachine {
    private FormulaMachine left, right;
    private Set<String> actionsRemainInLeft, actionsGoInRight;

    /**
     * Says whether or not we have moved to the right sub-machine.
     */
    private boolean hasTransitioned = false;

    public PathFormulaMachine(PathFormula pf) {
        if (pf instanceof Always) {
            Always af = (Always) pf;
            left = null;
            actionsRemainInLeft = null;
            right = createFormulaMachine(af.stateFormula);
            // TODO: Do we need actions for an always statement?
            actionsGoInRight = af.getActions();
            hasTransitioned = true;
        }
        else if (pf instanceof Eventually) {
            Eventually ef = (Eventually) pf;
            left = null;
            actionsRemainInLeft = ef.getLeftActions();
            right = createFormulaMachine(ef.stateFormula);
            actionsGoInRight = ef.getRightActions();
        }
        else if (pf instanceof Neg) {
            Neg nf = (Neg) pf;
            left = null;
            actionsRemainInLeft = null;
            right = new PathFormulaMachine(nf.pathFormula);
            actionsGoInRight = null;
            hasTransitioned = true;
        }
        else if (pf instanceof Next) {
            Next nf = (Next) pf;
            left = null;
            actionsRemainInLeft = null;
            right = createFormulaMachine(nf.stateFormula);
            actionsGoInRight = nf.getActions();
        }
        else if (pf instanceof Until) {
            Until uf = (Until) pf;
            left = createFormulaMachine(uf.left);
            actionsRemainInLeft = uf.getLeftActions();
            right = createFormulaMachine(uf.right);
            actionsGoInRight = uf.getRightActions();
        }
        else throw new IllegalArgumentException("Unhandled case " + pf.getClass());
    }

    @Override
    public boolean isAccepting(State s) {
        if (hasTransitioned)
            return right.isAccepting(s);

        return left != null && left.isAccepting(s);
    }

    @Override
    public boolean isCompleted() {
        return hasTransitioned && right.isCompleted();
    }

    @Override
    public void transition(String action) {
        if (hasTransitioned) {
            right.transition(action);
            return;
        }
        // Else, we are in the left.


        // If left is incomplete.
        if (left != null && !left.isCompleted()) {
            left.transition(action);
            return;
        }

        // Else, left is complete.
        if (actionsRemainInLeft != null && actionsRemainInLeft.contains(action)) {
            // We remain in the left.
            return;
        }
        // The action cannot be (should not be) in both sets at the same time.
        if (actionsGoInRight == null || actionsGoInRight.contains(action)) {
            // TODO: Do we make a difference between "empty set (no actions)" and "no set (any action)" ???
            hasTransitioned = true;
            return;
        }

        throw new IllegalArgumentException("Cannot transition with unsupported action: \"" + action + "\"!");
    }
}
