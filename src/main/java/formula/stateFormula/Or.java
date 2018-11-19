package formula.stateFormula;

import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;

import static formula.pathFormula.PathFormula.THIS_STATE_MAKES_ME_FALSE;

public class Or extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public Or(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" || ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(State s) {
        return left.isValidIn(s) || right.isValidIn(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        LinkedHashMap<TransitionTo, State> toRemoveL = left.shouldPrune(s);
        LinkedHashMap<TransitionTo, State> toRemoveR = right.shouldPrune(s);
        if (toRemoveL == THIS_STATE_MAKES_ME_FALSE) {
            return toRemoveR;
        }
        if (toRemoveR == THIS_STATE_MAKES_ME_FALSE) {
            return toRemoveL;
        }
        toRemoveL.putAll(toRemoveR);
        return toRemoveL;
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        LinkedHashMap<TransitionTo, State> toSaveL = left.shouldNotPrune(s);
        LinkedHashMap<TransitionTo, State> toSaveR = right.shouldNotPrune(s);
        if (toSaveL == THIS_STATE_MAKES_ME_FALSE) {
            return toSaveR;
        }
        if (toSaveR == THIS_STATE_MAKES_ME_FALSE) {
            return toSaveL;
        }
        toSaveL.putAll(toSaveR);
        return toSaveL;
    }
}