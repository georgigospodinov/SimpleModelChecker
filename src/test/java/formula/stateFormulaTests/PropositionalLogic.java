package formula.stateFormulaTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import formula.stateFormula.And;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.Or;
import formula.stateFormula.StateFormula;

public class PropositionalLogic {
	StateFormula constraint = new BoolProp(true);

    @Test
    public void StateFormulaNot() {
    	BoolProp t = new BoolProp(true);
    	Not n = new Not(t);
    	assertFalse(n.isValidIn(null, constraint));
    }
    
    @Test
    public void StateFormulaAnd() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);

    	And ff = new And(f, f);
    	assertFalse(ff.isValidIn(null, constraint));
    	And ft = new And(f, t);
    	assertFalse(ft.isValidIn(null, constraint));
    	And tf = new And(t, f);
    	assertFalse(tf.isValidIn(null, constraint));    	
    	And tt = new And(t, t);
    	assertTrue(tt.isValidIn(null, constraint));    	
    }
    
    @Test
    public void StateFormulaOr() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);

    	Or ff = new Or(f, f);
    	assertFalse(ff.isValidIn(null, constraint));
    	Or ft = new Or(f, t);
    	assertTrue(ft.isValidIn(null, constraint));
    	Or tf = new Or(t, f);
    	assertTrue(tf.isValidIn(null, constraint));    	
    	Or tt = new Or(t, t);
    	assertTrue(tt.isValidIn(null, constraint));    	
    }
    
    @Test 
    public void printLogical() {
    	BoolProp t = new BoolProp(true);
    	Or or = new Or(t, t);
    	And and = new And(t, t);
    	assertTrue(or.toString().equals("(" + t + " || " + t + ")"));
    	assertTrue(and.toString().equals("(" + t + " && " + t + ")"));
    	
    }
    
}
