package enemy;

import java.awt.Color;
import java.awt.Graphics;

import overhead.MainLoop;
import game.DodgerGame;
import game.Movable;
import game.Player;

/**
 * A Circle object represents a basic enemy circle the player must avoid
 *  
 * @author Wesley Cox
 * @last_edited 11/18/15
 */
public class Circle extends Movable implements Enemy {
	
	private static final int OFFSCREEN_BUFFER = 10000;
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	private int velx, vely, radius;
	private Player player;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @param speed
	 * @param radius
	 */
	public Circle(int x, int y, int direction, int speed, int radius) {
		super(x, y);
			
		velx = (int)(speed * Math.cos(Math.toRadians(direction)));
		vely = -(int)(speed * Math.sin(Math.toRadians(direction)));
		this.radius = radius;
		
		player = Player.getPlayer();
	}
	
	/**
	 * @return true if the circle is completely offscreen, false otherwise
	 */
	private boolean offScreen() {
		return (x + radius < -OFFSCREEN_BUFFER || x - radius > DodgerGame.PANEL_X + OFFSCREEN_BUFFER ||
				y + radius < -OFFSCREEN_BUFFER || y - radius > DodgerGame.PANEL_Y + OFFSCREEN_BUFFER);
	}
	
	//////////////////////////////////////////////////
	// Update
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 */
	@Override
	public void update() {
		move(velx, vely);
		
		if (isCollided(player)) {
			player.collided(this);
		}
		if(offScreen()) {
			MainLoop.remove(this);
		}
	}
	
	/**
	 * TODO
	 */
	@Override
	public boolean isCollided(Player player) {
		return (hypotenuse(Math.abs(player.x() - x), Math.abs(player.y() - y)) <= Player.HITBOX_RADIUS + radius);
	}
	
	/**
	 * TODO move to Math-Random
	 * @param x
	 * @param y
	 * @return
	 */
	private static int hypotenuse(int x, int y) {
		return (int)Math.sqrt((x * x) + (y * y));
	}
	
	//////////////////////////////////////////////////
	// Painting
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		g.setColor(Color.BLACK);
		g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}
}
