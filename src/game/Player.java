package game;

import java.awt.Color;
import java.awt.Graphics;

import overhead_interfaces.GameObj;
import enemy.Enemy;

/**
 * The red dot the player controls in the game
 * @author Wesley Cox
 *
 */
public class Player extends Movable implements GameObj{
	
	/****************************** Class level methods ***********************************/

	public static final int HITBOX_RADIUS = 7;
	private static final int SPEED = 38;
	private static final int DIAG_SPEED = 27;
	private static final int SLOW_SPEED = 19;
	private static final int SLOW_DIAG_SPEED = 14;
	private static Player player;
	
	public static Player getPlayer() {
		if (player == null)
			throw new RuntimeException("Attempted to getPlayer before initiallization");
		return player;
	}
	
	/****************************************** Instance fields ************************************/
	
	private boolean moveUp, moveDown, moveLeft, moveRight, upPriority, leftPriority; //direction of player movement
	private boolean isSlow; //intentional slow-down of player
	private DodgerGame game;
	
	public Player(DodgerGame game) {
		super(250, 250);
		this.game = game;
		Stats.initialize();
		player = this;
		
		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;
		upPriority = false;
		leftPriority = false;
		isSlow = false;
	}
	
	/***************************************** Event methods ****************************************/
	
	public void setUp(boolean setting) {
		moveUp = setting;
		upPriority = setting;
	}
	
	public void setDown(boolean setting) {
		moveDown = setting;
		upPriority = !setting;
	}
	
	public void setLeft(boolean setting) {
		moveLeft = setting;
		leftPriority = setting;
	}
	
	public void setRight(boolean setting) {
		moveRight = setting;
		leftPriority = !setting;
	}
	
	public void setSlow(boolean setting) {
		isSlow = setting;
	}
	
	//**************************************** Update Methods **************************************//
	
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
		Stats.update();
	}

	/**
	 * horizontal: positive-rightwards, zero-stationary, negative-leftwards
	 * vertical: positive-downwards, zero-stationary, negative-upwards
	 * @param movePos
	 * @param moveNeg
	 * @param negPriority
	 * @return
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
	
	public void collided(Enemy enemy) {
		if (!Stats.isInvulnerable()) {
			Stats.lives--;
			if (Stats.lives > 0) {
				Stats.setInvulnerable(100);
			} else {
				game.menuMode();
			}
		}
	}
	
	//****************************************** Painting methods ***********************************//
	
	@Override
	public void draw(Graphics g) {
		if (Stats.respawnTimer % 2 == 0)
			drawPlayer(g, x, y);
		Stats.draw(g);
	}
	
	private static void drawPlayer(Graphics g, int x, int y) {
		int radius = HITBOX_RADIUS + 1;
		g.setColor(Color.RED);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		g.setColor(Color.BLACK);
		g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	//****************************** Player Statistics/Overhead ******************************//
	private static class Stats {
		public static int lives;
		public static int respawnTimer;
		public static DodgerGame game;
		
		public static void initialize() {
			lives = 3;
			respawnTimer = 0;
			game = DodgerGame.getDodgerGame();
		}
		
		//******************** Manipulation ***************************//
		
		public static boolean isInvulnerable() {
			return respawnTimer > 0;
		}
		
		public static void setInvulnerable(int count) {
			respawnTimer = count;
		}
		
		//******************** Update and Paint *************************//
		
		public static void update() {
			if (isInvulnerable())
				respawnTimer--;
		}
		
		public static void draw(Graphics g) {
			for(int i = 0; i < lives - 1; i++) {
				drawPlayer(g, Border.RIGHT_BORDER - ((i * 2 + 1) * (HITBOX_RADIUS + 3)), Border.BOT_BORDER + 2 * HITBOX_RADIUS);
			}
			g.drawString("Wave " + game.getLevel() + "!", Border.LEFT_BORDER + 3, Border.BOT_BORDER + 20);
		}
	}
}
