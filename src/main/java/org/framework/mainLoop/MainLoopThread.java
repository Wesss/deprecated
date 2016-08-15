package org.framework.mainLoop;

import org.framework.panel.GamePanel;
import org.framework.panel.GamePanelGraphics;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * This Thread fires an update and a screen refresh to the game based on
 * its desired frames per seconds
 */
public class MainLoopThread implements Runnable {

    //TODO fix timing code
    //TODO rename each component more appropriately

    private GamePanel panel;
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

    protected void setReferences(GamePanel panel) {
        this.panel = panel;
        panel.createBufferStrategy(2);
        this.strategy = panel.getBufferStrategy();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Graphics graphics = null;
            try {
                graphics = strategy.getDrawGraphics();
                graphics.clearRect(0, 0, panel.getWidth(), panel.getHeight());
                mainLoop.nextFrame(new GamePanelGraphics(graphics, panel));
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
}
