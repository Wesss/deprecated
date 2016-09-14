package org.framework.mainLoop;

public class MainLoopFactoryFactory {

    private MainLoopFactoryFactory() {}

    public static MainLoopFactory getMainLoopFactory() {
        return new MainLoopFactory();
    }

    public static void destroyMainLoopFactory(MainLoopFactory factory) {
        // TODO implement destroyMainLoopFactory
    }
}
