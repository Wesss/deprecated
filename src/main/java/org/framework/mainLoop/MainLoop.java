package org.framework.mainLoop;

import org.framework.canvas.GameCanvasModel;

public class MainLoop {

    public static final String PRE_INIT_ERRMSG = "This Object has not been fully initiallized yet";

    private MainLoopModel model;
    private MainLoopController controller;
    private MainLoopRunnableTarget target;
    private Thread mainLoopThread;

    protected MainLoop(MainLoopModel model,
                       MainLoopController controller,
                       MainLoopRunnableTarget target) {
        this.model = model;
        this.controller = controller;
        this.target = target;
        this.mainLoopThread = new Thread(target);
    }

    /**
     * Sets up proper references needed to run the MainLoopController
     *
     * @param canvasModel the GameCanvasModel displaying the game
     */
    public void setReferences(GameCanvasModel canvasModel) {
        target.setReferences(canvasModel);
    }

    /**
     * Starts the update/repaint cycle
     *
     * @required setReferences be called before this
     */
    public void start() {
        mainLoopThread.start();
    }

    protected void exit() {
        mainLoopThread.interrupt();
    }

    public MainLoopModel getModel() {
        return model;
    }

    public MainLoopController getController() {
        return controller;
    }
}
