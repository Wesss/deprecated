package org.framework.mainLoop;

public class MainLoopFactory {

    // TODO seperate into interface factory

    private MainLoopController singleton;
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
            throw new RuntimeException("Attempted to initialize a second MainLoopController");
        singletonModel = new MainLoopModel(updatesPerSecond);
        singletonAdvancedInterface = new MainLoopAdvancedInterface(singletonModel);
        singleton = new MainLoopController(
                this,
                singletonAdvancedInterface,
                new MainLoopGroupFactory(getAdvancedInterface(), MainLoopController.GAMEOBJ_GROUP_PRIORITY)
        );
    }

    public MainLoopController getMainLoop() {
        if (singleton == null) {
            throw new RuntimeException("MainLoopController has not been constructed yet");
        }
        return singleton;
    }

    public MainLoopModel getMainLoopModel() {
        if (singletonModel == null) {
            throw new RuntimeException("MainLoopModel has not been constructed yet");
        }
        return singletonModel;
    }

    protected MainLoopAdvancedInterface getAdvancedInterface() {
        if (singletonAdvancedInterface == null) {
            throw new RuntimeException("MainLoopModel has not been constructed yet");
        }
        return singletonAdvancedInterface;
    }

    protected MainLoopCustomGroupsInterface getCustomGroupsInterface(int maximumPriority) {
        if (singletonAdvancedInterface == null) {
            throw new RuntimeException("MainLoopModel has not been constructed yet");
        }
        return new MainLoopCustomGroupsInterface(new MainLoopGroupFactory(singletonAdvancedInterface, maximumPriority));
    }
}
