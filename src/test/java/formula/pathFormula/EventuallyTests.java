package formula.pathFormula;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import formula.stateFormula.AtomicProp;

public class EventuallyTests {

	@Test
	public void eventTrueTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula e = new Eventually(new AtomicProp("q"), null, null);	
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertTrue(e.exists(s, path));
			assertTrue(path.size() == 3);
		}
	}
	
	@Test
	public void eventFalseEndTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		PathFormula e = new Eventually(new BoolProp(false), null, null);	
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertFalse(e.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test
	public void eventFalseCycleTest() throws IOException {	
		Model m = Model.parseModel("src/test/resources/ts/m3.json");
		PathFormula e = new Eventually(new BoolProp(false), null, null);	
		LinkedList<State> path = new LinkedList<>();
		for (State s: m.getInitStates()) {
			assertFalse(e.exists(s, path));
			assertTrue(path.size() == 0);
		}
	}
	
	@Test 
	public void printTest() {
		StateFormula f = new BoolProp(true);
		PathFormula e = new Eventually(f, null, null);
		assertTrue(e.toString().equals("F" + f));
	}
}
