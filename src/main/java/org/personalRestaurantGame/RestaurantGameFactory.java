package org.personalRestaurantGame;

import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;

public class RestaurantGameFactory implements GameFactory {
    private RestaurantGame singleton;

    @Override
    public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
        singleton = new RestaurantGame(mainLoop, canvas);
        return singleton;
    }

    @Override
    public GameEventListener dispatchGameEventListener() {
        return new EventListener(singleton);
    }
}
