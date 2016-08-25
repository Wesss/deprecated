package org.framework.mainLoop;

/**
 * Allocator for MainLoopGroup
 */
public class MainLoopGroupFactory {

    private MainLoopAdvancedInterface inter;
    private int upperPriority;

    /**
     * The mainLoopGroup will be added to the mainLoop on the next frame, meaning any objs
     * immediately put into the group will be added two frames later
     *
     * @param inter
     * @param upperPriority
     */
    public MainLoopGroupFactory(MainLoopAdvancedInterface inter, int upperPriority) {
        this.inter = inter;
        this.upperPriority = upperPriority;
    }

    /**
     * TODO
     * @param priority
     * @param layer
     * @return
     * @throws IllegalArgumentException if priority >= upperPriority
     */
    public MainLoopGroup createMainLoopGroup(int priority, int layer) {
        if (priority >= upperPriority)
            throw new IllegalArgumentException("group  priority must be less than the upper bound priority");
        MainLoopGroup mainLoopGroup = new MainLoopGroup(inter, priority, layer);
        inter.insertAction(inter.createAddAction(mainLoopGroup, upperPriority, 0), MainLoop.DEFAULT_ACTIONGROUP);
        return mainLoopGroup;
    }

    public void destoryMainLoopGroup(MainLoopGroup group) {
        // TODO
    }
}
