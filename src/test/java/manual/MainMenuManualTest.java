package manual;

import org.wesss.game_framework.GameFramework;
import org.wesss.game_framework.canvas.GameCanvasController;
import org.wesss.game_framework.domain.Game;
import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.game_framework.domain.GameFactory;
import org.wesss.game_framework.mainLoop.MainLoopController;
import org.wesss.personal_restaurant_game.RestaurantGame;
import org.wesss.personal_restaurant_game.mainMenu.MainMenuFactory;

public class MainMenuManualTest {
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
