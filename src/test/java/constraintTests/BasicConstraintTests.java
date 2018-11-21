package constraintTests;

import formula.FormulaParser;
import formula.pathFormula.Eventually;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.State;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicConstraintTests {
	LinkedList<State> stateList = new LinkedList<>();
	
	
	@Test
	public void boolPropTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf = FormulaParser.parseRawFormulaString("TRUE");	
		StateFormula constraint = FormulaParser.parseRawFormulaString("TRUE");
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint));
		}

    	path = new LinkedList<>();		
		pf = FormulaParser.parseRawFormulaString("TRUE");	
		constraint = FormulaParser.parseRawFormulaString("FALSE");
		for (State s: m.getInitStates()) {
			assertFalse(pf.isValidIn(s, constraint));
		}
	}
	
	@Test
	public void varTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf = FormulaParser.parseRawFormulaString("p");	
		StateFormula constraint = FormulaParser.parseRawFormulaString("p");
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint));
		}
	}
	
	@Test
	public void notTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf = FormulaParser.parseRawFormulaString("p");	
		StateFormula constraint = FormulaParser.parseRawFormulaString("! FALSE");
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint));
		}

    	path = new LinkedList<>();		
		pf = FormulaParser.parseRawFormulaString("! r");	
		constraint = FormulaParser.parseRawFormulaString("! FALSE");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint));
		}
	}
	
	@Test
	public void existsTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf =  FormulaParser.parseRawFormulaString("!E G p");	
		StateFormula constraint = new ThereExists(new Eventually(new AtomicProp("q"), null, null));
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint));
		}
	}
	
	@Test
	public void forAllTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf =  FormulaParser.parseRawFormulaString("!E G p");	
		StateFormula constraint = new ThereExists(new Eventually(new AtomicProp("q"), null, null));
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint));
		}
	}
	

}
