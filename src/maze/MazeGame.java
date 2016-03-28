package maze;

import java.awt.Dimension;
import overhead.MainLoop;
import overhead_interfaces.Game;

/**
 * This class represents the Maze Game itself
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class MazeGame implements Game {
	
	public static final Dimension GAME_AREA = new Dimension(600, 600);
	public static final int FPS = 60;
	
	private Maze maze;

	public MazeGame(MainLoop mainLoop) {
		maze = new Maze(200, 200, Maze.Type.CYCLELESS);
		mainLoop.add(maze, 0);
	}
	
	@Override
	public void keyPressed(int keyCode) {}

	@Override
	public void keyReleased(int keyCode) {}

	@Override
	public void mousePressed(int x, int y, int button) {}

	@Override
	public void mouseReleased(int x, int y, int button) {}

	@Override
	public void mouseMoved(int x, int y) {}
}
