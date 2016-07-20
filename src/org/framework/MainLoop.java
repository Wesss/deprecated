package org.framework;

import static org.junit.Assert.*;
import static java.lang.Math.*;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.framework.interfaces.GameObj;

/**
 * This singleton class keeps track of all GameObjs in a game
 * and repeatedly updates them in fixed time intervals
 * 
 * @author Wesley Cox
 */
public class MainLoop {
	
	// TODO abstract out index/obj pairings
	// TODO refactor Animate Thread into its own class
	// TODO split class, too large?

	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	/* 
	 * Framework objects
	 * 
	 * Post start:
	 * panel != null
	 * updateCycle != null
	 */
	private GamePanel panel;
	private Thread updateCycle;
	
	/*  
	 * Representation of all the game objects currently being tracked by the MainLoop
	 * 
	 * 
	 * objToLayer != null
	 * for each (obj --> layer) pair in objToLayer
	 * 		layerToObj.get(layer) contains obj
	 * 
	 * objToPriority != null
	 * for each key value pair (obj --> layer) in objToPriority
	 * 		priorityToObj.get(priority) contains obj
	 * 
	 * objToLayer.keySet is equivalent to objToPriority.keyset
	 * 
	 * layerToObj != null
	 * maxLayer >= layerToObj.keyset()'s maximum when non-empty, 0 when empty
	 * for each (layer --> objs) pair in layerToObj.keset()
	 * 		layer >= 0
	 * 		objs != null
	 * 		objs.isEmpty
	 * 		for each obj being stored within objs
	 * 			obj != null
	 * 			obj also exists in priorityToObj
	 * 			obj also exists in objToLayer, objToPriority, priorityToObj
	 * 			obj does not exist anywhere else in layerToObj
	 * 
	 * priorityToObj != null
	 * maxPriority >= priorityToObj.keyset()'s maximum when non-empty, 0 when empty
	 * for each (priority --> objs) pair in priorityToObj.keset()
	 * 		priority >= 0
	 * 		objs != null
	 * 		!objs.isEmpty
	 * 		for each obj being stored within objs
	 * 			obj != null
	 * 			obj also exists in objToLayer, layerToObj, objToPriority
	 * 			obj does not exist anywhere else in priorityToObj
	 */
	private HashMap<GameObj, Integer> objToLayer;
	private HashMap<Integer, HashSet<GameObj>> layerToObj;
	private int maxLayer;
	private HashMap<GameObj, Integer> objToPriority;
	private HashMap<Integer, HashSet<GameObj>> priorityToObj;
	private int maxPriority;
	
	/* 
	 * MainLoop actions to be performed during the cycle-changing process
	 * 
	 * groupToAction != null
	 * maxGroup == groupToAction.keyset()'s maximum when non-empty, 0 when empty
	 * for each group in groupToAction.keset()
	 * 		group >= 0
	 * 		groupToAction.get(group) != null
	 * 		!groupToAction.get(group).isEmpty
	 * for each MainLoopAction action being stored within groupToAction
	 * 		action != null;
	 * 		if action is a MainLoopAddAction
	 * 			action.obj != null
	 * 		if action is a MainLoopRemoveAction
	 * 			action.obj != null
	 */
	private HashMap<Integer, HashSet<MainLoopAction>> groupToAction;
	private int maxGroup;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	protected MainLoop(int FPS) {
		updateCycle = new Thread(new Animate(FPS));
		
		objToLayer = new HashMap<>();
		layerToObj = new HashMap<>();
		maxLayer = 0;
		objToPriority = new HashMap<>();
		priorityToObj = new HashMap<>();
		maxPriority = 0;
		
		groupToAction = new HashMap<>();
		maxGroup = 0;
		
		// TODO these should be moved out when abstracting out the basic UI
//		foregroundGroup = new MainLoopGroup(advancedInterface(),
//				FOREGROUND_LAYER,
//				DEFAULT_PRIORITY,
//				GAMEOBJ_GROUP_PRIORITY);
//		backgroundGroup = new MainLoopGroup(advancedInterface(),
//				BACKGROUND_LAYER,
//				DEFAULT_PRIORITY,
//				GAMEOBJ_GROUP_PRIORITY);
	}
	
	/**
	 * Sets up proper references needed to run the MainLoop
	 * @param p the GamePanel displaying the game
	 */
	protected void setReferences(GamePanel p) {
		panel = p;
	}
	
	/**
	 * Starts the update/repaint cycle
	 * @requires setReferences be called before this
	 */
	protected void start() {
		if (panel == null) {
			throw new RuntimeException("references not yet set");
		}
		updateCycle.start();
	}
	
	//////////////////////////////////////////////////
	// Basic User Interface (API)
	//////////////////////////////////////////////////
	
	private static final int BACKGROUND_LAYER = 0;
	private static final int FOREGROUND_LAYER = 1;
	
	private static final int DEFAULT_PRIORITY = 0;
	// simulates "post update"
	protected static final int GAMEOBJ_GROUP_PRIORITY = 1;
	
	protected static final int DEFAULT_ACTIONGROUP = 0;
	protected static final int CLEAR_ACTIONGROUP = 1;
	protected static final int POSTCLEAR_ACTIONGROUP = 2;
	
	private MainLoopGroup foregroundGroup;
	private MainLoopGroup backgroundGroup;
	
	
	/**
	 * Adds obj to the foreground layer, removing it from the background layer if part of the
	 * background
	 * 
	 * @param obj
	 */
	public void add(GameObj obj) {
		backgroundGroup.remove(obj);
		foregroundGroup.add(obj);
	}

	/**
	 * Adds obj to the background layer, removing it from the foreground layer if part of the
	 * foreground
	 * 
	 * @param obj
	 */
	public void addBackground(GameObj obj) {
		foregroundGroup.remove(obj);
		backgroundGroup.add(obj);
	}

	//TODO
	public boolean contains(GameObj obj) {
		return foregroundGroup.contains(obj) || backgroundGroup.contains(obj);
	}

	//TODO
	public void remove(GameObj obj) {
		if (foregroundGroup.remove(obj) || backgroundGroup.remove(obj));
	}

	//TODO
	public void markClear() {
		foregroundGroup.markClear();
		backgroundGroup.markClear();
	}

	//TODO
	public void markClearForeground() {
		foregroundGroup.markClear();
	}

	//TODO
	public void markClearBackground() {
		backgroundGroup.markClear();
	}

	//TODO
	public void addPostClear(GameObj obj) {
		foregroundGroup.addPostClear(obj);
	}

	//TODO
	public void addBackgroundPostClear(GameObj obj) {
		backgroundGroup.addPostClear(obj);
	}
	
	// TODO
	public MainLoopAdvancedInterface advancedInterface() {
		return new MainLoopAdvancedInterface(this);
	}
	
	//////////////////////////////////////////////////
	// Advanced User Interface (API)
	//////////////////////////////////////////////////
	
	protected boolean containsAdv(GameObj obj) {
		if (obj == null) {
			return false;
		}
		
		return objToLayer.containsKey(obj);
	}
	
	protected void insertAction(MainLoopAction action, int actionGroup) {
		if (actionGroup < 0) {
			throw new IllegalArgumentException("invalid actionGroup of " + actionGroup);
		}
		if (action == null) {
			throw new IllegalArgumentException("null action inserted into mainloop");
		}
		
		if (!groupToAction.containsKey(actionGroup)) {
			HashSet<MainLoopAction> set = new HashSet<>();
			set.add(action);
			groupToAction.put(actionGroup, set);
		} else {
			groupToAction.get(actionGroup).add(action);
		}
		maxGroup = max(maxGroup, actionGroup);
	}

	protected boolean containsAction(MainLoopAction action) {
		if (action == null)
			return false;
		
		for (int group : groupToAction.keySet()) {
			if (groupToAction.get(group).contains(action))
				return true;
		}
		return false;
	}
	
	protected boolean containsAction(MainLoopAction action, int actionGroup) {
		if (action == null)
			return false;
		
		return (groupToAction.containsKey(actionGroup) &&
				groupToAction.get(actionGroup).contains(action));
	}
	
	protected void deleteAction(MainLoopAction action) {
		if (action == null)
			return;
		
		for (int group : groupToAction.keySet()) {
			HashSet<MainLoopAction> actions = groupToAction.get(group);
			if (actions.remove(action)) {
				if (actions.isEmpty()) {
					groupToAction.remove(group);
				}
				break;
			}
		}
	}
	
	protected void deleteAllActions() {
		groupToAction.clear();
		maxGroup = 0;
	}
	
	//////////////////////////////////////////////////
	// Framework Functionality
	//////////////////////////////////////////////////
	
	/**
	 * Moves the game state up one frame/update cycle
	 * @param g The graphics object to paint with
	 */
	protected void nextFrame(Graphics g) {
		updateObjs();
		resolveActions();
		paintObjs(g);
	}
	
	private void updateObjs() {
		for (int i = 0; i <= maxPriority; i++) {
			HashSet<GameObj> objs = priorityToObj.get(i);
			if (objs != null) {
				for (GameObj obj : objs)
					obj.update();
			}
		}
	}
	
	private void paintObjs(Graphics g) {
		for (int i = 0; i <= maxLayer; i++) {
			HashSet<GameObj> objs = layerToObj.get(i);
			if (objs != null) {
				for (GameObj obj : objs)
					obj.draw(g);
			}
		}
	}
	
	private void resolveActions() {
		for (int i = 0; i <= maxGroup; i++) {
			HashSet<MainLoopAction> actions = groupToAction.get(i);
			if (actions != null) {
				for (MainLoopAction action : actions)
					action.acceptResolution(this);
			}
		}
		groupToAction.clear();
		maxGroup = 0;
	}
	
	protected void visitResolution(MainLoopAction action) {
		throw new RuntimeException("visited unknown action type");
	}
	
	protected void visitResolution(MainLoopAddAction action) {
		Integer prevLayer = objToLayer.get(action.getObj());
		Integer prevPriority = objToPriority.get(action.getObj());
		
		if (prevLayer != null && prevPriority != null) {
			removeObjIndexPair(layerToObj, objToLayer, action.getObj(), prevLayer);
			if (!layerToObj.containsKey(prevLayer)) {
				layerToObj.remove(prevLayer);
				if (prevLayer == maxLayer && maxLayer > 0)
					maxLayer--; // TODO actually find max efficiently
			}
			removeObjIndexPair(priorityToObj, objToPriority, action.getObj(), prevPriority);
			if (!priorityToObj.containsKey(prevPriority)) {
				priorityToObj.remove(prevPriority);
				if (prevPriority == maxPriority && maxLayer > 0) {
					maxPriority--;
				}
			}
		}
		
		objToLayer.put(action.getObj(), action.getLayer());
		objToPriority.put(action.getObj(), action.getPriority());
		if (!layerToObj.containsKey(action.getLayer()))
			layerToObj.put(action.getLayer(), new HashSet<>());
		layerToObj.get(action.getLayer()).add(action.getObj());
		if (!priorityToObj.containsKey(action.getPriority()))
			priorityToObj.put(action.getPriority(), new HashSet<>());
		priorityToObj.get(action.getPriority()).add(action.getObj());
		
		maxLayer = max(maxLayer, action.getLayer());
		maxPriority = max(maxPriority, action.getPriority());
	}
	
	protected void visitResolution(MainLoopRemoveAction action) {
		int layer = objToLayer.get(action.getObj());
		int priority = objToPriority.get(action.getObj());
		removeObjIndexPair(layerToObj, objToLayer, action.getObj(), layer);
		if (!layerToObj.containsKey(layer)) {
			layerToObj.remove(layer);
			if (layer == maxLayer && maxLayer > 0)
				maxLayer--; // TODO duplicated above in Add resolution
		}
		removeObjIndexPair(priorityToObj, objToPriority, action.getObj(), priority);
		if (!priorityToObj.containsKey(priority)) {
			priorityToObj.remove(priority);
			if (priority == maxPriority && maxLayer > 0) {
				maxPriority--;
			}
		}
	}
	
	protected void visitResolution(MainLoopClearAction action) {
		objToLayer.clear();
		layerToObj.clear();
		maxLayer = 0;
		objToPriority.clear();
		priorityToObj.clear();
		maxPriority = 0;
	}
	
	private void removeObjIndexPair(HashMap<Integer, HashSet<GameObj>> indexToObjs,
									HashMap<GameObj, Integer> objToIndex,
									GameObj obj,
									int index) {
		objToIndex.remove(obj);
		indexToObjs.get(index).remove(obj);
		if (indexToObjs.get(index).isEmpty()) {
			indexToObjs.remove(index);
		}
	}
	
	/**
	 * This Thread fires an update and a screen refresh to the game based on
	 * its desired frames per seconds
	 */
	private class Animate implements Runnable {

		private int waitTime;
		
		/**
		 * @param fps the desired refresh rate of the screen measured in frames per second
		 */
		public Animate(int fps) {
			waitTime = 1000 / fps;
		}
		
		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					//TODO apparently repaint only quickly signals java to later
					//call paintComponent();
					//	Timing code should envelope the paintComponent code
					long startTime = System.currentTimeMillis();
					panel.repaint();
					long endTime = System.currentTimeMillis();
					
					if (startTime - endTime < waitTime) {
						Thread.sleep(waitTime - (startTime - endTime));
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	//////////////////////////////////////////////////
	// Domain
	//////////////////////////////////////////////////
	
	// TODO
	public static abstract class MainLoopAction {
		protected void acceptResolution(MainLoop loop) {
			loop.visitResolution(this);
		}
	}
	
	protected class MainLoopAddAction extends MainLoopAction {
		private GameObj obj;
		private int priority;
		private int layer;
		
		protected MainLoopAddAction(GameObj obj, int priority, int layer) {
			this.obj = obj;
			this.priority = priority;
			this.layer = layer;
		}
		
		protected GameObj getObj() {
			return obj;
		}
		
		protected int getPriority() {
			return priority;
		}
		
		protected int getLayer() {
			return layer;
		}
		
		protected void acceptResolution(MainLoop loop) {
			loop.visitResolution(this);
		}
	}
	
	protected class MainLoopRemoveAction extends MainLoopAction {
		private GameObj obj;
		
		protected MainLoopRemoveAction(GameObj obj) {
			this.obj = obj;
		}
		
		protected GameObj getObj() {
			return obj;
		}
		
		protected void acceptResolution(MainLoop loop) {
			loop.visitResolution(this);
		}
	}
	
	protected class MainLoopClearAction extends MainLoopAction {
		
		protected MainLoopClearAction() {
			// nothing!
		}
		
		protected void acceptResolution(MainLoop loop) {
			loop.visitResolution(this);
		}
	}
	
	/**
	 * TODO
	 * @param obj
	 * @param priority
	 * @param layer
	 * @return
	 * @throws IllegalArguementException if obj == null, priority < 0, layer < 0
	 */
	public MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
		if (obj == null || priority < 0 || layer < 0)
			throw new IllegalArgumentException("Illegal Add Action creation");
		return new MainLoopAddAction(obj, priority, layer);
	}
	
	/**
	 * TODO
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException if obj == null
	 */
	public MainLoopAction createRemoveAction(GameObj obj) {
		if (obj == null)
			throw new IllegalArgumentException("Illegal Remove Action creation");
		return new MainLoopRemoveAction(obj);
	}

	// TODO
	public MainLoopAction createClearAction() {
		return new MainLoopClearAction();
	}

	//////////////////////////////////////////////////
	// Testing
	//////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private void assertValid() {
		// framework
		assertNotNull(updateCycle);
		assertFalse(updateCycle.isAlive() && panel == null);

		// game objs
		assertTrue(maxLayer >= 0);
		assertTrue(maxPriority >= 0);
		assertNotNull(objToLayer);
		assertNotNull(layerToObj);
		assertNotNull(objToPriority);
		assertNotNull(priorityToObj);
		
		for (GameObj obj : objToLayer.keySet()) {
			assertNotNull(obj);
			assertTrue(layerToObj.get(objToLayer.get(obj)).contains(obj));
		}
		
		for (GameObj obj : objToPriority.keySet()) {
			assertNotNull(obj);
			assertTrue(priorityToObj.get(objToPriority.get(obj)).contains(obj));
		}
		
		Set<GameObj> layerObjStore = new HashSet<>();
		Set<GameObj> priorityObjStore = new HashSet<>();
		Set<Integer> layers = layerToObj.keySet();
		Set<Integer> priorities = layerToObj.keySet();
		
		for (int layer : layers) {
			assertTrue(layer >= 0);
			assertTrue(maxLayer >= layer);
			Set<GameObj> objs = layerToObj.get(layer);
			assertNotNull(objs);
			assertFalse(objs.isEmpty());
			for (GameObj obj : objs) {
				assertNotNull(obj);
				assertFalse("duplicate layer objs", layerObjStore.contains(obj));
				layerObjStore.add(obj);
			}
		}
		
		for (int priority : priorities) {
			assertTrue(priority >= 0);
			assertTrue(maxPriority >= priority);
			Set<GameObj> objs = priorityToObj.get(priority);
			assertNotNull(objs);
			assertFalse(objs.isEmpty());
			for (GameObj obj : objs) {
				assertNotNull(obj);
				assertFalse("duplicate priority objs", priorityObjStore.contains(obj));
				priorityObjStore.add(obj);
			}
		}
		
		assertEquals(objToPriority.keySet(), objToLayer.keySet());
		assertEquals(objToPriority.keySet(), priorityObjStore);
		assertEquals(objToLayer.keySet(), layerObjStore);
		Set<GameObj> allObjs = layerObjStore;
		
		// actions
		assertTrue(maxGroup >= 0);
		assertNotNull(groupToAction);
		
		Set<MainLoopAction> actionStore = new HashSet<>();
		Set<Integer> groups = groupToAction.keySet();
		
		for (int group : groups) {
			assertTrue(group >= 0);
			assertTrue(maxGroup >= group);
			Set<MainLoopAction> actions = groupToAction.get(group);
			assertNotNull(actions);
			assertFalse(actions.isEmpty());
			for (MainLoopAction action : actions) {
				assertNotNull(action);
				assertFalse("duplicate actions", actionStore.contains(action));
				actionStore.add(action);
				
				if (action instanceof MainLoopAddAction) {
					MainLoopAddAction add = (MainLoopAddAction) action;
					assertNotNull(add.getObj());
				} else if (action instanceof MainLoopRemoveAction) {
					MainLoopRemoveAction rem = (MainLoopRemoveAction) action;
					assertNotNull(rem.getObj());
				} else if (action instanceof MainLoopClearAction) {
					// nothing
				} else {
					fail("unknown action type recieved");
				}
			}
		}
	}
}
