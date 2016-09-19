package org.framework;

import javafx.util.Pair;
import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasFactory;
import org.framework.canvas.GameCanvasModel;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
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
 * 
 * @author Wesley Cox
 */
public class GameFramework {

    // TODO close window if game creation fails
    // TODO get rid of reflection; it prevents propagation of initialization errors

    public static final GameEventListener<Game> EMPTY_GAME_LISTENER = new EmptyGameListener();
    public static final double SCREEN_RATIO = 0.9;

    private static final GameFramework frameworkSingleton = new GameFramework();

    /**
     * TODO
     * currently running game -> components to exit
     */
    private Map<Game, Pair<MainLoop, GameCanvas>> gameToFrameworkComponents = new HashMap<>();

    private GameFramework() {}

    public static <T extends Game> Pair<MainLoopController, GameCanvasController> startGame(Class<T> game,
                                                                                            GameEventListener<? super T> listener,
                                                                                            int updatesPerSecond)
            throws InstantiationException {
        return frameworkSingleton.startGame(game, listener, updatesPerSecond, true);
    }

    private <T extends Game> Pair<MainLoopController, GameCanvasController> startGame(Class<T> game,
                                                                                      GameEventListener<? super T> listener,
                                                                                      int updatesPerSecond,
                                                                                      boolean dummy)
            throws InstantiationException {
        MainLoop mainLoop = MainLoopFactory.getMainLoop(updatesPerSecond);
        MainLoopController mainLoopController = mainLoop.getController();

        Dimension screen = GameCanvasModel.getScreenDimension();
        int gameLength = (int)(SCREEN_RATIO * min(screen.width, screen.height));
        GameCanvas canvas =
                GameCanvasFactory.createCanvas(gameLength, gameLength);
        GameCanvasController canvasController = canvas.getController();
        GameCanvasModel canvasModel = canvas.getModel();

        T newGame = createGame(game, mainLoopController, canvasController);

        mainLoop.setReferences(canvasModel);
        canvasModel.setReferences(listener);
        listener.acceptGame(newGame);
        mainLoop.start();
        return new Pair<>(mainLoopController, canvasController);
        // TODO add created MainLoop and GameCanvas to gameToFrameworkComponents
    }

    private <T extends Game> T createGame(Class<T> gameClass, MainLoopController mainLoop, GameCanvasController canvas)
            throws InstantiationException{
        T game = null;
        try {
            Constructor<?> emptyConstructor = null;
            Constructor<?>[] constructors = gameClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameters = constructor.getParameterTypes();
                if (parameters.length == 0) {
                    emptyConstructor = constructor;
                } else if (parameters.length == 2) {
                    if (parameters[0].equals(MainLoopController.class) && parameters[1].equals(GameCanvasController.class)) {
                        game = (T)constructor.newInstance(mainLoop, canvas);
                        break;
                    } else if (parameters[0].equals(GameCanvasController.class) || parameters[1].equals(MainLoopController.class)) {
                        game = (T)constructor.newInstance(canvas, mainLoop);
                        break;
                    }
                }
            }
            if (game == null && emptyConstructor != null) {
                game = (T)emptyConstructor.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new InstantiationException("Error initiallizing game. An exception may have bubbled out");
        }
        if (game == null) {
            throw new InstantiationException(
                    "given game class does not contain an empty or MainLoopController and GameCanvasController accepting constructor");
        }
        return game;
    }

    /**
     * Exits given game, destroying its canvas and stopping the main loop
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

    private static class EmptyGameListener implements GameEventListener<Game> {
        public void keyPressed(int keyCode) {}
        public void keyReleased(int keyCode) {}
        public void mousePressed(int x, int y, int button) {}
        public void mouseReleased(int x, int y, int button) {}
        public void mouseMoved(int x, int y) {}
        public void acceptGame(Game game) {}
    }
}
