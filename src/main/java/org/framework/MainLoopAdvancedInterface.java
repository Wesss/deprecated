package org.framework;

import org.framework.MainLoop.MainLoopAction;
import org.framework.interfaces.GameObj;
/**
 * TODO
 *
 * @author Wesley
 */
public class MainLoopAdvancedInterface {

    // TODO getActions, getObjs
    // TODO check name matching on mainloop callbacks
    // TODO better msgs on invalid action creation
    // TODO make singleton

    // the MainLoop being modified by this interface
    private MainLoop loop;

    protected MainLoopAdvancedInterface(MainLoop loop) {
        this.loop = loop;
    }

    //////////////////////////////////////////////////
    // Public Interface
    //////////////////////////////////////////////////

    /**
     * TODO
     * @param action
     * @param actionGroup
     * @throws IllegalArgumentException if actionGroup < 0, action == null
     */
    public void insertAction(MainLoopAction action, int actionGroup) {
        loop.insertAction(action, actionGroup);
    }

    // TODO
    public boolean containsAction(MainLoopAction action) {
        return loop.containsAction(action);
    }

    // TODO
    public boolean containsAction(MainLoopAction action, int actionGroup) {
        return loop.containsAction(action, actionGroup);
    }

    // TODO
    public void deleteAction(MainLoopAction action) {
        loop.deleteAction(action);
    }

    // TODO
    public void deleteAllActions() {
        loop.deleteAllActions();
    }

    // TODO
    public boolean contains(GameObj obj) {
        return loop.containsAdv(obj);
    }

    //////////////////////////////////////////////////
    // Actions
    //////////////////////////////////////////////////

    /**
     * TODO
     * @param obj
     * @param priority
     * @param layer
     * @return
     * @throws IllegalArgumentException if obj == null, priority < 0, layer < 0
     */
    public MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
        return loop.createAddAction(obj, priority, layer);
    }

    /**
     * TODO
     * @param obj
     * @return
     * @throws IllegalArgumentException if obj == null
     */
    public MainLoopAction createRemoveAction(GameObj obj) {
        return loop.createRemoveAction(obj);
    }

    // TODO
    public MainLoopAction createClearAction() {
        return loop.createClearAction();
    }
}