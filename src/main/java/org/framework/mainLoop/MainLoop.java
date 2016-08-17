package org.framework.mainLoop;

import static org.junit.Assert.*;
import static java.lang.Math.*;
import static org.framework.util.Exceptions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.framework.domain.MainLoopClearAction;
import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.MainLoopAction;
import org.framework.domain.MainLoopAddAction;
import org.framework.domain.MainLoopRemoveAction;
import org.framework.interfaces.GameObj;

/**
 * This singleton class keeps track of all GameObjs in a game
 * and repeatedly updates them in fixed time intervals
 * 
 * @author Wesley Cox
 */
public class MainLoop {

    // TODO abstract out index/obj pairings
    // TODO split class, too large?

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

    /*
     * Framework objects
     *
     * Post start:
     * canvas != null
     * updateCycle != null
     */
    private GameCanvas canvas;
    private Thread updateCycle;
    private MainLoopThread updateCycleMethod; //TODO find way to refactor and delete this

    /*
     * Interface state
     */
    private boolean basicOK;

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

    protected MainLoop(int fps) {
        updateCycleMethod = new MainLoopThread(this, fps);
        updateCycle = new Thread(updateCycleMethod);

        basicOK = true;

        objToLayer = new HashMap<>();
        layerToObj = new HashMap<>();
        maxLayer = 0;
        objToPriority = new HashMap<>();
        priorityToObj = new HashMap<>();
        maxPriority = 0;

        groupToAction = new HashMap<>();
        maxGroup = 0;

        // basic API setup
        MainLoopGroupFactory groupFactory =
                MainLoopGroupFactoryFactory.getMainLoopGroupFactory(this, GAMEOBJ_GROUP_PRIORITY);
        foregroundGroup = groupFactory.createMainLoopGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        backgroundGroup = groupFactory.createMainLoopGroup(DEFAULT_PRIORITY, BACKGROUND_LAYER);
    }

    /**
     * Sets up proper references needed to run the MainLoop
     * @param canvas the GameCanvas displaying the game
     */
    public void setReferences(GameCanvas canvas) {
        updateCycleMethod.setReferences(canvas);
        this.canvas = canvas;
    }

    /**
     * Starts the update/repaint cycle
     * @required setReferences be called before this
     */
    public void start() {
        if (canvas == null) {
            throw new RuntimeException(PRE_INIT_ERRMSG);
        }
        updateCycle.start();
    }

    //////////////////////////////////////////////////
    // Interface Swapping
    //////////////////////////////////////////////////

    /**
     * Switches
     *
     * @param upperBoundPriority
     * @return an interface for more detailed control over the mainLoop
     */
//    public MainLoopCustomGroupsInterface CustomGroups(int upperBoundPriority) {
//        return MainLoopCustomGroupsInterfaceFactory.getMainLoopCustomGroupsInterface(this, upperBoundPriority);
//    }

    /**
     * After this call, all BasicAPI calls (calls through this mainloop) and CustomGroupInterface calls will
     * disallowed
     * @return an interface for more detailed control over the mainLoop
     */
    public MainLoopAdvancedInterface advancedInterface() {
        basicOK = false;
        return MainLoopAdvancedInterfaceFactory.getAdvancedInterface(this);
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
     * @throws Exception
     */
    public void add(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
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
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.remove(obj);
        backgroundGroup.add(obj);
    }

    /**
     * @param obj
     * @return true iff obj is currently being kept track of by the mainLoop's basic API
     */
    public boolean contains(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        return foregroundGroup.contains(obj) || backgroundGroup.contains(obj);
    }

    /**
     * @param obj
     * @return true iff obj was present and successfully removed from the mainLoop's basic API
     */
    public boolean remove(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        return (foregroundGroup.remove(obj) || backgroundGroup.remove(obj));
    }

    /**
     * Marks the mainLoop to be cleared at the next frame
     */
    public void markClear() {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.markClear();
        backgroundGroup.markClear();
    }

    /**
     * Marks the mainLoop to clear all foreground objs at the next frame
     */
    public void markClearForeground() {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.markClear();
    }

    /**
     * Marks the mainLoop to clear all background objs at the next frame
     */
    public void markClearBackground() {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.markClear();
    }

    /**
     * Holds given obj to be added to the mainLoop after the clearing phase
     *
     * @param obj
     */
    public void addPostClear(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        foregroundGroup.addPostClear(obj);
    }

    /**
     * Holds given obj to be added to the mainLoop's background group after the clearing phase
     *
     * @param obj
     */
    public void addBackgroundPostClear(GameObj obj) {
        if (!basicOK)
            throw new RuntimeException(DISABLED_BASICAPI_ERRMSG);
        backgroundGroup.addPostClear(obj);
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
    // Domain
    //////////////////////////////////////////////////

    /**
     * @param obj
     * @param priority
     * @param layer
     * @return an MainLoopAction that when resolved by the mainLoop will add obj to the
     * mainloop with given update priority and paint layer
     * @throws IllegalArgumentException if obj == null, priority < 0, layer < 0
     */
    public MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
        if (obj == null || priority < 0 || layer < 0)
            throw new IllegalArgumentException("Illegal Add Action creation");
        return new MainLoopAddAction(obj, priority, layer);
    }

    /**
     * @param obj
     * @return a MainLoopAction that when resolved by the mainLoop will remove obj
     * from the mainloop
     * @throws IllegalArgumentException if obj == null
     */
    public MainLoopAction createRemoveAction(GameObj obj) {
        if (obj == null)
            throw new IllegalArgumentException("Illegal Remove Action creation");
        return new MainLoopRemoveAction(obj);
    }

    /**
     * @return a MainLoopAction that when resolved by the mainLoop will clear all objs
     * from the mainLoop
     */
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
        assertFalse(updateCycle.isAlive() && canvas == null);

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
