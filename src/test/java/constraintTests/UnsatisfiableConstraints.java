package constraintTests;


import static org.junit.Assert.assertFalse;

import java.util.LinkedList;

import org.junit.Test;

import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;

public class UnsatisfiableConstraints {
	private StateFormula constraint = new BoolProp(false);

	LinkedList<State> stateList = new LinkedList<>();
	
	@Test
    public void BoolConstTest() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertFalse(t.isValidIn(null, constraint, stateList));
    	assertFalse(f.isValidIn(null, constraint, stateList));
    }

}
