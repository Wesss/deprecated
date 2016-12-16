package org.wesss.game_framework.mainLoop;

public class MainLoopInterfaceFactory {

    private MainLoopModel model;
    private MainLoopAdvancedInterface singletonAdvInterface;

    protected MainLoopInterfaceFactory(MainLoopModel model) {
        this.model = model;
        singletonAdvInterface = new MainLoopAdvancedInterface(model);
    }

    protected MainLoopAdvancedInterface getAdvancedInterface() {
        return singletonAdvInterface;
    }

    protected MainLoopCustomGroupsInterface getCustomGroupsInterface(int maximumPriority) {
        // TODO
        return new MainLoopCustomGroupsInterface(new MainLoopGroupFactory(singletonAdvInterface, maximumPriority));
    }
}
