package org.system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.framework.MainLoop;
import org.framework.GameFramework;
import org.framework.interfaces.GameEventListener;
import org.framework.interfaces.GameObj;

/**
 * Test for Long initialization times
 * 
 * @author Wesley Cox
 */
public class DelayInitTest implements GameEventListener{

	private static final int PANEL_X = 500;
	private static final int PANEL_Y = 500;
	private static final int FPS = 60;
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(PANEL_X, PANEL_Y);
		GameFramework.startGame(DelayInitTest.class, FPS, dim);
		while(true) {}
	}
	
	public DelayInitTest(MainLoop mainLoop) {
		int sum = 0;
		for (int i = 0; i < 100000; i ++) {
			for (int j = 0; j < 100000; j++) {
				sum += j;
			}
		}
		mainLoop.add(new Num(sum), 0);
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
	
	private class Num implements GameObj {
		
		private int num;
		
		public Num(int num) {
			this.num = num;
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawString("" + num, 100, 100);
		}

		@Override
		public void update() {}
	}
}
