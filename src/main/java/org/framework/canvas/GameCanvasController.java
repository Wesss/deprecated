package org.framework.canvas;

/**
 * TODO
 */
public class GameCanvasController {

    private GameCanvasModel canvasModel;

    protected GameCanvasController(GameCanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    /**
     * @return the width of the drawable canvas in pixels
     */
    public int getActualX() {
        return  canvasModel.getActualX();
    }

    /**
     * @return the height of the drawable canvas in pixels
     */
    public int getActualY() {
        return canvasModel.getActualY();
    }

    /**
     * @return the x coordinate that the Graphics object will interpret as the rightmost edge of the canvas.
     */
    public int getVirtualX() {
        return canvasModel.getVirtualX();
    }

    /**
     * Set the x coordinate that the Graphics object will interpret the the rightmost edge of the canvas.
     * @param virtualX
     */
    public void setVirtualX(int virtualX) {
        canvasModel.setVirtualX(virtualX);
    }

    /**
     * @return the y coordinate that the Graphics object will interpret as the bottommost edge of the canvas.
     */
    public int getVirtualY() {
        return canvasModel.getVirtualY();
    }

    /**
     * Set the x coordinate that the Graphics object will interpret the the bottommost edge of the canvas.
     * @param virtualY
     */
    public void setVirtualY(int virtualY) {
        canvasModel.setVirtualY(virtualY);
    }
}
