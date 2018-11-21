package formula.pathFormulaTests;

import formula.pathFormula.PathFormula;
import formula.pathFormula.WeakUntil;
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
import static org.junit.Assert.assertTrue;

public class WeakUntilTests {
    StateFormula t = new BoolProp(true);
    StateFormula f = new BoolProp(false);


    /*
     * Tests are in addition to those in always
     *
     * Accepting in first state is covered
     *
     * Need to handle right hand side stuff
     *
     * Corner cases:
     * only right transitions are allowed
     *
     * Short circuit to rhs
     *
     *
     * weak until fails only if a becomes untrue before b becomes true
     *
     */


    @Test
    public void shortCircuitTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula wu = new WeakUntil(new BoolProp(false), new BoolProp(true), null, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(wu.exists(t, path, TRUE));
            assertTrue(path.size() == 1);
            path = new Path();
            assertTrue(wu.forAll(t, path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void varTrueTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula wu = new WeakUntil(new AtomicProp("p"), new AtomicProp("q"), null, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(wu.exists(t, path, TRUE));
            assertTrue(path.size() == 3);
            path = new Path();
            assertTrue(wu.forAll(t, path, TRUE));
            System.out.println(path);
            assertTrue(path.isEmpty());
        }
    }


    @Test
    public void noLeftActionsTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        StateFormula l = new AtomicProp("p");
        StateFormula r = new AtomicProp("q");

        PathFormula pathFormula = new WeakUntil(l, r, new HashSet<String>(), null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            // cannot make a transition
            assertTrue(pathFormula.exists(t, path, TRUE));
            assertTrue(path.size() == 1);
            path = new Path();
            assertTrue(pathFormula.forAll(t, path, TRUE));
            assertTrue(path.isEmpty());
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
        PathFormula pathFormula = new WeakUntil(l, r, leftFree, rightFree);
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 3);
        }
        path = new Path();
        pathFormula = new WeakUntil(l, r, leftRestr, rightFree);
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
        }
        path = new Path();
        pathFormula = new WeakUntil(l, r, leftFree, rightRestr);
        for (State s : m.getInitStates()) {
            assertTrue(pathFormula.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 2);
        }
    }

    @Test
    public void printTest() {
        PathFormula u = new WeakUntil(f, f, null, null);
        assertTrue(("(" + f + " W " + f + ")").equals(u.toString()));
    }

}
