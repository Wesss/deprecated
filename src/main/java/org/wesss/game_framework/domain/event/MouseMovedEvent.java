package org.wesss.game_framework.domain.event;

import org.wesss.game_framework.domain.GameEventListener;

public class MouseMovedEvent extends RawGameEvent {

    private int x;
    private int y;

    public MouseMovedEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void acceptEventListener(GameEventListener listener) {
        listener.processGameEvent(this);
    }

    /**
     * @return the x coordinate of the mouse
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of the mouse
     */
    public int getY() {
        return y;
    }
}
