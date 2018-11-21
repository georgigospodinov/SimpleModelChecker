package formula.pathFormulaTests;

import formula.pathFormula.Next;
import formula.pathFormula.PathFormula;
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

public class NextTests {
	
    @Test
    public void trueTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m2.json");
        PathFormula n = new Next(TRUE, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertTrue(n.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
            path = new Path();
            assertTrue(n.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void falseTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m2.json");
        PathFormula n = new Next(FALSE, null);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertFalse(n.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
            path = new Path();
            assertFalse(n.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
        }
    }
    
    @Test
    public void freeActionSetTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m3.json");
        Set<String> as = new HashSet<String>();
        as.add("act1");

        PathFormula n = new Next(TRUE, as);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertTrue(n.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
            path = new Path();
            assertTrue(n.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 1);
        }
    }
        
    @Test
    public void limitedActionSetTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m3.json");
        Set<String> as = new HashSet<String>();
        as.add("act2");
        PathFormula n = new Next(TRUE, as);
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertFalse(n.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
            path = new Path();
            assertFalse(n.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
        }
    }
    
    @Test
    public void emptyActionSetTest() throws IOException {
        Model m = Model.parseModel("src/test/resources/ts/m3.json");
        PathFormula n = new Next(TRUE, new HashSet<String>());
        for (State s : m.getInitStates()) {
            Path path = new Path();
            assertFalse(n.forAll(new TransitionTo(s), path, TRUE));
            assertTrue(path.size() == 0);
            path = new Path();
            assertFalse(n.exists(new TransitionTo(s), path, TRUE));
            assertTrue(path.isEmpty());
        }
    }

    @Test
    public void printTest() {
        PathFormula n = new Next(TRUE, null);
        assertTrue(n.toString().equals("X" + TRUE));
    }
}
