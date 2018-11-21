package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.State;
import model.TransitionTo;

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
	public boolean isValidIn(TransitionTo t, StateFormula constraint, LinkedList<State> basePath) {
		if (!constraint.holdsIn(t))
    		return false;
    	LinkedList<State> path = new LinkedList<>();
		boolean b = pathFormula.forAll(t, path, basePath);
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
		// TODO Auto-generated method stub
		return null;
		/*
		 * Next: stateformula of pathformula 
		 * 
		 * 
		 * Always: true if right.isValidin(s) else self 
		 * Eventually: true if right.isValidin(s) else self
		 * Until: true if right.isValidin(s) else self		 * 
		 * Weak Until: true if right.isValidin(s) else self
		 * 
		 * 
		 * 
		 * Also need transition checks
		 */
	}

}
