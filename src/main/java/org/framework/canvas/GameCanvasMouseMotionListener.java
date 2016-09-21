package org.framework.canvas;

import org.framework.domain.GameEventListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameCanvasMouseMotionListener implements MouseMotionListener{

    private final Object GAME_LOCK;
    private GameEventListener gameEventListener;
    private GameCanvasModel canvas;

    public GameCanvasMouseMotionListener(GameCanvasModel canvas, GameEventListener gameEventListener, Object lock) {
        this.canvas = canvas;
        this.gameEventListener = gameEventListener;
        GAME_LOCK = lock;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMovedTo(canvas.actualToVirtualX(e.getX()),
                canvas.actualToVirtualY(e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMovedTo(canvas.actualToVirtualX(e.getX()),
                canvas.actualToVirtualY(e.getY()));
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
