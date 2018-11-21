package formula.pathFormulaTests;

import formula.pathFormula.Next;
import formula.pathFormula.PathFormula;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NextTests {
	LinkedList<State> stateList = new LinkedList<>();
	
	@Test
	public void nextTrueTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula n = new Next(new BoolProp(true), null);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertTrue(n.exists(s, path));
			assertTrue(path.size() == 1);
		}
	}
	
	@Test
	public void nextFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula n = new Next(new BoolProp(false), null);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void nextFullyRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		PathFormula n = new Next(new BoolProp(true), new HashSet<String>());
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void nextNoTransitionsTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m0.json");
		PathFormula n = new Next(new BoolProp(true), null);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void nextRestrainedTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m3.json");
		Set<String> free = new HashSet<String>();
		free.add("act1");
		Set<String> restr = new HashSet<String>();
		restr.add("act2");
		
		PathFormula n = new Next(new BoolProp(true), free);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertTrue(n.exists(s, path));
			assertTrue(path.size() == 1);
		}
		n = new Next(new BoolProp(true), restr);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.exists(s, path));
			assertTrue(path.size() == 0);
		}
		n = new Next(new BoolProp(true), new HashSet<String>());
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void nextForAllTrue() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m4.json");
		PathFormula n = new Next(new BoolProp(true), null);
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertTrue(n.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void nextForAllFalse() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m4.json");
		PathFormula n = new Next(new AtomicProp("r"), null);
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertFalse(n.forAll(s, path));
			assertTrue(path.size() == 1);
		}
	}

	@Test
	public void nextForAllRestrained() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m3.json");
		Set<String> free = new HashSet<String>();
		free.add("act1");
		Set<String> restr = new HashSet<String>();
		restr.add("act2");
		
		PathFormula n = new Next(new BoolProp(true), free);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertTrue(n.forAll(s, path));
			assertTrue(path.size() == 0);
		}
		n = new Next(new BoolProp(true), restr);
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.forAll(s, path));
			assertTrue(path.size() == 0);
		}
		n = new Next(new BoolProp(true), new HashSet<String>());
		for (State s: m.getInitStates()) {
			LinkedList<State> path = new LinkedList<>();
			assertFalse(n.forAll(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void printTest() {
		StateFormula f = new BoolProp(true);
		PathFormula n = new Next(f, null);
		assertTrue(n.toString().equals("X" + f));		
	}


}
