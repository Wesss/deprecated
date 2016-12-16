package org.wesss.game_utils;

import org.wesss.game_framework.domain.GameEventListener;

/**
 * This represents a grid on the canvas, where clicking on a tile will fire its corresponding event listener
 */
public class ClickGrid implements GameEventListener {

    //TODO maybe figure out a clean way of simply forwarding events through other game event listeners?

    private int canvasX;
    private int canvasY;
    private int canvasWidth;
    private int canvasHeight;
    private int HorizontalTiles;
    private int verticalTiles;

    @Override
    public void keyPressed(int keyCode) {

    }

    @Override
    public void keyReleased(int keyCode) {

    }

    @Override
    public void mousePressed(int x, int y, int button) {

    }

    @Override
    public void mouseReleased(int x, int y, int button) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }
}
