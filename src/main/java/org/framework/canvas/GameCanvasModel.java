package org.framework.canvas;

import org.framework.domain.GameEventListener;

import java.awt.*;

/**
 * Handles the display of the given game.
 */
@SuppressWarnings("serial")
public class GameCanvasModel extends Canvas {

    /*
     * TODO support various canvas resizing
     */

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

    public static final int DEFAULT_VIRTUAL_X = 1000;
    public static final int DEFAULT_VIRTUAL_Y = 1000;

    /*
     * After creation, but before setReferences:
     * game == EmptyGame
     * mainLoop == null
     * Even as user events and repaint requests come in, they will be ignored until setReferences is called.
     *
     * After setReferences:
     * game and mainLoop are set to appropriately initialized overhead objects
     */

    private final Object GAME_LOCK = new Object();

    /*
     * actualX > 0
     * actualY > 0
     * virtualX > 0
     * virtualY > 0
     */
    private int actualX;
    private int actualY;
    private int virtualX;
    private int virtualY;

    //////////////////////////////////////////////////
    // Initialization
    //////////////////////////////////////////////////

    /**
     * Creates a new GameCanvasModel
     *
     * @param width the width of the canvas
     * @param height the height of the canvas
     * @param frame the frame in which this canvas is to reside.
     *              <UL><LI> must not already contain a Component </UL>
     */
    protected GameCanvasModel(int width, int height, Frame frame) {
        super();

        actualX = width;
        actualY = height;
        virtualX = DEFAULT_VIRTUAL_X;
        virtualY = DEFAULT_VIRTUAL_Y;
    }
    
    /**
     * Sets the references needed for this class to function
     * @param gameListener The Game this Panel displays
     */
    public void setReferences(GameEventListener gameListener) {
        addKeyListener(new GameCanvasKeyListener(gameListener ,GAME_LOCK));
        addMouseListener(new GameCanvasMousePressListener(this, gameListener, GAME_LOCK));
        addMouseMotionListener(new GameCanvasMouseMotionListener(this, gameListener, GAME_LOCK));
    }

    //////////////////////////////////////////////////
    // Getters/Setters
    //////////////////////////////////////////////////

    /**
     * @return the width of the drawable canvas in pixels
     */
    protected int getActualX() {
        return actualX;
    }

    /**
     * @return the height of the drawable canvas in pixels
     */
    protected int getActualY() {
        return actualY;
    }

    /**
     * @return the x coordinate that the Graphics object will interpret as the rightmost edge of the canvas.
     */
    protected int getVirtualX() {
        return virtualX;
    }

    /**
     * Set the x coordinate that the Graphics object will interpret the the rightmost edge of the canvas.
     * @param virtualX
     */
    protected void setVirtualX(int virtualX) {
        this.virtualX = virtualX;
    }

    /**
     * @return the y coordinate that the Graphics object will interpret as the bottommost edge of the canvas.
     */
    protected int getVirtualY() {
        return virtualY;
    }

    /**
     * Set the x coordinate that the Graphics object will interpret the the bottommost edge of the canvas.
     * @param virtualY
     */
    protected void setVirtualY(int virtualY) {
        this.virtualY = virtualY;
    }

    //////////////////////////////////////////////////
    // Transformations
    //////////////////////////////////////////////////

    private static int virtualToActualDimension(int metric, int actual, int virtual) {
        long product = ((long)metric) * ((long)actual);
        return (int)(product / virtual);
    }

    private static int actualToVirtualDimension(int metric, int actual, int virtual) {
        long product = ((long)metric) * ((long)virtual);
        return (int)(product / actual);
    }

    public int virtualToActualX(int x) {
        return virtualToActualDimension(x, actualX, virtualX);
    }

    public int virtualToActualY(int y) {
        return virtualToActualDimension(y, actualY, virtualY);
    }

    public int actualToVirtualX(int x) {
        return actualToVirtualDimension(x, actualX, virtualX);
    }

    public int actualToVirtualY(int y) {
        return actualToVirtualDimension(y, actualY, virtualY);
    }

    public int[] virtualToActualX(int[] metrics) {
        int[] result = new int[metrics.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = virtualToActualX(metrics[i]);
        }
        return result;
    }

    public int[] virtualToActualY(int[] metrics) {
        int[] result = new int[metrics.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = virtualToActualY(metrics[i]);
        }
        return result;
    }

    public int[] actualToVirtualX(int[] metrics) {
        int[] result = new int[metrics.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = actualToVirtualX(metrics[i]);
        }
        return result;
    }

    public int[] actualToVirtualY(int[] metrics) {
        int[] result = new int[metrics.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = actualToVirtualY(metrics[i]);
        }
        return result;
    }
}
