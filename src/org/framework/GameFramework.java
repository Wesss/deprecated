package org.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.framework.interfaces.AspectRatio;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;

/**
 * This is a non-instantiable function holding class that is responsible for running
 * the entire Overhead of a Game.
 * 
 * @author Wesley Cox
 */
public class GameFramework {
	
	public static final GameEventListener EMPTY_GAME_LISTENER = new EmptyGameListener();
	
	/**
	 * Bugs TODO move to issue tracker
	 * TODO BUG1: Some rare race condition still exists (via dodger DodgerGame startup)
	 */
	
	/**
	 * <b> Given game class must contain a constructor of type GameEventListener() or GameEventListener(MainLoop).</b>
	 * Instantiates and runs a game of the given class;
	 * If constructor of type GameEventListener(MainLoop) exists, then that constructor is called passing in the MainLoop
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
//	public static <T extends GameEventListener> MainLoop startGame(Class<T> listener, int fps, Dimension dimension) {
//		MainLoop mainLoop = MainLoop.init(fps);
//		GamePanel panel = new GamePanel(dimension);
//		
//		GameEventListener game = null;
//		
//		try {
//			Constructor<?> emptyConstructor = null;
//			
//			Constructor<?>[] constructors = listener.getConstructors();
//			for (int i = 0; i < constructors.length; i++) {
//				Class<?>[] parameters = constructors[i].getParameterTypes();
//				if (parameters.length == 1 && parameters[0].equals(MainLoop.class)) {
//					game = (GameEventListener)constructors[i].newInstance(mainLoop);
//				} else if (parameters.length == 0) {
//					emptyConstructor = constructors[i];
//				}
//			}
//			
//			if (game == null) {
//				if (emptyConstructor != null) {
//					game = (GameEventListener)emptyConstructor.newInstance();
//				} else {
//					throw new RuntimeException("given game class does not contain an appropriate constructor");
//				}
//			}
//		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
//				| InvocationTargetException e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
//		
//		mainLoop.setReferences(panel);
//		panel.setReferences(game, mainLoop);
//		
//		mainLoop.start();
//		
//		return mainLoop;
//	}
	
	/**
	 * TODO
	 * @param game
	 * @param listener
	 * @param ratio
	 * @param updatesPerSecond
	 * @return
	 */
	public static <T extends Game> MainLoop startGame(Class<T> game,
														GameEventListener listener,
														AspectRatio ratio,
														int updatesPerSecond) {
		MainLoop mainLoop = MainLoop.init(updatesPerSecond);
		GamePanel panel = new GamePanel(500, 500); // TODO
		
		Game newGame = null;
		
		try {
			Constructor<?> gameConstructor = null;
			
			Constructor<?>[] constructors = game.getConstructors();
			for (int i = 0; i < constructors.length; i++) { // TODO refactor to while loop
				Class<?>[] parameters = constructors[i].getParameterTypes();
				if (parameters.length == 1 && parameters[0].equals(MainLoop.class)) {
					gameConstructor = constructors[i];
				} else if (parameters.length == 0) {
					if (gameConstructor == null)
						gameConstructor = constructors[i];
				}
			}
			
			if (gameConstructor != null) {
				if (gameConstructor.getParameterTypes().length == 1) {
					newGame = (Game)gameConstructor.newInstance(mainLoop);
				} else {
					newGame = (Game)gameConstructor.newInstance();
				}
			} else {
				throw new RuntimeException("given game class does not contain an empty constructor");
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		mainLoop.setReferences(panel);
		panel.setReferences(listener, mainLoop);
		listener.acceptGame(newGame);
		mainLoop.start();
		return mainLoop;
	}
	
	/**
	 * Disables creation
	 */
	private GameFramework() {}
	
	private static class EmptyGameListener implements GameEventListener {
		public void keyPressed(int keyCode) {}
		public void keyReleased(int keyCode) {}
		public void mousePressed(int x, int y, int button) {}
		public void mouseReleased(int x, int y, int button) {}
		public void mouseMoved(int x, int y) {}
		public void acceptGame(Game game) {}
    }
}
