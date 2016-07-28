package org.framework;

public class MainLoopAdvancedInterfaceFactory {
    public static MainLoopAdvancedInterface getAdvancedInterface(MainLoop loop) {
        return new MainLoopAdvancedInterface(loop);
    }
}
