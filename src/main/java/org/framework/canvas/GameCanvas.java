package org.framework.canvas;

public class GameCanvas {
    private GameCanvasController controller;
    private GameCanvasModel model;

    protected GameCanvas(GameCanvasController controller, GameCanvasModel model) {
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
        // TODO
    }
}
