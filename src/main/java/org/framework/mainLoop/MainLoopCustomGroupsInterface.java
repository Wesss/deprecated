package org.framework.mainLoop;

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

    // TODO
    //create group
    public MainLoopGroup createGroup(int priority, int layer) {
        return null;
    }

    //remove group
    public void removeGroup(MainLoopGroup group) {
        //should clear group prior
    }

    //clear groups
    public void clearGroups() {

    }

    //get groups
    public Iterable<MainLoopGroup> getGroups() {
        return null;
    }
}
