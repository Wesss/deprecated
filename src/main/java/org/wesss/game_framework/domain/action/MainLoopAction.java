package org.wesss.game_framework.domain.action;

import org.wesss.game_framework.mainLoop.MainLoopModel;

/**
 * An action that can be inserted into and resolved by a mainLoop upon
 * triggering the next frame
 */
public abstract class MainLoopAction {
    public void acceptAction(MainLoopModel loop) {
        loop.visitAction(this);
    }
}
