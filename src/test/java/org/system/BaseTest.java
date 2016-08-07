package org.system;

import java.awt.Color;
import java.awt.Graphics;

import org.framework.GameFramework;
import org.framework.GamePanelGraphics;
import org.framework.MainLoop;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameObj;

/**
 * Test for Panel/Window operations
 * (Move, Minimize, Close, Display)
 * 
 * @author Wesley Cox
 */
public class BaseTest implements Game {

    private static final int FPS = 60;

    public static void main(String args[]) {
        GameFramework.startGame(BaseTest.class, GameFramework.EMPTY_GAME_LISTENER, FPS);
    }

    public BaseTest(MainLoop mainLoop) {
        mainLoop.add(new Line());
    }

    private class Line implements GameObj {

        @Override
        public void paint(GamePanelGraphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(100, 100, 400, 400);
        }

        @Override
        public void update() {}
    }
}
