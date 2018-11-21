package formula.stateFormulaTests;

import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Variable {
    StateFormula constraint = new BoolProp(true);

    @Test
    public void varNoLabels() {
        AtomicProp x = new AtomicProp("x");
        State none = new State();
        none.setLabels(new String[0]);
        assertFalse(x.isValidIn(none, constraint));
    }

    @Test
    public void varSingleLabel() {
        String var = "x";
        String notVar = "y";
        AtomicProp x = new AtomicProp(var);
        AtomicProp y = new AtomicProp(notVar);

        State state = new State();
        String[] labels = {var};
        state.setLabels(labels);
        assertTrue(x.isValidIn(state, constraint));
        assertFalse(y.isValidIn(state, constraint));
    }

    @Test
    public void varManyLabels() {
        String var = "x";
        String notVar = "y";
        AtomicProp x = new AtomicProp(var);
        AtomicProp y = new AtomicProp(notVar);
        AtomicProp z = new AtomicProp("z");

        // many labels, inc. x
        State state = new State();
        String[] labels = {notVar, var};
        state.setLabels(labels);
        assertTrue(x.isValidIn(state, constraint));
        assertTrue(y.isValidIn(state, constraint));
        assertFalse(z.isValidIn(state, constraint));
    }

    @Test
    public void varPathTest() {
        // TODO
        String var = "x";
        String notVar = "y";
        AtomicProp x = new AtomicProp(var);
        AtomicProp y = new AtomicProp(notVar);

        State state = new State();
        String[] labels = {var};
        state.setLabels(labels);
        Path path = new Path();
        /*
        assertTrue(x.isValidIn(state, constraint, path));
        assertTrue(path.isEmpty());
        assertFalse(y.isValidIn(state, constraint, path));
        assertTrue(path.size() == 1);
        */
    }

}
