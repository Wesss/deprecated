package org.framework;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import org.framework.MainLoopAdvancedInterface.MainLoopAction;
import org.framework.interfaces.GameObj;

/**
 * This singleton class keeps track of all GameObjs in a game and repeatedly updates in fixed time intervals
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
	 * panel != null
	 * updateCycle != null
	 */
	private GamePanel panel;
    private Thread updateCycle;
	
    /*  
     * Representation of all the game objects currently being tracked by the MainLoop
     * 
     * layerToObj != null
     * maxLayer >= layerToObj.keyset()'s maximum when non-empty, -1 when empty
     * for each layer in layerToObj.keset()
	 * 		layerToObj.get(layer) != null
	 * 		!layerToObj.get(layer).isEmpty
     * 		layerToObj.get(layer) also exists in priorityToObj
     * 		layerToObj.get(layer) does not exist anywhere else in layerToObj
     * 
     * priorityToObj != null
     * maxPriority >= priorityToObj.keyset()'s maximum when non-empty, -1 when empty
     * for each layer in layerToObj.keset()
	 * 		priorityToObj.get(priority) != null
	 * 		!priorityToObj.get(priority).isEmpty
	 * 		priorityToObj.get(layer) also exists in layerToObj
     * 		priorityToObj.get(layer) does not exist anywhere else in priorityToObj
     */
	private HashMap<Integer, HashSet<GameObj>> layerToObj;
	private int maxLayer;
	private HashMap<Integer, HashSet<GameObj>> priorityToObj;
	private int maxPriority;
	
	/* 
	 * MainLoop actions to be performed during the cycle-changing process
	 * 
	 * groupToAction != null
	 * maxGroup == groupToAction.keyset()'s maximum when non-empty, -1 when empty
	 * for each group in groupToAction.keset()
	 * 		groupToAction.get(group) != null
	 * 		!groupToAction.get(group).isEmpty
	 * for each MainLoopAction action being stored within groupToAction
	 * 		if action is a MainLoopAddAction
	 * 			TODO
	 * 		if action is a MainLoopRemoveAction
	 * 			TODO
	 */
	private HashMap<Integer, HashSet<MainLoopAdvancedInterface.MainLoopAction>> groupToAction;
	private int maxGroup;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	protected MainLoop(int FPS) {
		updateCycle = new Thread(new Animate(FPS));
		
		layerToObj = new HashMap<>();
		maxLayer = -1;
		priorityToObj = new HashMap<>();
		maxPriority = -1;
		
		groupToAction = new HashMap<>();
		maxGroup = -1;
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
	
	public void addPostClearBackground(GameObj obj) {
		// TODO
	}
	
	public MainLoopAdvancedInterface advancedInterface() {
		// TODO
		return null;
	}
	
	//////////////////////////////////////////////////
	// Advanced User Interface (API)
	//////////////////////////////////////////////////
	
	protected void addAction(MainLoopAction action, int actionGroup) {
		// TODO
	}

	protected boolean containsAction(MainLoopAction action) {
		// TODO
		return false;
	}
	
	protected boolean containsAction(MainLoopAction action, int actionGroup) {
		// TODO
		return false;
	}
	
	protected void removeAction(MainLoopAction action) {
		// TODO
	}
	
	protected void clearActions() {
		// TODO
	}
	
	protected void clearActionGroup(int actionGroup) {
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
    	// TODO
    }
}
