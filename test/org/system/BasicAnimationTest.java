package org.system;

import java.awt.Graphics;

import org.framework.MainLoop;
import org.framework.GameFramework;
import org.framework.interfaces.AspectRatio;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameObj;

/**
 * Test for Animation
 * (Update and Paint cycles)
 * 
 * @author Wesley Cox
 */
public class BasicAnimationTest implements Game{
	
	private static final int PANEL_X = 500;
	// private static final int PANEL_Y = 600;
	private static final int FPS = 60;
	
	public static void main(String args[]) {
		GameFramework.startGame(BasicAnimationTest.class, GameFramework.EMPTY_GAME_LISTENER, AspectRatio.ONE_TO_ONE, FPS);
		while(true) {}
	}
	
	public BasicAnimationTest(MainLoop mainLoop) {
		mainLoop.add(new WrapCircle(500, 70, 40), 0);
		mainLoop.add(new Display1(), 0);
		mainLoop.add(new Display2(), 0);
		mainLoop.add(new Display3(), 0);
	}
	
	/**************************************** Basic Shapes *************************************************/
	
	private class Line implements GameObj {
		
		private int x1, y1, x2, y2;
		
		public Line() {
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
		}
		
		public void set(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		@Override
		public void draw(Graphics g) {
			g.drawLine(x1, y1, x2, y2);
		}

		@Override
		public void update() {}
	}
	
	private class WrapCircle implements GameObj {
		
		protected int centerX, centerY, radius;
		
		public WrapCircle(int x, int y, int rad) {
			centerX = x;
			centerY = y;
			radius = rad;
		}
		
		public void checkWrap() {
			if (centerX > PANEL_X) {
				centerX -= PANEL_X;
			} else if (centerX < 0) {
				centerX += PANEL_X;
			} else {
				return;
			}
			checkWrap();
		}
		
		@Override
		public void draw(Graphics g) {
			g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
			
			//wrap around the screen
			if (centerX + radius >= PANEL_X) {
				g.drawOval(centerX - radius - PANEL_X, centerY - radius, radius * 2, radius * 2);
			}
			if (centerX - radius <= PANEL_X) {
				g.drawOval(centerX - radius + PANEL_X, centerY - radius, radius * 2, radius * 2);
			}
		}

		@Override
		public void update() {
			centerX += 2;
			checkWrap();
		}
	}
	
	/**
	 * Here, the circle's orbit's coordinates are represented
	 */
	private class OrbitCircle implements GameObj {
		
		protected int axelX, axelY, orbRadius, radius;
		protected double rotation;
		
		public OrbitCircle(int axelX, int axelY, int orbitRad, int rad) {
			this.axelX = axelX;
			this.axelY = axelY;
			orbRadius = orbitRad;
			radius = rad;
		}
		
		public int getX() {
			return (int) (axelX + orbRadius * Math.cos(Math.toRadians(rotation)));
		}
		
		public int getY() {
			return (int) (axelY + orbRadius * Math.sin(Math.toRadians(rotation)));
		}
		
		public void checkWrap() {
			if (rotation > 360) {
				rotation -= 360;
			} else if (rotation < 0) {
				rotation += 360;
			} else {
				return;
			}
			checkWrap();
		}
		
		@Override
		public void draw(Graphics g) {
			int centerX = (int) (axelX + orbRadius * Math.cos(Math.toRadians(rotation)));
			int centerY = (int) (axelY + orbRadius * Math.sin(Math.toRadians(rotation)));
			g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
		}

		@Override
		public void update() {}
	}
	
	/************************************ Fancier configurations ************************************/
	
	private class Display1 implements GameObj {
		
		OrbitCircle baseCircle, oCir;
		Line lx, ly, lxbase, lybase;
		private static final int x = 150;
		private static final int y = 200;
		private static final int orbitRad = 40;
		
		public Display1() {
			oCir = new OrbitCircle(x, y, orbitRad, 10);
			baseCircle = new OrbitCircle(x, y, 0, orbitRad);
			lxbase = new Line();
			lxbase.set(x - 55, oCir.getY() - orbitRad, x - 55, oCir.getY() + orbitRad);
			lybase = new Line();
			lybase.set(oCir.getX() - orbitRad * 2, y - 55, oCir.getX(), y - 55);
			
			lx = new Line();
			ly = new Line();
			setLines();
		}
		
		public void setLines() {
			lx.set(x - 55, oCir.getY(), oCir.getX(), oCir.getY());
			ly.set(oCir.getX(), y - 55, oCir.getX(), oCir.getY());
		}
		
		@Override
		public void update() {
			oCir.rotation += 2;
			oCir.checkWrap();
			setLines();
		}

		@Override
		public void draw(Graphics g) {
			baseCircle.draw(g);
			oCir.draw(g);
			lx.draw(g);
			ly.draw(g);
			lxbase.draw(g);
			lybase.draw(g);
		}
	}
	
	private class Display2 implements GameObj {
		
		Line l1, l2, l3;
		OrbitCircle baseCircle, p11, p12, p21, p22, p31, p32;
		private static final int x = 150;
		private static final int y = 350;
		private static final int orbitRad = 40;

		public Display2() {
			baseCircle = new OrbitCircle(x, y, 0, orbitRad);
			p11 = new OrbitCircle(x, y, orbitRad, 0);
			p12 = new OrbitCircle(x, y, orbitRad, 0);
			p21 = new OrbitCircle(x, y, orbitRad, 0);
			p22 = new OrbitCircle(x, y, orbitRad, 0);
			p31 = new OrbitCircle(x, y, orbitRad, 0);
			p32 = new OrbitCircle(x, y, orbitRad, 0);
			p32.rotation = 100;
			
			l1 = new Line();
			l2 = new Line();
			l3 = new Line();
			setLines();
		}
		
		public void setLines() {
			l1.set(p11.getX(), p11.getY(), p12.getX(), p12.getY());
			l2.set(p21.getX(), p21.getY(), p22.getX(), p22.getY());
			l3.set(p31.getX(), p31.getY(), p32.getX(), p32.getY());
		}
		
		@Override
		public void update() {
			p11.rotation -= 2;
			p11.checkWrap();
			p12.rotation += 2;
			p12.checkWrap();
			
			p21.rotation -= 3.2;
			p21.checkWrap();
			p22.rotation += 4;
			p22.checkWrap();
			
			p31.rotation -= 0.6;
			p31.checkWrap();
			p32.rotation -= 0.6;
			p32.checkWrap();
			setLines();
		}

		@Override
		public void draw(Graphics g) {
			baseCircle.draw(g);
			l1.draw(g);
			l2.draw(g);
			l3.draw(g);
		}
	}
	
	private class Display3 implements GameObj{
		
		OrbitCircle baseCircle, o1, o1_1;
		private static final int x = 425;
		private static final int y = 350;

		public Display3() {
			baseCircle = new OrbitCircle(x, y, 0, 70);
			o1 = new OrbitCircle(x, y, 70, 40);
			o1_1 = new OrbitCircle(o1.getX(), o1.getY(), 70, 20);
			
		}
		
		public void setCenter(OrbitCircle orbit, OrbitCircle base) {
			orbit.axelX = base.getX();
			orbit.axelY = base.getY();
		}
		
		public void update() {
			o1.rotation -= 0.15;
			o1.checkWrap();
			
			o1_1.rotation += 0.41;
			o1_1.checkWrap();
			setCenter(o1_1, o1);
		}

		@Override
		public void draw(Graphics g) {
			baseCircle.draw(g);
			o1.draw(g);
			o1_1.draw(g);
		}
	}
}
