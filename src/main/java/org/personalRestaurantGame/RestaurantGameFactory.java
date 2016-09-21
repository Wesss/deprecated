package org.personalRestaurantGame;

import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;

public class RestaurantGameFactory implements GameFactory {
    @Override
    public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
        return new RestaurantGame(mainLoop, canvas);
    }
}
