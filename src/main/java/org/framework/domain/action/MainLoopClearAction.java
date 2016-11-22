package org.framework.domain.action;

import org.framework.mainLoop.MainLoopModel;

public class MainLoopClearAction extends MainLoopAction {
    public MainLoopClearAction() {
        // nothing!
    }

    @Override
    public void acceptAction(MainLoopModel loop) {
        loop.visitAction(this);
    }
}
