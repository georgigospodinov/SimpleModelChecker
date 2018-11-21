package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        // "src/test/resources/model1.json"
        Model model = Model.parseModel(args[0]);

        // "src/test/resources/constraint1.json"
        StateFormula fairnessConstraint = new FormulaParser(args[1]).parse();

        // "src/test/resources/ctl1.json"
        StateFormula query = new FormulaParser(args[2]).parse();

        ModelChecker mc = new SimpleModelChecker();

        boolean checked = mc.check(model, fairnessConstraint, query);
        System.out.println("checked = " + checked);
        System.out.println(Arrays.toString(mc.getTrace()));
    }
}
