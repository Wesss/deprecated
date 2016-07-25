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

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.framework.interfaces.GameEventListener;

/**
 * Handles the display of the given game.
 * 
 * TODO try/compare instead of letting java's mainframe handle repaint requests,
 * 		keep hold of graphics object and redraw in a self-defined loop
 * TODO: create non-parallel abstraction?
 * 
 * @author Wesley Cox
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

    /**
     * After creation, but before setReferences:
     * game == EmptyGame
     * mainLoop == null
     * Even as user events and repaint requests come in, they will be ignored until setReferences is called.
     *
     * After setReferences:
     * game and mainLoop are set to appropriately initialized overhead objects
     */

    private GameEventListener gameEventListener;
    private MainLoop mainLoop;

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
        this(new Dimension(width, height));
    }
    
    /**
     * Creates a new GamePanel of specified size for given game g
     *
     * @param gameArea the dimensions of the area that the game takes up (in pixels)
     * 		<UL><LI> must not be null </UL>
     */
    protected GamePanel(Dimension gameArea) {
        super();
        gameEventListener = GameFramework.EMPTY_GAME_LISTENER;

        this.setPreferredSize(gameArea);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    //////////////////////////////////////////////////
    // Framework Functionality
    //////////////////////////////////////////////////

    @Override
    public void paintComponent(Graphics g) {
        if (mainLoop != null)
            synchronized (gameEventListener) {
                super.paintComponent(g);
                mainLoop.nextFrame(g);
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
            synchronized (gameEventListener) {
                if (!pressedKeys.contains(code)) {
                    pressedKeys.add(code);
                    gameEventListener.keyPressed(code);
                }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();
            synchronized (gameEventListener) {
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
            synchronized (gameEventListener) {
                gameEventListener.mousePressed(e.getX(), e.getY(), e.getButton());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            synchronized (gameEventListener) {
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
            synchronized (gameEventListener) {
                gameEventListener.mouseMoved(x, y);
            }
        }
    }
}
