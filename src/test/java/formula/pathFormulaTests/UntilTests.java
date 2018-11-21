package formula.pathFormulaTests;

import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static formula.stateFormula.BoolProp.TRUE;
import static formula.stateFormula.BoolProp.FALSE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UntilTests {
	
	@Test
	public void leftInvalidTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula pf = new Until(FALSE, new AtomicProp("q"), null, null);
        for (State s : m.getInitStates()) {
    		Path path = new Path();
            assertFalse(pf.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
            path = new Path();
            assertFalse(pf.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
        }
	}
	
    @Test
    public void actionSetTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        StateFormula l = new AtomicProp("p");
        StateFormula r = new AtomicProp("q");
        Set<String> leftAct = new HashSet<String>();
        leftAct.add("act1");
        Set<String> rightAct = new HashSet<String>();
        rightAct.add("act2");
        PathFormula pf = new Until(l, r, leftAct, rightAct);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertTrue(pf.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
            path = new Path();
            assertTrue(pf.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void printTest() {
        StateFormula f = new BoolProp(true);
        PathFormula u = new Until(f, f, null, null);
        assertTrue(("(" + f + " U " + f + ")").equals(u.toString()));
    }

}
