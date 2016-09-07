package org.framework.canvas;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;

/**
 * The GameCanvasFactory is responsible for allocating the GameCanvasModel
 */
public class GameCanvasFactory {

    // TODO get rid of pair return type

    /**
     * Creates a new GameCanvasController of specified size and adds it to the given frame.
     *
     * @param frame the frame that will hold the created canvas
     * @param width the width of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @param height the height of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @throws IllegalArgumentException if width or height <= 0 or frame is null
     */
    public static Pair<GameCanvasController, GameCanvasModel> createCanvas(Frame frame, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("GameCanvasModel dimensions must be greater than 0");
        }
        if (frame == null) {
            throw new IllegalArgumentException("Frame must not be null");
        }

        GameCanvasModel canvasModel = new GameCanvasModel(width, height, frame);
        GameCanvasController canvas = new GameCanvasController(canvasModel);
        canvasModel.setPreferredSize(new Dimension(width, height));

        frame.add(canvasModel);
        frame.pack();
        frame.setVisible(true);
        canvasModel.setFocusable(true);

        return new Pair<>(canvas, canvasModel);
    }

    /**
     * @return An emtpy Frame which to hold the GameCanvasModel
     */
    public static Frame createFrame() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return frame;
    }
}
