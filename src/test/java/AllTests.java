import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import constraintTests.ConstraintTests;
import formula.Formula;
import modelChecker.ModelCheckerTest;

@RunWith(Suite.class)
@SuiteClasses({ConstraintTests.class, Formula.class, ModelCheckerTest.class })
public class AllTests {

}
