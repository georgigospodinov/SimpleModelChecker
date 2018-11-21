package constraintTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.Or;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.TransitionTo;

import static formula.stateFormula.BoolProp.TRUE;
import static formula.stateFormula.BoolProp.FALSE;

public class ChildConstraintTests {
	
	@Test 
	public void notTest() {
		StateFormula parent = new Not(FALSE);
		StateFormula child = parent.childConstraint(null);
		assertTrue(child instanceof Not);
		assertTrue(child.holdsIn(new TransitionTo(new State())));
	}
	
	@Test 
	public void boolTest() {
		StateFormula t = TRUE.childConstraint(null);
		assertTrue(t instanceof BoolProp);
		assertTrue(t.holdsIn(new TransitionTo(new State())));
		
		StateFormula f = FALSE.childConstraint(null);
		assertTrue(f instanceof BoolProp);
		assertFalse(f.holdsIn(new TransitionTo(new State())));
	}
	

	@Test 
	public void logicTest() {
		StateFormula and = (new And(TRUE, TRUE)).childConstraint(null);
		assertTrue(and instanceof And);
		assertTrue(and.holdsIn(new TransitionTo(new State())));
		
		StateFormula or = (new Or(TRUE, TRUE)).childConstraint(null);
		assertTrue(or instanceof Or);
		assertTrue(or.holdsIn(new TransitionTo(new State())));
	}
	

	@Test 
	public void varTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula varTrue = new AtomicProp("p");
		StateFormula varFalse = new AtomicProp("q");
		for (State s: m.getInitStates()) {
			StateFormula childTrue = varTrue.childConstraint(new TransitionTo(s));
			assertTrue(childTrue instanceof BoolProp);
			assertTrue(childTrue.holdsIn(new TransitionTo(new State())));

			StateFormula childFalse = varFalse.childConstraint(new TransitionTo(s));
			assertTrue(childFalse instanceof BoolProp);
			assertFalse(childFalse.holdsIn(new TransitionTo(new State())));
		}
		
	}

}
