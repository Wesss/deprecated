package org.integration;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.domain.GameObj;
import org.framework.mainLoop.MainLoopController;

import java.awt.*;

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
    }

    public BaseTest() {

    }

    @Override
    public GameEventListener dispatchGameEventListener() {
        return GameEventListener.EMPTY_GAME_LISTENER;
    }
}
