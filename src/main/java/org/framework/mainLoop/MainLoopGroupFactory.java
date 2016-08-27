package org.framework.mainLoop;

import org.framework.domain.MainLoopAction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Allocator for MainLoopGroup
 */
public class MainLoopGroupFactory {

    private MainLoopAdvancedInterface inter;
    private Map<MainLoopGroup, MainLoopAction> addedGroupsActions;
    private int upperPriority;

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
        this.upperPriority = upperPriority;
    }

    /**
     * TODO
     * @param priority
     * @param layer
     * @return
     * @throws IllegalArgumentException if priority >= upperPriority
     */
    protected MainLoopGroup createMainLoopGroup(int priority, int layer) {
        if (priority >= upperPriority)
            throw new IllegalArgumentException("group  priority must be less than the upper bound priority");
        MainLoopGroup mainLoopGroup = new MainLoopGroup(inter, priority, layer);
        MainLoopAction action = inter.createAddAction(mainLoopGroup, upperPriority, 0);
        addedGroupsActions.put(mainLoopGroup, action);
        inter.insertAction(action, MainLoop.DEFAULT_ACTIONGROUP);
        return mainLoopGroup;
    }

    protected void destoryMainLoopGroup(MainLoopGroup group) {
        MainLoopAction action = addedGroupsActions.remove(group);
        if (inter.containsAction(action)) {
            inter.deleteAction(action);
        } else {
            inter.insertAction(inter.createRemoveAction(group), MainLoop.DEFAULT_ACTIONGROUP);
        }
    }
}
