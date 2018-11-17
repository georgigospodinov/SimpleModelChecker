package formula.pathFormula;

import model.State;

public abstract class PathFormula {
    public abstract void writeToBuffer(StringBuilder buffer);
    
    public abstract boolean pathFrom(State s);
}
