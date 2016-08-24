package org.framework.canvas;

import javax.swing.*;
import java.awt.*;

/**
 * The GameCanvasFactory is responsible for allocating the GameCanvas
 */
public class GameCanvasFactory {

    /**
     * Creates a new GameCanvas of specified size for given game g
     *
     * @param frame the frame that will hold the created canvas
     * @param width the width of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @param height the height of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @throws IllegalArgumentException if width or height <= 0 or frame is null
     */
    public static GameCanvas createCanvas(Frame frame, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("GameCanvas dimensions must be greater than 0");
        }
        if (frame == null) {
            throw new IllegalArgumentException("Frame must not be null");
        }

        GameCanvas canvas = new GameCanvas(width, height, frame);
        canvas.setPreferredSize(new Dimension(width, height));

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        canvas.setFocusable(true);

        return canvas;
    }

    /**
     * @return An emtpy Frame which to hold the GameCanvas
     */
    public static Frame createFrame() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return frame;
    }
}
