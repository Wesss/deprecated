package org.framework.domain;


/**
 * This represents an event listener that handles player input
 * 
 * @author Wesley Cox
 */
public interface GameEventListener {

    GameEventListener EMPTY_GAME_LISTENER = new EmptyGameListener();

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

    class EmptyGameListener implements GameEventListener {
        public void keyPressed(int keyCode) {}
        public void keyReleased(int keyCode) {}
        public void mousePressed(int x, int y, int button) {}
        public void mouseReleased(int x, int y, int button) {}
        public void mouseMoved(int x, int y) {}
        public void acceptGame(Game game) {}
    }
}
