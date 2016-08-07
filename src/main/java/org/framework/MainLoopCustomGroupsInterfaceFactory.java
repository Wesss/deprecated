package org.framework;

/**
 * Allocator for MainLoopCustomGroupsInterface
 */
public class MainLoopCustomGroupsInterfaceFactory {

    private MainLoopCustomGroupsInterfaceFactory() {}

    protected static MainLoopCustomGroupsInterface getMainLoopCustomGroupsInterface(MainLoop loop, int upperPriority) {
        return new MainLoopCustomGroupsInterface(
                MainLoopGroupFactoryFactory.getMainLoopGroupFactory(loop, upperPriority),
                MainLoopAdvancedInterfaceFactory.getAdvancedInterface(loop));
    }

}
