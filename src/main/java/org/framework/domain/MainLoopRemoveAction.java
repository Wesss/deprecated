package org.framework.domain;


import org.framework.interfaces.GameObj;
import org.framework.mainLoop.MainLoop;

public class MainLoopRemoveAction extends MainLoopAction {
    private GameObj obj;

    public MainLoopRemoveAction(GameObj obj) {
        this.obj = obj;
    }

    public GameObj getObj() {
        return obj;
    }

    @Override
    public void acceptResolution(MainLoop loop) {
        loop.visitResolution(this);
    }
}
