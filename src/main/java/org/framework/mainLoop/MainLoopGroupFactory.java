package org.framework.mainLoop;

import org.framework.domain.MainLoopAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Allocator for MainLoopGroup
 */
public class MainLoopGroupFactory {

    private MainLoopAdvancedInterface inter;
    private Map<MainLoopGroup, MainLoopAction> addedGroupsActions;
    private int maxPriority;

    /**
     * The mainLoopGroup will be added to the mainLoop on the next frame, meaning any objs
     * immediately put into the group will be added two frames later
     *
     * @param inter
     * @param upperPriority
     */
    protected MainLoopGroupFactory(MainLoopAdvancedInterface inter, int upperPriority) {
        this.inter = inter;
        this.addedGroupsActions = new HashMap<>();
        this.maxPriority = upperPriority;
    }

    /**
     * TODO
     * @param priority
     * @param layer
     * @return
     * @throws IllegalArgumentException if priority > maxPriority
     */
    protected MainLoopGroup createMainLoopGroup(int priority, int layer) {
        if (priority > maxPriority)
            throw new IllegalArgumentException("group  priority must be less than or equal to the maximum priority");
        MainLoopGroup mainLoopGroup = new MainLoopGroup(inter, priority, layer);
        MainLoopAction action = inter.createAddAction(mainLoopGroup, maxPriority + 1, 0);
        addedGroupsActions.put(mainLoopGroup, action);
        inter.insertAction(action, MainLoopController.DEFAULT_ACTIONGROUP);
        return mainLoopGroup;
    }

    /**
     * does not remove any objs in the group
     * @param group
     */
    protected void destroyMainLoopGroup(MainLoopGroup group) {
        MainLoopAction action = addedGroupsActions.remove(group);
        if (inter.containsAction(action)) {
            inter.removeAction(action);
        } else {
            inter.insertAction(inter.createRemoveAction(group), MainLoopController.DEFAULT_ACTIONGROUP);
        }
    }
}
