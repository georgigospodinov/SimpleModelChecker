package constraintTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.pathFormula.Always;
import formula.pathFormula.Eventually;
import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.pathFormula.WeakUntil;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;
import static formula.stateFormula.BoolProp.TRUE;
import static formula.stateFormula.BoolProp.FALSE;
 
public class PathFormulaConstraints {
	
	@Test
	public void eventuallyTest() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A F q");
		Model passing = Model.parseModel("src/test/resources/ts/m6.json");
		Model failing = Model.parseModel("src/test/resources/ts/m7.json");
	    PathFormula e = new Eventually(new AtomicProp("r"), null, null);
	    
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(e.exists(t, path, constraint));	
	        path = new Path();
	        assertFalse(e.forAll(t, path, constraint));	        
	    }
	    for (State s : failing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        boolean b = e.exists(t, path, constraint);
    		System.out.println(path);
	        assertFalse(b);
	        path = new Path();
	        assertFalse(e.forAll(t, path, constraint));	        
	    }
	}
	

	@Test
	public void alwaysTest() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A F q");
		Model passing = Model.parseModel("src/test/resources/ts/m6.json");
		Model failing = Model.parseModel("src/test/resources/ts/m7.json");
	    PathFormula a = new Always(new AtomicProp("p"), null);
	    
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(a.exists(t, path, constraint));	 
	        path = new Path();
	        assertFalse(a.forAll(t, path, constraint));	       
	    }	    
	    for (State s : failing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(a.exists(t, path, constraint));	    
	        path = new Path();
	        assertTrue(a.forAll(t, path, constraint));	         
	    }	    
	}
	
	
	@Test
	public void childConstraintTest() throws IOException {
		StateFormula constraint = FormulaParser.parseRawFormulaString("A F p");
		Model passing = Model.parseModel("src/test/resources/ts/m8.json");
	    PathFormula e = new Eventually(new AtomicProp("q"), null, null);
	    PathFormula a = new Always(new BoolProp(true), null);
	    
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertTrue(e.exists(t, path, constraint));
	        path = new Path();
	        assertTrue(a.exists(t, path, constraint));	 
	        path = new Path();
	        assertTrue(e.forAll(t, path, constraint));
	        assertFalse(e.forAll(t, path, TRUE));
	        path = new Path();
	        assertTrue(a.forAll(t, path, constraint));
	    }    
	}
	
	@Test
	public void untilBreachTest() throws IOException {
		StateFormula constraint = FALSE;
		Model passing = Model.parseModel("src/test/resources/ts/m1.json");
	    PathFormula wu = new Until(new AtomicProp("p"), new AtomicProp("q"), null, null);
	    PathFormula su = new WeakUntil(new AtomicProp("q"), new AtomicProp("p"), null, null);
	    
	    for (State s : passing.getInitStates()) {
	        TransitionTo t = new TransitionTo(s);
	        Path path = new Path();
	        assertFalse(wu.forAll(t, path, constraint));
	        path = new Path();
	        assertTrue(su.forAll(t, path, constraint));
	    }    
	}

}
