package formula.stateFormulaTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import formula.stateFormula.And;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.Or;

public class PropositionalLogic {

    @Test
    public void StateFormulaNot() {
    	BoolProp t = new BoolProp(true);
    	Not n = new Not(t);
    	assertFalse(n.isValidIn(null));
    }
    
    @Test
    public void StateFormulaAnd() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);

    	And ff = new And(f, f);
    	assertFalse(ff.isValidIn(null));
    	And ft = new And(f, t);
    	assertFalse(ft.isValidIn(null));
    	And tf = new And(t, f);
    	assertFalse(tf.isValidIn(null));    	
    	And tt = new And(t, t);
    	assertTrue(tt.isValidIn(null));    	
    }
    
    @Test
    public void StateFormulaOr() {
    	BoolProp t = new BoolProp(true);
    	BoolProp f = new BoolProp(false);

    	Or ff = new Or(f, f);
    	assertFalse(ff.isValidIn(null));
    	Or ft = new Or(f, t);
    	assertTrue(ft.isValidIn(null));
    	Or tf = new Or(t, f);
    	assertTrue(tf.isValidIn(null));    	
    	Or tt = new Or(t, t);
    	assertTrue(tt.isValidIn(null));    	
    }
    
}
