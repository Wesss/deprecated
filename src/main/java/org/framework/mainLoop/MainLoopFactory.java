package org.framework.mainLoop;

public class MainLoopFactory {

    // TODO seperate into interface factory
    // TODO create mainLoop object to encompass model and controller

    private MainLoopController singleton;
    private MainLoopAdvancedInterface singletonAdvancedInterface;
    private MainLoopModel singletonModel;

    protected MainLoopFactory() {
        singleton = null;
        singletonAdvancedInterface = null;
        singletonModel = null;
    }

    // TODO rewire to return MainLoop object

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

    public MainLoopController getMainLoopController() {
        if (singleton == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return singleton;
    }

    public MainLoopModel getMainLoopModel() {
        if (singletonModel == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return singletonModel;
    }

    protected MainLoopAdvancedInterface getAdvancedInterface() {
        if (singletonAdvancedInterface == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return singletonAdvancedInterface;
    }

    protected MainLoopCustomGroupsInterface getCustomGroupsInterface(int maximumPriority) {
        if (singletonAdvancedInterface == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return new MainLoopCustomGroupsInterface(new MainLoopGroupFactory(singletonAdvancedInterface, maximumPriority));
    }

    protected static void exit() {
        // TODO implement exit
    }
}
