package formula.pathFormula;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import model.Model;
import model.State;

public class UntilTests {
	
	
	@Test 
	public void noTransitionTests() {	
		
		// true until true 
		// should always accept (already in final state)
		Until path = new Until(new BoolProp(true), new BoolProp(true), null, null);		
		assertTrue(path.pathFrom(null));
		
		// false until true 
		// should always accept (already in final state)
		path = new Until(new BoolProp(false), new BoolProp(true), null, null);		
		assertTrue(path.pathFrom(null));
		
		
		// false until false
		// should always fail
		path = new Until(new BoolProp(false), new BoolProp(false), null, null);		
		assertFalse(path.pathFrom(null));		
	}
	
	@Test 
	public void unrestrainedTransitionTests() throws IOException {
		
		Until path = new Until(new AtomicProp("p"), new AtomicProp("q"), null, null);		
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		for (State s: m.getInitStates()) {
			assertTrue(path.pathFrom(s));
		}
		
	}
	
	@Test 
	public void cycleDetectionTest() {
		fail("Not yet implemented");
		
		// 
		
	}

}
