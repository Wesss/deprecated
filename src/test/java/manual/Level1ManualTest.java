package manual;

import org.wesss.game_framework.GameFramework;
import org.wesss.game_framework.canvas.GameCanvasController;
import org.wesss.game_framework.domain.Game;
import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.game_framework.domain.GameFactory;
import org.wesss.game_framework.mainLoop.MainLoopController;
import org.wesss.personal_restaurant_game.RestaurantGame;
import org.wesss.personal_restaurant_game.game.LevelFactory;

public class Level1ManualTest {
    public static final int FPS = 60;

    public static void main(String[] args) {
        GameFramework.startGame(new LevelTestFactory(), RestaurantGame.FPS);
    }

    public static class LevelTestFactory implements GameFactory {

        @Override
        public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
            // TODO make factory non static to abstract this manual test factory out more completely
            FakeRestaurantGame.singletonTestPipeline = LevelFactory.getLevel1(
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
