package org.personalRestaurantGame;

import org.framework.canvas.GameCanvasController;
import org.framework.interfaces.Game;
import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.mainMenu.MainMenu;

import static org.personalRestaurantGame.RestaurantGame.State.*;

public class RestaurantGame implements Game {

    public static final String UNKNOWN_STATE = "unknown state reached";
    public static enum State {
        UNINITIALIZED, MAIN_MENU, MAIN_GAME
    }

    private MainLoopController mainLoop;
    private GameCanvasController canvas;
    private State state;

    public RestaurantGame(MainLoopController mainLoop, GameCanvasController canvas) {
        this.mainLoop = mainLoop;
        this.canvas = canvas;
        this.state = UNINITIALIZED;
        swapState(MAIN_MENU);
    }

    public void swapState(State newState) {
        if (newState == UNINITIALIZED) {
            throw new IllegalArgumentException("cannot switch to uninitialized state");
        }
        switch (state) {
            case MAIN_MENU:
                break;
            case MAIN_GAME:
                mainLoop.markClear();
                break;
            case UNINITIALIZED:
                break;
            default:
                throw new RuntimeException(UNKNOWN_STATE);
        }

        state = newState;
        switch (state) {
            case MAIN_MENU:
                mainLoop.addPostClear(new MainMenu());
                break;
            case MAIN_GAME:
                break;
            default:
                throw new RuntimeException(UNKNOWN_STATE);
        }
    }
}
