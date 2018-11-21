package constraintTests;


import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;
import org.junit.Test;

import java.util.LinkedList;

public class UnsatisfiableConstraints {
    private StateFormula constraint = new BoolProp(false);

    LinkedList<State> stateList = new LinkedList<>();

    @Test
    public void BoolConstTest() {
        BoolProp t = new BoolProp(true);
        BoolProp f = new BoolProp(false);
        //assertFalse(t.isValidIn(null, constraint, stateList));
        //assertFalse(f.isValidIn(null, constraint, stateList));
    }

}
