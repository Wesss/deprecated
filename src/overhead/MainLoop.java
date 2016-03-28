package overhead;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import overhead_interfaces.GameObj;

/**
 * This singleton class keeps track of all GameObjs in a game and repeatedly updates in fixed time intervals
 * 
 * TODO: make class non-static?
 * TODO: make update cycle dynamic
 * TODO: fix timing code
 * TODO: still untested
 * TODO: create non-parallel abstraction?
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class MainLoop {

	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 */
	
	private MainLoop singleton = null;
	private GamePanel panel;
	
    private Thread updateCycle;
	
	private HashMap<Integer, HashSet<GameObj>> layerToObj;
	private HashMap<GameObj, Integer> objToLayer;
	private int maxLayer;
	
	private HashMap<GameObj, Integer> delayedAdd;
	private HashSet<GameObj> delayedRemove;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 * @param FPS
	 */
	protected MainLoop(int FPS) {
		if (singleton != null)
			throw new RuntimeException("Atempted to create a second MainLoop");
		
		singleton = this;
		updateCycle = new Thread(new Animate(FPS));
		
		layerToObj = new HashMap<Integer, HashSet<GameObj>>();
		objToLayer = new HashMap<GameObj, Integer>();
		maxLayer = 0;
		delayedAdd = new HashMap<GameObj, Integer>();
		delayedRemove = new HashSet<GameObj>();
	}
	
	/**
	 * TODO
	 * @param p the GamePanel displaying the game
	 */
	protected void setReferences(GamePanel p) {
    	panel = p;
	}
	
	/**
	 * starts the update/repaint cycle
	 */
	protected void start() {
        updateCycle.start();
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
		
		delayedAdd.put(obj, layer);
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
		return (objToLayer.containsKey(obj) || delayedAdd.keySet().contains(obj))
				&& !delayedRemove.contains(obj);
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
		
		delayedRemove.add(obj);
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
		delayedAdd.remove(obj);
	}
	
	/**
	 * Clears all game objects from the Main Loop
	 */
	public void clear() {
		for(GameObj obj : objToLayer.keySet()) {
			if (!delayedRemove.contains(obj))
				remove(obj);
		}
	}
	
	//////////////////////////////////////////////////
	// Overhead Functionality
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
		
		for (GameObj obj : delayedAdd.keySet())
			addDelayed(obj, delayedAdd.get(obj));
		for (GameObj obj : delayedRemove)
			removeDelayed(obj);
		delayedAdd.clear();
		delayedRemove.clear();
		
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
