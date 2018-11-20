package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.State;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;

    public ThereExists(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> basePath) {
    	if (!constraint.holdsIn(s))
    		return false;
    	LinkedList<State> path = new LinkedList<>();
        boolean exists = pathFormula.exists(s, path, basePath);
        /*
        Iterator<State> i = path.descendingIterator();
        System.out.println("Path: ");
        while (i.hasNext())
            System.out.println(i.next());
        System.out.println("---");
        */
        path.addAll(basePath);
        while (!basePath.isEmpty())
        	basePath.removeAll(path);
        basePath.addAll(path);
        return exists;
    }

	@Override
	public boolean holdsIn(State s) {
		return isValidIn(s, new BoolProp(true), new LinkedList<State>());
	}

	@Override
	public boolean holdsInLeaf(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateFormula childConstraint(State s) {
		return new BoolProp(holdsIn(s));
	}

}
