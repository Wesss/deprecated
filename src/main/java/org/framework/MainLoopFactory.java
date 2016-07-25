package org.framework;

public class MainLoopFactory {

    // TODO create FactoryFactory to be able to test initialization
    // TODO test this

    private MainLoop singleton = null;

    public MainLoop getMainLoop() {
        if (singleton == null) {
            throw new RuntimeException("MainLoop has not been constructed yet");
        }
        return singleton;
    }

    protected void constructMainLoop(int updatesPerSecond) {
        if (updatesPerSecond <= 0)
            throw new IllegalArgumentException("updatesPerSecond must be positive");
        if (singleton != null)
            throw new RuntimeException("Atempted to initiallize a second MainLoop");
        singleton = new MainLoop(updatesPerSecond);
    }
}
