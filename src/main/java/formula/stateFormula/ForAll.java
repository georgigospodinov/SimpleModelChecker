package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.Next;
import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.pathFormula.WeakUntil;
import model.Path;
import model.TransitionTo;

import static formula.stateFormula.BoolProp.FALSE;
import static formula.stateFormula.BoolProp.TRUE;

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
        if (pathFormula instanceof Next) {
            Next n = (Next) this.pathFormula;
            if (t.isIn(n.actions))
                return n.stateFormula;
            else return FALSE;
        }

        boolean validIn = false;
        if (pathFormula instanceof WeakUntil) {
            WeakUntil weakUntil = (WeakUntil) this.pathFormula;
            if (t.isIn(weakUntil.rightActions))
                validIn = weakUntil.right.isValidIn(t.getTrg(), TRUE);

        }
        else if (pathFormula instanceof Until) {
            validIn = ((Until) pathFormula).right.isValidIn(t.getTrg(), TRUE);
        }

        if (validIn) return TRUE;
        else return this;
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
