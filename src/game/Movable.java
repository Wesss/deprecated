package game;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited Summer 2015
 */
public abstract class Movable {
	protected int x, y; //the coordinates of the object on the panel
	protected int x2, y2; //decipixel offset
	
	/**
	 * Sets the initial position of this obj
	 * @param x the initial x coordinate of this obj
	 * @param y the initial y coordinate of this obj
	 */
	public Movable(int x, int y) {
		this.x = x;
		this.y = y;
		x2 = 0;
		y2 = 0;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	/**
	 * Displace the player's position
	 * @param x the horizontal displacement in deci-pixels
	 * @param y the vertical displacement in deci-pixels
	 */
	public void move(int x, int y) {
		this.x += x / 10;
		this.y += y / 10;
		
		x2 += x % 10;
		y2 += y % 10;	
		
		if (x2 > 5) {
			x2 -= 10;
			this.x++;
		} else if (x2 < -5) {
			x2 += 10;
			this.x--;
		}
		if (y2 > 5) {
			y2 -= 10;
			this.y++;
		} else if (y2 < -5) {
			y2 += 10;
			this.y--;
		}
	}
}
