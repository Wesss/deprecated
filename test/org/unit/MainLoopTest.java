package org.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.GamePanel;
import org.framework.MainLoop;
import org.framework.MainLoopAdvancedInterface;
import org.framework.MainLoopAdvancedInterface.MainLoopAction;
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
	private static MainLoopAdvancedInterface advInter = null;
	
	private static Method mainloopValidate = null;
	private static Method nextFrame = null;
	
	private static Graphics mockGraphics = mock(Graphics.class);
	private static GamePanel mockPanel = mock(GamePanel.class);
	private GameObj mockObj = mock(GameObj.class);
	
	//////////////////////////////////////////////////
	// Setup
	//////////////////////////////////////////////////
	
	@BeforeClass
	public static void setupClass() {
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

			nextFrame = MainLoop.class.getDeclaredMethod("nextFrame", Graphics.class);
			nextFrame.setAccessible(true);
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException
				| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
		advInter = mainloop.advancedInterface();
	}
	
	@Before
	public void setup() {
		reset(mockPanel);
	}
	
	@Test
	public void testSetup() {
		// just see if setup works correctly
	}
	
	@Test
	public void testInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		mainloopValidate.invoke(mainloop);
	}
	
	
	
	//////////////////////////////////////////////////
	// Basic API Tests
	//////////////////////////////////////////////////
	
	/*@Test
	public void add() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		mainloop.add(mockObj);
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void addContains() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		mainloop.add(mockObj);
		assertTrue(mainloop.contains(mockObj));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void contains() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertFalse(mainloop.contains(mockObj));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}*/
	
	//////////////////////////////////////////////////
	// Advanced API Tests
	//////////////////////////////////////////////////
	
	@Test
	public void advAddAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advContainsNoAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertFalse(advInter.containsAction(advInter.createAddAction(mockObj, 0, 0)));
		assertFalse(advInter.containsAction(advInter.createAddAction(mockObj, 0, 0), 0));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advContainsAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction action = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(action, 0);
		assertTrue(advInter.containsAction(action));
		assertTrue(advInter.containsAction(action, 0));
		assertFalse(advInter.contains(mockObj));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advRemoveAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction action = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(action, 0);
		advInter.deleteAction(action);
		assertFalse(advInter.containsAction(action));
		assertFalse(advInter.containsAction(action, 0));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advNextFrame() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		nextFrame.invoke(mainloop, mockGraphics);
		mainloopValidate.invoke(mainloop);
	}

	/*
	-insadd
	-conact/
	-insadd conadd
	-insadd deladd
	deladd
	insadd insadd
	-nextfr
	insadd nextfr
	conobj
	insadd nextfr conobj
	insadd nextfr insrem nextfr
	delAll
	insadd insclr delAll
	insclr nextfr
	insadd insadd2 <group>
	insadd insadd2 nextfr <paint>
	insadd insadd2 nextfr nextfr <priority>
	insadd insadd2 nextfr insclr nextfr
	insadd insadd2 nextfr insclr insrem(group 2) nextfr
	*/
}
