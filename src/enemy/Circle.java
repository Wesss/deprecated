package enemy;

import java.awt.Color;
import java.awt.Graphics;

import overhead.MainLoop;
import game.DodgerGame;
import game.Player;
import game.Position;
import numbers.MyMath;

/**
 * A Circle object represents a basic enemy circle the player must avoid
 *  
 * @author Wesley Cox
 * @last_edited 12/01/15
 */
public class Circle implements Enemy {
	
	private static final int OFFSCREEN_BUFFER = 10000;
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	/**
	 * A Circle:
	 * 	has a location on the panel defined by postion
	 * 	has a size/collision area around its location of length radius
	 * 	travels in the x and y direction at a rate of velx and vely
	 * 			decipixels per frame respectively.
	 * 
	 * Representation Invariant:
	 * position != null
	 */
	private Position position;
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
		position = new Position(x, y);
			
		velx = (int)(speed * Math.cos(Math.toRadians(direction)));
		vely = -(int)(speed * Math.sin(Math.toRadians(direction)));
		this.radius = radius;
		
		player = Player.getPlayer();
	}
	
	/**
	 * @return true if the circle is completely offscreen, false otherwise
	 */
	private boolean offScreen() {
		return (position.x() + radius < -OFFSCREEN_BUFFER ||
				position.x() - radius > DodgerGame.PANEL_X + OFFSCREEN_BUFFER ||
				position.y() + radius < -OFFSCREEN_BUFFER ||
				position.y() - radius > DodgerGame.PANEL_Y + OFFSCREEN_BUFFER);
	}
	
	//////////////////////////////////////////////////
	// Update
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 */
	@Override
	public void update() {
		position.move(velx, vely);
		
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
		return (MyMath.hypotenuse(Math.abs(player.x() - position.x()),
								  Math.abs(player.y() - position.y()))
				<= Player.HITBOX_RADIUS + radius);
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
		g.fillOval(position.x() - radius, position.y() - radius, radius * 2, radius * 2);
		g.setColor(Color.BLACK);
		g.drawOval(position.x() - radius, position.y() - radius, radius * 2, radius * 2);
	}
}
