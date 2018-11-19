package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.rmi.UnexpectedException;
import java.util.LinkedHashMap;

import static formula.pathFormula.PathFormula.THIS_STATE_MAKES_ME_FALSE;

public class And extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public And(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" && ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(State s) {
        return left.isValidIn(s) && right.isValidIn(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        LinkedHashMap<TransitionTo, State> toRemoveL = left.shouldPrune(s);
        LinkedHashMap<TransitionTo, State> toRemoveR = right.shouldPrune(s);
        if (toRemoveL == THIS_STATE_MAKES_ME_FALSE || toRemoveR == THIS_STATE_MAKES_ME_FALSE)
            return THIS_STATE_MAKES_ME_FALSE;
        toRemoveL.putAll(toRemoveR);
        return toRemoveL;
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        LinkedHashMap<TransitionTo, State> toSaveL = left.shouldNotPrune(s);
        LinkedHashMap<TransitionTo, State> toSaveR = right.shouldNotPrune(s);
        // TODO: This may occur when an always path formula requires that all subsequent paths be cut but that formula is true in the current state.
        if (toSaveL != null && toSaveL.isEmpty())
            try {
                throw new UnexpectedException("Empty map!");
            }
            catch (UnexpectedException e) {
                e.printStackTrace();
            }
        if (toSaveR != null && toSaveR.isEmpty())
            try {
                throw new UnexpectedException("Empty map!");
            }
            catch (UnexpectedException e) {
                e.printStackTrace();
            }

        if (toSaveL == THIS_STATE_MAKES_ME_FALSE || toSaveR == THIS_STATE_MAKES_ME_FALSE)
            return THIS_STATE_MAKES_ME_FALSE;
        toSaveL.putAll(toSaveR);
        return toSaveL;
    }
}