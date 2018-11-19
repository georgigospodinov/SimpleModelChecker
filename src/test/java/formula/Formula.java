package formula;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import formula.pathFormulaTests.PathFormulaSuite;
import formula.stateFormulaTests.StateFormulaSuite;;


@RunWith(Suite.class)
@SuiteClasses({StateFormulaSuite.class, PathFormulaSuite.class})
public class Formula {

}
