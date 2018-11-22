package constraintTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BooleanConstraint.class, ForAllConstraints.class, PathFormulaConstraints.class, ChildConstraintTests.class, LogicalConstraints.class})
public class ConstraintTests {

}
