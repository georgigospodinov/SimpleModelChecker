package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.Path;
import model.TransitionTo;

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
    public boolean isValidIn(TransitionTo t, Path p, StateFormula constraint) {
        return constraint.holdsIn(t) && pathFormula.forAll(t, p);
    }

    @Override
    public boolean holdsIn(TransitionTo t) {
        return isValidIn(t, new Path(), new BoolProp(true));
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
