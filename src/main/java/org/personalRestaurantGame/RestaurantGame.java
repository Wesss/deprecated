package org.personalRestaurantGame;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.mainLoop.MainLoopController;
import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.personalRestaurantGame.game.LevelFactory;
import org.personalRestaurantGame.mainMenu.MainMenuFactory;
import org.personalRestaurantGame.domain.GamePipeline;
import org.personalRestaurantGame.domain.GameStateStore;

import static org.personalRestaurantGame.RestaurantGame.State.*;

public class RestaurantGame implements Game {

    // TODO game transition

    public static final int MAXIMUM_UPDATE_PRIORITY = 10;
    public static final String UNKNOWN_STATE_ERROR_MSG = "unknown state reached";

    public enum State {
        UNINITIALIZED, MAIN_MENU, NEW_GAME
    }

    private final MainLoopCustomGroupsInterface mainLoop;
    private final GameCanvasController canvas;

    private GamePipeline currentGamePipeline;
    private GameEventListener currentEventListener;
    private State state;

    public RestaurantGame(MainLoopController mainLoop, GameCanvasController canvas) {
        this.mainLoop = mainLoop.customGroupsInterface(MAXIMUM_UPDATE_PRIORITY);
        this.canvas = canvas;
        this.state = UNINITIALIZED;
        currentGamePipeline = GamePipeline.EMPTY_GAME_PIPELINE;
        currentEventListener = GameEventListener.EMPTY_GAME_LISTENER;
        currentGamePipeline.acceptGameStateStore(new GameStateStore());
    }

    ////////////////////
    // Game State
    ////////////////////

    public void swapState(State newState) {
        if (newState == UNINITIALIZED) {
            throw new IllegalArgumentException("cannot switch to uninitialized state");
        }

        GameStateStore currentStore = currentGamePipeline.returnGameStateStore();
        mainLoop.removeAllGroups();
        switch (state) {
            case MAIN_MENU:
                break;
            case NEW_GAME:
                break;
            case UNINITIALIZED:
                break;
            default:
                throw new RuntimeException(UNKNOWN_STATE_ERROR_MSG);
        }

        state = newState;
        switch (state) {
            case MAIN_MENU:
                currentGamePipeline = MainMenuFactory.getMainMenu(this, mainLoop);
                break;
            case NEW_GAME:
                currentGamePipeline = LevelFactory.getLevel1();
                break;
            default:
                throw new RuntimeException(UNKNOWN_STATE_ERROR_MSG);
        }

        currentGamePipeline.acceptGameStateStore(currentStore);
        currentEventListener = currentGamePipeline.dispatchEventListener();
    }

    public void exit() {
        GameFramework.exitGame(this);
    }

    ////////////////////
    // Event Distribution
    ////////////////////

    public void keyPressed(int keyCode) {
        currentEventListener.keyPressed(keyCode);
    }

    public void keyReleased(int keyCode) {
        currentEventListener.keyReleased(keyCode);
    }

    public void mousePressed(int x, int y, int button) {
        currentEventListener.mousePressed(x, y, button);
    }

    public void mouseReleased(int x, int y, int button) {
        currentEventListener.mouseReleased(x, y, button);
    }

    public void mouseMoved(int x, int y) {
        currentEventListener.mouseMoved(x, y);
    }
}
