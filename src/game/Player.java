package game;

import java.awt.Color;
import java.awt.Graphics;

import overhead_interfaces.GameObj;
import enemy.Enemy;

/**
 * This class represents the red dot the player controls within the game
 * 
 * @author Wesley Cox
 * @last_edited 11/23/15
 */
public class Player extends Movable implements GameObj{
	
	/**
	 * TODO make singleton or get rid of static reference-getting
	 */

	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////

	public static final int HITBOX_RADIUS = 7;
	private static final int DISPLAY_RADIUS = 8;
	private static final int SPEED = 38;
	private static final int DIAG_SPEED = (int)(SPEED / Math.sqrt(2));
	private static final int SLOW_SPEED = 19;
	private static final int SLOW_DIAG_SPEED = (int)(SLOW_SPEED / Math.sqrt(2));
	private static final int RESPAWN_TIME = 100;
	private static Player player;
	
	/**
	 * The player is moving in a direction, defined as:
	 * 	For the x direction:
	 * 		-left if (moveLeft && (!moveRight || leftPriority))
	 *  	-right if (moveRight && (!moveLeft || !leftPriority))
	 *  	-stationary otherwise;
	 *  For the y direction:
	 * 		-up if (moveUp && (!moveDown || upPriority))
	 *  	-down if (moveDown && (!moveUp || !upPriority))
	 *  	-stationary otherwise;
	 * 
	 * The player can move diagonally
	 * The player moves at speed:
	 * 	SPEED if (!isSlow)
	 * 	SLOW_SPEED if (isSlow)
	 * 
	 * The player has total remaining lives defined by (lives)
	 * The player is invulnerable if respawnTimer > 0
	 * 
	 * Representation Invariant:
	 * 	game != null
	 * 	lives >= 0
	 * 	respawnTimer >= 0
	 * 								
	 */
	private boolean moveUp, moveDown, moveLeft, moveRight, upPriority, leftPriority;
	private boolean isSlow;
	private DodgerGame game;
	
	private int lives; //TODO move to DodgerGame?
	private int respawnTimer;
	
	//////////////////////////////////////////////////
	// Initialization & References
	//////////////////////////////////////////////////
	
	public Player(DodgerGame game) {
		super(250, 250);
		this.game = game;
		
		lives = 3;
		respawnTimer = 0;
		player = this;
		
		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;
		upPriority = false;
		leftPriority = false;
		isSlow = false;
	}
	
	/**
	 * TODO
	 * @return the current player instance
	 */
	public static Player getPlayer() {
		if (player == null)
			throw new RuntimeException("Attempted to get Player before initiallization");
		return player;
	}
	
	//////////////////////////////////////////////////
	// Manipulation
	//////////////////////////////////////////////////
	
	/**
	 * Sets player's intended direction (see function name)
	 * @param setting true when the player wants to move in this direction
	 */
	public void setUp(boolean setting) {
		moveUp = setting;
		upPriority = setting;
	}
	
	/**
	 * Sets player's intended direction (see function name)
	 * @param setting true when the player wants to move in this direction
	 */
	public void setDown(boolean setting) {
		moveDown = setting;
		upPriority = !setting;
	}
	
	/**
	 * Sets player's intended direction (see function name)
	 * @param setting true when the player wants to move in this direction
	 */
	public void setLeft(boolean setting) {
		moveLeft = setting;
		leftPriority = setting;
	}
	
	/**
	 * Sets player's intended direction (see function name)
	 * @param setting true when the player wants to move in this direction
	 */
	public void setRight(boolean setting) {
		moveRight = setting;
		leftPriority = !setting;
	}
	
	/**
	 * Sets player's speed
	 * @param setting true when the player wants to move slowly
	 */
	public void setSlow(boolean setting) {
		isSlow = setting;
	}
	
	/**
	 * @return true if the player is currently invulnerable
	 */
	private boolean isInvulnerable() {
		return respawnTimer > 0;
	}
	
	/**
	 * Set the player to be invulnerable
	 * @param count the number of frames the player is to be invulnerable
	 */
	private void setInvulnerable(int count) {
		respawnTimer = count;
	}
	
	//////////////////////////////////////////////////
	// Update
	//////////////////////////////////////////////////
	
	/**
	 * Displace the player based on direction and speed,
	 * decrement invulnerability timer if invulnerable
	 */
	@Override
	public void update() {
		int h = getDirection(moveRight, moveLeft, leftPriority);
		int v = getDirection(moveDown, moveUp, upPriority);
		
		if (isSlow) {
			movePlayer(SLOW_SPEED, SLOW_DIAG_SPEED, h, v);
		} else {
			movePlayer(SPEED, DIAG_SPEED, h, v);
		}
		
		checkBorder();
		if (isInvulnerable())
			respawnTimer--;
	}

	/**
	 * horizontal: positive-rightwards, zero-stationary, negative-leftwards
	 * vertical: positive-downwards, zero-stationary, negative-upwards
	 */
	private static int getDirection(boolean movePos, boolean moveNeg, boolean negPriority) {
		if ((moveNeg && movePos && negPriority) || (moveNeg && !movePos)) {
			return -1;
		} else if ((moveNeg && movePos && !negPriority) || (!moveNeg && movePos)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * Displace player based on direction and speed
	 */
	private void movePlayer(int speed, int diagSpeed, int h, int v) {
		if (h > 0 && v == 0) {
			//right
			move(speed, 0);
		} else if (h > 0 && v < 0) {
			//up-right
			move(diagSpeed, -diagSpeed);
		} else if (h == 0 && v < 0) {
			//up
			move(0, -speed);
		} else if (h < 0 && v < 0) {
			//up-left
			move(-diagSpeed, -diagSpeed);
		} else if (h < 0 && v == 0) {
			//left
			move(-speed, 0);
		} else if (h < 0 && v > 0) {
			//down-left
			move(-diagSpeed, diagSpeed);
		} else if (h == 0 && v > 0) {
			//down
			move(0, speed);
		} else if (h > 0 && v > 0) {
			//down-right
			move(diagSpeed, diagSpeed);
		}
	}
	
	/**
	 * snap player back if the dot crossed a border
	 */
	private void checkBorder() {
		if (x < Border.LEFT_BORDER + HITBOX_RADIUS) {
			//left border collision
			x = Border.LEFT_BORDER + HITBOX_RADIUS;
		} else if (x > Border.RIGHT_BORDER - HITBOX_RADIUS) {
			//right border collision
			x = Border.RIGHT_BORDER - HITBOX_RADIUS;
		}
		if (y < Border.TOP_BORDER + HITBOX_RADIUS) {
			//left border collision
			y = Border.TOP_BORDER + HITBOX_RADIUS;
		} else if (y > Border.BOT_BORDER - HITBOX_RADIUS) {
			//right border collision
			y = Border.BOT_BORDER - HITBOX_RADIUS;
		}
	}
	
	/**
	 * Subtracts a life, then sets player to be invulnerable if lives still remain,
	 * else returns the user to the menu
	 * @param enemy the enemy we have collided with
	 */
	public void collided(Enemy enemy) {
		if (!isInvulnerable()) {
			lives--;
			if (lives > 0) {
				setInvulnerable(RESPAWN_TIME);
			} else {
				game.menuMode();
			}
		}
	}
	
	//////////////////////////////////////////////////
	// Painting
	//////////////////////////////////////////////////
	
	@Override
	public void draw(Graphics g) {
		//flicker player drawing if we are invulnerable
		if (respawnTimer % 2 == 0)
			drawPlayer(g, x, y);
		drawStats(g);
	}
	
	/**
	 * Draw the player as a red circle centered around (x,y)
	 */
	private static void drawPlayer(Graphics g, int x, int y) {
		g.setColor(Color.RED);
		g.fillOval(x - DISPLAY_RADIUS, y - DISPLAY_RADIUS, DISPLAY_RADIUS * 2, DISPLAY_RADIUS * 2);
		g.setColor(Color.BLACK);
		g.drawOval(x - DISPLAY_RADIUS, y - DISPLAY_RADIUS, DISPLAY_RADIUS * 2, DISPLAY_RADIUS * 2);
	}
	
	/**
	 * Draw UI
	 */
	private void drawStats(Graphics g) {
		for(int i = 0; i < lives - 1; i++) {
			drawPlayer(g, Border.RIGHT_BORDER - ((i * 2 + 1) * (HITBOX_RADIUS + 3)), Border.BOT_BORDER + 2 * HITBOX_RADIUS);
		}
		g.drawString("Wave " + game.getLevel() + "!", Border.LEFT_BORDER + 3, Border.BOT_BORDER + 20);
	}
}
