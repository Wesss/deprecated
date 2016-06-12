package org.framework;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.framework.interfaces.GameEventListener;

/**
 * This is a non-instantiable function holding class that is responsible for running
 * the entire Overhead of a Game.
 * 
 * @author Wesley Cox
 */
public class GameFramework {
	
	/**
	 * Bugs
	 * TODO BUG1: Some rare race condition still exists (via dodger DodgerGame startup)
	 * TODO BUG2: Sometimes keyboard listener does not work upon startup (via EventFireTest)
	 */
	
	/**
	 * <b> Given game class must contain a constructor of type Game() or Game(MainLoop).</b>
	 * Instantiates and runs a game of the given class;
	 * If constructor of type Game(MainLoop) exists, then that constructor is called passing in the MainLoop
	 * created for this game. Otherwise Game() is called.
	 * The game runs at a speed of given fps (frames per second) on a panel of given dimension.
	 * 
	 * @param <T> The type GameEventListener to use
	 * 
	 * @param listener the class of the type of game to instantiate (reached with MyGame.class)
	 * @param dimension the size of the window the game is to be played on
	 * 
	 * @return the MainLoop created for this game
	 */
	public static <T extends GameEventListener> MainLoop startGame(Class<T> listener, int fps, Dimension dimension) {
		MainLoop mainLoop = MainLoop.init(fps);
		GamePanel panel = new GamePanel(dimension);
		
		GameEventListener game = null;
		
		try {
			Constructor<?> emptyConstructor = null;
			
			Constructor<?>[] constructors = listener.getConstructors();
			for (int i = 0; i < constructors.length; i++) {
				Class<?>[] parameters = constructors[i].getParameterTypes();
				if (parameters.length == 1 && parameters[0].equals(MainLoop.class)) {
					game = (GameEventListener)constructors[i].newInstance(mainLoop);
				} else if (parameters.length == 0) {
					emptyConstructor = constructors[i];
				}
			}
			
			if (game == null) {
				if (emptyConstructor != null) {
					game = (GameEventListener)emptyConstructor.newInstance();
				} else {
					throw new RuntimeException("given game class does not contain an appropriate constructor");
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		mainLoop.setReferences(panel);
		panel.setReferences(game, mainLoop);
		
		mainLoop.start();
		
		return mainLoop;
	}
	
	/**
	 * Disables creation
	 */
	private GameFramework() {}
}
