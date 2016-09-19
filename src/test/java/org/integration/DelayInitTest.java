package org.integration;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameObj;
import org.framework.mainLoop.MainLoopController;

import java.awt.*;

/**
 * Test for Long initialization times
 * This test computers a large sum while initializing the game.
 * The Player should not be able to work the canvas/game into an incorrect state
 * 
 * @author Wesley Cox
 */
public class DelayInitTest implements Game{

    private static final int FPS = 60;

    public static void main(String args[]) throws InstantiationException {
        GameFramework.startGame(DelayInitTest.class, GameFramework.EMPTY_GAME_LISTENER, FPS);
    }

    public DelayInitTest(MainLoopController mainLoop, GameCanvasController canvas) {
        int sum = 0;
        for (int i = 0; i < 100000; i ++) {
            for (int j = 0; j < 100000; j++) {
                sum += j;
            }
        }
        mainLoop.add(new Num(sum));
    }

    private class Num implements GameObj {

        private int num;

        public Num(int num) {
            this.num = num;
        }

        @Override
        public void paint(GameCanvasGraphics g) {
            g.setColor(Color.BLACK);
            g.drawString("" + num, 100, 100);
        }

        @Override
        public void update() {}
    }
}
