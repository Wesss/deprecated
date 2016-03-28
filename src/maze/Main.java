package maze;

import overhead.Overhead;

/**
 * Kicks off the Maze game
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class Main {
	
	public static void main(String args[]) {
		Overhead.startGame(MazeGame.class, MazeGame.FPS, MazeGame.GAME_AREA);
		while (true) {}
	}
}
