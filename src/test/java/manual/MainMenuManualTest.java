package manual;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.mainMenu.MainMenuFactory;

public class MainMenuManualTest {
    public static final int FPS = 60;

    public static void main(String[] args) {
        GameFramework.startGame(new MainMenuTestFactory(), RestaurantGame.FPS);
    }

    public static class MainMenuTestFactory implements GameFactory {

        @Override
        public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
            FakeRestaurantGame.singletonTestPipeline = MainMenuFactory.getMainMenu(
                    new FakeRestaurantGame(),
                    mainLoop.customGroupsInterface(RestaurantGame.MAXIMUM_UPDATE_PRIORITY));
            return FakeRestaurantGame.singletonTestPipeline;
        }

        @Override
        public GameEventListener dispatchGameEventListener() {
            return FakeRestaurantGame.singletonTestPipeline.dispatchEventListener();
        }
    }
}
