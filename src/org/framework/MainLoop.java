package org.framework;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import org.framework.interfaces.GameObj;

/**
 * This singleton class keeps track of all GameObjs in a game and repeatedly updates in fixed time intervals
 * 
 * TODO: fix timing code
 * TODO: still untested
 * TODO: create non-parallel abstraction?
 * 
 * @author Wesley Cox
 * last_edited 6/12/16
 */
public class MainLoop {

	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	// singleton object
	private static MainLoop singleton = null;
	
	/* 
	 * Framework objects
	 *  
	 * panel != null
	 * updateCycle != null
	 */
	private GamePanel panel;
    private Thread updateCycle;
	
    /*  
     * Representation of all the game objects currently being updated by the MainLoop
     * 
     * layerToObj != null
     * objToLayer != null
     * maxLayer >= 0;
     * For each GameObj obj and its associated Layer i in the MainLoop
     * 		layerToObj.get(i).contains(obj) == true
     * 		objsToLayer.get(obj) == i
     * 		No other entries exist
     * maxLayer == max(all layers in layerToObj.keyset())
     * 		== 0 if no gameobjs present
     */
	private HashMap<Integer, HashSet<GameObj>> layerToObj;
	private HashMap<GameObj, Integer> objToLayer;
	private int maxLayer;
	
	/* 
	 * GameObjs to be added or removed at the end of each cycle
	 * 
	 * markedAdd != null
	 * markedRemove != null
	 * For each GameObj obj in markedAdd.keyset()
	 * 		obj is not present in MainLoop
	 * For each GameObj obj in markedRemove
	 * 		obj is present in MainLoop
	 */
	private HashMap<GameObj, Integer> markedAdd;
	private HashSet<GameObj> markedRemove;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	private MainLoop(int FPS) {
		singleton = this;
		updateCycle = new Thread(new Animate(FPS));
		
		layerToObj = new HashMap<Integer, HashSet<GameObj>>();
		objToLayer = new HashMap<GameObj, Integer>();
		maxLayer = 0;
		markedAdd = new HashMap<GameObj, Integer>();
		markedRemove = new HashSet<GameObj>();
	}
	
	/**
	 * Initializes the MainLoop
	 * @param FPS the desired frames(updates) per second 
	 */
	protected static MainLoop init(int FPS) {
		if (singleton != null)
			throw new RuntimeException("Atempted to initiallize a second MainLoop");
		return new MainLoop(FPS);
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
        updateCycle.start();
	}
	
	/**
	 * @return the MainLoop currently being run
	 */
	public static MainLoop getMainLoop() {
		if (singleton == null)
			throw new RuntimeException("MainLoop not initialized");
		return singleton;
	}
	
	//////////////////////////////////////////////////
	// User Interaction
	//////////////////////////////////////////////////
	
	/**
	 * Adds an object to the set of objects to painted and updated with the MainLoop
	 * with default layer 0
	 * @param obj the object to be added to the loop
	 * @requires obj is not already in the set of draw-able objects
	 */
	public void add(GameObj obj) {
		add(obj, 0);
	}
	
	/**
	 * Adds an object to the set of objects to painted and updated with the MainLoop
	 * @param obj the object to be added to the loop
	 * @param layer the layer in which the object is to be painted
	 * 		Layer 0 represents the back-most layer of drawing.
	 *		Update order and Paint order within a single layer are undefined.
	 * @requires obj is not already in the set of draw-able objects
	 */
	public void add(GameObj obj, int layer) {
		if (contains(obj)) {
			throw new RuntimeException("Tried to add obj " + obj
					+ " to painter whilst already a part of the drawable set");
		}
		
		markedAdd.put(obj, layer);
	}
	
	/**
	 * Workaround to avoid concurrent Set modification if an obj's update method
	 * 		adds another obj to the MainLoop
	 * @param obj the object to be added to the loop
	 * @param layer the layer in which the object is to be painted
	 * 		Layer 0 represents the back-most layer of drawing.
	 *		Update order and Paint order within a single layer are undefined.
	 * @requires obj is not already in the set of MainLoop objects
	 */
	private void addDelayed(GameObj obj, int layer) {
		objToLayer.put(obj, layer);
		
		if (!layerToObj.containsKey(layer)) {
			layerToObj.put(layer, new HashSet<GameObj>());
			if (layer > maxLayer) {
				maxLayer = layer;
			}
		}
		layerToObj.get(layer).add(obj);
	}
	
	/**
	 * @param obj the object to check for
	 * @return true when given object exists within the set of objects to painted
	 * 		and updated with the MainLoop
	 */
	public boolean contains(GameObj obj) {
		return (objToLayer.containsKey(obj) || markedAdd.keySet().contains(obj))
				&& !markedRemove.contains(obj);
	}
	
	/**
	 * removes an object from the set of objects to be painted and updated with the MainLoop
	 * @param obj the Object to remove
	 */
	public void remove(GameObj obj) {
		if (!contains(obj)) {
			throw new IllegalArgumentException("Tried to remove non-existant obj "
					+ obj + " from the MainLoop");
		}
		
		markedRemove.add(obj);
	}
	
	/**
	 * Workaround to avoid concurrent Set modification if an obj's update method
	 * 		adds another obj to the MainLoop
	 * @param obj the object to be removed the loop
	 * @requires obj is not already in the set of MainLoop objects
	 */
	private void removeDelayed(GameObj obj) {
		layerToObj.get(objToLayer.get(obj)).remove(obj);
		objToLayer.remove(obj);
		markedAdd.remove(obj);
	}
	
	/**
	 * Clears all game objects from the Main Loop
	 */
	public void clear() {
		for(GameObj obj : objToLayer.keySet()) {
			if (!markedRemove.contains(obj))
				remove(obj);
		}
	}
	
	//////////////////////////////////////////////////
	// Framework Functionality
	//////////////////////////////////////////////////
	
	/**
	 * draws out the current state of the game
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
		
		for (GameObj obj : markedAdd.keySet())
			addDelayed(obj, markedAdd.get(obj));
		for (GameObj obj : markedRemove)
			removeDelayed(obj);
		markedAdd.clear();
		markedRemove.clear();
		
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
}
