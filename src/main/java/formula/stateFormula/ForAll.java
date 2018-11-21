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
        Path fa = new Path();
        if (pathFormula.forAll(t, fa, constraint)) {
        	return true;
        }
        //p.push(t);
        for (TransitionTo tran: fa)
        	p.push(tran);
        return false;
        
    }
    
    /*
     * A for all constrain holds if there exists an execution path for which it is true.
     * 
     * (non-Javadoc)
     * @see formula.stateFormula.StateFormula#holdsIn(model.TransitionTo)
     */
    @Override
    public boolean holdsIn(TransitionTo t) {
        StateFormula e = new ThereExists(pathFormula);
        return e.isValidIn(t,  new Path(), TRUE);
    }

    @Override
    public StateFormula childConstraint(TransitionTo t) {
        if (pathFormula instanceof Next) {
            Next n = (Next) this.pathFormula;
            if ((n.actions == null && t.getActions() != null) || t.isIn(n.actions))
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
