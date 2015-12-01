package game;

/**
 * An instance of this class represents the positional data of
 * some movable object.
 * 
 * @author Wesley Cox
 * @last_edited 12/01/15
 */
public class Position {
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	/**
	 * A Position:
	 * 	has location (x, y) in pixels, where (0,0) is the top left pixel of the panel.
	 *	has a un-drawable displacement (x2, y2)	in deci-pixels, where (0, 0) stands for
	 *			no displacement.
	 * Having positions be negative/outside the panel is acceptable.
	 * 
	 * Representation Invariant:
	 * -5 <= x2 <= 5
	 * -5 <= y2 <= 5
	 */
	
	private int x, y;
	private int x2, y2;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	/**
	 * Sets the initial position of this obj
	 * @param x the initial x coordinate of this obj
	 * @param y the initial y coordinate of this obj
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		x2 = 0;
		y2 = 0;
	}
	
	//////////////////////////////////////////////////
	// Instance Information
	//////////////////////////////////////////////////
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	//////////////////////////////////////////////////
	// Manipulation
	//////////////////////////////////////////////////
	
	/**
	 * Displace the player's position in deci-pixels
	 * @param x the horizontal displacement in deci-pixels
	 * @param y the vertical displacement in deci-pixels
	 */
	public void move(int dx, int dy) {
		this.x += dx / 10;
		this.y += dy / 10;
		
		x2 += dx % 10;
		y2 += dy % 10;	
		
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
	
	/**
	 * Sets the player's position to the given coordinates
	 * @param x the new x coordinate (in pixels)
	 * @param y the new y coordinate (in pixels)
	 */
	public void snap(int x, int y) {
		this.x = x;
		this.y = y;
		x2 = 0;
		y2 = 0;
	}
}
