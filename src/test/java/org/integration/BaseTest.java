package org.integration;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.Game;
import org.framework.domain.GameObj;
import org.framework.mainLoop.MainLoopController;

import java.awt.*;

/**
 * Test for Panel/Window operations
 * (Move, Minimize, Close, Display)
 * 
 * @author Wesley Cox
 */
public class BaseTest implements Game {

    private static final int FPS = 60;

    public static void main(String args[]) throws InstantiationException {
        GameFramework.startGame(BaseTest.class, GameFramework.EMPTY_GAME_LISTENER, FPS);
    }

    public BaseTest(MainLoopController mainLoop, GameCanvasController canvas) {
        mainLoop.add(new Line());
    }

    private class Line implements GameObj {

        @Override
        public void paint(GameCanvasGraphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(100, 100, 400, 400);
        }

        @Override
        public void update() {}
    }
}
