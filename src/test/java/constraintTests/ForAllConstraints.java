package constraintTests;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ForAllConstraints {
	
	@Test
	public void varTest() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A F q");
		Model passing = Model.parseModel("src/test/resources/ts/m6.json");
		Model failing = Model.parseModel("src/test/resources/ts/m7.json");
	    StateFormula e = FormulaParser.parseRawFormulaString("E F r");
	    StateFormula a = FormulaParser.parseRawFormulaString("A F r");
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(e.isValidIn(t, path, constraint));	
	        path = new Path();
	        assertFalse(a.isValidIn(t, path, constraint));	        
	    }
	    
	    for (State s : failing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertFalse(e.isValidIn(t, path, constraint));	 
	        path = new Path();
	        assertFalse(a.isValidIn(t, path, constraint));	       
	    }
	}
	

	@Test
	public void forAllEventuallyTest() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A F q");
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
	public void forAllAlwaysTest() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A G (p||q)");
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
	public void forAllNestedEventuallyTests() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A F A F q");
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
	public void forAllNestedAlwaysTests() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A G A F q");
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
	


}
