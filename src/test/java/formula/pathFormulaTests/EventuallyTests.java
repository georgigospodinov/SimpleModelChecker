package formula.pathFormulaTests;

import formula.pathFormula.Eventually;
import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;

import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventuallyTests {
	LinkedList<State> stateList = new LinkedList<>();

	@Test
	public void eventTrueTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula e = new Eventually(new AtomicProp("q"), null, null);
		Path path = new Path();
		for (State s: m.getInitStates()) {
			assertTrue(e.exists(new TransitionTo(s), path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventFalseEndTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula e = new Eventually(new Not(new AtomicProp("p")), null, null);	
		Path path = new Path();
		for (State s: m.getInitStates()) {
			assertTrue(e.exists(new TransitionTo(s), path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventFalseCycleTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m3.json");
		Path path = new Path();
		PathFormula e = new Eventually(new BoolProp(false), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(e.exists(new TransitionTo(s), path));
			assertTrue(path.size() == 0);
		}
	}
	

	@Test
	public void eventForAllTrueTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		Path path = new Path();
		PathFormula pathFormula = new Eventually(new AtomicProp("q"), null, null);	
		for (State s: m.getInitStates()) {
			assertTrue(pathFormula.forAll(new TransitionTo(s), path));
			//assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void eventForAllFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		Path path = new Path();
		PathFormula pathFormula = new Eventually(new AtomicProp("r"), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(new TransitionTo(s), path));
			System.out.println("\n\n\n" + path.size() + "\n\n\n");
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventForAllAlwaysFalseTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		Path path = new Path();
		PathFormula pathFormula = new Eventually(new BoolProp(false), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(new TransitionTo(s), path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventForAllCycleTest() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m2.json");
		Path path = new Path();
		PathFormula pathFormula = new Until(new BoolProp(true), new AtomicProp("r"), null, null);	
		for (State s: m.getInitStates()) {
			assertFalse(pathFormula.forAll(new TransitionTo(s), path));
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
