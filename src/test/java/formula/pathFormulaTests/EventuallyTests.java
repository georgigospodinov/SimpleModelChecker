package formula.pathFormulaTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import formula.pathFormula.Eventually;
import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.AtomicProp;

public class EventuallyTests {
	LinkedList<State> stateList = new LinkedList<>();

	@Test
	public void eventTrueTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula e = new Eventually(new AtomicProp("q"), null, null);	
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertTrue(e.exists(s, path, stateList));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventFalseEndTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula e = new Eventually(new Not(new AtomicProp("p")), null, null);	
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertTrue(e.exists(s, path, stateList));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventFalseCycleTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m3.json");
		PathFormula e = new Eventually(new BoolProp(false), null, null);	
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertFalse(e.exists(s, path, stateList));
			assertTrue(path.size() == 0);
		}
	}
	

	@Test
	public void eventForAllTrueTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new Eventually(new AtomicProp("q"), null, null);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.forAll(s, path, stateList));
			//assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void eventForAllFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new Eventually(new AtomicProp("r"), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(s, path, stateList));
			System.out.println("\n\n\n" + path.size() + "\n\n\n");
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventForAllAlwaysFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new Eventually(new BoolProp(false), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(s, path, stateList));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventForAllCycleTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		LinkedList<State>  path = new LinkedList<>();
		PathFormula pathFormula = new Until(new BoolProp(true), new AtomicProp("r"), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(s, path, stateList));
			assertTrue(path.size() == 3);
		}
	}
	
	
	@Test 
	public void printTest() {
		StateFormula f = new BoolProp(true);
		PathFormula e = new Eventually(f, null, null);
		assertTrue(e.toString().equals("F" + f));
	}
}
