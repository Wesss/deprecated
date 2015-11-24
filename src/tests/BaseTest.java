package tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import overhead.Overhead;
import overhead.MainLoop;
import overhead_interfaces.Game;
import overhead_interfaces.GameObj;

/**
 * Test for Panel/Window operations
 * (Move, Minimize, Close, Static Display)
 * 
 * @author Wesley Cox
 * @last_edited 11/23/15
 */
public class BaseTest implements Game{

	private static final int PANEL_X = 500;
	private static final int PANEL_Y = 500;
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(PANEL_X, PANEL_Y);
		Overhead.startGame(new BaseTest(), dim);
		while(true) {}
	}

	@Override
	public void initiallize() {
		MainLoop.add(new Line(), 0);
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
