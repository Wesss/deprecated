package overhead;

import java.awt.Dimension;

import overhead_interfaces.Game;

/**
 * This is a non-instantiable function holding class that is responsible for running
 * the entire Overhead of a Game.
 * 
 * @author Wesley Cox
 * @last_edited 11/23/15
 */
public class Overhead {
	
	/**
	 * TODO get rid of class parameter passing somehow (factory method?)
	 * 
	 * Bugs
	 * TODO BUG1: Some rare race condition still exists (via dodger gDodgerame startup)
	 * TODO BUG2: Sometimes keyboard listener does not work upon startup (via EventFireTest)
	 * 
	 * TODO BUG3: upon a game take a long time to initialize, a single repaint call all events fed into the game
	 * result in a nullPointer error (replicated in BaseTest)
	 * game appeared unaffected after initialization completed
	 * 
	 * 		pre-initialization event errors temporaraly patched (see GamePanel
	 */
	
	/**
	 * Runs the given game; providing EventListeners, Panel/Frame, and
	 * the MainLoop
	 * @param <T> The type of game to instantiate
	 * 
	 * @param gametype the class of the type of game to instantiate (reached with MyGame.class)
	 * @param dimension the size of the window the game is to be played on
	 */
	public static <T extends Game> void startGame(Class<T> gametype, Dimension dimension) {
		MainLoop mainLoop = new MainLoop();
		GamePanel panel = new GamePanel(dimension);
		
		Game game = null;
		try {
			game = gametype.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		mainLoop.setReferences(panel);
		panel.setReferences(game, mainLoop);
		
		mainLoop.start();
	}
	
	/**
	 * Disables creation
	 */
	private Overhead() {}
}
