package org.personalRestaurantGame.game;

import org.framework.domain.GameEventListener;
import org.personalRestaurantGame.domain.GamePipeline;
import org.personalRestaurantGame.domain.GameStateStore;

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
