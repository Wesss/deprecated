package org.personalRestaurantGame.model;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.GameObj;
import org.gameUtil.EventAcceptor;

public interface GamePipeline extends GameObj {

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
    public EventAcceptor dispatchEventAcceptor();

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
        public EventAcceptor dispatchEventAcceptor() {
            return EventAcceptor.EMPTY_EVENT_ACCEPTOR;
        }

        @Override
        public void update() {

        }

        @Override
        public void paint(GameCanvasGraphics g) {

        }
    }
}
