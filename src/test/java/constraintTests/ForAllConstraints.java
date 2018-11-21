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
	public void varTest() throws IOException {
		Model passing = Model.parseModel("src/test/resources/ts/m6.json");
		Model failing = Model.parseModel("src/test/resources/ts/m7.json");
	    StateFormula f = FormulaParser.parseRawFormulaString("E F r");
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(f.isValidIn(t, path, constraint));	        
	    }
	    for (State s : failing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertFalse(f.isValidIn(t, path, constraint));	        
	    }
	}
	

	@Test
	public void forAllTest() throws IOException {
		constraint = FormulaParser.parseRawFormulaString("A F q");
		Model passing = Model.parseModel("src/test/resources/ts/m6.json");
		Model failing = Model.parseModel("src/test/resources/ts/m7.json");
	    StateFormula f = FormulaParser.parseRawFormulaString("A F q");
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(f.isValidIn(t, path, constraint));	        
	    }
	    for (State s : failing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(f.isValidIn(t, path, constraint));	        
	    }
	}


}
