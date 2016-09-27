package org.framework;

import javafx.util.Pair;
import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasFactory;
import org.framework.canvas.GameCanvasModel;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.*;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

/**
 * This is a non-instantiable function holding class that is responsible for running
 * the entire Overhead of a Game.
 */
public class GameFramework {

    // TODO throw better exceptions

    public static final double SCREEN_RATIO = 0.9;

    private static final GameFramework frameworkSingleton = new GameFramework();

    /**
     * currently running game -> components to exit
     */
    private Map<Game, Pair<MainLoop, GameCanvas>> gameToFrameworkComponents = new HashMap<>();

    private GameFramework() {
    }

    public static Pair<MainLoopController, GameCanvasController> startGame(GameFactory factory,
                                                                           int updatesPerSecond)
            throws RuntimeException {
        return frameworkSingleton.startGame(factory, updatesPerSecond, true);
    }

    private Pair<MainLoopController, GameCanvasController> startGame(GameFactory factory,
                                                                     int updatesPerSecond,
                                                                     boolean dummy)
            throws RuntimeException {
        MainLoop mainLoop = MainLoopFactory.getMainLoop(updatesPerSecond);
        MainLoopController mainLoopController = mainLoop.getController();

        Dimension screen = GameCanvas.getScreenDimension();
        int gameDimension = (int) (SCREEN_RATIO * min(screen.width, screen.height));
        GameCanvas canvas =
                GameCanvasFactory.createCanvas(gameDimension, gameDimension);
        GameCanvasController canvasController = canvas.getController();
        GameCanvasModel canvasModel = canvas.getModel();

        Game newGame;
        try {
            newGame = factory.createGame(mainLoopController, canvasController);
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of game", e);
        }

        mainLoop.setReferences(canvasModel);
        canvasModel.setReferences(factory.dispatchGameEventListener());
        gameToFrameworkComponents.put(newGame, new Pair<>(mainLoop, canvas));

        canvas.start();
        mainLoop.start();
        return new Pair<>(mainLoopController, canvasController);
    }

    /**
     * Exits given game, destroying its canvas and stopping the main loop.
     * <p>
     * Does nothing if game hasn't finished the startGame routine yet
     *
     * @param game
     */
    public static void exitGame(Game game) {
        frameworkSingleton.exitGame(game, true);
    }

    public void exitGame(Game game, boolean dummy) {
        Pair<MainLoop, GameCanvas> components = gameToFrameworkComponents.get(game);
        if (components == null) {
            return;
        }

        MainLoopFactory.destroyMainLoop(components.getKey());
        GameCanvasFactory.destroyCanvas(components.getValue());
    }

}
