package constraintTests;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import org.junit.Test;

import java.io.IOException;

import static formula.stateFormula.BoolProp.TRUE;
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
	
	@Test
	public void mutexNestedTest() throws IOException {
		// system without mutex
		Model model = Model.parseModel("src/test/resources/mutex/model.json");
		// constraint provides mutex
		StateFormula constraint = new FormulaParser("src/test/resources/mutex/const.json").parse();
		//StateFormula constraint = FormulaParser.parseRawFormulaString("(AG!(c1 && c2) && (AF c1 && AF c2))");
		System.out.println(constraint);
		
		
		
		// formula expects mutex
	    // StateFormula f = new FormulaParser("src/test/resources/mutex/both_critical.json").parse();
		StateFormula f = FormulaParser.parseRawFormulaString("!EF (c1 && c2)");
		System.out.println(f);
		
	    for (State s : model.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        // should fail without constraint
	        //assertFalse(f.isValidIn(t, path, TRUE));
	        path = new Path();
	        assertTrue(f.isValidIn(t, path, constraint));
	        
	        //f.isValidIn(t, path, constraint);
	        for (String tr: path.toTrace())
	        	System.out.println(tr);
	    }
	}


}
