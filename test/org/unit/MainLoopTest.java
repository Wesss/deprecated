package org.unit;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.MainLoop;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MainLoopTest{
	
	public static final int NO_UPS = 0;
	public static final int SLOW_UPS = 2;
	public static final int STANDARD_UPS = 60;
	public static final int STRESSTEST_UPS = 300;
	
	// @Rule
    // public ExpectedException thrown = ExpectedException.none();

	@Test
	public void initStandardTest() {
		try {
			Method init = MainLoop.class.getDeclaredMethod("init", int.class);
			init.setAccessible(true);
			MainLoop mainloop = (MainLoop) init.invoke(null, STANDARD_UPS);
			assertNotNull(mainloop);
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException
				| IllegalAccessException | InvocationTargetException e) {
			fail();
		}
	}
	
	@Test
	public void initZeroTest() {
		// thrown.expect(IllegalArgumentException.class);
		// thrown.expectMessage("updatesPerSecond must be positive");
		Method init;
		try {
			init = MainLoop.class.getDeclaredMethod("init", int.class);
			init.setAccessible(true);
			MainLoop mainloop = (MainLoop) init.invoke(null, NO_UPS);
			assertNotNull(mainloop);
		} catch (NoSuchMethodException |
				SecurityException |
				IllegalAccessException |
				IllegalArgumentException e) {
			fail();
		} catch (InvocationTargetException e) {
			// pass!
		}
	}

}
