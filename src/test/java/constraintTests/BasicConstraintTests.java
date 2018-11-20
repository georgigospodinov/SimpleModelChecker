package constraintTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import formula.FormulaParser;
import formula.pathFormula.Always;
import formula.pathFormula.Eventually;
import formula.pathFormula.Until;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.State;

public class BasicConstraintTests {
	LinkedList<State> stateList = new LinkedList<>();
	
	
	@Test
	public void boolPropTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf = FormulaParser.parseRawFormulaString("TRUE");	
		StateFormula constraint = FormulaParser.parseRawFormulaString("TRUE");
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint, path));
		}

    	path = new LinkedList<>();		
		pf = FormulaParser.parseRawFormulaString("TRUE");	
		constraint = FormulaParser.parseRawFormulaString("FALSE");
		for (State s: m.getInitStates()) {
			assertFalse(pf.isValidIn(s, constraint, path));
		}
	}
	
	@Test
	public void varTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf = FormulaParser.parseRawFormulaString("p");	
		StateFormula constraint = FormulaParser.parseRawFormulaString("p");
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint, path));
		}
	}
	
	@Test
	public void notTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf = FormulaParser.parseRawFormulaString("p");	
		StateFormula constraint = FormulaParser.parseRawFormulaString("! FALSE");
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint, path));
		}

    	path = new LinkedList<>();		
		pf = FormulaParser.parseRawFormulaString("! r");	
		constraint = FormulaParser.parseRawFormulaString("! FALSE");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint, path));
		}
	}
	
	@Test
	public void existsTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf =  FormulaParser.parseRawFormulaString("!E G p");	
		StateFormula constraint = new ThereExists(new Eventually(new AtomicProp("q"), null, null));
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint, path));
		}
	}
	
	@Test
	public void forAllTest() throws IOException {
    	LinkedList<State> path = new LinkedList<>();		
		StateFormula pf =  FormulaParser.parseRawFormulaString("!E G p");	
		StateFormula constraint = new ThereExists(new Eventually(new AtomicProp("q"), null, null));
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pf.isValidIn(s, constraint, path));
		}
	}
	

}
