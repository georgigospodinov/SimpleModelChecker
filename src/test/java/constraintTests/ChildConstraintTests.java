package constraintTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import formula.pathFormula.Eventually;
import formula.pathFormula.Next;
import formula.pathFormula.Until;
import formula.pathFormula.WeakUntil;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.ForAll;
import formula.stateFormula.Not;
import formula.stateFormula.Or;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
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
	
	@Test 
	public void thereExistsTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula exists = new ThereExists(new Eventually(new AtomicProp("q"), null, null));
		StateFormula notExists = new ThereExists(new Eventually(new AtomicProp("r"), null, null));
		
		for (State s: m.getInitStates()) {
			StateFormula childTrue = exists.childConstraint(new TransitionTo(s));
			assertTrue(childTrue instanceof BoolProp);
			assertTrue(childTrue.holdsIn(new TransitionTo(new State())));

			StateFormula childFalse = notExists.childConstraint(new TransitionTo(s));
			assertTrue(childFalse instanceof BoolProp);
			assertFalse(childFalse.holdsIn(new TransitionTo(new State())));
		}
	}
	
	@Test 
	public void forAllNextTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula forAll = new ForAll(new Next(new AtomicProp("p"), null));
		
		for (State s: m.getInitStates()) {
			StateFormula childTrue = forAll.childConstraint(new TransitionTo(s));
			assertTrue(childTrue instanceof BoolProp);
			assertFalse(childTrue.holdsIn(null));
			for (TransitionTo t: s.getTransitions()) {
				childTrue = forAll.childConstraint(t);
				assertTrue(childTrue instanceof AtomicProp);
				assertTrue(childTrue.holdsIn(t));
			}
		}
	}
	
	@Test 
	public void forAllWeakUntilTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula untilTrue = new ForAll(new WeakUntil(TRUE, new AtomicProp("q"), null, null));
		StateFormula untilFalse = new ForAll(new WeakUntil(new AtomicProp("p"), new AtomicProp("r"), null, null));
		StateFormula untilInstant = new ForAll(new WeakUntil(new AtomicProp("p"), TRUE, null, null));
		StateFormula untilInstantFalse = new ForAll(new WeakUntil(FALSE, new AtomicProp("r"), new HashSet<String>(), null));
		 
		for (State s: m.getInitStates()) {
			TransitionTo t = new TransitionTo(s);
			
			//transition does not violate constraint, and it holds
			StateFormula childTrue = untilTrue.childConstraint(t);
			assertTrue(childTrue instanceof ForAll);
			assertTrue(childTrue.holdsIn(t));

			//transition does not violate constraint, but it does not hold
			StateFormula childFalse = untilFalse.childConstraint(t);
			assertTrue(childFalse instanceof ForAll);
			assertFalse(childFalse.holdsIn(t));
			
			//transition passes constraint, becomes stable in TRUE
			StateFormula childInstant = untilInstant.childConstraint(t);
			assertTrue(childInstant instanceof BoolProp);
			assertTrue(childInstant.holdsIn(t));
			
			//transition fails constraint, becomes FALSE
			StateFormula childInstantFail = untilInstantFalse.childConstraint(t);
			assertTrue(childInstantFail instanceof BoolProp);
			assertFalse(childInstantFail.holdsIn(t));
		}
	}
	
	@Test 
	public void forAllStrongUntilTest() throws IOException {
		//TODO
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula untilTrue = new ForAll(new Until(TRUE, new AtomicProp("q"), null, null));
		StateFormula untilFalse = new ForAll(new Until(new AtomicProp("p"), new AtomicProp("r"), null, null));
		StateFormula untilInstant = new ForAll(new Until(new AtomicProp("p"), TRUE, null, null));
		StateFormula untilInstantFalse = new ForAll(new Until(FALSE, new AtomicProp("r"), new HashSet<String>(), null));
		 
		for (State s: m.getInitStates()) {
			TransitionTo t = new TransitionTo(s);
			
			//transition does not violate constraint, and it holds
			StateFormula childTrue = untilTrue.childConstraint(t);
			assertTrue(childTrue instanceof ForAll);
			assertTrue(childTrue.holdsIn(t));

			//transition does not violate constraint, but it does not hold
			StateFormula childFalse = untilFalse.childConstraint(t);
			assertTrue(childFalse instanceof ForAll);
			assertFalse(childFalse.holdsIn(t));
			
			//transition passes constraint, becomes stable in TRUE
			StateFormula childInstant = untilInstant.childConstraint(t);
			assertTrue(childInstant instanceof BoolProp);
			assertTrue(childInstant.holdsIn(t));
			
			//transition fails constraint, becomes FALSE
			StateFormula childInstantFail = untilInstantFalse.childConstraint(t);
			assertTrue(childInstantFail instanceof BoolProp);
			assertFalse(childInstantFail.holdsIn(t));
		}
	}
	
	@Test 
	public void forAllInvalidTest() throws IOException {
		StateFormula untilTrue = new ForAll(null);
		try {
			untilTrue.childConstraint(null);
			fail();
		} catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}
	
	/*
	 * TODO
	 * 
	 * For all with action set constraints
	 * 
	 */

}
