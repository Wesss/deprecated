package org.framework.mainLoop;

import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasGraphics;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * This Thread fires an update and a screen refresh to the game based on
 * its desired frames per seconds
 */
public class MainLoopThread implements Runnable {
    private static final int DOUBLE_BUFFERED = 2;

    //TODO fix timing code
    //TODO rename each component more appropriately

    private GameCanvas canvas;
    private BufferStrategy strategy;
    private MainLoop mainLoop;
    private int waitTime;

    /**
     * @param fps the desired refresh rate of the screen measured in frames per second
     */
    public MainLoopThread(MainLoop mainloop, int fps) {
        this.mainLoop = mainloop;
        waitTime = 1000 / fps;
    }

    protected void setReferences(GameCanvas canvas) {
        this.canvas = canvas;
        canvas.createBufferStrategy(DOUBLE_BUFFERED);
        this.strategy = canvas.getBufferStrategy();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Graphics graphics = null;
            try {
                graphics = strategy.getDrawGraphics();
                clearCanvas(graphics, canvas);
                mainLoop.nextFrame(new GameCanvasGraphics(graphics, canvas));
                if( !strategy.contentsLost() )
                    strategy.show();

                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace(); //TODO error more gracefully
            } finally {
                if( graphics != null )
                    graphics.dispose();
            }
        }
    }

    private static void clearCanvas(Graphics graphics, GameCanvas canvas) {
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
