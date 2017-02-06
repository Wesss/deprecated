package org.wesss.game_utils;

import org.wesss.game_framework.domain.GameEventListener;
import org.wesss.game_framework.domain.event.GameEvent;

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
    public void processGameEvent(GameEvent event) {

    }
}
