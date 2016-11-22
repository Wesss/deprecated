package org.framework.domain.action;


import org.framework.domain.GameObj;
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
    public void acceptAction(MainLoopModel loop) {
        loop.visitAction(this);
    }
}
