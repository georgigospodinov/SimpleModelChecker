package formula.stateFormulaTests;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.BoolProp;
import formula.stateFormula.ForAll;
import formula.stateFormula.ThereExists;

public class PathQualifiers {
	
	@Test 
	public void existsTests() {
		PathFormula pf = new Until(new BoolProp(true), new BoolProp(true), null, null);	
		ThereExists e = new ThereExists(pf);		
		assertTrue(e.isValidIn(null, null));
		assertTrue(e.toString().equals("(E" + pf + ")"));
	}
	
	@Test 
	public void forAllTests() {
		PathFormula pf = new Until(new BoolProp(true), new BoolProp(true), null, null);	
		ForAll a = new ForAll(pf);		
		assertTrue(a.isValidIn(null, null));
		assertTrue(a.toString().equals("(A" + pf + ")"));
	}

}
