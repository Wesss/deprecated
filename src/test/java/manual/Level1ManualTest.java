package manual;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.game.LevelFactory;
import org.personalRestaurantGame.mainMenu.MainMenuFactory;

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
