package org.framework;

import static org.framework.MainLoop.DEFAULT_ACTIONGROUP;

/**
 * TODO
 *
 * @author Wesley
 */
public class MainLoopGroupFactory {

    // TODO abstract MainLoopFactoryFactory
    // TODO rename maxPriority to upperPriority in other classes
    // TODO disable other creation (everywhere else too)

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
        inter.insertAction(inter.createAddAction(mainLoopGroup, upperPriority, 0), DEFAULT_ACTIONGROUP);
        return mainLoopGroup;
    }

    public void destoryMainLoopGroup(MainLoopGroup group) {
        // TODO
    }
}
