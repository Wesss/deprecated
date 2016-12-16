package org.wesss.game_framework.domain.event;

import org.wesss.game_framework.domain.GameEventListener;

public class MouseReleasedEvent extends RawGameEvent {

    private int x;
    private int y;
    private int buttonID;

    public MouseReleasedEvent(int x, int y, int buttonID) {
        this.x = x;
        this.y = y;
        this.buttonID = buttonID;
    }

    public void acceptEventListener(GameEventListener listener) {
        listener.processGameEvent(this);
    }

    /**
     * @return the x coordinate of the mouse press
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of the mouse press
     */
    public int getY() {
        return y;
    }

    /**
     * @return the ID of the button being released.
     * <p>
     * 1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons
     */
    public int getButtonID() {
        return buttonID;
    }
}
