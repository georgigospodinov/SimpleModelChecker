package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Model model = Model.parseModel(args[0]);
        StateFormula constraint = new FormulaParser(args[1]).parse();
        StateFormula query = new FormulaParser(args[2]).parse();

        ModelChecker mc = new SimpleModelChecker();

        System.out.println(model);
        System.out.println("\nChecking Query: " + query);
        System.out.println("Under Constraint: " + constraint);
        if (mc.check(model, constraint, query)) {
	        System.out.println("Model Passed");
        }
        else {
	        System.out.println("Model Failed on Trace:");
	        for (String s: mc.getTrace())
	        	System.out.println("\t" + s);
        }
    }
}
