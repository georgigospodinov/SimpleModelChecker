package constraintTests;


import static org.junit.Assert.assertFalse;

import org.junit.Test;

import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;

public class UnsatisfiableConstraints {
	private StateFormula constraint = new BoolProp(false);
	
	@Test
    public void BoolConstTest() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertFalse(t.isValidIn(null, constraint));
    	assertFalse(f.isValidIn(null, constraint));
    }

}
