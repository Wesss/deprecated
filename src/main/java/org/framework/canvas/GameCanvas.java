package org.framework.canvas;

import org.framework.GameFramework;
import org.framework.interfaces.GameEventListener;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the display of the given game.
 */
@SuppressWarnings("serial")
public class GameCanvas extends Canvas {

    /*
     * TODO support various canvas sizes and resizing (keep track of scale)
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
    private GameEventListener gameEventListener;

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
    // Static Utils
    //////////////////////////////////////////////////

    public static Dimension getScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    //////////////////////////////////////////////////
    // Initialization
    //////////////////////////////////////////////////

    /**
     * Creates a new GameCanvas of specified dimension for given game g
     *
     * @param dimension the drawable area to be available for painting
     */
    public GameCanvas(Dimension dimension) {
        this(dimension.width, dimension.height);
    }
    
    /**
     * Creates a new GameCanvas of specified size for given game g
     *
     * @param width the width of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @param height the height of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     */
    public GameCanvas(int width, int height) {
        super();
        gameEventListener = GameFramework.EMPTY_GAME_LISTENER;

        actualX = width;
        actualY = height;
        virtualX = DEFAULT_VIRTUAL_X;
        virtualY = DEFAULT_VIRTUAL_Y;

        this.setPreferredSize(new Dimension(width, height));
        createFrame();
        setFocusable(true);
    }
    
    /**
     * Sets the references needed for this class to function
     * @param gameListener The Game this Panel displays
     */
    public void setReferences(GameEventListener gameListener) {
        gameEventListener = gameListener;

        addKeyListener(new GameCanvasKeyListener(gameListener ,GAME_LOCK));
        addMouseListener(new GameCanvasMousePressListener(this, gameListener, GAME_LOCK));
        addMouseMotionListener(new GameCanvasMouseMotionListener(this, gameListener, GAME_LOCK));
    }
    
    /**
     * Creates the JFrame (aka window border) for this Panel and
     * fits this Panel into it.
     */
    private void createFrame() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    //////////////////////////////////////////////////
    // Public API
    //////////////////////////////////////////////////

    /**
     * @return the width of the drawable canvas in pixels
     */
    public int getActualX() {
        return actualX;
    }

    /**
     * @return the height of the drawable canvas in pixels
     */
    public int getActualY() {
        return actualY;
    }

    /**
     * @return the x coordinate that the Graphics object will interpret as the rightmost edge of the canvas.
     */
    public int getVirtualX() {
        return virtualX;
    }

    /**
     * Set the x coordinate that the Graphics object will interpret the the rightmost edge of the canvas.
     * @param virtualX
     */
    public void setVirtualX(int virtualX) {
        this.virtualX = virtualX;
    }

    /**
     * @return the y coordinate that the Graphics object will interpret as the bottommost edge of the canvas.
     */
    public int getVirtualY() {
        return virtualY;
    }

    /**
     * Set the x coordinate that the Graphics object will interpret the the bottommost edge of the canvas.
     * @param virtualY
     */
    public void setVirtualY(int virtualY) {
        this.virtualY = virtualY;
    }

    //////////////////////////////////////////////////
    // Transformations
    //////////////////////////////////////////////////

    // TODO seperate into controller and model so that users dont have access to these

    private static int virtualToActualDimension(int metric, int actual, int virtual) {
        long product = ((long)metric) * ((long)actual);
        return (int)(product / virtual);
    }

    private static int actualToVirtualDimension(int metric, int actual, int virtual) {
        long product = ((long)metric) * ((long)virtual);
        return (int)(product / actual);
    }

    public int virtualToActualX(int x) {
        return virtualToActualDimension(x, getActualX(), getVirtualX());
    }

    public int virtualToActualY(int y) {
        return virtualToActualDimension(y, getActualY(), getVirtualY());
    }

    public int actualToVirtualX(int x) {
        return actualToVirtualDimension(x, getActualX(), getVirtualX());
    }

    public int actualToVirtualY(int y) {
        return actualToVirtualDimension(y, getActualY(), getVirtualY());
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
