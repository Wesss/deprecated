package org.personalRestaurantGame;

import org.framework.canvas.GameCanvasController;
import org.framework.interfaces.Game;
import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.game.LevelFactory;
import org.personalRestaurantGame.mainMenu.MainMenuFactory;
import util.EventAcceptor;

import static org.personalRestaurantGame.RestaurantGame.State.*;

public class RestaurantGame implements Game {

    public static final String UNKNOWN_STATE = "unknown state reached";
    public enum State {
        UNINITIALIZED, MAIN_MENU, NEW_GAME
    }

    private final MainLoopController mainLoop;
    private final GameCanvasController canvas;

    private EventAcceptor currentEventAcceptor;
    private State state;

    public RestaurantGame(MainLoopController mainLoop, GameCanvasController canvas) {
        this.mainLoop = mainLoop;
        this.canvas = canvas;
        this.state = UNINITIALIZED;
        swapState(MAIN_MENU);
    }

    ////////////////////
    // Game State
    ////////////////////

    public void swapState(State newState) {
        if (newState == UNINITIALIZED) {
            throw new IllegalArgumentException("cannot switch to uninitialized state");
        }
        switch (state) {
            case MAIN_MENU:
            case NEW_GAME:
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
                currentEventAcceptor = MainMenuFactory.initMainMenu(this, mainLoop);
                break;
            case NEW_GAME:
                currentEventAcceptor = LevelFactory.initLevel1();
                break;
            default:
                throw new RuntimeException(UNKNOWN_STATE);
        }
    }

    public void exit() {
        // TODO make game framework end-game endpoint
        System.exit(0);
    }

    ////////////////////
    // Event Distribution
    ////////////////////

    public void keyPressed(int keyCode) {
        currentEventAcceptor.keyPressed(keyCode);
    }

    public void keyReleased(int keyCode) {
        currentEventAcceptor.keyReleased(keyCode);
    }

    public void mousePressed(int x, int y, int button) {
        currentEventAcceptor.mousePressed(x, y, button);
    }

    public void mouseReleased(int x, int y, int button) {
        currentEventAcceptor.mouseReleased(x, y, button);
    }

    public void mouseMoved(int x, int y) {
        currentEventAcceptor.mouseMoved(x, y);
    }
}
