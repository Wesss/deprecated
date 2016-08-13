package org.framework.mainLoop;

public class MainLoopFactory {

    private MainLoop singleton = null;

    protected MainLoopFactory() {}

    public MainLoop getMainLoop() {
        if (singleton == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return singleton;
    }

    public void constructMainLoop(int updatesPerSecond) {
        if (updatesPerSecond <= 0)
            throw new IllegalArgumentException("updatesPerSecond must be positive");
        if (singleton != null)
            throw new RuntimeException("Atempted to initiallize a second MainLoop");
        singleton = new MainLoop(updatesPerSecond);
    }
}
