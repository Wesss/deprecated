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
	 * Bugs
	 * TODO Some rare race condition still exists (via dodger gDodgerame startup)
	 * TODO Sometimes keyboard listener does not work upon startup (via EventFireTest)
	 */
	
	/**
	 * Runs the given game; providing EventListeners, Panel/Frame, and
	 * the MainLoop
	 * 
	 * @param game the game to be run
	 * @param dimension the size of the window the game is to be played on
	 */
	public static void startGame(Game game, Dimension dimension) {
		MainLoop mainLoop = new MainLoop();
		GamePanel panel = new GamePanel(dimension);
		
		mainLoop.setReferences(panel);
		panel.setReferences(game, mainLoop);
		
		//main loop must start after initialization
		game.initiallize();
		mainLoop.start();
	}
	
	/**
	 * Disables creation
	 */
	private Overhead() {}
}
