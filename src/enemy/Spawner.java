package enemy;

import overhead.MainLoop;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited Summer 2015
 */
public class Spawner {
	private int x = 0;
	private int y = 0;
	private int direction = 0;
	private int speed = 1;
	private int radius = 1;
	public enum Type {
		CIRCLE
	}
	private Type type;
	
	public Spawner(Type type) {
		this.type = type;
	}
	
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
		switch (type) {
		case CIRCLE :
			MainLoop.add(new Circle(x, y, direction, speed, radius));
		}
	}
}
