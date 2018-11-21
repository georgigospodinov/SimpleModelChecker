package formula.stateFormulaTests;

import formula.stateFormula.*;
import model.Path;
import model.State;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PropositionalLogic {
	StateFormula constraint = new BoolProp(true);
	State s = new State();
	BoolProp t = new BoolProp(true);
	BoolProp f = new BoolProp(false);

    @Test
    public void notTest() {
		Not nt = new Not(t);
		Not nf = new Not(f);
		assertFalse(nt.isValidIn(s, constraint));
		assertTrue(nf.isValidIn(s, constraint));
    }

    @Test
    public void notPathTest() {
		// TODO
		Not nt = new Not(t);
		Not nf = new Not(f);
		Path path = new Path();
    	/*
    	nf.isValidIn(s, constraint, path);
    	assertTrue(path.isEmpty());
    	nt.isValidIn(s, constraint, path);
    	assertTrue(path.size() == 1);
    	*/
    }

    @Test
    public void andTest() {
		And ff = new And(f, f);
		assertFalse(ff.isValidIn(s, constraint));
		And ft = new And(f, t);
		assertFalse(ft.isValidIn(s, constraint));
		And tf = new And(t, f);
		assertFalse(tf.isValidIn(s, constraint));
		And tt = new And(t, t);
		assertTrue(tt.isValidIn(s, constraint));
    }

    @Test
    public void andPathTest() {
		// TODO
		And tt = new And(t, t);
		And ff = new And(f, f);
		Path path = new Path();
    	/*
    	tt.isValidIn(s, constraint, path);
    	assertTrue(path.isEmpty());
    	ff.isValidIn(s, constraint, path);
    	assertTrue(path.size() == 1);
    	*/
    }

    @Test
    public void orTest() {
		Or ff = new Or(f, f);
		assertFalse(ff.isValidIn(s, constraint));
		Or ft = new Or(f, t);
		assertTrue(ft.isValidIn(s, constraint));
		Or tf = new Or(t, f);
		assertTrue(tf.isValidIn(s, constraint));
		Or tt = new Or(t, t);
		assertTrue(tt.isValidIn(s, constraint));
    }

	@Test
    public void orPathTest() {
		// TODO
		Or tt = new Or(t, t);
		Or ff = new Or(f, f);
		Path path = new Path();
    	/*
    	tt.isValidIn(s, constraint, path);
    	assertTrue(path.isEmpty());
    	ff.isValidIn(s, constraint, path);
    	assertTrue(path.size() == 1);
    	*/
    }

	@Test
    public void printTest() {
		Or or = new Or(t, t);
		And and = new And(t, t);
		Not not = new Not(t);
		assertTrue(or.toString().equals("(" + t + " || " + t + ")"));
		assertTrue(and.toString().equals("(" + t + " && " + t + ")"));
		assertTrue(not.toString().equals("!(" + t + ")"));

    }

}
