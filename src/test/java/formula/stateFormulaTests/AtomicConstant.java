package formula.stateFormulaTests;

import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtomicConstant {
	StateFormula constraint = new BoolProp(true);

    @Test
    public void StateFormulaBool() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertTrue(t.isValidIn(null, constraint));
    	assertFalse(f.isValidIn(null, constraint));
    }
    
    @Test
    public void printBool() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertTrue(t.toString().equals(" True "));
    	assertTrue(f.toString().equals(" False "));
    }

}
 