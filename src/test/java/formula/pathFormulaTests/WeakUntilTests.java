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

import static formula.stateFormula.BoolProp.TRUE;
import static org.junit.Assert.assertTrue;

public class WeakUntilTests {
    StateFormula t = new BoolProp(true);
    StateFormula f = new BoolProp(false);

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
    public void printTest() {
        PathFormula u = new WeakUntil(f, f, null, null);
        assertTrue(("(" + f + " W " + f + ")").equals(u.toString()));
    }

}
