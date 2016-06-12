package org.system;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.framework.MainLoop;
import org.framework.GameFramework;
import org.framework.interfaces.GameEventListener;
import org.framework.interfaces.GameObj;

/**
 * Test for EventListeners
 * (Key press/release, Mouse press/release, mouse position)
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class EventFireTest implements GameEventListener{
	
	private static final int PANEL_X = 500;
	private static final int PANEL_Y = 500;
	private static final int FPS = 60;
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(PANEL_X, PANEL_Y);
		GameFramework.startGame(EventFireTest.class, FPS, dim);
		while(true) {}
	}

	private GameString[] log;
	private GameString mouseLoc;
	
	public EventFireTest(MainLoop mainLoop) {
		log = new GameString[20];
		
		for (int i = 0; i < log.length; i++) {
			log[i] = new GameString(20, 430 - (i * 20));
			mainLoop.add(log[i], 0);
		}
		
		mouseLoc = new GameString(20, 460);
		mainLoop.add(mouseLoc, 0);
	}

	@Override
	public void mousePressed(int x, int y, int button) {
		newEvent("pressed  button "+ button);
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
		newEvent("released button "+ button);
	}

	@Override
	public void mouseMoved(int x, int y) {
		mouseLoc.setString("Mouse Position: (" + x + ", " + y + ")");
	}

	@Override
	public void keyPressed(int key) {
		newEvent("pressed  key "+ KeyEvent.getKeyText(key));
	}

	@Override
	public void keyReleased(int key) {
		newEvent("released key "+ KeyEvent.getKeyText(key));
	}
	
	public void newEvent(String s) {
		for (int i = log.length - 1; i > 0; i--) {
			log[i].setString(log[i - 1].getString());
		}
		log[0].setString(s); 
	}
	
	private class GameString implements GameObj {
		
		private String string;
		private int x;
		private int y;
		
		/**
		 * @param x the x coordinate of the top left of the string
		 * @param y the y coordinate of the top left of the string
		 */
		public GameString(int x, int y) {
			string = "";
			this.x = x;
			this.y = y;
		}
		
		public String getString() {
			return string;
		}
		
		public void setString(String s) {
			string = s;
		}
		
		@Override
		public void draw(Graphics g) {
			g.drawString(string, x, y);
		}

		@Override
		public void update() {}
	}
}
