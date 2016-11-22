package org.framework.mainLoop;

import org.framework.domain.action.MainLoopAction;
import org.framework.domain.action.MainLoopActionFactory;
import org.framework.domain.GameObj;

/**
 * This Interface is a much more powerful tool for manipulating the main loopModel.
 */
public class MainLoopAdvancedInterface {

    // TODO getActions, getObjs
    // TODO check name matching on mainloop callbacks
    // TODO better msgs on invalid action creation

    // the MainLoopController being modified by this interface
    private MainLoopModel loopModel;

    protected MainLoopAdvancedInterface(MainLoopModel loopModel) {
        this.loopModel = loopModel;
    }

    //////////////////////////////////////////////////
    // Public Interface
    //////////////////////////////////////////////////

    /**
     * TODO
     *
     * @param action
     * @param actionGroup
     * @throws IllegalArgumentException if actionGroup < 0, action == null
     */
    public void insertAction(MainLoopAction action, int actionGroup) {
        loopModel.insertAction(action, actionGroup);
    }

    /**
     * @param action
     * @return true iff the mainLoop currently has the action waiting to be executed in the next update period
     */
    public boolean containsAction(MainLoopAction action) {
        return loopModel.containsAction(action);
    }

    /**
     * @param action
     * @param actionGroup
     * @return true iff  the mainLoop currently has the action waiting to be executed in the next update period in the
     * given group.
     */
    public boolean containsAction(MainLoopAction action, int actionGroup) {
        return loopModel.containsAction(action, actionGroup);
    }

    /**
     * Removes given action from the pool of actions to be executed in the next update period
     *
     * @param action
     */
    public void removeAction(MainLoopAction action) {
        loopModel.deleteAction(action);
    }

    /**
     * Removes all actions from the pool of actions to be executed in the next update period
     */
    public void removeAllActions() {
        loopModel.deleteAllActions();
    }

    /**
     * @param obj
     * @return true iff the mainLoop contains given object
     */
    public boolean contains(GameObj obj) {
        return loopModel.containsAdv(obj);
    }

    //////////////////////////////////////////////////
    // Actions
    //////////////////////////////////////////////////

    /**
     * TODO
     *
     * @param obj
     * @param priority
     * @param layer
     * @return
     * @throws IllegalArgumentException if obj == null, priority < 0, layer < 0
     */
    public MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
        return MainLoopActionFactory.createAddAction(obj, priority, layer);
    }

    /**
     * TODO
     *
     * @param obj
     * @return
     * @throws IllegalArgumentException if obj == null
     */
    public MainLoopAction createRemoveAction(GameObj obj) {
        return MainLoopActionFactory.createRemoveAction(obj);
    }

    // TODO
    public MainLoopAction createClearAction() {
        return MainLoopActionFactory.createClearAction();
    }
}