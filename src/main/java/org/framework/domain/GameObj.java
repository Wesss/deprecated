package org.framework.domain;

import org.framework.canvas.GameCanvasGraphics;

/**
 * This class represents an object of the game that can be displayed
 */
public interface GameObj {

    public static final GameObj EMTPY_GAME_OBJ = new EmptyGameObj();

    /**
     * Updates obj state after a single interval of game time (one frame)
     * This is called directly after each frame is drawn and before the next frame's event input is processed;
     */
    public void update();

    /**
     * draws this object onto the given graphics object
     *
     * @param g The graphics object to paint to
     */
    public void paint(GameCanvasGraphics g);

    static class EmptyGameObj implements GameObj {

        @Override
        public void update() {
        }

        @Override
        public void paint(GameCanvasGraphics g) {
        }
    }
}
