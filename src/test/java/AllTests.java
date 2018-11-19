import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import formula.Formula;
import formula.stateFormula.BoolProp;


//import modelChecker.ModelCheckerTest;

@RunWith(Suite.class)
@SuiteClasses({BoolProp.class, Formula.class, /* TODO ModelCheckerTest.class */ })
public class AllTests {

}
