package org.framework.mainLoop;

import org.framework.domain.MainLoopAction;
import org.framework.domain.MainLoopActionFactory;
import org.framework.interfaces.GameObj;
/**
 * This Interface is a much more powerful tool for manipulating the main loopModel.
 */
public class MainLoopAdvancedInterface {

    // TODO getActions, getObjs
    // TODO check name matching on mainloop callbacks
    // TODO better msgs on invalid action creation
    // TODO make singleton

    // the MainLoop being modified by this interface
    private MainLoopModel loopModel;

    protected MainLoopAdvancedInterface(MainLoopModel loopModel) {
        this.loopModel = loopModel;
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
        loopModel.insertAction(action, actionGroup);
    }

    // TODO
    public boolean containsAction(MainLoopAction action) {
        return loopModel.containsAction(action);
    }

    // TODO
    public boolean containsAction(MainLoopAction action, int actionGroup) {
        return loopModel.containsAction(action, actionGroup);
    }

    // TODO
    public void deleteAction(MainLoopAction action) {
        loopModel.deleteAction(action);
    }

    // TODO
    public void deleteAllActions() {
        loopModel.deleteAllActions();
    }

    // TODO
    public boolean contains(GameObj obj) {
        return loopModel.containsAdv(obj);
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
        return MainLoopActionFactory.createAddAction(obj, priority, layer);
    }

    /**
     * TODO
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