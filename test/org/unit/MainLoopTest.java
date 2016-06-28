package org.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.GamePanel;
import org.framework.MainLoop;
import org.framework.MainLoopFactory;
import org.framework.interfaces.GameObj;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainLoopTest{
	
	public static final int NO_UPS = 0;
	public static final int SLOW_UPS = 2;
	public static final int STANDARD_UPS = 60;
	public static final int STRESSTEST_UPS = 300;
	
	private static MainLoop mainloop = null;
	private static Method mainloopValidate = null;
	private static GamePanel mockPanel = mock(GamePanel.class);
	
	private GameObj mockObj;
	
	//////////////////////////////////////////////////
	// Setup
	//////////////////////////////////////////////////
	
	@BeforeClass
	public static void setupClass() {
		reset(mockPanel);
		mainloop = null;
		
		try {
			Method init = MainLoopFactory.class.getDeclaredMethod("constructMainLoop", int.class);
			init.setAccessible(true);
			init.invoke(null, STANDARD_UPS);
			mainloop = (MainLoop) MainLoopFactory.getMainLoop();
			
			Method ref = MainLoop.class.getDeclaredMethod("setReferences", GamePanel.class);
			ref.setAccessible(true);
			ref.invoke(mainloop, mockPanel);
			
			mainloopValidate = MainLoop.class.getDeclaredMethod("assertValid");
			mainloopValidate.setAccessible(true);
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException
				| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Before
	public void setup() {
		mockObj = mock(GameObj.class);
	}
	
	@Test
	public void testSetup() {
		// just see if setup works correctly
	}
	
	//////////////////////////////////////////////////
	// Basic API Tests
	//////////////////////////////////////////////////
	
	@Test
	public void add0() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		mainloop.add(mockObj);
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void add1() {
		mainloop.add(mockObj);
		assertTrue(mainloop.contains(mockObj));
		verifyZeroInteractions(mockObj);
	}
	
}
