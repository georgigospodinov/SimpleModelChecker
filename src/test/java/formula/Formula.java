package formula;

import formula.pathFormulaTests.PathFormulaSuite;
import formula.stateFormulaTests.StateFormulaSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

;


@RunWith(Suite.class)
@SuiteClasses({StateFormulaSuite.class, PathFormulaSuite.class})
public class Formula {

}
