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
import model.TransitionTo;

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
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula et = FormulaParser.parseRawFormulaString("E X TRUE");
		StateFormula ef = FormulaParser.parseRawFormulaString("E X FALSE");
		Path path = new Path();
		for (State s : m.getInitStates()) {
			TransitionTo t = new TransitionTo(s);
			et.isValidIn(t, path, constraint);
			assertTrue(path.isEmpty());
			path = new Path();
			ef.isValidIn(t, path, constraint);
			assertTrue(path.isEmpty());
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
		Model m = Model.parseModel("src/test/resources/ts/m1.json");
		StateFormula at = FormulaParser.parseRawFormulaString("A X TRUE");
		StateFormula af = FormulaParser.parseRawFormulaString("A X FALSE");
		Path path = new Path();
		for (State s : m.getInitStates()) {
			TransitionTo t = new TransitionTo(s);
			at.isValidIn(t, path, constraint);
			assertTrue(path.isEmpty());
			path = new Path();
			af.isValidIn(t, path, constraint);
			System.out.println(path);
			assertTrue(path.size() == 2);
		}
	}

}
