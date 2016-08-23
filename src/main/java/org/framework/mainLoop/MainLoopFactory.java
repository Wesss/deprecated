package org.framework.mainLoop;

public class MainLoopFactory {

    private MainLoop singleton;
    private MainLoopAdvancedInterface singletonAdvancedInterface;
    private MainLoopModel singletonModel;

    protected MainLoopFactory() {
        singleton = null;
        singletonAdvancedInterface = null;
        singletonModel = null;
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
        singletonAdvancedInterface = new MainLoopAdvancedInterface(singletonModel);
        singleton = new MainLoop(
                singletonAdvancedInterface,
                new MainLoopGroupFactory(getAdvancedInterface(), MainLoop.GAMEOBJ_GROUP_PRIORITY)
        );
    }

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

    public MainLoopAdvancedInterface getAdvancedInterface() {
        if (singletonAdvancedInterface == null) {
            throw new RuntimeException("MainLoopModel has not been constructed yet");
        }
        return singletonAdvancedInterface;
    }
}
