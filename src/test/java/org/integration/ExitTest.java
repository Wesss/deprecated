package org.integration;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.domain.GameObj;
import org.framework.mainLoop.MainLoopController;

/**
 * Test for exiting
 */
public class ExitTest implements Game {

    private static final int FPS = 60;

    public static void main(String args[]) throws InstantiationException {
        GameFramework.startGame(new ExitTestFactory(), FPS);
    }

    private static class ExitTestFactory implements GameFactory {
        @Override
        public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
            return new ExitTest(mainLoop, canvas);
        }
    }

    public ExitTest(MainLoopController mainLoop, GameCanvasController canvas) {
        mainLoop.add(new CountdownExit(this));
    }

    @Override
    public GameEventListener dispatchGameEventListener() {
        return GameEventListener.EMPTY_GAME_LISTENER;
    }

    private class CountdownExit implements GameObj {

        private int countdown;
        private Game game;

        public CountdownExit(Game game) {
            this.countdown = 60;
            this.game = game;
        }

        @Override
        public void paint(GameCanvasGraphics g) {
            g.drawString("" + countdown, 100, 100);
        }

        @Override
        public void update() {
            countdown--;
            if (countdown == 0) {
                GameFramework.exitGame(game);
            } else if (countdown == -60) {
                System.exit(1);
            }
        }
    }
}
