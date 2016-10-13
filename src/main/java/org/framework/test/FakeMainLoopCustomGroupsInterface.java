package org.framework.test;

import org.framework.mainLoop.MainLoopAdvancedInterface;
import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.framework.mainLoop.MainLoopGroup;
import org.framework.mainLoop.MainLoopGroupFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;

/**
 * TODO
 */
public class FakeMainLoopCustomGroupsInterface extends MainLoopCustomGroupsInterface {

    private Map<Integer, Set<MainLoopGroup>> priorityToGroup; //TODO abstract this out in general-utils
    private Map<Integer, Set<MainLoopGroup>> layerToGroup;

    public static FakeMainLoopCustomGroupsInterface getFake(int maxPriority) {
        return new FakeMainLoopCustomGroupsInterface(maxPriority);
    }

    protected FakeMainLoopCustomGroupsInterface(int maxPriority) {
        super(new MainLoopGroupFactory(mock(MainLoopAdvancedInterface.class), maxPriority));
        priorityToGroup = new HashMap<>();
        layerToGroup = new HashMap<>();
    }

    @Override
    public MainLoopGroup createGroup(int priority, int layer) {
        MainLoopGroup newGroup = super.createGroup(priority, layer);
        if (!priorityToGroup.containsKey(priority))
            priorityToGroup.put(priority, new HashSet<MainLoopGroup>());
        priorityToGroup.get(priority).add(newGroup);
        if (!layerToGroup.containsKey(layer))
            layerToGroup.put(layer, new HashSet<MainLoopGroup>());
        layerToGroup.get(layer).add(newGroup);
        return newGroup;
    }

    @Override
    public boolean containsGroup(MainLoopGroup group) {
        return super.containsGroup(group);
    }

    @Override
    public boolean removeGroup(MainLoopGroup group) {
        // TODO blocked on refactoring out (A -> B) and (B -> Set<A>) pairings
        return super.removeGroup(group);
    }

    @Override
    public void removeAllGroups() {
        super.removeAllGroups();
    }

    @Override
    public Set<MainLoopGroup> getGroups() {
        return super.getGroups();
    }

    public void nextFrame() {
        // TODO
    }
}
