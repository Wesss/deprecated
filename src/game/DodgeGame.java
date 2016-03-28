package game;

import overhead_interfaces.Game;

public interface DodgeGame extends Game {
	
	/**
	 * Call back for letting the game know the User opted to exit the game
	 */
	public void menuExitGame();

	/**
	 * Call back for letting the game know the User opted to start the game
	 */
	public void menuStartGame();
	
	/**
	 * Call back for letting the game know the player ran out of lives
	 */
	public void playerOutOfLives();
	
	/**
	 * Call back for letting the game know the Scripter ran out of levels
	 */
	public void scripterOutOfLevels();
}
