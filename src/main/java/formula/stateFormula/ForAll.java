package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.State;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    public boolean isValidIn(State s, StateFormula constraint, LinkedList<State> basePath) {
    	LinkedList<State> path = new LinkedList<>();
        boolean b = pathFormula.forAll(s, path, basePath);
        Iterator<State> i = path.descendingIterator();
        System.out.println("Path: ");
        while (i.hasNext())
            System.out.println(i.next());
        System.out.println("---");
        path.addAll(basePath);
        while (!basePath.isEmpty())
        	basePath.removeAll(path);
        basePath.addAll(path);
        return b;
    }

	@Override
	public boolean holdsIn(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean holdsInLeaf(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateFormula fromHere(State s) {
		// TODO Auto-generated method stub
		return null;
	}

}
