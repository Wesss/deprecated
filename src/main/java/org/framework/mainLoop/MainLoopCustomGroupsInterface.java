package org.framework.mainLoop;

import java.util.Collection;
import java.util.Set;

/**
 * TODO
 */
public class MainLoopCustomGroupsInterface {

    // TODO figure out how to disable auto generated class javadoc from intiilJ(ie. 'created by' stuff)

    private MainLoopGroupFactory factory;
    private MainLoopAdvancedInterface advInter;

    protected MainLoopCustomGroupsInterface(MainLoopGroupFactory factory,
                                            MainLoopAdvancedInterface advInter) {
        this.factory = factory;
        this.advInter = advInter;
    }

    public MainLoopGroup createGroup(int priority, int layer) {
        return null;
    }

    public boolean containsGroup(MainLoopGroup group) {
        return false;
    }

    public boolean removeGroup(MainLoopGroup group) {
        return false;
        //should clear group prior
    }

    //clear groups
    public void clearAllGroups() {

    }

    //get groups
    public Collection<MainLoopGroup> getGroups() {
        return null;
    }
}
