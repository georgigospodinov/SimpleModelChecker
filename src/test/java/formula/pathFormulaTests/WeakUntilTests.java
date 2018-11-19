package formula.pathFormulaTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

import formula.pathFormula.PathFormula;
import formula.pathFormula.WeakUntil;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

public class WeakUntilTests {
	/*
	 * 
	 * TODO remove notes 
	 * 
	 * Until should return true if it finds a path, and store the path in the linked list 
	 * If it does not find a path, the list should be length 0.
	 * 
	 */
	
	
	@Test 
	public void untilBasicTests() {	
		
		// true until true 
		// should always accept (already in final state)
		LinkedList<State> path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new BoolProp(true), new BoolProp(true), null, null);	
		assertTrue(pathFormula.exists(null, path));
		assertTrue(path.size() == 1);
		
		// false until true 
		// should always accept (already in final state)
		path = new LinkedList<>();
		pathFormula = new WeakUntil(new BoolProp(false), new BoolProp(true), null, null);		
		assertTrue(pathFormula.exists(null, path));
		assertTrue(path.size() == 1);
		
		
		// false until false
		// should always fail
		path = new LinkedList<>();
		pathFormula = new WeakUntil(new BoolProp(false), new BoolProp(false), null, null);		
		assertFalse(pathFormula.exists(null, path));	
		assertTrue(path.size() == 0);	
		
	}
	
	@Test 
	public void untilTrueTests() throws IOException {
		LinkedList<State> path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new AtomicProp("p"), new AtomicProp("q"), null, null);		
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 3);
		}
	}

	@Test 
	public void trueUntilFalseTest() throws IOException {
		LinkedList<State> path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new BoolProp(true), new BoolProp(false), null, null);		
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test 
	public void untilFalseEndTest() throws IOException {
		LinkedList<State> path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new AtomicProp("p"), new BoolProp(false), null, null);		
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test 
	public void untilCycleTest() throws IOException {

		LinkedList<State> path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new AtomicProp("p"), new AtomicProp("q"), null, null);// , null, null);		
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test 
	public void untilFullyRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		
		StateFormula l = new AtomicProp("p");
		StateFormula r = new AtomicProp("q");
		Set<String> empty = new HashSet<String>();

		LinkedList<State> path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(l, r, null, null);	
		// as strong until
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 3);
		}
		path = new LinkedList<>();
		pathFormula = new WeakUntil(l, r, empty, null);	
		/* should stop in first state 
		 * second state is not r, so cannot be reached by right transitions
		 * stays in first state 
		 * no onwards transitions (empty list)
		 */
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 1);
		}
		path = new LinkedList<>();
		pathFormula = new WeakUntil(l, r, null, empty);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.exists(s, path));
			assertTrue(path.size() == 0);
		}
		path = new LinkedList<>();
		pathFormula = new WeakUntil(l, r, empty, empty);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 1);
		}
	}
	
	@Test 
	public void untilPartiallyRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		
		StateFormula l = new AtomicProp("p");
		StateFormula r = new AtomicProp("q");
		Set<String> leftFree = new HashSet<String>();
		leftFree.add("act1");
		Set<String> rightFree = new HashSet<String>();
		rightFree.add("act2");
		Set<String> leftRestr = new HashSet<String>();
		leftRestr.add("act2");
		Set<String> rightRestr = new HashSet<String>();
		rightRestr.add("act1");

		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(l, r, leftFree, rightFree);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 3);
		}
		path = new LinkedList<>();
		pathFormula = new WeakUntil(l, r, leftRestr, rightFree);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 1);
		}
		path = new LinkedList<>();
		pathFormula = new WeakUntil(l, r, leftFree, rightRestr);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 2);
		}
		path = new LinkedList<>();
		pathFormula = new WeakUntil(l, r, leftRestr, rightRestr);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.exists(s, path));
			assertTrue(path.size() == 1);
		}
	}
	
	@Test
	public void untilForAllTrueTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new BoolProp(true), new AtomicProp("q"), null, null);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void untilForAllFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new BoolProp(true), new AtomicProp("r"), null, null);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void untilForAllAlwaysFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new BoolProp(false), new BoolProp(false), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(s, path));
			assertTrue(path.size() == 1);
		}
	}
	
	@Test
	public void untilForAllCycleTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new WeakUntil(new BoolProp(true), new AtomicProp("r"), null, null);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void printTest() {
		StateFormula f = new BoolProp(true);
		PathFormula u = new WeakUntil(f, f, null, null);	
		assertTrue(("(" + f + " W " + f + ")").equals(u.toString()));		
	}

}
