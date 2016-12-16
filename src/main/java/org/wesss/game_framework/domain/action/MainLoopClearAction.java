package org.wesss.game_framework.domain.action;

import org.wesss.game_framework.mainLoop.MainLoopModel;

public class MainLoopClearAction extends MainLoopAction {
    public MainLoopClearAction() {
        // nothing!
    }

    @Override
    public void acceptAction(MainLoopModel loop) {
        loop.visitAction(this);
    }
}
