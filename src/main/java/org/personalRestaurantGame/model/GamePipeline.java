package org.personalRestaurantGame.model;

import org.framework.domain.Game;
import org.framework.domain.GameEventListener;

public interface GamePipeline extends Game {

    public static final GamePipeline EMPTY_GAME_PIPELINE = new EmptyGamePipeline();

    /**
     * Transfers ownership of the current game state store onto this.
     * This is called only once right after creation.
     *
     * @param store
     */
    public void acceptGameStateStore(GameStateStore store);

    /**
     * Releases ownership of the current game state store.
     * This is called only once just before the destruction of this.
     *
     * @return the GameStateStore that was previously accepted
     */
    public GameStateStore returnGameStateStore();

    /**
     * @return an EventAcceptor that will be funneled all game events
     */
    public GameEventListener dispatchEventListener();

    ////////////////////
    // Empty instance
    ////////////////////

    static class EmptyGamePipeline implements GamePipeline {

        private GameStateStore store;

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
            return GameEventListener.EMPTY_GAME_LISTENER;
        }
    }
}
