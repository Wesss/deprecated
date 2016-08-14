package org.framework.mainLoop;

import org.framework.panel.GamePanel;

/**
 * This Thread fires an update and a screen refresh to the game based on
 * its desired frames per seconds
 */
public class LoopThread implements Runnable {

    private GamePanel panel;
    private int waitTime;

    /**
     * @param fps the desired refresh rate of the screen measured in frames per second
     */
    public LoopThread(int fps) {
        waitTime = 1000 / fps;
    }

    protected void setReferences(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //call paintComponent();
                //	Timing code should envelope the paintComponent code
                long startTime = System.currentTimeMillis();
                panel.repaint();
                long endTime = System.currentTimeMillis();

                if (startTime - endTime < waitTime) {
                    Thread.sleep(waitTime - (startTime - endTime));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
