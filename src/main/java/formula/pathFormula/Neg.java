package formula.pathFormula;

import formula.FormulaParser;
import model.State;
import model.TransitionTo;

import java.util.LinkedHashMap;

// TODO: We should not use this class as it messes things up.
public class Neg extends PathFormula {
    public final PathFormula pathFormula;

    public Neg(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NOT_TOKEN);
        pathFormula.writeToBuffer(buffer);
    }

    @Override
    public boolean pathFrom(State s) {
        return !pathFormula.pathFrom(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldPrune(State s) {
        return pathFormula.shouldNotPrune(s);
    }

    @Override
    public LinkedHashMap<TransitionTo, State> shouldNotPrune(State s) {
        return pathFormula.shouldPrune(s);
    }
}
