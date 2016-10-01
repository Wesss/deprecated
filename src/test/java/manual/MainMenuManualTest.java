package manual;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.mainMenu.MainMenu;
import org.personalRestaurantGame.mainMenu.MainMenuFactory;

import static org.mockito.Mockito.mock;

public class MainMenuManualTest {
    public static final int FPS = 60;

    public static void main(String[] args) {
        GameFramework.startGame(new MainMenuTestFactory(), FPS);
    }

    private static class MainMenuTestFactory implements GameFactory {

        public static MainMenu singleton;

        @Override
        public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
            singleton = MainMenuFactory.getMainMenu(
                    new FakeRestaurantGame(),
                    mainLoop.customGroupsInterface(5));
            return singleton;
        }

        @Override
        public GameEventListener dispatchGameEventListener() {
            return singleton.dispatchEventListener();
        }
    }

    private static class FakeRestaurantGame extends RestaurantGame {

        public FakeRestaurantGame() {
            super(mock(MainLoopController.class), mock(GameCanvasController.class));
        }

        @Override
        public void swapState(State newState) {
            System.out.println("State Switch call [" + newState + "] registered");
            GameFramework.exitGame(MainMenuTestFactory.singleton);
        }

        @Override
        public void exit() {
            System.out.println("Exit call registered");
            GameFramework.exitGame(MainMenuTestFactory.singleton);
        }
    }
}
