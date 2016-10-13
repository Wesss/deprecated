package org.framework.mainLoop;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 */
public class MainLoopCustomGroupsInterface {

    private MainLoopGroupFactory factory;

    private Set<MainLoopGroup> groups;

    protected MainLoopCustomGroupsInterface(MainLoopGroupFactory factory) {
        this.factory = factory;
        groups = new HashSet<>();
    }

    /**
     * TODO
     *
     * @param priority
     * @param layer
     * @return
     */
    public MainLoopGroup createGroup(int priority, int layer) {
        if (priority < 0 || layer < 0) {
            throw new IllegalArgumentException("priority and layer must be non-negative");
        }
        MainLoopGroup group = factory.createMainLoopGroup(priority, layer);
        groups.add(group);
        return group;
    }

    /**
     * TODO
     *
     * @param group
     * @return
     */
    public boolean containsGroup(MainLoopGroup group) {
        return groups.contains(group);
    }

    /**
     * Removes the group and all game objs it contains from the main loop
     *
     * @param group
     * @return true iff the mainLoop contained this group
     */
    public boolean removeGroup(MainLoopGroup group) {
        if (group == null) {
            return false;
        }
        group.markClear();
        factory.destroyMainLoopGroup(group);
        return groups.remove(group);
    }

    /**
     * Removes the all groups and all game objs under them from the main loop
     */
    public void removeAllGroups() {
        for (MainLoopGroup group : groups) {
            group.markClear();
            factory.destroyMainLoopGroup(group);
        }
        groups.clear();
    }

    /**
     * TODO
     *
     * @return
     */
    public Set<MainLoopGroup> getGroups() {
        return new HashSet<>(groups);
    }
}
