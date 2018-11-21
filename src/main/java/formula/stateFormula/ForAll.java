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
        if (!constraint.holdsIn(t))
        	return false;
        p.push(t);
        if (pathFormula.forAll(t, p, TRUE)) {
        	p.pop();
        	return true;
        }
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

        if (pathFormula instanceof WeakUntil) {
            WeakUntil wu = (WeakUntil) this.pathFormula;
            if ((wu.rightActions == null || t.isIn(wu.rightActions)) && wu.right.isValidIn(t.getTrg(), TRUE))
                return TRUE;
            if (wu.leftActions == null || t.isIn(wu.leftActions))
                return this;
            else return FALSE;
        }
        else if (pathFormula instanceof Until) {
            Until u = (Until) this.pathFormula;
            if ((u.rightActions == null || t.isIn(u.rightActions)) && u.right.isValidIn(t.getTrg(), TRUE))
                return TRUE;
            if (u.leftActions == null || t.isIn(u.leftActions))
                return this;
            else return FALSE;
        }
        throw new UnsupportedOperationException("All pathFormulas are instances of one of the three.");
    }

}
