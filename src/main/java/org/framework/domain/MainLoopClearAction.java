package org.framework.domain;

import org.framework.mainLoop.MainLoop;

public class MainLoopClearAction extends MainLoopAction {
    public MainLoopClearAction() {
        // nothing!
    }

    @Override
    public void acceptResolution(MainLoop loop) {
        loop.visitResolution(this);
    }
}
