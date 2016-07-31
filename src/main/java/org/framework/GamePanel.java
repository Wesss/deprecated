package org.framework;
import java.awt.Dimension;
import java.awt.Graphics;
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
     */

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

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
    // Initialization
    //////////////////////////////////////////////////
    
    /**
     * Creates a new GamePanel of specified size for given game g
     *
     * @param width the width of the display's panel (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     * @param height the height of the display's panel (in pixels)
     * 		<UL><LI> must be > 0 </UL>
     */
    protected GamePanel(int width, int height) {
        super();
        gameEventListener = GameFramework.EMPTY_GAME_LISTENER;

        // TODO change to wire in actual transformations
        actualX = width;
        actualY = height;
        virtualX = 500;
        virtualY = 500;

        this.setPreferredSize(new Dimension(width, height));
        createFrame();
        setFocusable(true);

        addKeyListener(new KeyLis());
        addMouseListener(new MousePressLis());
        addMouseMotionListener(new MouseMoveLis());
    }
    
    /**
     * Sets the references needed for this class to function
     * @param g The Game this Panel displays
     * @param m The MainLoop powering the Game
     */
    protected void setReferences(GameEventListener g, MainLoop m) {
        gameEventListener = g;
        mainLoop = m;
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
    // Framework Functionality
    //////////////////////////////////////////////////

    protected int getActualX() {
        return actualX;
    }

    protected int getActualY() {
        return actualY;
    }

    protected int getVirtualX() {
        return virtualX;
    }

    protected void setVirtualX(int virtualX) {
        this.virtualX = virtualX;
    }

    protected int getVirtualY() {
        return virtualY;
    }

    protected void setVirtualY(int virtualY) {
        this.virtualY = virtualY;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (mainLoop != null)
            synchronized (GAME_LOCK) {
                super.paintComponent(g);
                mainLoop.nextFrame(new GamePanelGraphics(g, this));
            }
    }

    //////////////////////////////////////////////////
    // Event Listeners
    //////////////////////////////////////////////////
    
    /**
     * This class passes key press/release events to the game
     * 
     * Key event are filtered such that only when a key is pressed
     * will keyPressed() be fired (instead of multiple firings when
     * a key is held down).
     */
    private class KeyLis implements KeyListener {

        private Set<Integer> pressedKeys;

        public KeyLis() {
            pressedKeys = new HashSet<>();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            synchronized (GAME_LOCK) {
                if (!pressedKeys.contains(code)) {
                    pressedKeys.add(code);
                    gameEventListener.keyPressed(code);
                }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();
            synchronized (GAME_LOCK) {
                if (pressedKeys.contains(code)) {
                    pressedKeys.remove(code);
                    gameEventListener.keyReleased(code);
                }
            }

        }
        
        @Override
        public void keyTyped(KeyEvent e) {}
    }

    /**
     * This class passes mouse press, release, and movement events to the game
     */
    private class MousePressLis implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            synchronized (GAME_LOCK) {
                gameEventListener.mousePressed(e.getX(), e.getY(), e.getButton());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            synchronized (GAME_LOCK) {
                gameEventListener.mouseReleased(e.getX(), e.getY(), e.getButton());
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    
    private class MouseMoveLis implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseMovedTo(e.getX(), e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseMovedTo(e.getX(), e.getY());
        }

        /**
         * Represents an event where the mouse is simply moved, regardless of
         * whether or not the mouse is pressed.
         *
         * @param x coordinate of the current mouse position
         * @param y coordinate of the current mouse position
         */
        private void mouseMovedTo(int x, int y) {
            synchronized (GAME_LOCK) {
                gameEventListener.mouseMoved(x, y);
            }
        }
    }
}
