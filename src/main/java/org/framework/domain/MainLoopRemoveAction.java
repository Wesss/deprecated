package org.framework.domain;


import org.framework.MainLoop;
import org.framework.interfaces.GameObj;

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
