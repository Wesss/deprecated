package enemy;

import overhead_interfaces.GameObj;
import game.Player;

public interface Enemy extends GameObj {
	
	/**
	 * @param player the player to check collision with
	 * @return true when this Enemy and the player have collided
	 */
	public boolean isCollided(Player player);
}
