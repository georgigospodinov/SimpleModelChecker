package modelChecker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({IntegrationTests.class, CallerTest.class, TraceTests.class})
public class ModelCheckerSuite {

}
