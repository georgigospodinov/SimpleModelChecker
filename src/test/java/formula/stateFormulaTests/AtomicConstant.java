package formula.stateFormulaTests;

import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

public class AtomicConstant {
	StateFormula constraint = new BoolProp(true);
	LinkedList<State> stateList = new LinkedList<>();
	
    @Test
    public void StateFormulaBool() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertTrue(t.isValidIn(null, constraint, stateList));
    	assertFalse(f.isValidIn(null, constraint, stateList));
    }
    
    @Test
    public void printBool() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);
    	assertTrue(t.toString().equals(" True "));
    	assertTrue(f.toString().equals(" False "));
    }

}
 