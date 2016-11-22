package org.framework.domain.event;

import org.framework.domain.GameEventListener;

public class GameEvent {
    public GameEvent() {
        //nothing!
    }

    /**
     * visitor pattern to allow accepting different event classes
     * @param listener
     */
    public void acceptEventListener(GameEventListener listener) {
        listener.processGameEvent(this);
    }
}
