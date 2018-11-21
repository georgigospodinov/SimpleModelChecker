package constraintTests;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
	    StateFormula e = FormulaParser.parseRawFormulaString("E F r");
	    StateFormula a = FormulaParser.parseRawFormulaString("A F r");
	    //TODO
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
	        //TODO
	        //assertFalse(e.isValidIn(t, path, constraint));	 
	        //assertFalse(a.isValidIn(t, path, constraint));	       
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
