package org.personalRestaurantGame;

import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;

import static org.personalRestaurantGame.RestaurantGame.State.MAIN_MENU;

public class RestaurantGameFactory implements GameFactory {
    private RestaurantGame singleton;

    @Override
    public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
        singleton = new RestaurantGame(mainLoop, canvas);
        singleton.swapState(MAIN_MENU);
        return singleton;
    }

    @Override
    public GameEventListener dispatchGameEventListener() {
        return new EventListener(singleton);
    }
}
