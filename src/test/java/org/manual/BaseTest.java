package org.manual;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;

/**
 * Test for Panel/Window operations
 * (Move, Minimize, Close, Display)
 *
 * @author Wesley Cox
 */
public class BaseTest implements Game {

    private static final int FPS = 60;

    public static void main(String args[]) throws InstantiationException {
        GameFramework.startGame(new BaseTestFactory(), FPS);
    }

    private static class BaseTestFactory implements GameFactory {

        @Override
        public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
            return new BaseTest();
        }

        @Override
        public GameEventListener dispatchGameEventListener() {
            return GameEventListener.EMPTY_GAME_LISTENER;
        }
    }

    public BaseTest() {

    }

}
