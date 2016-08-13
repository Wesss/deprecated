package org.framework.panel;

import org.framework.interfaces.GameEventListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class passes mouse press, release, and movement events to the game
 */
public class GamePanelMousePressListener implements MouseListener {

    private final Object GAME_LOCK;
    private GameEventListener<?> gameEventListener;
    private GamePanel panel;

    public GamePanelMousePressListener(GamePanel panel, GameEventListener<?> gameEventListener, Object lock) {
        this.panel = panel;
        this.gameEventListener = gameEventListener;
        GAME_LOCK = lock;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (GAME_LOCK) {
            gameEventListener.mousePressed(panel.actualToVirtualX(e.getX()),
                    panel.actualToVirtualY(e.getY()),
                    e.getButton());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (GAME_LOCK) {
            gameEventListener.mouseReleased(panel.actualToVirtualX(e.getX()),
                    panel.actualToVirtualY(e.getY()),
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