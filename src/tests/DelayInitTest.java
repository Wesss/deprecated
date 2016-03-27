package tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import overhead.Overhead;
import overhead.MainLoop;
import overhead_interfaces.Game;
import overhead_interfaces.GameObj;

/**
 * Test for Long initialization times
 * 
 * @author Wesley Cox
 * @last_edited 3/27/15
 */
public class DelayInitTest implements Game{

	private static final int PANEL_X = 500;
	private static final int PANEL_Y = 500;
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(PANEL_X, PANEL_Y);
		Overhead.startGame(DelayInitTest.class, dim);
		while(true) {}
	}
	
	public DelayInitTest() {
		int sum = 0;
		for (int i = 0; i < 100000; i ++) {
			for (int j = 0; j < 100000; j++) {
				sum += j;
			}
		}
		MainLoop.add(new Num(sum), 0);
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
