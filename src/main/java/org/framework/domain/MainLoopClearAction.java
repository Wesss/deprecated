package org.framework.domain;

import org.framework.mainLoop.MainLoopModel;

public class MainLoopClearAction extends MainLoopAction {
    public MainLoopClearAction() {
        // nothing!
    }

    @Override
    public void acceptResolution(MainLoopModel loop) {
        loop.visitResolution(this);
    }
}
