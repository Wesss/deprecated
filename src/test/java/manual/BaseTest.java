package manual;

import org.wesss.game_framework.GameFramework;
import org.wesss.game_framework.canvas.GameCanvasController;
import org.wesss.game_framework.domain.Game;
import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.game_framework.domain.GameFactory;
import org.wesss.game_framework.mainLoop.MainLoopController;

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
