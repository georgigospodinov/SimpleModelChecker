package modelChecker;

import static formula.stateFormula.BoolProp.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

public class TraceTests {
	String path = "src/test/resources/integration/";
	
	@Test
    public void failExistsTest() throws IOException {
        Model model = Model.parseModel(path + "m1.json");
        StateFormula ctl = FormulaParser.parseRawFormulaString("E F r");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        mc.check(model, constr, ctl);
        assertTrue(mc.getTrace().length == 1);
    }
    
    @Test
    public void failForAllTest() throws IOException {
        Model model = Model.parseModel(path + "m2.json");
        // for all paths, it is always the case that all paths eventually give r is true
        StateFormula ctl = FormulaParser.parseRawFormulaString("A G p");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        mc.check(model, constr, ctl);
        assertTrue(mc.getTrace().length == 2);
    }
    
    @Test
    public void failAATest() throws IOException {
        Model model = Model.parseModel(path + "m4.json");
        // for all paths, it is always the case that all paths eventually give r is true
        StateFormula ctl = FormulaParser.parseRawFormulaString("A F A G p ");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        assertFalse(mc.check(model, constr, ctl));
        assertTrue(mc.getTrace().length == 5);
    }
    
    @Test
    public void failAETest() throws IOException {
        Model model = Model.parseModel(path + "m1.json");
        // for all paths, it is always the case that all paths eventually give r is true
        StateFormula ctl = FormulaParser.parseRawFormulaString("A F E G p ");
        StateFormula constr = TRUE;
        ModelChecker mc = new SimpleModelChecker();
        mc.check(model, constr, ctl);
        assertTrue(mc.getTrace().length == 3);
    }
    

    
}
