package org.gameUtil;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.GameObj;

/**
 * This represents a function that will be fired after a delay
 */
public class CountdownCallback implements GameObj{

    // TODO document CountdownCallback

    public static CountdownCallback EMPTY_COUNTDOWN_EVENT = new CountdownCallback(-1, CountdownCallback::stub);

    private static void stub() {

    }

    private int remainingTicks;
    private Runnable callback;
    private boolean isDisabled;
    private boolean isSuspended;

    /**
     * If ticksToEvent is 0, the callback is run as soon as the countdown is unsuspended
     * <p>
     * if negative, the callback is never run
     *
     * @param ticksToEvent
     * @param callback
     */
    public CountdownCallback(int ticksToEvent, Runnable callback) {
        this.remainingTicks = ticksToEvent;
        this.callback = callback;
        isSuspended = true;
        isDisabled = false;

        if (ticksToEvent < 0) {
            isDisabled = true;
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
        if (remainingTicks == 0) {
            isDisabled = true;
            callback.run();
        }
    }

    @Override
    public void update() {
        tick();
    }

    @Override
    public void paint(GameCanvasGraphics g) {

    }

    ////////////////////
    // Testing
    ////////////////////

    // TODO incorporate in some debug printing framework
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
