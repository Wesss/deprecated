package org.gameUtil;

public class CountdownEvent {

    // TODO document CountdownEvent

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
}
