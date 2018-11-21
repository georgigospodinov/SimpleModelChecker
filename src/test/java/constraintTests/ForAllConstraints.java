package constraintTests;

import formula.FormulaParser;
import formula.pathFormula.Eventually;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

import static formula.stateFormula.BoolProp.FALSE;
import static formula.stateFormula.BoolProp.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ForAllConstraints {
	StateFormula constraint;
	TransitionTo t = new TransitionTo(new State());
	
	@Before
	public void setup () throws IOException {
		constraint = FormulaParser.parseRawFormulaString("A F q");
	}
	
	   
	@Test
	public void boolTest() {
		assertFalse(TRUE.isValidIn(t, new Path(), FALSE));
	}
	
	
	@Test
	public void logicTest() throws IOException {
		StateFormula and = FormulaParser.parseRawFormulaString("TRUE && TRUE");
		StateFormula or = FormulaParser.parseRawFormulaString("TRUE || TRUE");
		StateFormula not = FormulaParser.parseRawFormulaString("! FALSE");
		assertFalse(and.isValidIn(t, new Path(), FALSE));
		assertFalse(or.isValidIn(t, new Path(), FALSE));
		assertFalse(not.isValidIn(t, new Path(), FALSE));
	}
	

	@Test
	public void varTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
	    StateFormula f = FormulaParser.parseRawFormulaString("p");
	    for (State s : m.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
			assertFalse(f.isValidIn(t, new Path(), FALSE));
	    }
	}
	
	@Test
	public void existsTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
	    StateFormula f = FormulaParser.parseRawFormulaString("E F TRUE");
	    for (State s : m.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
			assertFalse(f.isValidIn(t, new Path(), FALSE));
	    }
	}

	@Test
	public void forAllTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
	    StateFormula f = FormulaParser.parseRawFormulaString("A F TRUE");
	    for (State s : m.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
			assertFalse(f.isValidIn(t, new Path(), FALSE));
	    }
	}


}
