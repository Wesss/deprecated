package org.framework;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.framework.mainLoop.MainLoop;
import org.framework.mainLoop.MainLoopFactory;
import org.framework.mainLoop.MainLoopFactoryFactory;
import org.framework.canvas.GameCanvas;

import static java.lang.Math.min;

/**
 * This is a non-instantiable function holding class that is responsible for running
 * the entire Overhead of a Game.
 * 
 * @author Wesley Cox
 */
public class GameFramework {

    // TODO make game require a factory method to allow giving of mainloop and canvas
    // TODO prevent misuse of public framework functions (ie set references, start?)

    public static final GameEventListener<Game> EMPTY_GAME_LISTENER = new EmptyGameListener();
    public static final double SCREEN_RATIO = 0.9;

    private GameFramework() {}

    /*
     * <b> Given game class must contain a constructor of type GameEventListener() or GameEventListener(MainLoop).</b>
     * Instantiates and runs a game of the given class;
     * If constructor of type GameEventListener(MainLoop) exists, then that constructor is called passing in the MainLoop
     * created for this game. Otherwise Game() is called.
     * The game runs at a speed of given fps (frames per second) on a canvas of given dimension.
     *
     * @param <T> The type GameEventListener to use
     *
     * @param listener the class of the type of game to instantiate (reached with MyGame.class)
     * @param dimension the size of the window the game is to be played on
     *
     * @return the MainLoop created for this game
     */

    /**
     * TODO
     * @param game
     * @param listener
     * @param updatesPerSecond
     * @return
     */
    public static <T extends Game> MainLoop startGame(Class<T> game,
                                                      GameEventListener<? super T> listener,
                                                      int updatesPerSecond) {
        MainLoopFactory factory = MainLoopFactoryFactory.getMainLoopFactory();
        factory.constructMainLoop(updatesPerSecond);
        MainLoop mainLoop = factory.getMainLoop();

        Dimension screen = GameCanvas.getScreenDimension();
        int gameLength = (int)(SCREEN_RATIO * min(screen.width, screen.height));
        GameCanvas canvas = new GameCanvas(new Dimension(gameLength, gameLength));

        T newGame = createGame(game, mainLoop, canvas);

        mainLoop.setReferences(canvas);
        canvas.setReferences(listener);
        listener.acceptGame(newGame);
        mainLoop.start();
        return mainLoop;
    }

    private static <T extends Game> T createGame(Class<T> gameClass, MainLoop mainLoop, GameCanvas canvas) {
        T game = null;
        try {
            Constructor<?>[] constructors = gameClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameters = constructor.getParameterTypes();
                if (parameters.length == 0) {
                    game = (T)constructor.newInstance();
                    break;
                } else if (parameters.length == 2) {
                    if (parameters[0].equals(MainLoop.class) && parameters[1].equals(GameCanvas.class)) {
                        game = (T)constructor.newInstance(mainLoop, canvas);
                        break;
                    } else if (parameters[0].equals(GameCanvas.class) || parameters[1].equals(MainLoop.class)) {
                        game = (T)constructor.newInstance(canvas, mainLoop);
                        break;
                    }
                }
            }
            if (game == null) {
                throw new RuntimeException("given game class does not contain an empty or mainloop accepting constructor");
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            //TODO error more gracefully? (ie. with custom exception)
            throw new RuntimeException(e);
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
