package modelChecker;

import static formula.stateFormula.BoolProp.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

public class MutexTests {

	@Test
	public void mutexFailsTest() throws IOException {
		System.out.println("Model without mutex constraint");
		String[] args = {
				"src/test/resources/mutex/model.json", 
				"src/test/resources/integration/true.json", 
				"src/test/resources/mutex/not_both_critical.json"};
		new Main();
		Main.main(args);
		
		// system without mutex
		Model model = Model.parseModel("src/test/resources/mutex/model.json");
		// constraint does not provide provides mutex
		StateFormula constraint = TRUE;
		// formula expects mutex
	    StateFormula f = new FormulaParser("src/test/resources/mutex/not_both_critical.json").parse();

        ModelChecker mc = new SimpleModelChecker();
        assertFalse(mc.check(model, constraint, f));
	}
	
	@Test
	public void mutexPassesTest() throws IOException {
		System.out.println("Model with mutex constraint");
		String[] args = {
				"src/test/resources/mutex/model.json", 
				"src/test/resources/mutex/exclusion_fairness.json", 
				"src/test/resources/mutex/not_both_critical.json"};
		new Main();
		Main.main(args);
		
		// system without mutex
		Model model = Model.parseModel("src/test/resources/mutex/model.json");
		// constraint enforces mutex
		StateFormula constraint = new FormulaParser("src/test/resources/mutex/exclusion_fairness.json").parse();
		// formula expects mutex
	    StateFormula f = new FormulaParser("src/test/resources/mutex/not_both_critical.json").parse();

        ModelChecker mc = new SimpleModelChecker();
        assertTrue(mc.check(model, constraint, f));
	}
}
