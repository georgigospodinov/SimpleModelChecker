package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.State;

import java.util.Iterator;
import java.util.LinkedList;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;

    public ForAll(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(State s) {
    	LinkedList<State> path = new LinkedList<>();
        boolean b = pathFormula.forAll(s, path);
        Iterator<State> i = path.descendingIterator();
        System.out.println("Path: ");
        while (i.hasNext())
            System.out.println(i.next());
        System.out.println("---");
        return b;
    }

}
