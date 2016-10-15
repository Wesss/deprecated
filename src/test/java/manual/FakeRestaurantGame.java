package manual;


import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.domain.GamePipeline;

import static org.mockito.Mockito.mock;

public class FakeRestaurantGame extends RestaurantGame {

    // TODO make this non-public
    public static GamePipeline singletonTestPipeline;

    public FakeRestaurantGame() {
        super(mock(MainLoopController.class), mock(GameCanvasController.class));
    }

    @Override
    public void swapState(State newState) {
        System.out.println("State Switch call [" + newState + "] registered");
        GameFramework.exitGame(singletonTestPipeline);
    }

    @Override
    public void exit() {
        System.out.println("Exit call registered");
        GameFramework.exitGame(singletonTestPipeline);
    }
}
