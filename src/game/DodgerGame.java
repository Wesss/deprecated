package game;

import java.awt.event.KeyEvent;
import java.util.Queue;

import overhead.MainLoop;
import overhead_interfaces.Game;
import enemy.Script;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited 11/23/15
 */
public class DodgerGame implements Game {
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	public static final int PANEL_X = 500;
	public static final int PANEL_Y = 500;
	
	private static enum State{
		MENU, GAME
	}
	
	/**
	 * TODO
	 */
	
	private Player player;
	private Menu menu;
	private State state;
	
	private Script curScript;
	private Queue<Script> scriptQueue;
	private int level;
	
	//////////////////////////////////////////////////
	// Static level
	//////////////////////////////////////////////////
	
	private static DodgerGame game;
	
	/**
	 * @return the current player object
	 */
	public static DodgerGame getDodgerGame() {
		if (game == null) {
			throw new RuntimeException("Attempted to get game reference before initialization");
		}
		return game;
	}
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	public DodgerGame() {
		menuMode();
		game = this;
	}
	
	//////////////////////////////////////////////////
	// Game mode handling
	//////////////////////////////////////////////////
	
	public void menuMode() {
		state = State.MENU;
		MainLoop.clear();
		
		menu = new Menu(this);
		MainLoop.add(menu);
	}
	
	public void gameMode() {
		state = State.GAME;
		MainLoop.clear();
		
		player = new Player(this);
		
		MainLoop.add(player, 1);
		MainLoop.add(new Border(), 2);
		scriptQueue = Script.createScriptQueue();
		level = 0;
		nextLevel();
	}
	

	//////////////////////////////////////////////////
	// Level Script handlers
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 */
	public void nextLevel() {
		if (scriptQueue.isEmpty()) {
			curScript = null;
			menuMode();
		} else {
			if (curScript != null)
				Script.end(curScript);
			level++;
			curScript = scriptQueue.remove();
			Script.run(curScript);
		}
	}
	
	/**
	 * @return the current level of the game
	 */
	public int getLevel() {
		return level;
	}
	
	//////////////////////////////////////////////////
	// Key-press callees
	//////////////////////////////////////////////////
	
	@Override
	public void keyPressed(int keyCode) {
		switch (state) {
		case GAME :
			switch (keyCode) {
			case KeyEvent.VK_UP :
				player.setUp(true);
				break;
			case KeyEvent.VK_LEFT :
				player.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT :
				player.setRight(true);
				break;
			case KeyEvent.VK_DOWN :
				player.setDown(true);
				break;
			case KeyEvent.VK_SHIFT :
				player.setSlow(true);
			}
			break;
		case MENU :
			switch (keyCode) {
			case KeyEvent.VK_UP : case KeyEvent.VK_RIGHT :
				menu.up();
				break;
			case KeyEvent.VK_DOWN : case KeyEvent.VK_LEFT :
				menu.down();
				break;
			case KeyEvent.VK_SHIFT : case KeyEvent.VK_ENTER :
				menu.select();
			}
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		switch (state) {
		case GAME :
			switch (keyCode) {
			case KeyEvent.VK_UP :
				player.setUp(false);
				break;
			case KeyEvent.VK_LEFT :
				player.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT :
				player.setRight(false);
				break;
			case KeyEvent.VK_DOWN :
				player.setDown(false);
				break;
			case KeyEvent.VK_SHIFT :
				player.setSlow(false);
			}
			break;
		case MENU :
		}
	}

	
	//The mouse is not used in this game
	@Override
	public void mousePressed(int x, int y, int button) {}
	@Override
	public void mouseReleased(int x, int y, int button) {}
	@Override
	public void mouseMoved(int x, int y) {}
}
