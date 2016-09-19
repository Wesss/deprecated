package org.framework.canvas;

import java.awt.*;
import java.awt.event.WindowEvent;

public class GameCanvas {
    private Frame frame;
    private GameCanvasController controller;
    private GameCanvasModel model;

    protected GameCanvas(Frame frame, GameCanvasController controller, GameCanvasModel model) {
        this.frame = frame;
        this.controller = controller;
        this.model = model;
    }

    public GameCanvasController getController() {
        return controller;
    }

    public GameCanvasModel getModel() {
        return model;
    }

    public void exit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
