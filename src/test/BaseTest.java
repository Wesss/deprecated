package test;

import java.awt.Color;
import java.awt.Graphics;

import overhead.MainLoop;
import abstractions.Game;
import abstractions.GameObj;

public class BaseTest extends Game{

	public static void main(String args[]) {
		new BaseTest();
		while(true) {}
	}
	
	public BaseTest() {
		super(500, 500);
	}

	@Override
	public void initiallize() {
		MainLoop.add(new Line(), 0);
	}

	@Override
	public void keyPressed(int keyCode) {
	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
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
