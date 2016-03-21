package maze;

import java.awt.Dimension;

import overhead.MainLoop;
import overhead_interfaces.Game;

public class MazeGame implements Game {
	
	public static final Dimension GAME_AREA = new Dimension(600, 600);
	
	private Maze maze;

	public MazeGame() {
		maze = new Maze(200, 200, Maze.Type.CYCLELESS);
		MainLoop.add(maze, 0);
	}
	
	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int x, int y, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(int x, int y, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
	}
}
