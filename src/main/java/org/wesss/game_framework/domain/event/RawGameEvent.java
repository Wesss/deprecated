package org.wesss.game_framework.domain.event;

import org.wesss.game_framework.domain.GameEventListener;

public class RawGameEvent extends GameEvent {
    public RawGameEvent() {
        //nothing!
    }

    public void acceptEventListener(GameEventListener listener) {
        listener.processGameEvent(this);
    }
}
