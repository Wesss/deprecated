package org.framework.canvas;

import org.framework.interfaces.GameEventListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class passes mouse press, release, and movement events to the game
 */
public class GameCanvasMousePressListener implements MouseListener {

    private final Object GAME_LOCK;
    private GameEventListener<?> gameEventListener;
    private GameCanvas canvas;

    public GameCanvasMousePressListener(GameCanvas canvas, GameEventListener<?> gameEventListener, Object lock) {
        this.canvas = canvas;
        this.gameEventListener = gameEventListener;
        GAME_LOCK = lock;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (GAME_LOCK) {
            gameEventListener.mousePressed(canvas.actualToVirtualX(e.getX()),
                    canvas.actualToVirtualY(e.getY()),
                    e.getButton());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (GAME_LOCK) {
            gameEventListener.mouseReleased(canvas.actualToVirtualX(e.getX()),
                    canvas.actualToVirtualY(e.getY()),
                    e.getButton());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}