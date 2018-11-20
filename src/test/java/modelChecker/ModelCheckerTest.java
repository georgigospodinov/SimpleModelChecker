package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ModelCheckerTest {

    /*
     * An example of how to set up and call the model building methods and make
     * a call to the model checker itself. The contents of model.json,
     * constraint1.json and ctl.json are just examples, you need to add new
     * models and formulas for the mutual exclusion task.
     */
    @Test
    public void buildAndCheckModel() {
        try {
            Model model = Model.parseModel("src/test/resources/ts/m1.json");

            //StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraint1.json").parse();
            //StateFormula query = new FormulaParser("src/test/resources/custom/ctl_1.json").parse();
            StateFormula ctl = new BoolProp(true);
            StateFormula constr = new BoolProp(true);
            ModelChecker mc = new SimpleModelChecker();

            // TODO IMPLEMENT
            assertTrue(mc.check(model, constr, ctl));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
