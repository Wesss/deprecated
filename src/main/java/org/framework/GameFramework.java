package org.framework;

import javafx.util.Pair;
import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasFactory;
import org.framework.canvas.GameCanvasModel;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.framework.mainLoop.MainLoopController;
import org.framework.mainLoop.MainLoopFactory;
import org.framework.mainLoop.MainLoopFactoryFactory;
import org.framework.mainLoop.MainLoopModel;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.lang.Math.min;

/**
 * This is a non-instantiable function holding class that is responsible for running
 * the entire Overhead of a Game.
 * 
 * @author Wesley Cox
 */
public class GameFramework {

    // TODO close window if game creation fails

    public static final GameEventListener<Game> EMPTY_GAME_LISTENER = new EmptyGameListener();
    public static final double SCREEN_RATIO = 0.9;

    private GameFramework() {}

    /**
     * Instantiates an instance of the given game class. The class must contain an accessible empty constructor or a
     * constructor that takes a MainLoopController and a GameCanvasController.
     *
     * @param game the class of the game to be run
     *             If a constructor that takes a MainLoopController and GameCanvasModel is present, that constructor will be called; passing in
     *             the appropriate game controllers.
     *             Otherwise, the empty constructor is called.
     *             Throws a RuntimeException if neither constructor is present
     * @param listener The listener that will receive user events
     * @param updatesPerSecond
     * @return a pair where pair.key is the MainLoopController and pair.value is the GameCanvasModel to control the game
     * @throws InstantiationException if a valid constructor for the given game class is not present
     * @param <T> the type of the game to run
     */
    public static <T extends Game> Pair<MainLoopController, GameCanvasController> startGame(Class<T> game,
                                                                                            GameEventListener<? super T> listener,
                                                                                            int updatesPerSecond)
            throws InstantiationException {
        MainLoopFactory factory = MainLoopFactoryFactory.getMainLoopFactory();
        factory.constructMainLoop(updatesPerSecond);
        MainLoopModel mainLoopModel = factory.getMainLoopModel();
        MainLoopController mainLoopController = factory.getMainLoop();

        Dimension screen = GameCanvasModel.getScreenDimension();
        int gameLength = (int)(SCREEN_RATIO * min(screen.width, screen.height));
        GameCanvas canvas =
                GameCanvasFactory.createCanvas(GameCanvasFactory.createFrame(), gameLength, gameLength);
        GameCanvasController canvasController = canvas.getController();
        GameCanvasModel canvasModel = canvas.getModel();

        T newGame = createGame(game, mainLoopController, canvasController);

        mainLoopModel.setReferences(canvasModel);
        canvasModel.setReferences(listener);
        listener.acceptGame(newGame);
        mainLoopModel.start();
        return new Pair<>(mainLoopController, canvasController);
    }

    private static <T extends Game> T createGame(Class<T> gameClass, MainLoopController mainLoop, GameCanvasController canvas)
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
            throw new InstantiationException(e.getMessage());
        }
        if (game == null) {
            throw new InstantiationException(
                    "given game class does not contain an empty or MainLoopController and GameCanvasController accepting constructor");
        }
        return game;
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
