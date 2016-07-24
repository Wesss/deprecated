package org;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.unit.GameFrameworkTest;
import org.unit.MainLoopAdvancedInterTest;
import org.unit.MainLoopBasicInterTest;

@RunWith(Suite.class)
@SuiteClasses({ GameFrameworkTest.class, MainLoopBasicInterTest.class, MainLoopAdvancedInterTest.class })
public class AllUnitTests {}