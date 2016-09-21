package org.personalRestaurantGame;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.mainLoop.MainLoopController;
import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.personalRestaurantGame.model.GamePipeline;
import org.personalRestaurantGame.model.GameStateStore;
import org.gameUtil.EventAcceptor;

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
    private EventAcceptor currentEventAcceptor;
    private State state;

    public RestaurantGame(MainLoopController mainLoop, GameCanvasController canvas) {
        this.mainLoop = mainLoop.customGroupsInterface(MAXIMUM_UPDATE_PRIORITY);
        this.canvas = canvas;
        this.state = UNINITIALIZED;
        currentGamePipeline = GamePipeline.EMPTY_GAME_PIPELINE;
        currentEventAcceptor = EventAcceptor.EMPTY_EVENT_ACCEPTOR;
        currentGamePipeline.acceptGameStateStore(new GameStateStore());
        swapState(MAIN_MENU); // TODO create a game.start method such that the game can be initiallized without worrying about transitioinal stuff
    }

    @Override
    public GameEventListener dispatchGameEventListener() {
        return new EventListener(this);
    }

    ////////////////////
    // Game State
    ////////////////////

    // TODO split into different methods
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
                currentGamePipeline = GamePipelineFactory.getMainMenu(this, mainLoop);
                break;
            case NEW_GAME:
                currentGamePipeline = GamePipelineFactory.getLevel1();
                break;
            default:
                throw new RuntimeException(UNKNOWN_STATE_ERROR_MSG);
        }

        currentGamePipeline.acceptGameStateStore(currentStore);
        currentEventAcceptor = currentGamePipeline.dispatchEventAcceptor();
        // TODO rewire how game framework becomes aware of the pipeline
    }

    public void exit() {
        GameFramework.exitGame(this);
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
