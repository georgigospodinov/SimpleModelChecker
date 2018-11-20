package formula.stateFormulaTests;


import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.BoolProp;
import formula.stateFormula.ForAll;
import formula.stateFormula.ThereExists;
import model.State;

public class PathQualifiers {
	LinkedList<State> stateList = new LinkedList<>();
	
	@Test 
	public void existsTests() {
		PathFormula pf = new Until(new BoolProp(true), new BoolProp(true), null, null);	
		ThereExists e = new ThereExists(pf);		
		assertTrue(e.isValidIn(null, null, stateList));
		assertTrue(e.toString().equals("(E" + pf + ")"));
	}
	
	@Test 
	public void forAllTests() {
		PathFormula pf = new Until(new BoolProp(true), new BoolProp(true), null, null);	
		ForAll a = new ForAll(pf);		
		assertTrue(a.isValidIn(null, null, stateList));
		assertTrue(a.toString().equals("(A" + pf + ")"));
	}

}
