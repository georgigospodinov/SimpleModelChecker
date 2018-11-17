package formula.machines;

import formula.stateFormula.StateFormula;
import model.State;

public class StateFormulaMachine extends FormulaMachine {
    public final StateFormula sf;

    public StateFormulaMachine(StateFormula sf) {
        this.sf = sf;
    }

    @Override
    public boolean isAccepting(State s) {
        return sf.isValidIn(s);
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public void transition(String action) {
        // No transitioning to do.
    }
}
