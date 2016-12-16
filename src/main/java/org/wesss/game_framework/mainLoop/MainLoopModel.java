package org.wesss.game_framework.mainLoop;

import org.wesss.game_framework.canvas.GameCanvasGraphics;
import org.wesss.game_framework.domain.action.MainLoopAction;
import org.wesss.game_framework.domain.action.MainLoopAddAction;
import org.wesss.game_framework.domain.action.MainLoopClearAction;
import org.wesss.game_framework.domain.action.MainLoopRemoveAction;
import org.wesss.game_framework.domain.GameObj;
import org.wess.general_utils.collection.MapToSets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * This class holds the state of the MainLoopController
 */
public class MainLoopModel {


    /*
     * Representation of all the game objects currently being tracked by the MainLoopController
     *
     * layerToObj.values() is unordered-equivalents to keyToObjs.values()
     * maxLayer >= layerToObj.keyset()'s maximum when non-empty, 0 when empty
     * maxPriority >= priorityToObj.keyset()'s maximum when non-empty, 0 when empty
     */
    private MapToSets<Integer, GameObj> layerToObjs;
    private int maxLayer; // TODO abstract max index into mapToSets, actually reduce to minimum
    private MapToSets<Integer, GameObj> priorityToObjs;
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
        layerToObjs = new MapToSets<>();
        maxLayer = 0;
        priorityToObjs = new MapToSets<>();
        maxPriority = 0;

        groupToAction = new HashMap<>();
        maxGroup = 0;
    }


    //////////////////////////////////////////////////
    // Advanced User Interface (API)
    //////////////////////////////////////////////////

    protected boolean containsAdv(GameObj obj) {
        return obj != null && layerToObjs.containsValue(obj);
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
     *
     * @param g The graphics object to paint with
     */
    public void nextFrame(GameCanvasGraphics g) {
        updateObjs();
        resolveActions();
        paintObjs(g);
    }

    private void updateObjs() {
        for (int i = 0; i <= maxPriority; i++) {
            Set<GameObj> objs = priorityToObjs.get(i);
            if (objs != null) {
                for (GameObj obj : objs)
                    obj.update();
            }
        }
    }

    private void paintObjs(GameCanvasGraphics g) {
        for (int i = 0; i <= maxLayer; i++) {
            Set<GameObj> objs = layerToObjs.get(i);
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
                    action.acceptAction(this);
            }
        }
        groupToAction.clear();
        maxGroup = 0;
    }

    @SuppressWarnings("unused")
    public void visitAction(MainLoopAction action) {
        throw new RuntimeException("visited unknown action type");
    }

    public void visitAction(MainLoopAddAction action) {
        Integer prevLayer = layerToObjs.getKey(action.getObj()); // TODO remove taking out of previous k,v pair
        Integer prevPriority = priorityToObjs.getKey(action.getObj());

        if (prevLayer != null && prevPriority != null) {
            layerToObjs.remove(prevLayer, action.getObj());
            priorityToObjs.remove(prevPriority, action.getObj());
        }

        layerToObjs.put(action.getLayer(), action.getObj());
        priorityToObjs.put(action.getPriority(), action.getObj());

        maxLayer = max(maxLayer, action.getLayer());
        maxPriority = max(maxPriority, action.getPriority());
    }

    public void visitAction(MainLoopRemoveAction action) {
        Integer layer = layerToObjs.getKey(action.getObj());
        Integer priority = priorityToObjs.getKey(action.getObj());

        if (layer != null && priority != null) {
            layerToObjs.remove(layer, action.getObj());
            priorityToObjs.remove(priority, action.getObj());
        }
    }

    @SuppressWarnings("unused")
    public void visitAction(MainLoopClearAction action) {
        layerToObjs.clear();
        maxLayer = 0;
        priorityToObjs.clear();
        maxPriority = 0;
    }

    //////////////////////////////////////////////////
    // Testing
    //////////////////////////////////////////////////

    public void assertValid() {
        // game objs
        assertTrue(maxLayer >= 0);
        assertTrue(maxPriority >= 0);
        assertNotNull(layerToObjs);
        assertNotNull(priorityToObjs);

        // TODO abstract comparison of array lists into custom matchers
        Set<GameObj> layerObjs = new HashSet<>(layerToObjs.values());
        Set<GameObj> priorityObjs = new HashSet<>(priorityToObjs.values());
        assertThat(layerToObjs.values(), hasSize(layerObjs.size()));
        assertThat(priorityToObjs.values(), hasSize(priorityObjs.size()));
        assertThat(layerObjs, hasSize(priorityObjs.size()));


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
