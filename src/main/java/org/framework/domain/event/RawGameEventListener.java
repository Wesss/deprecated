package org.framework.domain.event;

import org.framework.domain.GameEventListener;

/**
 * TODO raw gameevent listener class desc
 */
public class RawGameEventListener implements GameEventListener<RawGameEvent> {

    @Override
    public void processGameEvent(RawGameEvent event) {
        if (event instanceof KeyPressedEvent) {
            processGameEvent((KeyPressedEvent) event);
        } else if (event instanceof KeyReleasedEvent) {
            processGameEvent((KeyReleasedEvent) event);
        } else if (event instanceof MousePressedEvent) {
            processGameEvent((MousePressedEvent) event);
        } else if (event instanceof MouseReleasedEvent) {
            processGameEvent((MouseReleasedEvent) event);
        } else if (event instanceof  MouseMovedEvent) {
            processGameEvent((MouseMovedEvent) event);
        }
    }

    public void processGameEvent(KeyPressedEvent event) {

    }

    public void processGameEvent(KeyReleasedEvent event) {

    }

    public void processGameEvent(MousePressedEvent event) {

    }

    public void processGameEvent(MouseReleasedEvent event) {

    }

    public void processGameEvent(MouseMovedEvent event) {

    }
}
