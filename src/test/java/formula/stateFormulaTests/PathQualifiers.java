package formula.stateFormulaTests;


import formula.FormulaParser;
import formula.pathFormula.Next;
import formula.pathFormula.PathFormula;
import formula.stateFormula.BoolProp;
import formula.stateFormula.ForAll;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.Path;
import model.State;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathQualifiers {
	StateFormula constraint = new BoolProp(true);

	@Test
	public void printTests() throws IOException {
		PathFormula pf = new Next(new BoolProp(true), null);
		StateFormula e = new ThereExists(pf);
		StateFormula a = new ForAll(pf);
		assertTrue(a.toString().equals("(A" + pf + ")"));
		assertTrue(e.toString().equals("(E" + pf + ")"));
	}

	@Test
	public void existsTests() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula et = FormulaParser.parseRawFormulaString("E X TRUE");
		StateFormula ef = FormulaParser.parseRawFormulaString("E X FALSE");
		for (State s : m.getInitStates()) {
			assertTrue(et.isValidIn(s, constraint));
			assertFalse(ef.isValidIn(s, constraint));
		}
	}

	@Test
	public void existsPathTests() throws IOException {
		// TODO
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula et = FormulaParser.parseRawFormulaString("E X TRUE");
		StateFormula ef = FormulaParser.parseRawFormulaString("E X FALSE");
		Path path = new Path();
		for (State s : m.getInitStates()) {
			/*
			et.isValidIn(s, constraint, path);
			assertTrue(path.isEmpty());
			ef.isValidIn(s, constraint, path);
			assertTrue(path.size() == 1);
			*/
		}
	}

	@Test
	public void forAllTests() throws IOException {
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula at = FormulaParser.parseRawFormulaString("A X TRUE");
		StateFormula af = FormulaParser.parseRawFormulaString("A X FALSE");
		for (State s : m.getInitStates()) {
			assertTrue(at.isValidIn(s, constraint));
			assertFalse(af.isValidIn(s, constraint));
		}
	}

	@Test
	public void forAllPathTests() throws IOException {
		// TODO
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula at = FormulaParser.parseRawFormulaString("A X TRUE");
		StateFormula af = FormulaParser.parseRawFormulaString("A X FALSE");
		Path path = new Path();
		for (State s : m.getInitStates()) {
			/*
			at.isValidIn(s, constraint, path);
			assertTrue(path.isEmpty());
			af.isValidIn(s, constraint, path);
			// TODO  is this correct?
			assertTrue(path.size() == 2);
			*/
		}
	}

}
