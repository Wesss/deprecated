package org.framework.domain;


import org.framework.mainLoop.MainLoopModel;

public class MainLoopRemoveAction extends MainLoopAction {
    private GameObj obj;

    public MainLoopRemoveAction(GameObj obj) {
        this.obj = obj;
    }

    public GameObj getObj() {
        return obj;
    }

    @Override
    public void acceptResolution(MainLoopModel loop) {
        loop.visitResolution(this);
    }
}
