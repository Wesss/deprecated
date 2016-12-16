package org.wesss.game_framework.domain.action;


import org.wesss.game_framework.domain.GameObj;
import org.wesss.game_framework.mainLoop.MainLoopModel;

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
