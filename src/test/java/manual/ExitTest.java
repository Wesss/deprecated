package manual;

import org.wesss.game_framework.GameFramework;
import org.wesss.game_framework.canvas.GameCanvasController;
import org.wesss.game_framework.canvas.GameCanvasGraphics;
import org.wesss.game_framework.domain.Game;
import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.game_framework.domain.GameFactory;
import org.wesss.game_framework.domain.GameObj;
import org.wesss.game_framework.mainLoop.MainLoopController;

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

        @Override
        public GameEventListener dispatchGameEventListener() {
            return GameEventListener.EMPTY_GAME_LISTENER;
        }
    }

    public ExitTest(MainLoopController mainLoop, GameCanvasController canvas) {
        mainLoop.add(new CountdownExit(this));
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
