package formula.stateFormulaTests;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.BoolProp;
import formula.stateFormula.ThereExists;

public class PathQualifiers {
	
	@Test 
	public void exists() {
		PathFormula pf = new Until(new BoolProp(true), new BoolProp(true), null, null);	
		ThereExists e = new ThereExists(pf);		
		assertTrue(e.isValidIn(null));
		assertTrue(e.toString().equals("(E" + pf + ")"));
	}

}
