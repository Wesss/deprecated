package org.wesss.game_framework.mainLoop;

import org.wesss.game_framework.canvas.GameCanvasGraphics;
import org.wesss.game_framework.domain.GameObj;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a grouping of GameObjs currently stored within the MainLoopController
 *
 * @author Wesley
 */
public class MainLoopGroup implements GameObj {

    // TODO prevent adding of obj to different groups/mainloop

    private final int groupLayer;
    private final int groupPriority;
    private final MainLoopAdvancedInterface inter;

    /*
     * objs != null
     * newObjs != null
     * newPostClearObjs != null
     * remObjs != null
     *
     * for each GameObj obj in addObjs
     * 		obj is contained by the mainLoop
     * for each GameObj obj is remObjs
     * 		obj is contained by the mainLoop
     * for each GameObj obj in newObjs
     * 		obj is not contained by the mainLoop
     * for each GameObj obj in newPostClearObjs
     * 		obj is not contained by the mainLoop
     *
     * For any GameObj obj
     * 		obj is contained in at most one of objs, newObjs, and newPostClearObjs, and remObjs
     * TODO invariant check function?
     */
    private boolean markClear;
    private Set<GameObj> objs;
    private Set<GameObj> newObjs;
    private Set<GameObj> newPostClearObjs;
    private Set<GameObj> remObjs;

    protected MainLoopGroup(MainLoopAdvancedInterface inter,
                            int layer,
                            int priority) {
        this.inter = inter;
        this.groupLayer = layer;
        this.groupPriority = priority;

        markClear = false;
        objs = new HashSet<>();
        newObjs = new HashSet<>();
        newPostClearObjs = new HashSet<>();
        remObjs = new HashSet<>();
    }

    //////////////////////////////////////////////////
    // public API
    //////////////////////////////////////////////////

    /**
     * Adds given object to the group. Nothing if obj is already a part of this group
     *
     * @param obj
     */
    public void add(GameObj obj) {
        if (remObjs.contains(obj)) {
            remObjs.remove(obj);
            objs.add(obj);
        } else if (!contains(obj)) {
            newObjs.add(obj);
        }
    }

    /**
     * Adds given object to the group after the group is potentially cleared at the end of a frame
     * Nothing if obj is already a part of this group
     *
     * @param obj
     */
    public void addPostClear(GameObj obj) {
        remove(obj);
        newPostClearObjs.add(obj);
    }

    /**
     * Removed given obj from the group if it is part of the group. Nothing otherwise
     *
     * @param obj
     */
    public boolean remove(GameObj obj) {
        if (objs.remove(obj)) {
            remObjs.add(obj);
            return true;
        } else if (!newObjs.remove(obj)) {
            return newPostClearObjs.remove(obj);
        }
        return false;
    }

    /**
     * @param obj
     * @return true iff obj is either contained by this group or will be after
     * potentially clearing the group
     */
    public boolean contains(GameObj obj) {
        return objs.contains(obj) || newObjs.contains(obj) || newPostClearObjs.contains(obj);
    }

    /**
     * Clears all objs in this group from the mainLoop and from this group on the next frame
     */
    public void markClear() {
        markClear = true;
    }

    //////////////////////////////////////////////////
    // Framework Functionality
    //////////////////////////////////////////////////

    /**
     * FRAMEWORK FUNCTIONALITY ONLY
     * DO NOT CALL THIS METHOD
     */
    @Override
    public void update() {
        if (markClear) {
            markClear = false;
            for (GameObj obj : objs) {
                inter.insertAction(inter.createRemoveAction(obj),
                        MainLoopController.CLEAR_ACTIONGROUP);
            }
            objs.clear();
            newObjs.clear();
            remObjs.clear();
        } else {
            for (GameObj remObj : remObjs) {
                inter.insertAction(inter.createRemoveAction(remObj),
                        MainLoopController.DEFAULT_ACTIONGROUP);
            }
            remObjs.clear();
            for (GameObj newObj : newObjs) {
                objs.add(newObj);
                inter.insertAction(inter.createAddAction(newObj, groupPriority, groupLayer),
                        MainLoopController.DEFAULT_ACTIONGROUP);
            }
            newObjs.clear();
        }
        for (GameObj newPostClearObj : newPostClearObjs) {
            objs.add(newPostClearObj);
            inter.insertAction(inter.createAddAction(newPostClearObj, groupPriority, groupLayer),
                    MainLoopController.POSTCLEAR_ACTIONGROUP);
        }
        newPostClearObjs.clear();
    }

    /**
     * FRAMEWORK FUNCTIONALITY ONLY
     * DO NOT CALL THIS METHOD
     */
    @Override
    public void paint(GameCanvasGraphics g) {
        // nothing
    }

    ////////////////////
    // Testing
    ////////////////////

    /**
     * PUBLIC FOR TESTING
     */
    public Set<GameObj> getGameObjsInMainLoop() {
        HashSet<GameObj> mainLoopObjs = new HashSet<>();
        mainLoopObjs.addAll(remObjs);
        mainLoopObjs.addAll(objs);
        return mainLoopObjs;
    }
}
