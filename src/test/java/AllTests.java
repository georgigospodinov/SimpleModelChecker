import constraintTests.ConstraintTests;
import formula.Formula;
import modelChecker.ModelCheckerSuite;
import modelTests.ModelTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ConstraintTests.class, Formula.class, ModelCheckerSuite.class, ModelTestSuite.class})
public class AllTests {

}
