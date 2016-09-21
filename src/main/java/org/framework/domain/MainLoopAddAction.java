package org.framework.domain;

import org.framework.mainLoop.MainLoopModel;

public class MainLoopAddAction extends MainLoopAction {
    private GameObj obj;
    private int priority;
    private int layer;

    public MainLoopAddAction(GameObj obj, int priority, int layer) {
        this.obj = obj;
        this.priority = priority;
        this.layer = layer;
    }

    public GameObj getObj() {
        return obj;
    }

    public int getPriority() {
        return priority;
    }

    public int getLayer() {
        return layer;
    }

    @Override
    public void acceptResolution(MainLoopModel loop) {
        loop.visitResolution(this);
    }
}
