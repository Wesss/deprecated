package org.framework.domain.event;

import org.framework.domain.GameEventListener;

public class RawGameEvent extends GameEvent {
    public RawGameEvent() {
        //nothing!
    }

    public void acceptEventListener(GameEventListener listener) {
        listener.processGameEvent(this);
    }
}
