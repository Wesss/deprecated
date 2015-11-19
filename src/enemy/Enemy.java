package enemy;

import overhead_interfaces.GameObj;
import game.Player;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited 11/15/15
 */
public interface Enemy extends GameObj {

	public static enum EnemyType {
		CIRCLE
	}
	/**
	 * @param player the player to check collision with
	 * @return true when this Enemy and the player have collided
	 */
	public boolean isCollided(Player player);
}
