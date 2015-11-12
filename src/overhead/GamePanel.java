package overhead;
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

import abstractions.Game;

/**
 * Handles the display of the given game.
 * TODO try/compare instead of letting java's mainframe handle repaint requests,
 * 		keep hold of graphics object and redraw in a self-defined loop
 * 
 * @author Wesley
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	private Game game;
    
	/**
	 * TODO
	 * @param g the game to display
	 * @param width the width of the display's panel (in pixels)
	 * @param height the height of the display's panel (in pixels)
	 */
    public GamePanel(Game g, int width, int height) {
    	this(g, new Dimension(width, height));
    }
    
    /**
	 * TODO
	 * @param g the game to display
	 * @param gameArea the dimensions of the area that the game takes up (in pixels)
	 */
    public GamePanel(Game g, Dimension gameArea) {
    	super();
    	game = g;
    	
    	this.setPreferredSize(gameArea);
    	createFrame();
        setFocusable(true);

        addKeyListener(new KeyLis());
        addMouseListener(new MousePressLis());
        addMouseMotionListener(new MouseMoveLis());
        
        Thread t = new Thread(new Animate(60));
        t.run();
    }

	@Override
    public void paintComponent(Graphics g) {
        synchronized (game) {
            super.paintComponent(g);
        	MainLoop.nextFrame(g);
        }
    }
    
    /**
     * TODO
     */
    private void createFrame() {
    	JFrame frame = new JFrame();
    	frame.setResizable(false);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setContentPane(this);
    	frame.pack();
    	frame.setVisible(true);
    }
    
    /**
     * TODO
     * @author Wesley
     *
     */
    private class KeyLis implements KeyListener {
    	
    	private Set<Integer> pressedKeys;
    	
    	public KeyLis() {
    		pressedKeys = new HashSet<>();
    	}
    	
        @Override
        public void keyPressed(KeyEvent e) {
        	int code = e.getKeyCode();
        	synchronized (game) {
        		if (!pressedKeys.contains(code)) {
        			pressedKeys.add(code);
					game.keyPressed(code);
				}
        	}
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
        	int code = e.getKeyCode();
    		synchronized (game) {
    			if (pressedKeys.contains(code)) {
            		pressedKeys.remove(code);
					game.keyReleased(code);
				}
        	}
        	
        }
        
        @Override
        public void keyTyped(KeyEvent e) {}
    }

    /**
     * TODO
     * @author Wesley
     *
     */
    private class MousePressLis implements MouseListener {

    	@Override
    	public void mousePressed(MouseEvent e) {
    		synchronized (game) {
				game.mousePressed(e.getX(), e.getY(), e.getButton());
    		}
    	}

    	@Override
    	public void mouseReleased(MouseEvent e) {
    		synchronized (game) {
				game.mouseReleased(e.getX(), e.getY(), e.getButton());
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
		 * @param x x coordinate of the current mouse position
		 * @param y y coordinate of the current mouse position
		 */
    	private void mouseMovedTo(int x, int y) {
    		synchronized (game) {
    			game.mouseMoved(x, y);
    		}
    	}
    }
    
    /**
     * This Thread fires an update and a screen refresh to the game based on
     * its desired frames per seconds
     * 
     * @author Wesley
     */
    private class Animate implements Runnable {

    	private int waitTime;
    	
    	/**
    	 * 
    	 * @param fps the desired refresh rate of the screen measured in frames per second
    	 */
    	public Animate(int fps) {
    		waitTime = 1000 / fps;
    	}
    	
    	@Override
    	public void run() {
    		while (!Thread.interrupted()) {
    			try {
    				//TODO apparently repaint only quickly signals java to later call paintComponent();
    				//	Timing code should envelope the paintComponent code
    				long startTime = System.currentTimeMillis();
                    repaint();
                    long endTime = System.currentTimeMillis();
                    
                    if (startTime - endTime < waitTime) {
                    	Thread.sleep(waitTime - (startTime - endTime));
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                	System.exit(1);
                }
            }
    	}
    }
}
