package org.wesss.game_framework.domain;

/**
 * This class represents the game itself to be run
 */
public interface Game {

    public static final Game EMPTY_GAME = new EmptyGame();

    static class EmptyGame implements Game {

    }
}
