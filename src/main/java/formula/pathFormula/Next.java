package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.Path;
import model.State;
import model.TransitionTo;

import java.util.Set;

public class Next extends PathFormula {
    //TODO replace
    StateFormula constraint = new BoolProp(true);
    public final StateFormula stateFormula;
    public final Set<String> actions;

    public Next(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NEXT_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public boolean exists(TransitionTo t, Path p, StateFormula constraint) {
        State current = t.getTrg();
        for (TransitionTo transition : current.getTransitions()) {
            if (actions == null || transition.isIn(actions)) {
                if (stateFormula.isValidIn(transition, p, this.constraint)) {
                    p.push(transition);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean forAll(TransitionTo t, Path p, StateFormula constraint) {
        if (actions != null && actions.isEmpty())
            return false;

        int paths = 0;
        for (TransitionTo transition : t.getTrg().getTransitions()) {
            if (actions == null || transition.isIn(actions)) {
                paths++;
                if (!stateFormula.isValidIn(transition, p, this.constraint)) {
                    p.push(transition);
                    return false;
                }
            }
        }
        // We need at least one valid path
        return paths > 0;
    }
}
