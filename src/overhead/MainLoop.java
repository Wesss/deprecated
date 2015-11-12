package overhead;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import abstractions.GameObj;

/**
 * This class keeps track of all GameObjs that are to be repeatedly updated and/or drawn.
 * 
 * NOTE: Main loop is not actually stored here. The Main Loop resides in GamePanel.
 * Given naming makes adding objects to the MainLoop more intuitive.
 * TODO: still untested
 * 
 * @author Wesley
 *
 */
public class MainLoop {
	
	private static HashMap<Integer, HashSet<GameObj>> layer2objs = new HashMap<Integer, HashSet<GameObj>>();
	private static HashMap<GameObj, Integer> obj2layer = new HashMap<GameObj, Integer>();
	private static int maxLayer = 0;
	
	private static HashMap<GameObj, Integer> delayedAdd = new HashMap<GameObj, Integer>();
	private static HashSet<GameObj> delayedRemove = new HashSet<GameObj>();
	
	/**
	 * Adds an object to the set of objects to painted and updated with the MainLoop with default layer 0
	 * @param obj the object to be added to the loop
	 *		Update order and Paint order within a single layer are undefined.
	 * @requires obj is not already in the set of draw-able objects
	 */
	public static void add(GameObj obj) {
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
	public static void add(GameObj obj, int layer) {
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
	private static void addDelayed(GameObj obj, int layer) {
		obj2layer.put(obj, layer);
		
		if (!layer2objs.containsKey(layer)) {
			layer2objs.put(layer, new HashSet<GameObj>());
			if (layer > maxLayer) {
				maxLayer = layer;
			}
		}
		layer2objs.get(layer).add(obj);
	}
	
	/**
	 * @param obj the object to check for
	 * @return true when given object exists within the set of objects to painted and updated with the MainLoop
	 */
	public static boolean contains(GameObj obj) {
		return (obj2layer.containsKey(obj) || delayedAdd.keySet().contains(obj)) && !delayedRemove.contains(obj);
	}
	
	/**
	 * removes an object from the set of objects to be painted and updated with the MainLoop
	 * @param obj the Object to remove
	 */
	public static void remove(GameObj obj) {
		if (!contains(obj)) {
			throw new IllegalArgumentException("Tried to remove non-existant obj " + obj + " from the MainLoop");
		}
		
		delayedRemove.add(obj);
	}
	
	/**
	 * Workaround to avoid concurrent Set modification if an obj's update method
	 * 		adds another obj to the MainLoop
	 * @param obj the object to be removed the loop
	 * @requires obj is not already in the set of MainLoop objects
	 */
	private static void removeDelayed(GameObj obj) {
		layer2objs.get(obj2layer.get(obj)).remove(obj);
		obj2layer.remove(obj);
		delayedAdd.remove(obj);
	}
	
	/**
	 * Clears all game objects from the Main Loop
	 */
	public static void clear() {
		for(GameObj obj : obj2layer.keySet()) {
			if (!delayedRemove.contains(obj))
				remove(obj);
		}
	}
	
	/**
	 * draws out the current state of the game
	 * @param g The graphics object to paint with
	 */
	public static void nextFrame(Graphics g) {
		for (int i = 0; i <= maxLayer; i++) {
			if (layer2objs.containsKey(i)) {
				HashSet<GameObj> objs = layer2objs.get(i);
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
			if (layer2objs.containsKey(i)) {
				HashSet<GameObj> objs = layer2objs.get(i);
				for (GameObj obj : objs) {
					obj.draw(g);
				}
			}
		}
	}
}
