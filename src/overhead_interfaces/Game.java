package overhead_interfaces;


/**
 * This represents a Game that handles all of the data unique to the game itself
 * 
 * @author Wesley Cox
 * @last_edited 11/23/15
 */
public interface Game {
	
	/**
	 * DO NOT USE THE CONSTRUCTOR TO INITIALIZE ANYTHING. USE THIS METHOD
	 * OTHERWISE RACE CONDITIONS WILL OCCUR
	 * <p>
	 * This method will be called once before the game is started
	 */
	public void initiallize();
	
	/**
	 * Fires appropriate commands when the provided key (on the keyboard)
	 * is initially pressed.
	 * Specific Keys can be found using KeyEvent.getKeyText(key) and can
	 * be more efficiently compared with KeyEvent.(key constant).
	 * ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
	 * 
	 * @param key the unicode character of the key being pressed.
	 */
	public void keyPressed(int keyCode);
	
	/**
	 * Fires appropriate commands when the provided key (on the keyboard) is released.
	 * Specific Keys can be found using KeyEvent.getKeyText(key) and can
	 * be more efficiently compared with KeyEvent.(key constant).
	 * ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
	 * 
	 * @param key the unicode character of the key being pressed.
	 */
	public void keyReleased(int keyCode);
	
	/**
	 * Fires appropriate commands when provided mouse button is initially pushed down
	 * 
	 * @param x x coordinate of the press on the overall Panel
	 * @param y y coordinate of the press on the overall Panel
	 * @param button an int representing which mouse button was pressed.
	 * 		//1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons
	 */
	public void mousePressed(int x, int y, int button);
	
	/**
	 * Fires appropriate commands when provided mouse button is released
	 * 
	 * @param x x coordinate of the press on the overall Panel
	 * @param y y coordinate of the press on the overall Panel
	 * @param button an int representing which mouse button was pressed.
	 * 		//1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons
	 */
	public void mouseReleased(int x, int y, int button);
	
	/**
	 * Fires appropriate commands when provided mouse is moved to a new
	 * position
	 * 
	 * @param x x coordinate of the mouse's current position on the overall Panel
	 * @param y y coordinate of the mouse's current position on the overall Panel
	 */
	public void mouseMoved(int x, int y);
	
}
