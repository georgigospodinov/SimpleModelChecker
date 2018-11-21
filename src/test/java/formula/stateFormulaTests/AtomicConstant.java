package formula.stateFormulaTests;

import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtomicConstant {

    @Test
    public void boolTrueFalse() {
        StateFormula constraint = new BoolProp(true);
        BoolProp t = new BoolProp(true);
        BoolProp f = new BoolProp(false);
        assertTrue(t.isValidIn(null, constraint));
        assertFalse(f.isValidIn(null, constraint));
    }

    @Test
    public void boolPathLength() {
        // TODO
        // check returned path is correct length

        StateFormula constraint = new BoolProp(true);
        Path path = new Path();
        BoolProp t = new BoolProp(true);
        BoolProp f = new BoolProp(false);
        State state = new State();
        /*
    	assertTrue(t.isValidIn(state, constraint, path));
    	assertTrue(path.size() == 0);
    	assertFalse(f.isValidIn(state, constraint, path));
    	assertTrue(path.size() == 1);
    	*/
    }

    @Test
    public void printBool() {
        BoolProp t = new BoolProp(true);
        BoolProp f = new BoolProp(false);
        assertTrue(t.toString().equals(" True "));
        assertTrue(f.toString().equals(" False "));
    }

}
 