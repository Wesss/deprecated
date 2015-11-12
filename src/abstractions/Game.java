package abstractions;

import overhead.GamePanel;

/**
 * This represents the Game component that handles all of the data unique to the game itself
 * 
 * @author Wesley Cox
 * @last_edited 11/10/15
 */
public abstract class Game {
	public static int PANEL_X;
	public static int PANEL_Y;
	
	/**
	 * DO NOT USE THE CONSTRUCTOR TO INITIALIZE ANYTHING. USE initialize METHOD OTHERWISE RACE CONDITIONS WILL OCCUR
	 * @param x width of the game panel
	 * @param y height of the game panel
	 */
	public Game(int x, int y) {
		PANEL_X = x;
		PANEL_Y = y;
		initiallize();
		@SuppressWarnings("unused")
		GamePanel gp = new GamePanel(this, x, y);
	}
	
	/**
	 * This method will be called before the draw/event main loop is started
	 * DO NOT USE THE CONSTRUCTOR TO INITIALIZE ANYTHING. USE THIS METHOD OTHERWISE RACE CONDITIONS WILL OCCUR
	 */
	public abstract void initiallize();
	/**
	 * Fires appropriate commands when the provided key (on the keyboard) is initially pressed.
	 * Specific Keys can be found using KeyEvent.getKeyText(key) and can be more efficiently compared
	 * with KeyEvent.(key constant). ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
	 * 
	 * @param key the unicode character of the key being pressed.
	 */
	public abstract void keyPressed(int keyCode);
	
	/**
	 * Fires appropriate commands when the provided key (on the keyboard) is released.
	 * Specific Keys can be found using KeyEvent.getKeyText(key) and can be more efficiently compared
	 * with KeyEvent.(key constant). ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
	 * 
	 * @param key the unicode character of the key being pressed.
	 */
	public abstract void keyReleased(int keyCode);
	
	/**
	 * Fires appropriate commands when provided mouse button is initially pushed down
	 * 
	 * @param x x coordinate of the press on the overall Panel
	 * @param y y coordinate of the press on the overall Panel
	 * @param button an int representing which mouse button was pressed.
	 * 		//1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons
	 */
	public abstract void mousePressed(int x, int y, int button);
	
	/**
	 * Fires appropriate commands when provided mouse button is released
	 * 
	 * @param x x coordinate of the press on the overall Panel
	 * @param y y coordinate of the press on the overall Panel
	 * @param button an int representing which mouse button was pressed.
	 * 		//1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons
	 */
	public abstract void mouseReleased(int x, int y, int button);
	
	/**
	 * Fires appropriate commands when provided mouse is moved to a new
	 * position
	 * 
	 * @param x x coordinate of the mouse's current position on the overall Panel
	 * @param y y coordinate of the mouse's current position on the overall Panel
	 */
	public abstract void mouseMoved(int x, int y);
	
}
