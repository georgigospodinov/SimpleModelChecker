package constraintTests;

import static formula.stateFormula.BoolProp.TRUE;
import static formula.stateFormula.BoolProp.FALSE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import formula.stateFormula.And;
import formula.stateFormula.Not;
import formula.stateFormula.Or;
import formula.stateFormula.StateFormula;

public class LogicalConstraints {

	@Test
	public void orTest() {
		StateFormula pass = new Or(FALSE, TRUE);
		StateFormula fail = new Or(FALSE, FALSE);
		assertFalse(pass.isValidIn(null, FALSE));
		assertFalse(fail.isValidIn(null, FALSE));
		assertTrue(pass.holdsIn(null));
		assertFalse(fail.holdsIn(null));
	}
	
	@Test
	public void andTest() {
		StateFormula pass = new And(TRUE, TRUE);
		assertTrue(pass.holdsIn(null));
		StateFormula fail = new And(FALSE, FALSE);
		assertFalse(fail.holdsIn(null));
		fail = new And(TRUE, FALSE);
		assertFalse(fail.holdsIn(null));
		fail = new And(FALSE, TRUE);
		assertFalse(fail.holdsIn(null));
	}
	

	@Test
	public void notTest() {
		StateFormula pass = new Not(FALSE);
		StateFormula fail = new Not(TRUE);
		assertTrue(pass.holdsIn(null));
		assertFalse(fail.holdsIn(null));
	}
	
	
}
