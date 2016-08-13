package org.framework.domain;

import org.framework.mainLoop.MainLoop;

/**
 * An action that can be inserted into and resolved by a mainLoop upon
 * triggering the next frame
 */
public abstract class MainLoopAction {
    public void acceptResolution(MainLoop loop) {
        loop.visitResolution(this);
    }
}
