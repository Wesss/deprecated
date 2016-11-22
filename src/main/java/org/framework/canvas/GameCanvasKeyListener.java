package org.framework.canvas;

import org.framework.domain.GameEventListener;
import org.framework.domain.event.KeyPressedEvent;
import org.framework.domain.event.KeyReleasedEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * This class passes key press/release events to the game
 * <p>
 * Key event are filtered such that only when a key is pressed
 * will keyPressed() be fired (instead of multiple firings when
 * a key is held down).
 */
public class GameCanvasKeyListener implements KeyListener {

    private final Object GAME_LOCK;
    private GameEventListener gameEventListener;
    private Set<Integer> pressedKeys;

    public GameCanvasKeyListener(GameEventListener gameEventListener, Object lock) {
        this.gameEventListener = gameEventListener;
        GAME_LOCK = lock;
        pressedKeys = new HashSet<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        synchronized (GAME_LOCK) {
            if (!pressedKeys.contains(code)) {
                pressedKeys.add(code);
                gameEventListener.processGameEvent(
                        //TODO put all event creation into a factory?
                        new KeyPressedEvent(
                                e.getKeyCode()
                        )
                );
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        synchronized (GAME_LOCK) {
            if (pressedKeys.contains(code)) {
                pressedKeys.remove(code);
                gameEventListener.processGameEvent(
                        new KeyReleasedEvent(
                                e.getKeyCode()
                        )
                );
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}