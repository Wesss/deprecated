package org.framework;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.framework.MainLoopAdvancedInterface.MainLoopAction;
import org.framework.MainLoopAdvancedInterface.MainLoopAddAction;
import org.framework.MainLoopAdvancedInterface.MainLoopClearAction;
import org.framework.MainLoopAdvancedInterface.MainLoopRemoveAction;
import org.framework.interfaces.GameObj;

/**
 * This singleton class keeps track of all GameObjs in a game
 * and repeatedly updates them in fixed time intervals
 * 
 * @author Wesley Cox
 */
public class MainLoop {

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
	 * allObjs != null
	 * for each GameObj obj in allObjs
	 * 		obj also exists in layerToObj
	 * 		obj also exists in priorityToObj
	 * 		
	 * 
	 * layerToObj != null
	 * maxLayer >= layerToObj.keyset()'s maximum when non-empty, 0 when empty
	 * for each layer in layerToObj.keset()
	 * 		layer >= 0
	 * 		layerToObj.get(layer) != null
	 * 		!layerToObj.get(layer).isEmpty
	 * for each GameObj obj being stored within layerToObj
	 * 		obj != null
	 * 		obj also exists in priorityToObj
	 * 		obj also exists in allObjs
	 * 		obj does not exist anywhere else in layerToObj
	 * 
	 * priorityToObj != null
	 * maxPriority >= priorityToObj.keyset()'s maximum when non-empty, 0 when empty
	 * for each priority in priorityToObj.keset()
	 * 		priority >= 0
	 * 		priorityToObj.get(priority) != null
	 * 		!priorityToObj.get(priority).isEmpty
	 * for each GameObj obj being stored within priorityToObj
	 * 		obj != null
	 * 		obj also exists in layerToObj
	 * 		obj also exists in allObjs TODO
	 * 		obj does not exist anywhere else in priorityToObj
	 */
	private HashSet<GameObj> allObjs;
	private HashMap<Integer, HashSet<GameObj>> layerToObj;
	private int maxLayer;
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
	 * 			action.obj also exists in addActionObjs TODO
	 * 			action.obj is not already a part of the main loop
	 * 			action.obj is not present in another add action
	 * 		if action is a MainLoopRemoveAction
	 * 			action.obj != null
	 * 			action.obj also exists in remActionObjs TODO
	 * 			action.obj is a part of the main loop
	 * 			action.obj is not present in another remove action
	 * 
	 * addActionObjs != null TODO
	 * for each GameObj obj in addActionObjs
	 * 		obj also exists in an addAction in groupToAction TODO
	 * 
	 * remActionObjs != null TODO
	 * for each GameObj obj in remActionObjs
	 * 		obj also exists in an removeAction in groupToAction TODO
	 */
	private HashMap<Integer, HashSet<MainLoopAdvancedInterface.MainLoopAction>> groupToAction;
	private HashSet<GameObj> addActionObjs;
	private HashSet<GameObj> remActionObjs;

	private int maxGroup;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	protected MainLoop(int FPS) {
		updateCycle = new Thread(new Animate(FPS));
		
		allObjs = new HashSet<>();
		layerToObj = new HashMap<>();
		maxLayer = 0;
		priorityToObj = new HashMap<>();
		maxPriority = 0;
		
		groupToAction = new HashMap<>();
		maxGroup = 0;
		addActionObjs = new HashSet<>();
		remActionObjs = new HashSet<>();
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
	
	public void add(GameObj obj) {
		// TODO
	}
	
	public void addBackground(GameObj obj) {
		// TODO
	}
	
	public boolean contains(GameObj obj) {
		// TODO
		return false;
	}
	
	public void remove(GameObj obj) {
		// TODO
	}
	
	public void markClear() {
		// TODO
	}
	
	public void markClearForeground() {
		// TODO
	}
	
	public void markClearBackground() {
		// TODO
	}
	
	public void addPostClear(GameObj obj) {
		// TODO
	}
	
	public void addBackgroundPostClear(GameObj obj) {
		// TODO
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
		
		// TODO
		return false;
	}
	
	protected void insertAction(MainLoopAction action, int actionGroup) {
		if (actionGroup < 0) {
			throw new IllegalArgumentException("invalid actionGroup of " + actionGroup);
		}
		if (action == null) {
			throw new IllegalArgumentException("null action inserted into mainloop");
		}
		
		if (action instanceof MainLoopAddAction) {
			MainLoopAddAction add = (MainLoopAddAction) action;
			if (addActionObjs.contains(add.getObj()) || allObjs.contains(add)) {
				throw new IllegalArgumentException("duplicate obj inserted into MainLoop");
			}
			addActionObjs.add(add.getObj());
		} else if (action instanceof MainLoopRemoveAction) {
			remActionObjs.add(((MainLoopRemoveAction)action).getObj());
		}
		
		if (!groupToAction.containsKey(actionGroup)) {
			HashSet<MainLoopAction> set = new HashSet<>();
			set.add(action);
			groupToAction.put(actionGroup, set);
		} else {
			groupToAction.get(actionGroup).add(action);
		}
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
		
		if (action instanceof MainLoopAddAction) {
			addActionObjs.remove(((MainLoopAddAction)action).getObj());
		} else if (action instanceof MainLoopRemoveAction) {
			remActionObjs.remove(((MainLoopRemoveAction)action).getObj());
		}
	}
	
	protected void deleteAllActions() {
		// TODO
	}
	
	//////////////////////////////////////////////////
	// Framework Functionality
	//////////////////////////////////////////////////
	
	/**
	 * Add all GameObjs from markedAdd to the MainLoop
	 */
	private void resolveAdds() {
//		for (GameObj obj : markedAdd.keySet()) {
//			int layer = markedAdd.get(obj);
//			
//			objToLayer.put(obj, layer);
//			if (!layerToObj.containsKey(layer)) {
//				layerToObj.put(layer, new HashSet<GameObj>());
//				if (layer > maxLayer) {
//					maxLayer = layer;
//				}
//			}
//			layerToObj.get(layer).add(obj);
//		}
//		
//		markedAdd.clear();
	}
	
	/**
	 * Remove all GameObjs from markedRemove from the MainLoop
	 */
	private void resolveRemoves() {
//		for (GameObj obj : markedRemove) {
//			layerToObj.get(objToLayer.get(obj)).remove(obj);
//			objToLayer.remove(obj);
//			markedAdd.remove(obj);
//		}
//		
//		markedRemove.clear();
	}
	
	/**
	 * Moves the game state up one frame/update cycle
	 * @param g The graphics object to paint with
	 */
	protected void nextFrame(Graphics g) {
		for (int i = 0; i <= maxLayer; i++) {
			if (layerToObj.containsKey(i)) {
				HashSet<GameObj> objs = layerToObj.get(i);
				for (GameObj obj : objs) {
					obj.update();
				}
			}
		}
		
		resolveAdds();
		resolveRemoves();
		
		for (int i = 0; i <= maxLayer; i++) {
			if (layerToObj.containsKey(i)) {
				HashSet<GameObj> objs = layerToObj.get(i);
				for (GameObj obj : objs) {
					obj.draw(g);
				}
			}
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
		assertNotNull(allObjs);
		assertNotNull(layerToObj);
		assertNotNull(priorityToObj);
		
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
		
		assertEquals(allObjs, priorityObjStore);
		assertEquals(allObjs, layerObjStore);
		
		// actions
		assertTrue(maxGroup >= 0);
		assertNotNull(groupToAction);
		assertNotNull(addActionObjs);
		assertNotNull(remActionObjs);
		
		Set<MainLoopAction> actionStore = new HashSet<>();
		Set<GameObj> addObjStore = new HashSet<>();
		Set<GameObj> remObjStore = new HashSet<>();
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
					assertFalse(allObjs.contains(add.getObj()));
					assertFalse(addObjStore.contains(add.getObj()));
					addObjStore.add(add.getObj());
				} else if (action instanceof MainLoopRemoveAction) {
					MainLoopRemoveAction rem = (MainLoopRemoveAction) action;
					assertNotNull(rem.getObj());
					assertTrue(allObjs.contains(rem.getObj()));
					assertFalse(remObjStore.contains(rem.getObj()));
					remObjStore.add(rem.getObj());
				} else if (action instanceof MainLoopClearAction) {
					// nothing
				} else {
					fail("unknown action type recieved");
				}
			}
		}
		
		assertEquals(addObjStore, addActionObjs);
		assertEquals(remObjStore, remActionObjs);
	}
}
