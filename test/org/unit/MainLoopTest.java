package org.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.GamePanel;
import org.framework.MainLoop;
import org.framework.MainLoopAdvancedInterface;
import org.framework.MainLoopAdvancedInterface.MainLoopAction;
import org.framework.MainLoopFactory;
import org.framework.MainLoopFactoryFactory;
import org.framework.interfaces.GameObj;
import org.junit.Before;
import org.junit.Test;

public class MainLoopTest{
	
	public static final int NO_UPS = 0;
	public static final int SLOW_UPS = 2;
	public static final int STANDARD_UPS = 60;
	public static final int STRESSTEST_UPS = 300;
	
	private MainLoop mainloop = null;
	private MainLoopAdvancedInterface advInter = null;
	
	private Method mainloopValidate = null;
	private Method nextFrame = null;
	
	private Graphics mockGraphics = mock(Graphics.class);
	private GamePanel mockPanel = mock(GamePanel.class);
	private GameObj mockObj = mock(GameObj.class);
	private GameObj mockObj2 = mock(GameObj.class);
	
	//////////////////////////////////////////////////
	// Setup
	//////////////////////////////////////////////////
	
	@Before
	public void setup() {
		reset(mockPanel, mockGraphics, mockObj);
		
		mainloop = null;
		try {
			Method getFactory = MainLoopFactoryFactory.class.getDeclaredMethod("getMainLoopFactory");
			getFactory.setAccessible(true);
			MainLoopFactory factory = (MainLoopFactory) getFactory.invoke(null);
			
			Method init = MainLoopFactory.class.getDeclaredMethod("constructMainLoop", int.class);
			init.setAccessible(true);
			init.invoke(factory, STANDARD_UPS);
			mainloop = (MainLoop) factory.getMainLoop();
			
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
	public void advInsertAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
	public void advDeleteNoAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		advInter.deleteAction(advInter.createAddAction(mockObj, 0, 0));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advContainsNoObj() {
		assertFalse(advInter.contains(mockObj));
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
	public void advDeleteAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction action = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(action, 0);
		advInter.deleteAction(action);
		assertFalse(advInter.containsAction(action));
		assertFalse(advInter.containsAction(action, 0));
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advNextFrameNoObjs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		nextFrame.invoke(mainloop, mockGraphics);
		mainloopValidate.invoke(mainloop);
	}
	
	@Test
	public void advInsertObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(insert, 0);
		nextFrame.invoke(mainloop, mockGraphics);
		assertFalse(advInter.containsAction(insert));
		assertTrue(advInter.contains(mockObj));
		mainloopValidate.invoke(mainloop);
		verify(mockObj).update();
	}
	
	@Test
	public void advRemoveObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(insert, 0);
		nextFrame.invoke(mainloop, mockGraphics);
		MainLoopAction remove = advInter.createRemoveAction(mockObj);
		advInter.insertAction(remove, 0);
		nextFrame.invoke(mainloop, mockGraphics);
		assertFalse(advInter.containsAction(remove));
		assertFalse(advInter.contains(mockObj));
		mainloopValidate.invoke(mainloop);
		verify(mockObj).update();
	}
	
	/* bad calls */
	
	@Test
	public void advInsertSameObjTwice() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
		try {
			advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
			fail();
		} catch (IllegalArgumentException e) {}
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advInsertSameObjTwice2() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
		nextFrame.invoke(mainloop, mockGraphics);
		try {
			advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
			fail();
		} catch (IllegalArgumentException e) {}
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}

	/*
	-insadd
	-conact
	-deladd
	-conobj
	-insadd conadd
	-insadd deladd
	-nextfr
	delAll
	insadd insclr delAll
	-insadd nextfr conobj
	-insadd nextfr insrem nextfr
	insclr nextfr
	insadd insadd2 <group>
	insadd insadd2 nextfr <paint>
	insadd insadd2 nextfr nextfr <priority>
	insadd insadd2 nextfr insclr nextfr
	insadd insadd2 nextfr insclr insrem(group 2) nextfr
	
	badcalls
	 TODO remove these
	-insadd insadd
	-insadd nextfr insadd
	*/
}
