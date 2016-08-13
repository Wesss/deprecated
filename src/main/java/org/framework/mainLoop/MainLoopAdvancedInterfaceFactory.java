package org.framework.mainLoop;

public class MainLoopAdvancedInterfaceFactory {

    private MainLoopAdvancedInterfaceFactory() {}

    public static MainLoopAdvancedInterface getAdvancedInterface(MainLoop loop) {
        return new MainLoopAdvancedInterface(loop);
    }
}
