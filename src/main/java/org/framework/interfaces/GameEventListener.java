package org.framework.interfaces;


/**
 * This represents an event listener that handles player input
 * 
 * @author Wesley Cox
 */
public interface GameEventListener {
    
    /**
     * Passes in an instantiated game (to enable callbacks).<br>
     * This is called before any events are fired.
     *
     * @param game a newly instantiated game whose class was passed into GameFramework.gamestart()
     * TODO for now client is forced to cast to their specific game type
     */
    public void acceptGame(Game game);

    /**
     * Fires appropriate commands when the provided key (on the keyboard)
     * is initially pressed.
     * Specific Keys can be found using KeyEvent.getKeyText(key) and can
     * be more efficiently compared with KeyEvent.(key constant).
     * ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
     *
     * @param keyCode the unicode character of the key being pressed.
     */
    public void keyPressed(int keyCode);

    /**
     * Fires appropriate commands when the provided key (on the keyboard) is released.
     * Specific Keys can be found using KeyEvent.getKeyText(key) and can
     * be more efficiently compared with KeyEvent.(key constant).
     * ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
     *
     * @param keyCode the unicode character of the key being pressed.
     */
    public void keyReleased(int keyCode);

    /**
     * Fires appropriate commands when provided mouse button is initially pushed down
     *
     * @param x coordinate of the press on the overall Panel
     * @param y coordinate of the press on the overall Panel
     * @param button an int representing which mouse button was pressed.
     * 		<UL><LI> 1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons </UL>
     */
    public void mousePressed(int x, int y, int button);

    /**
     * Fires appropriate commands when provided mouse button is released
     *
     * @param x coordinate of the press on the overall Panel
     * @param y coordinate of the press on the overall Panel
     * @param button an int representing which mouse button was pressed.
     * 		<UL><LI> 1 - left, 2 - middle, 3 - right, 4+ fancy mouse buttons </UL>
     */
    public void mouseReleased(int x, int y, int button);

    /**
     * Fires appropriate commands when provided mouse is moved to a new
     * position
     *
     * @param x coordinate of the mouse's current position on the overall Panel
     * @param y coordinate of the mouse's current position on the overall Panel
     */
    public void mouseMoved(int x, int y);

}
