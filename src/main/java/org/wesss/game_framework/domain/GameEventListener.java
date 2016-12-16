package org.wesss.game_framework.domain;

import org.wesss.game_framework.domain.event.GameEvent;

/**
 * This represents an event listener that handles player input
 *
 * In order to have this event listener accept events, overload the processGameEvent function to take
 * specific GameEvents
 */
public interface GameEventListener<T extends GameEvent> {

    // TODO use generics to accept only certain subclasses of GameEvents

    // TODO find a cleaner way to define the empty game listener
    public static GameEventListener EMPTY_GAME_LISTENER = new GameEventListener<GameEvent>() {
        @Override
        public void processGameEvent(GameEvent event) {
            //nothing
        }
    };

    public void processGameEvent(T event);
}
