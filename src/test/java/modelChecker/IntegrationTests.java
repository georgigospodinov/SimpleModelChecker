package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import org.junit.Test;


import static formula.stateFormula.BoolProp.TRUE;
import static formula.stateFormula.BoolProp.FALSE;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntegrationTests {
	String path = "src/test/resources/integration/";
	
    @Test
    public void checkValidTest() throws IOException {
        Model model = Model.parseModel(path + "m1.json");
        StateFormula ctl = TRUE;
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertTrue(mc.check(model, constr, ctl));
        assertTrue(mc.getTrace().length == 0);
    }
    
    @Test
    public void checkExistsTest() throws IOException {
        Model model = Model.parseModel(path + "m1.json");
        StateFormula ctl = FormulaParser.parseRawFormulaString("E F q");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertTrue(mc.check(model, constr, ctl));
    }
    

    @Test
    public void checkForAllTest() throws IOException {
        Model model = Model.parseModel(path + "m2.json");
        // for all paths, it is always the case that al paths eventually give r is true
        StateFormula ctl = FormulaParser.parseRawFormulaString("A G A F r");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertTrue(mc.check(model, constr, ctl));
    }

    @Test
    public void failExistsTest() throws IOException {
        Model model = Model.parseModel(path + "m1.json");
        StateFormula ctl = FormulaParser.parseRawFormulaString("E F r");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertFalse(mc.check(model, constr, ctl));
    }
    
    @Test
    public void failForAllTest() throws IOException {
        Model model = Model.parseModel(path + "m2.json");
        // for all paths, it is always the case that al paths eventually give r is true
        StateFormula ctl = FormulaParser.parseRawFormulaString("A G p");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertFalse(mc.check(model, constr, ctl));
    }
    
    @Test
    public void manyInitTest() throws IOException {
        Model model = Model.parseModel(path + "m3.json");
        // for all paths, it is always the case that al paths eventually give r is true
        StateFormula ctl = FormulaParser.parseRawFormulaString("p");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertFalse(mc.check(model, constr, ctl));
    }
    
    
    
}
