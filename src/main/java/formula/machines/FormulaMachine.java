package formula.machines;

import formula.pathFormula.Neg;
import formula.stateFormula.ForAll;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.State;

public abstract class FormulaMachine {
    public static FormulaMachine createFormulaMachine(StateFormula sf) {
        if (sf instanceof ThereExists) {
            ThereExists te = (ThereExists) sf;
            return new PathFormulaMachine(te.pathFormula);
        }
        if (sf instanceof ForAll) {
            ForAll fa = (ForAll) sf;
            Not notExistsNegativePath = new Not(new ThereExists(new Neg(fa.pathFormula)));
            return createFormulaMachine(notExistsNegativePath);
        }
        return new StateFormulaMachine(sf);
    }

    public abstract boolean isAccepting(State s);

    public abstract boolean isCompleted();

    public abstract void transition(String action);
}
