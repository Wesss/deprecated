package org.framework;

/**
 * Allocator for MainLoopGroupFactory
 */
public class MainLoopGroupFactoryFactory {

    public static MainLoopGroupFactory getMainLoopGroupFactory(MainLoop loop, int upperPriority) {
        return new MainLoopGroupFactory(
                new MainLoopAdvancedInterface(loop),
                upperPriority);
    }
}
