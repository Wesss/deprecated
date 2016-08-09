package org.framework;

import org.framework.interfaces.GameEventListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GamePanelMouseMotionListener implements MouseMotionListener{

    private final Object GAME_LOCK;
    private GameEventListener<?> gameEventListener;

    public GamePanelMouseMotionListener(GameEventListener<?> gameEventListener, Object lock) {
        this.gameEventListener = gameEventListener;
        GAME_LOCK = lock;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMovedTo(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMovedTo(e.getX(), e.getY());
    }

    /**
     * Represents an event where the mouse is simply moved, regardless of
     * whether or not the mouse is pressed.
     *
     * @param x coordinate of the current mouse position
     * @param y coordinate of the current mouse position
     */
    private void mouseMovedTo(int x, int y) {
        synchronized (GAME_LOCK) {
            gameEventListener.mouseMoved(x, y);
        }
    }
}
