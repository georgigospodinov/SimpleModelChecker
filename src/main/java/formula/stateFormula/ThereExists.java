package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.LinkedList;

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
    public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> basePath) {
        if (!constraint.holdsIn(t))
    		return false;
    	LinkedList<State> path = new LinkedList<>();
        boolean exists = pathFormula.exists(t, path, basePath);
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
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        return constraint.holdsIn(t) && pathFormula.exists(t, p);

    }

    @Override
    public boolean holdsIn(TransitionTo t) {
        return isValidIn(t, new BoolProp(true), new LinkedList<State>());
	}

	@Override
    public boolean holdsInLeaf(TransitionTo t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public StateFormula childConstraint(TransitionTo t) {
        return new BoolProp(holdsIn(t));
	}

}
