package org.framework.mainLoop;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.canvas.GameCanvasModel;
import org.framework.domain.MainLoopAction;
import org.framework.domain.MainLoopAddAction;
import org.framework.domain.MainLoopClearAction;
import org.framework.domain.MainLoopRemoveAction;
import org.framework.interfaces.GameObj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;
import static org.junit.Assert.*;

/**
 * This class holds the state of the MainLoopController
 */
public class MainLoopModel {

    // TODO abstract out index/obj pairings

    /*
     * Representation of all the game objects currently being tracked by the MainLoopController
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
     * MainLoopController actions to be performed during the cycle-changing process
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

    protected MainLoopModel() {
        objToLayer = new HashMap<>();
        layerToObj = new HashMap<>();
        maxLayer = 0;
        objToPriority = new HashMap<>();
        priorityToObj = new HashMap<>();
        maxPriority = 0;

        groupToAction = new HashMap<>();
        maxGroup = 0;
    }


    //////////////////////////////////////////////////
    // Advanced User Interface (API)
    //////////////////////////////////////////////////

    protected boolean containsAdv(GameObj obj) {
        return obj != null && objToLayer.containsKey(obj);
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
        return action != null && (groupToAction.containsKey(actionGroup) && groupToAction.get(actionGroup).contains(action));

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
    public void nextFrame(GameCanvasGraphics g) {
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

    private void paintObjs(GameCanvasGraphics g) {
        for (int i = 0; i <= maxLayer; i++) {
            HashSet<GameObj> objs = layerToObj.get(i);
            if (objs != null) {
                for (GameObj obj : objs)
                    obj.paint(g);
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

    @SuppressWarnings("unused")
    public void visitResolution(MainLoopAction action) {
        throw new RuntimeException("visited unknown action type");
    }

    public void visitResolution(MainLoopAddAction action) {
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
                if (prevPriority == maxPriority && maxPriority > 0) {
                    maxPriority--;
                }
            }
        }

        objToLayer.put(action.getObj(), action.getLayer());
        objToPriority.put(action.getObj(), action.getPriority());
        if (!layerToObj.containsKey(action.getLayer()))
            layerToObj.put(action.getLayer(), new HashSet<GameObj>());
        layerToObj.get(action.getLayer()).add(action.getObj());
        if (!priorityToObj.containsKey(action.getPriority()))
            priorityToObj.put(action.getPriority(), new HashSet<GameObj>());
        priorityToObj.get(action.getPriority()).add(action.getObj());

        maxLayer = max(maxLayer, action.getLayer());
        maxPriority = max(maxPriority, action.getPriority());
    }

    public void visitResolution(MainLoopRemoveAction action) {
        Integer layer = objToLayer.get(action.getObj());
        Integer priority = objToPriority.get(action.getObj());
        if (layer != null && priority != null) {
            removeObjIndexPair(layerToObj, objToLayer, action.getObj(), layer);
            if (!layerToObj.containsKey(layer)) {
                layerToObj.remove(layer);
                if (layer == maxLayer && maxLayer > 0)
                    maxLayer--; // TODO duplicated above in Add resolution, to be resolved with refactoring out hashmap pairings
            }
            removeObjIndexPair(priorityToObj, objToPriority, action.getObj(), priority);
            if (!priorityToObj.containsKey(priority)) {
                priorityToObj.remove(priority);
                if (priority == maxPriority && maxPriority > 0) {
                    maxPriority--;
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void visitResolution(MainLoopClearAction action) {
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



    //////////////////////////////////////////////////
    // Testing
    //////////////////////////////////////////////////

    public void assertValid() {
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
        Set<Integer> priorities = priorityToObj.keySet();

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
                    //} else if (action instanceof MainLoopClearAction) {
                    // nothing
                } else {
                    fail("unknown action type recieved");
                }
            }
        }
    }
}
