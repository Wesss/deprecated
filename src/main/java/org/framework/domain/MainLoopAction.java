package org.framework.domain;

import org.framework.mainLoop.MainLoopModel;

/**
 * An action that can be inserted into and resolved by a mainLoop upon
 * triggering the next frame
 */
public abstract class MainLoopAction {
    public void acceptResolution(MainLoopModel loop) {
        loop.visitResolution(this);
    }
}
