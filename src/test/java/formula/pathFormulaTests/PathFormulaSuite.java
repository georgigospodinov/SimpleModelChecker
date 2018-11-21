package formula.pathFormulaTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({UntilTests.class, NextTests.class, AlwaysTests.class, EventuallyTests.class, WeakUntilTests.class})
public class PathFormulaSuite {

}
