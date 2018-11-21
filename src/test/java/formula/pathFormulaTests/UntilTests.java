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
import java.util.LinkedList;
import java.util.Set;

import static formula.stateFormula.BoolProp.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UntilTests {
    LinkedList<State> stateList = new LinkedList<>();
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
        Path path = new Path();
        Until pathFormula = new Until(new BoolProp(true), new BoolProp(true), null, null);
        assertTrue(pathFormula.exists(null, path, TRUE));
        assertTrue(path.size() == 1);

        // false until true
        // should always accept (already in final state)
        path = new Path();
        pathFormula = new Until(new BoolProp(false), new BoolProp(true), null, null);
        assertTrue(pathFormula.exists(null, path, TRUE));
        assertTrue(path.size() == 1);


        // false until false
        // should always fail
        path = new Path();
        pathFormula = new Until(new BoolProp(false), new BoolProp(false), null, null);
        assertFalse(pathFormula.exists(null, path, TRUE));
        assertTrue(path.size() == 0);

    }

    @Test
    public void untilTrueTests() throws IOException {
        Path path = new Path();
        Until pathFormula = new Until(new AtomicProp("p"), new AtomicProp("q"), null, null);
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
    }

    @Test
    public void untilFalseEndTest() throws IOException {
        Path path = new Path();
        Until pathFormula = new Until(new AtomicProp("p"), new BoolProp(false), null, null);
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
    }

    @Test
    public void untilFalseCycleTest() throws IOException {

        Path path = new Path();
        Until pathFormula = new Until(new AtomicProp("p"), new AtomicProp("q"), null, null);// , null, null);
        Model m = Model.parseModel("src/test/resources/ts/m2.json");
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
    }

    @Test
    public void untilFullyRestrainedTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");

        StateFormula l = new AtomicProp("p");
        StateFormula r = new AtomicProp("q");
        Set<String> empty = new HashSet<String>();

        Path path = new Path();
        Until pathFormula = new Until(l, r, null, null);
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
        path = new Path();
        pathFormula = new Until(l, r, empty, null);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
        path = new Path();
        pathFormula = new Until(l, r, null, empty);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
        path = new Path();
        pathFormula = new Until(l, r, empty, empty);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
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

        Path path = new Path();
        Until pathFormula = new Until(l, r, leftFree, rightFree);
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
        path = new Path();
        pathFormula = new Until(l, r, leftRestr, rightFree);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
        path = new Path();
        pathFormula = new Until(l, r, leftFree, rightRestr);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
        path = new Path();
        pathFormula = new Until(l, r, leftRestr, rightRestr);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
    }

    @Test
    public void untilForAllTrueTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        Path path = new Path();
        Until pathFormula = new Until(new BoolProp(true), new AtomicProp("q"), null, null);
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
        }
    }

    @Test
    public void untilForAllFalseTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        Path path = new Path();
        Until pathFormula = new Until(new BoolProp(true), new AtomicProp("r"), null, null);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
    }

    @Test
    public void untilForAllAlwaysFalseTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        Path path = new Path();
        Until pathFormula = new Until(new BoolProp(false), new BoolProp(false), null, null);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
        }
    }

    @Test
    public void untilForAllCycleTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m2.json");
        Path path = new Path();
        Until pathFormula = new Until(new BoolProp(true), new AtomicProp("r"), null, null);
        for (State s : m.getInitStates()) {
            assertFalse(pathFormula.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
    }

    @Test
    public void printTest() {
        StateFormula f = new BoolProp(true);
        PathFormula u = new Until(f, f, null, null);
        assertTrue(("(" + f + " U " + f + ")").equals(u.toString()));
    }

}
