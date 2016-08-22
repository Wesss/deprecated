package org.framework.mainLoop;

public class MainLoopFactory {

    private MainLoop singleton = null;
    private MainLoopModel singletonModel = null;

    protected MainLoopFactory() {}

    public MainLoop getMainLoop() {
        if (singleton == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return singleton;
    }

    public MainLoopModel getMainLoopModel() {
        if (singletonModel == null) {
            throw new RuntimeException("MainLoopModel has not been constructed yet");
        }
        return singletonModel;
    }

    /**
     *
     * @param updatesPerSecond
     */
    public void constructMainLoop(int updatesPerSecond) {
        if (updatesPerSecond <= 0)
            throw new IllegalArgumentException("updatesPerSecond must be positive");
        if (singleton != null)
            throw new RuntimeException("Attempted to initialize a second MainLoop");
        singletonModel = new MainLoopModel(updatesPerSecond);
        singleton = new MainLoop(this);
    }

    public MainLoopAdvancedInterface getAdvancedInterface() {
        if (singletonModel == null) {
            throw new RuntimeException("MainLoopModel has not been constructed yet");
        }
        return new MainLoopAdvancedInterface(singletonModel);
    }
}
