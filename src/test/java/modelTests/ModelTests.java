package modelTests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import model.Model;
import model.TransitionTo;

public class ModelTests {

	@Test
	public void getEmptyStates() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		assertTrue(m.getState("") == null);
	}
	
	@Test
	public void transitionInTest() {
		String[] acts = {"act1"};
		TransitionTo t = new TransitionTo(null, null, acts);
		assertTrue(t.isIn(null));
	}
	
}
