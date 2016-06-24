package org.unit;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.GamePanel;
import org.framework.MainLoop;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainLoopTest{
	
	public static final int NO_UPS = 0;
	public static final int SLOW_UPS = 2;
	public static final int STANDARD_UPS = 60;
	public static final int STRESSTEST_UPS = 300;
	
	private static MainLoop mainloop = null;
	private static GamePanel mockPanel = mock(GamePanel.class);
	
	//////////////////////////////////////////////////
	// Helpers
	//////////////////////////////////////////////////
	
	@BeforeClass
	public static void setup() {
		reset(mockPanel);
		mainloop = null;
		
		try {
			Method init = MainLoop.class.getDeclaredMethod("init", int.class);
			init.setAccessible(true);
			mainloop = (MainLoop) init.invoke(null, STANDARD_UPS);
			
			Method ref = MainLoop.class.getDeclaredMethod("setReferences", GamePanel.class);
			ref.setAccessible(true);
			ref.invoke(mainloop, mockPanel);
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException
				| IllegalAccessException | InvocationTargetException e) {
			Exception ex = e;
			fail();
			System.out.println(e.toString() + " " + e.getMessage());
		}
		System.out.println("ASDF2");
		
	}
	
	//////////////////////////////////////////////////
	// Init Tests
	//////////////////////////////////////////////////
	
	// @Rule
    // public ExpectedException thrown = ExpectedException.none();

	@Test
	public void initStandardTest() {
//		try {
//			Method init = MainLoop.class.getDeclaredMethod("init", int.class);
//			init.setAccessible(true);
//			MainLoop mainloop = (MainLoop) init.invoke(null, STANDARD_UPS);
//			assertNotNull(mainloop);
//		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException
//				| IllegalAccessException | InvocationTargetException e) {
//			fail();
//		}
	}
	
	@Test
	public void initZeroTest() {
		// thrown.expect(IllegalArgumentException.class);
		// thrown.expectMessage("updatesPerSecond must be positive");
//		Method init;
//		Exception caught = null;
//		try {
//			init = MainLoop.class.getDeclaredMethod("init", int.class);
//			init.setAccessible(true);
//			MainLoop mainloop = (MainLoop) init.invoke(null, NO_UPS);
//			assertNotNull(mainloop);
//		} catch (NoSuchMethodException |
//				SecurityException |
//				IllegalAccessException |
//				IllegalArgumentException e) {
//			fail();
//		} catch (InvocationTargetException e) {
//			caught  = null;
//		}
		
//		assertNotNull(caught);
	}
	
	//////////////////////////////////////////////////
	// Advanced API Tests
	//////////////////////////////////////////////////
	
	@Test
	public void AddTest() {
		// TODO
	}
	
}
