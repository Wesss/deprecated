package org.framework;

public class MainLoopFactory {
	
	private static MainLoop singleton = null;
	
	public static MainLoop getMainLoop() {
		if (singleton == null) {
			throw new RuntimeException("MainLoop has not been constructed yet");
		}
		return singleton;
	}
	
	protected static void constructMainLoop(int updatesPerSecond) {
		if (updatesPerSecond <= 0)
			throw new IllegalArgumentException("updatesPerSecond must be positive");
		if (singleton != null)
			throw new RuntimeException("Atempted to initiallize a second MainLoop");
		singleton = new MainLoop(updatesPerSecond);
	}
}
