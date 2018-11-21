package formula.pathFormulaTests;

import formula.pathFormula.Eventually;
import formula.pathFormula.PathFormula;
import formula.stateFormula.AtomicProp;
import model.Model;
import model.Path;
import model.State;
import model.TransitionTo;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;

import static formula.stateFormula.BoolProp.TRUE;
import static formula.stateFormula.BoolProp.FALSE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventuallyTests {
	
    @Test
    public void becomesTrueTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula e = new Eventually(new AtomicProp("q"), null, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertTrue(e.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
            path = new Path();
            assertTrue(e.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
        }
    }
    
    @Test
    public void shortCircuitTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula e = new Eventually(new AtomicProp("p"), null, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertTrue(e.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
            path = new Path();
            assertTrue(e.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void deadEndTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula e = new Eventually(FALSE, null, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertFalse(e.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
            path = new Path();
            assertFalse(e.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
    }

    @Test
    public void cycleTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m2.json");
        PathFormula e = new Eventually(FALSE, null, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertFalse(e.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
            path = new Path();
            assertFalse(e.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
    }


    @Test
    public void leftActionSetTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        HashSet<String> as = new HashSet<>();
        PathFormula pf = new Eventually(new AtomicProp("q"), as, null);
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
    public void rightActionSetTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        HashSet<String> as = new HashSet<>();
        PathFormula pf = new Eventually(new AtomicProp("q"), null, as);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertFalse(pf.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
            path = new Path();
            assertFalse(pf.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
    }

    @Test
    public void printTest() {
        PathFormula e = new Eventually(TRUE, null, null);
        assertTrue(e.toString().equals("F" + TRUE));
    }
}
