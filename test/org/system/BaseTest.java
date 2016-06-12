package org.system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.framework.MainLoop;
import org.framework.GameFramework;
import org.framework.interfaces.GameEventListener;
import org.framework.interfaces.GameObj;

/**
 * Test for Panel/Window operations
 * (Move, Minimize, Close, Display)
 * 
 * @author Wesley Cox
 */
public class BaseTest implements GameEventListener{

	private static final int PANEL_X = 500;
	private static final int PANEL_Y = 500;
	private static final int FPS = 60;
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(PANEL_X, PANEL_Y);
		GameFramework.startGame(BaseTest.class, FPS, dim);
		while(true) {}
	}
	
	public BaseTest(MainLoop mainLoop) {
		mainLoop.add(new Line(), 0);
	}

	@Override
	public void keyPressed(int keyCode) {}

	@Override
	public void keyReleased(int keyCode) {}

	@Override
	public void mousePressed(int x, int y, int button) {}

	@Override
	public void mouseReleased(int x, int y, int button) {}

	@Override
	public void mouseMoved(int x, int y) {}
	
	private class Line implements GameObj {

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawLine(100, 100, 400, 400);
		}

		@Override
		public void update() {}
	}
}
