package formula.stateFormulaTests;

import formula.stateFormula.BoolProp;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtomicConstant {

    @Test
    public void StateFormulaBool() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertTrue(t.isValidIn(null));
    	assertFalse(f.isValidIn(null));
    }

}
 