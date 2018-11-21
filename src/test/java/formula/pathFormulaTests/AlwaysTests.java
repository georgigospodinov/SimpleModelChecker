package formula.pathFormulaTests;

import formula.pathFormula.Always;
import formula.pathFormula.PathFormula;
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

import static formula.stateFormula.BoolProp.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlwaysTests {
    LinkedList<State> stateList = new LinkedList<>();
    StateFormula t = new BoolProp(true);
    StateFormula f = new BoolProp(false);

    /*
     * G _A a
     * Always holds if:
     * Only transitions from A occur and a is true in every state
     *
     * Accepts loops.
     *
     * Fails if and only if a transition from A leads to a state where a is false
     *
     * Corner case: transition not in A gives state where a is false
     *
     */

    @Test
    public void linearVarTest() throws IOException {
        //TODO
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula p = new Always(new AtomicProp("p"), null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertFalse(p.exists(t, path, TRUE));
            System.out.println(path);
            assertTrue(path.size() == 0);
            path = new Path();
            assertFalse(p.forAll(t, path, TRUE));
            System.out.println(path.size());
            assertTrue(path.size() == 3);
        }
    }

    @Test
    public void linearBoolTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m1.json");
        PathFormula alwaysTrue = new Always(t, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(alwaysTrue.exists(t, path, TRUE));
            assertTrue(path.size() == 3);
            path = new Path();
            assertTrue(alwaysTrue.forAll(t, path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void cycleVarTest() throws IOException {
        //TODO
        Model m = Model.parseModel("src/test/resources/ts/m2.json");
        PathFormula a = new Always(new AtomicProp("p"), null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(a.exists(t, path, TRUE));
            System.out.println(path);
            assertTrue(path.size() == 3);
            path = new Path();
            assertTrue(a.forAll(t, path, TRUE));
            assertTrue(path.isEmpty());
        }
    }


    @Test
    public void actionSetTest() throws IOException {
        //TODO
        Model m = Model.parseModel("src/test/resources/ts/m4.json");
        HashSet<String> as = new HashSet<>();
        as.add("act1");
        PathFormula a = new Always(new AtomicProp("p"), as);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(a.exists(t, path, TRUE));
            assertTrue(path.size() == 2);
            path = new Path();
            assertTrue(a.forAll(t, path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void noPossibleTransitions() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m0.json");
        PathFormula a = new Always(t, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(a.exists(t, path, TRUE));
            assertTrue(path.size() == 1);
            path = new Path();
            assertTrue(a.forAll(t, path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void existsOnlyTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m5.json");
        PathFormula a = new Always(new AtomicProp("p"), null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            TransitionTo t = new TransitionTo(s);
            assertTrue(a.exists(t, path, TRUE));
            assertTrue(path.size() == 2);
            path = new Path();
            assertFalse(a.forAll(t, path, TRUE));
            assertTrue(path.size() == 2);
        }
    }

    @Test
    public void printTest() {
        PathFormula a = new Always(t, null);
        assertTrue(a.toString().equals("G" + t));
    }
}
