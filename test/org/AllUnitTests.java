package org;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.unit.GameFrameworkTest;
import org.unit.MainLoopTest;

@RunWith(Suite.class)
@SuiteClasses({ GameFrameworkTest.class, MainLoopTest.class })
public class AllUnitTests {

}
