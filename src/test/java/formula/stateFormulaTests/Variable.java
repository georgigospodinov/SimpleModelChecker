package formula.stateFormulaTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import formula.stateFormula.AtomicProp;
import model.State;

public class Variable {

	@Test
	public void StateFormulaVariableNone() {
		String var = "x";
		AtomicProp x = new AtomicProp(var);
		
		// no label 
		State none = new State();
		String[] empty = {};
		none.setLabels(empty);
		assertFalse(x.isValidIn(none));
	
	}
	public void StateFormulaVariableSinglelabel() {
		String var = "x";
		AtomicProp x = new AtomicProp(var);
		// one label, x 
		State singleTrue = new State();
		String[] justX = {var};
		singleTrue.setLabels(justX);
		assertTrue(x.isValidIn(singleTrue));

		// one label, not x
		State singleFalse = new State();
		String[] justNotX = {"y"};
		singleFalse.setLabels(justNotX);
		assertFalse(x.isValidIn(singleFalse));		
	}
	
	public void StateFormulaVariableManyLabels() {
		String var = "x";
		AtomicProp x = new AtomicProp(var);
		// many labels, inc. x 
		State manyTrue = new State();
		String[] withX = {"y", var};
		manyTrue.setLabels(withX);
		assertTrue(x.isValidIn(manyTrue));

		State manyTrueRev = new State();
		String[] withXRev = {var, "y"};
		manyTrueRev.setLabels(withXRev);
		assertTrue(x.isValidIn(manyTrueRev));
		
		
		// many labels, not inc. x
		State manyFalse = new State();
		String[] withoutX = {"y", "z"};
		manyFalse.setLabels(withoutX);
		assertFalse(x.isValidIn(manyFalse));
		

		
	}

}
