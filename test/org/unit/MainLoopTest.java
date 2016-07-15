package org.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.GamePanel;
import org.framework.MainLoop;
import org.framework.MainLoop.MainLoopAction;
import org.framework.MainLoopAdvancedInterface;
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
		
		mainloopValidate.invoke(mainloop);
		assertTrue(advInter.containsAction(action));
		assertTrue(advInter.containsAction(action, 0));
		assertFalse(advInter.contains(mockObj));
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advDeleteAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction action = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(action, 0);
		advInter.deleteAction(action);
		
		mainloopValidate.invoke(mainloop);
		assertFalse(advInter.containsAction(action));
		assertFalse(advInter.containsAction(action, 0));
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advInsertSameObjTwice() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction action1 = advInter.createAddAction(mockObj, 0, 0);
		MainLoopAction action2 = advInter.createAddAction(mockObj, 1, 1);
		advInter.insertAction(action1, 0);
		advInter.insertAction(action2, 1);

		mainloopValidate.invoke(mainloop);
		assertTrue(advInter.containsAction(action1));
		assertTrue(advInter.containsAction(action1, 0));
		assertTrue(advInter.containsAction(action2));
		assertTrue(advInter.containsAction(action2, 1));
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advInsertSameObjTwiceDiffFrames() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
		nextFrame.invoke(mainloop, mockGraphics);
		advInter.insertAction(advInter.createAddAction(mockObj, 1, 1), 0);
		nextFrame.invoke(mainloop, mockGraphics);
		
		// TODO check second obj's layer or prioirty
		mainloopValidate.invoke(mainloop);
		assertTrue(advInter.contains(mockObj));
		verify(mockObj, times(2)).draw(mockGraphics);
	}
	
	@Test
	public void advNextFrameNoObjs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		nextFrame.invoke(mainloop, mockGraphics);
		mainloopValidate.invoke(mainloop);
	}
	
	@Test
	public void advDeleteAllNoActions() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		advInter.deleteAllActions();
		mainloopValidate.invoke(mainloop);
	}
	
	@Test
	public void advDeleteAllActions() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction action1 = advInter.createAddAction(mockObj, 0, 0);
		MainLoopAction action2 = advInter.createClearAction();
		advInter.insertAction(action1, 1);
		advInter.insertAction(action2, 0);
		advInter.deleteAllActions();
		
		mainloopValidate.invoke(mainloop);
		assertFalse(advInter.containsAction(action1));
		assertFalse(advInter.containsAction(action2));
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void advInsertObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(insert, 0);
		nextFrame.invoke(mainloop, mockGraphics);

		mainloopValidate.invoke(mainloop);
		assertFalse(advInter.containsAction(insert));
		assertTrue(advInter.contains(mockObj));
		verify(mockObj).draw(mockGraphics);
	}
	
	@Test
	public void advRemoveObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
		advInter.insertAction(insert, 0);
		nextFrame.invoke(mainloop, mockGraphics);
		MainLoopAction remove = advInter.createRemoveAction(mockObj);
		advInter.insertAction(remove, 0);
		nextFrame.invoke(mainloop, mockGraphics);

		mainloopValidate.invoke(mainloop);
		assertFalse(advInter.containsAction(remove));
		assertFalse(advInter.contains(mockObj));
		verify(mockObj).update();
		verify(mockObj).draw(mockGraphics);
	}

	/*
	-insadd
	-conact
	-deladd
	-conobj
	-insadd conadd
	-insadd deladd
	-nextfr
	-delAll
	-insadd insclr delAll
	-insadd nextfr conobj
	-insadd insadd
	-insadd nextfr insadd nextfr
	-insadd nextfr insrem nextfr
	insclr nextfr TODO
	insadd insadd2 <group>
	insadd insadd2 nextfr <paint>
	insadd insadd2 nextfr nextfr <priority>
	insadd insadd2 nextfr insclr nextfr
	insadd insadd2 nextfr insclr insrem(group 2) nextfr
	*/
}
