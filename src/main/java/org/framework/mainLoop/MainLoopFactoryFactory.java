package org.framework.mainLoop;

public class MainLoopFactoryFactory {

    private MainLoopFactoryFactory() {}

    public static MainLoopFactory getMainLoopFactory() {
        return new MainLoopFactory();
    }
}
