package org.wesss.personal_restaurant_game;

import org.wesss.game_framework.canvas.GameCanvasController;
import org.wesss.game_framework.domain.Game;
import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.game_framework.domain.GameFactory;
import org.wesss.game_framework.mainLoop.MainLoopController;

import static org.wesss.personal_restaurant_game.RestaurantGame.State.MAIN_MENU;

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
