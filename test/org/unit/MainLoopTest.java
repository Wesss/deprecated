package org.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.MainLoop;
import org.junit.Test;

public class MainLoopTest{
	
	public static final int NO_UPS = 0;
	public static final int SLOW_UPS = 2;
	public static final int STANDARD_UPS = 60;
	public static final int STRESSTEST_UPS = 300;
	
	//////////////////////////////////////////////////
	// Helpers
	//////////////////////////////////////////////////
	
	private MainLoop initMainLoop(int UPS) {
		MainLoop mainloop = null;
		try {
			Method init = MainLoop.class.getDeclaredMethod("init", int.class);
			init.setAccessible(true);
			mainloop = (MainLoop) init.invoke(null, STANDARD_UPS);
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException
				| IllegalAccessException | InvocationTargetException e) {
			fail();
		}
		return mainloop;
	}
	
	//////////////////////////////////////////////////
	// Tests
	//////////////////////////////////////////////////
	
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
			// pass
		}
	}

}
