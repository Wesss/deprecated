package maze;

import java.awt.Dimension;
import java.awt.Graphics;

import overhead_interfaces.GameObj;
import graph.Graph;
import numbers.MyMath;

public class Maze implements GameObj{

	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	public enum Connection {
		NORMAL
	}
	public enum Type {
		CYCLELESS
	}
	
	/**
	 * Abstraction Function:
	 * A maze object represents a maze of size width by height
	 * The maze Graph represents the entirety of the maze where:
	 * 	each square is represented by a Grid(x, y)
	 * 	the ability to travel to a different square is represented
	 * 			by the Connection edges within the graph
	 * (0,0) is the top-left most grid
	 * 
	 * 
	 * Representation Invariant:
	 * maze != null
	 * width != 0
	 * height != 0
	 */
	
	private Graph<Grid, Connection> maze;
	private int width;
	private int height;
	private int wallRadiusDrawCounter; //TODO move into a painter class

	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	public Maze(int x, int y, Type type) {
		if (x == 0 || y == 0) 
			throw new IllegalArgumentException("maze cannot be of length 0");
		
		width = x;
		height = y;
		
		switch (type) {
		case CYCLELESS:
			maze = MazeGenerator.createCyclelessMaze(x, y);
		}
		
		wallRadiusDrawCounter = 0;
	}
	
	//////////////////////////////////////////////////
	// Instance Information
	//////////////////////////////////////////////////
	
	/** 
	 * @return the horizontal length of the maze (in grids/squares)
	 */
	public int getWidth() {
		return width;
	}
	
	/** 
	 * @return the vertical length of the maze (in grids/squares)
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns true when an open path is available from x1, y1 to x2, y2
	 * @param x1 the x coordinate of the first grid in the maze
	 * @param y1 the y coordinate of the first grid in the maze
	 * @param x2 the x coordinate of the second grid in the maze
	 * @param y2 the y coordinate of the second grid in the maze
	 * @return a boolean that is true when a path exists between the two coordinates
	 */
	public boolean hasConnection(int x1, int y1, int x2, int y2) {
		return maze.hasSomeEdge(new Grid(x1, y1), new Grid(x2, y2));
	}
	
	/**
	 * TODO
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public Connection connectionType(int x1, int y1, int x2, int y2) {
		return Connection.NORMAL; //TODO
	}
	
	//////////////////////////////////////////////////
	// Painting
	//////////////////////////////////////////////////
	
	@Override
	public void draw(Graphics g) {
		if (wallRadiusDrawCounter < MyMath.hypotenuse(width, height) + 1) {
			wallRadiusDrawCounter++;
		}
		drawMazeInRadius(this, g, MazeGame.GAME_AREA, wallRadiusDrawCounter);
	}
	
	/**
	 * TODO
	 * @param maze
	 * @param g
	 * @param drawArea
	 * @param radius
	 */
	private static void drawMazeInRadius(Maze maze, Graphics g, Dimension drawArea, int radius) {
		int mazeWidth = maze.getWidth();
		int mazeHeight = maze.getHeight();
		int gridDrawWidth = drawArea.width / mazeWidth;
		int gridDrawHeight = drawArea.height / mazeHeight;
		
		//draw maze border
		g.drawRect(0, 0, drawArea.width - 1, drawArea.height - 1);
		
		//draw vertical walls
		for (int i = 0; i < mazeWidth - 1; i++) {
			for (int j = 0; j < mazeHeight; j++) {
				if (!maze.hasConnection(i, j, i + 1, j) || radius < MyMath.hypotenuse(i, j) + 1) {
					g.drawLine((i + 1) * gridDrawWidth, j * gridDrawHeight,
							(i + 1) * gridDrawWidth, (j + 1) * gridDrawHeight);
				}
			}
		}
		
		//draw horizontal walls
		for (int i = 0; i < mazeWidth; i++) {
			for (int j = 0; j < mazeHeight - 1; j++) {
				if (!maze.hasConnection(i, j, i, j + 1) || radius < MyMath.hypotenuse(i, j) + 1) {
					g.drawLine(i * gridDrawWidth, (j + 1) * gridDrawHeight,
							(i + 1) * gridDrawWidth, (j + 1) * gridDrawHeight);
				}
			}
		}
	}
	


	//No update needed
	@Override
	public void update() {}
}
