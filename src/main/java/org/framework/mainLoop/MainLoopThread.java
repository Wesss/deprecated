package org.framework.mainLoop;

import org.framework.canvas.GameCanvasModel;
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

    private GameCanvasModel canvas;
    private MainLoopModel mainLoopModel;
    private int waitTime;

    /**
     * @param fps the desired refresh rate of the screen measured in frames per second
     */
    public MainLoopThread(MainLoopModel mainLoopModel, int fps) {
        this.mainLoopModel = mainLoopModel;
        waitTime = 1000 / fps;
    }

    protected void setReferences(GameCanvasModel canvas) {
        this.canvas = canvas;
    }

    @Override
    public void run() {
        canvas.createBufferStrategy(DOUBLE_BUFFERED);
        BufferStrategy strategy = canvas.getBufferStrategy();
        while (!Thread.interrupted()) {
            Graphics graphics = null;
            try {
                graphics = strategy.getDrawGraphics();
                clearCanvas(graphics, canvas);
                mainLoopModel.nextFrame(new GameCanvasGraphics(graphics, canvas));
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

    private static void clearCanvas(Graphics graphics, GameCanvasModel canvas) {
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
