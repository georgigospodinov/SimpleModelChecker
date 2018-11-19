package formula.pathFormula;

import formula.FormulaParser;
import formula.stateFormula.BoolProp;
import formula.stateFormula.StateFormula;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;
import java.util.Set;

public class Eventually extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Eventually(StateFormula stateFormula, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.stateFormula = stateFormula;
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
        buffer.append(FormulaParser.EVENTUALLY_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

	@Override
	public boolean pathFrom(State s) {
        Until u = new Until(new BoolProp(true), stateFormula, leftActions, rightActions);
        return u.pathFrom(s);
	}

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        Until u = new Until(new BoolProp(true), stateFormula, leftActions, rightActions);
        return u.shouldPrune(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        Until u = new Until(new BoolProp(true), stateFormula, leftActions, rightActions);
        return u.shouldNotPrune(s);
    }
}
