package org.framework.domain;

import org.framework.canvas.GameCanvasController;
import org.framework.mainLoop.MainLoopController;

/**
 * Classes implementing GameFactory are responsible for creating a single instance of the desired game
 */
public interface GameFactory {

    /**
     * TODO document GameFactory
     *
     * @param mainLoop
     * @param canvas
     * @return
     */
    public Game createGame(MainLoopController mainLoop, GameCanvasController canvas);

    /**
     * @return
     */
    public GameEventListener dispatchGameEventListener();
}
