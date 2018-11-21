package formula.stateFormulaTests;

import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Variable {
	StateFormula constraint = new BoolProp(true);
	LinkedList<State> stateList = new LinkedList<>();

	@Test
	public void StateFormulaVariableNone() {
		String var = "x";
		AtomicProp x = new AtomicProp(var);
		
		// no label 
		State none = new State();
		String[] empty = {};
		none.setLabels(empty);
        assertFalse(x.isValidIn(none, constraint));
	
	}

    @Test
    public void StateFormulaVariableSinglelabel() {
        String var = "x";
        AtomicProp x = new AtomicProp(var);
        // one label, x
        State singleTrue = new State();
        String[] justX = {var};
        singleTrue.setLabels(justX);
        assertTrue(x.isValidIn(singleTrue, constraint));

        // one label, not x
        State singleFalse = new State();
        String[] justNotX = {"y"};
        singleFalse.setLabels(justNotX);
        assertFalse(x.isValidIn(singleFalse, constraint));
    }

    @Test
	public void StateFormulaVariableManyLabels() {
		String var = "x";
		AtomicProp x = new AtomicProp(var);
		// many labels, inc. x 
		State manyTrue = new State();
		String[] withX = {"y", var};
		manyTrue.setLabels(withX);
        assertTrue(x.isValidIn(manyTrue, constraint));

		State manyTrueRev = new State();
		String[] withXRev = {var, "y"};
		manyTrueRev.setLabels(withXRev);
        assertTrue(x.isValidIn(manyTrueRev, constraint));
		
		
		// many labels, not inc. x
		State manyFalse = new State();
		String[] withoutX = {"y", "z"};
		manyFalse.setLabels(withoutX);
        assertFalse(x.isValidIn(manyFalse, constraint));
	}

}
