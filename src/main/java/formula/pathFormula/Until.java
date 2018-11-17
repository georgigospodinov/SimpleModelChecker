package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.Set;

public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public boolean skipPathSymbol(State s) {
        return left.isValidIn(s) || right.isValidIn(s);
    }

	@Override
	public boolean pathFrom(State s) {
		if (rightActions==null && right.isValidIn(s) ) {
			// in final state
			// could get there by any means 
			return true;
		}
		else if (!left.isValidIn(s)) {
			// not in final state 
			// until condition is not true
			return false;
		}
		else {
			// TODO cycle checks
			
			// check if there is a transition to a state where right is valid 
			for (TransitionTo t: s.getTransitions()) {
				if (rightActions == null || t.isIn(rightActions)) {
					if (right.isValidIn(t.getTrg()))
						return true;
				}
			}
			
			// forwards recursive checks
			for (TransitionTo t: s.getTransitions()) {
				if (leftActions == null || t.isIn(leftActions)) {
					if (left.isValidIn(t.getTrg())) {
						if (pathFrom(t.getTrg()))
							return true;
					}
				}
			}
			return false;
		}
	}
}
