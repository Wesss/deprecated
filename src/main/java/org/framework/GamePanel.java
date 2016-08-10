package org.framework;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import org.framework.interfaces.GameEventListener;

/**
 * Handles the display of the given game.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    /*
     * TODO try/compare instead of letting java's mainframe handle repaint requests, keep hold of graphics object and redraw in a self-defined loop
     * TODO support various panel sizes and resizing (keep track of scale)
     * TODO apply virtual transformation to listeners
     * TODO document virtual transformation
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
    private MainLoop mainLoop;

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
     * Creates a new GamePanel of specified dimension for given game g
     *
     * @param dimension the drawable area to be available for painting
     */
    protected GamePanel(Dimension dimension) {
        this(dimension.width, dimension.height);
    }
    
    /**
     * Creates a new GamePanel of specified size for given game g
     *
     * @param width the width of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @param height the height of the drawable area to be available for painting (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     */
    protected GamePanel(int width, int height) {
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
     * @param g The Game this Panel displays
     * @param m The MainLoop powering the Game
     */
    protected void setReferences(GameEventListener g, MainLoop m) {
        gameEventListener = g;
        mainLoop = m;

        addKeyListener(new GamePanelKeyListener(g ,GAME_LOCK));
        addMouseListener(new GamePanelMousePressListener(g, GAME_LOCK));
        addMouseMotionListener(new GamePanelMouseMotionListener(g, GAME_LOCK));
    }
    
    /**
     * Creates the JFrame (aka window border) for this Panel and
     * fits this Panel into it.
     */
    private void createFrame() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
    }

    //////////////////////////////////////////////////
    // Public API
    //////////////////////////////////////////////////

    /**
     * @return the width of the drawable panel in pixels
     */
    public int getActualX() {
        return actualX;
    }

    /**
     * @return the height of the drawable panel in pixels
     */
    public int getActualY() {
        return actualY;
    }

    /**
     * @return the x coordinate that the GamePanelGraphics object will interpret as the rightmost edge of the panel
     */
    public int getVirtualX() {
        return virtualX;
    }

    /**
     * TODO
     * @param virtualX
     */
    public void setVirtualX(int virtualX) {
        this.virtualX = virtualX;
    }

    /**
     * TODO
     * @return the y ooordinate that the GamePanelGraphics object will interpret as the rightmost edge of the panel
     */
    public int getVirtualY() {
        return virtualY;
    }

    /**
     * TODO
     * @param virtualY
     */
    public void setVirtualY(int virtualY) {
        this.virtualY = virtualY;
    }

    //////////////////////////////////////////////////
    // Framework Functionality
    //////////////////////////////////////////////////

    @Override
    public void paintComponent(Graphics g) {
        if (mainLoop != null)
            synchronized (GAME_LOCK) {
                super.paintComponent(g);
                mainLoop.nextFrame(new GamePanelGraphics(g, this));
            }
    }
}
