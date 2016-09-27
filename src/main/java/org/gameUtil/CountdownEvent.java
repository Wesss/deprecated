package org.gameUtil;

import org.framework.canvas.GameCanvasGraphics;

public class CountdownEvent {

    // TODO document CountdownEvent

    public static CountdownEvent EMPTY_COUNTDOWN_EVENT = new CountdownEvent(-1, CountdownEvent::stub);

    private static void stub() {

    }

    private int remainingTicks;
    private Runnable callback;
    private boolean isDisabled;
    private boolean isSuspended;

    /**
     * If ticksToEvent is 0, the callback is run immediately, if negative it is never run
     *
     * @param ticksToEvent
     * @param callback
     */
    public CountdownEvent(int ticksToEvent, Runnable callback) {
        this.remainingTicks = ticksToEvent;
        this.callback = callback;
        isSuspended = false;
        isDisabled = false;

        if (ticksToEvent < 0) {
            isDisabled = true;
        } else if (ticksToEvent == 0) {
            isDisabled = true;
            callback.run();
        }
    }

    public void tick() {
        if (isDisabled || isSuspended) {
            return;
        }

        remainingTicks--;
        if (remainingTicks == 0) {
            isDisabled = true;
            callback.run();
        }
    }

    public void suspend() {
        isSuspended = true;
    }

    public void resume() {
        isSuspended = false;
    }

    ////////////////////
    // Testing
    ////////////////////

    public void printStats(GameCanvasGraphics g, int x, int y, int offset) {
        if (isDisabled) {
            g.drawString("[disabled]", x, y + (20 * offset));
        } else if (isSuspended) {
            g.drawString("[suspended] " + remainingTicks, x, y + (20 * offset));
        } else {
            g.drawString("[active] " + remainingTicks, x, y + (20 * offset));
        }
    }
}
