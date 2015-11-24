package enemy;

import overhead.MainLoop;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited 11/15/15
 */
public class CircleSpawner {
	
	/**
	 * TODO create abstraction to spawn any type of enemy
	 */
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 */
	private int x;
	private int y;
	private int direction;
	private int speed;
	private int radius;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	public CircleSpawner() {
		radius = 1;
	}
	
	//////////////////////////////////////////////////
	// Manipulation
	//////////////////////////////////////////////////
	
	/**
	 * sets the x coordinate for subsequently made Circles
	 * @param x the x coordinate of the spawn
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * sets the y coordinate for subsequently made Circles
	 * @param y the y coordinate of the spawn
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * sets the direction of travel for subsequently made Circles
	 * @param dir the direction of travel (in degrees).
	 * 		0 is defined as toward the right, increasing in the ccw direction
	 */
	public void setDir(int dir) {
		this.direction = dir;
	}
	
	/**
	 * sets the speed for subsequently made Circles
	 * @param speed the speed of travel (in deci-pixels per frame)
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * sets the collision radius for subsequently made Circles
	 * @param radius the collision radius (in pixels)
	 */
	public void setRadius(int radius) {
		if (radius <= 0) {
			throw new RuntimeException("radius given <= 0, EnemyCircle radius must be positive");
		}
		this.radius = radius;
	}
	
	/**
	 * @return a new Enemy instance based on the most recent parameter settings
	 */
	public void spawn() {
		MainLoop.add(new Circle(x, y, direction, speed, radius));
	}
}
