package org.framework;

public class MainLoopFactoryFactory {

    private MainLoopFactoryFactory() {}

    protected static MainLoopFactory getMainLoopFactory() {
        return new MainLoopFactory();
    }
}
