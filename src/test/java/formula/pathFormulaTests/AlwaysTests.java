package formula.pathFormulaTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

import formula.pathFormula.Always;
import formula.pathFormula.PathFormula;
import formula.pathFormula.WeakUntil;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

public class AlwaysTests {
	
	@Test
	public void alwaysTrueCycleTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula a = new Always(new BoolProp(true), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.exists(s, path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void alwaysTrueEndTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula a = new Always(new BoolProp(true), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.exists(s, path));
			assertTrue(path.size() == 3);
		}
	}

	@Test
	public void alwaysFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula a = new Always(new BoolProp(false), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertFalse(a.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void alwaysNoTransitionsTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m0.json");
		PathFormula a = new Always(new BoolProp(true), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.exists(s, path));
			assertTrue(path.size() == 1);
		}
	}
	
	@Test
	public void alwaysFullyRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula a = new Always(new BoolProp(true), new HashSet<String>());
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.exists(s, path));
			assertTrue(path.size() == 1);
		}
	}
	
	@Test
	public void alwaysPartiallyRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		Set<String> free = new HashSet<String>();
		free.add("act1");
		free.add("act2");
		Set<String> restr = new HashSet<String>();
		restr.add("act1");
		
		PathFormula a = new Always(new BoolProp(true), free);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.exists(s, path));
			assertTrue(path.size() == 3);
		}
		a = new Always(new BoolProp(true), restr);
		path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.exists(s, path));
			assertTrue(path.size() == 2);
		}
	}
	
	@Test 
	public void alwaysForAllTrueCycleTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula a = new Always(new BoolProp(true), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}

	@Test 
	public void alwaysForAllTrueEndTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula a = new Always(new BoolProp(true), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test 
	public void alwaysForAllFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula a = new Always(new AtomicProp("r"), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertFalse(a.forAll(s, path));
			assertTrue(path.size() == 1);
		}
	}

	@Test 
	public void alwaysForAllRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		Set<String> restr = new HashSet<String>();
		restr.add("act2");
		PathFormula a = new Always(new AtomicProp("p"), restr);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertTrue(a.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}

	@Test 
	public void alwaysForAllChainTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		Set<String> restr = new HashSet<String>();
		restr.add("act2");
		PathFormula a = new Always(new AtomicProp("p"), null);
		LinkedList<State> path = new LinkedList<State>();
		for (State s: m.getInitStates()) {
			assertFalse(a.forAll(s, path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void printTest() {
		StateFormula f = new BoolProp(true);
		PathFormula a = new Always(f, null);
		assertTrue(a.toString().equals("G" + f));		
	}
}
