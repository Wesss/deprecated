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
     * @param width the width of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @param height the height of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @throws IllegalArgumentException if width or height <= 0
     */
    public static GameCanvas createCanvas(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("GameCanvas dimensions must be greater than 0");
        }
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameCanvas canvas = new GameCanvas(width, height, frame);
        canvas.setPreferredSize(new Dimension(width, height));

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        canvas.setFocusable(true);

        return canvas;
    }
}
