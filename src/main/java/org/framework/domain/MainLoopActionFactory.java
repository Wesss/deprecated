package org.framework.domain;

import org.framework.interfaces.GameObj;

public class MainLoopActionFactory {

    // TODO refactor these into designated factory

    /**
     * @param obj
     * @param priority
     * @param layer
     * @return an MainLoopAction that when resolved by the mainLoop will add obj to the
     * mainloop with given update priority and paint layer
     * @throws IllegalArgumentException if obj == null, priority < 0, layer < 0
     */
    public static MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
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
    public static MainLoopAction createRemoveAction(GameObj obj) {
        if (obj == null)
            throw new IllegalArgumentException("Illegal Remove Action creation");
        return new MainLoopRemoveAction(obj);
    }

    /**
     * @return a MainLoopAction that when resolved by the mainLoop will clear all objs
     * from the mainLoop
     */
    public static MainLoopAction createClearAction() {
        return new MainLoopClearAction();
    }
}
