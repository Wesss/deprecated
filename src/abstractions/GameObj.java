package abstractions;

import java.awt.Graphics;

/**
 * This class represents an object of the game that can be displayed
 * 
 * @author Wesley
 *
 */
public interface GameObj {
	
	/**
	 * Updates obj state after a single interval of game time (one frame)
	 * This is called directly after each frame is drawn and before the next frame's event input is processed;
	 */
	public void update();
	
	/**
	 * draws this object onto the given graphics object
	 * @param g The graphics object to draw to
	 */
	public void draw(Graphics g);
}
