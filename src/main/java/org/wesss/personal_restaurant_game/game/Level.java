package org.wesss.personal_restaurant_game.game;

import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.personal_restaurant_game.domain.GamePipeline;
import org.wesss.personal_restaurant_game.domain.GameStateStore;

public class Level implements GamePipeline {

    // TODO
    private GameStateStore store;

    public Level() {

    }

    @Override
    public void acceptGameStateStore(GameStateStore store) {
        this.store = store;
    }

    @Override
    public GameStateStore returnGameStateStore() {
        return store;
    }

    @Override
    public GameEventListener dispatchEventListener() {
        return new LevelEventListener(this);
    }
}
