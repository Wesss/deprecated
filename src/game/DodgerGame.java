package game;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import overhead.MainLoop;
import overhead_interfaces.Game;
import enemy.scripts.Script;
import enemy.scripts.Script01;
import enemy.scripts.Script02;
import enemy.scripts.Scripter;

/**
 * This class represents the dodging game itself
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class DodgerGame implements Game {
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	public static final int PANEL_X = 500;
	public static final int PANEL_Y = 500;
	public static final int FPS = 60;
	
	private static enum State{
		MENU, GAME
	}
	
	/**
	 * A DodgerGame represents the dodging game, where:
	 * 	player is the object the player controls
	 * 	menu is the start menu
	 *  MainLoop is the update cycle created for this game
	 * 
	 *  TODO is there a better way/different place to handle these fields?
	 * 	state represents the high-level state the game is currently in
	 * 	level represents the player's current level
	 */
	
	private Player player;
	private Menu menu;
	private State state;
	private MainLoop mainLoop;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	public DodgerGame(MainLoop mainLoop) {
		this.mainLoop = mainLoop;
		menuMode();
	}
	
	//////////////////////////////////////////////////
	// Game mode handling
	//////////////////////////////////////////////////
	
	public void menuMode() {
		state = State.MENU;
		mainLoop.clear();
		
		menu = new Menu(this);
		mainLoop.add(menu);
	}
	
	public void gameMode() {
		state = State.GAME;
		mainLoop.clear();
		
		player = new Player(this);
		
		Queue<Script> levelQueue = new LinkedList<>();
		levelQueue.add(new Script01(mainLoop));
		levelQueue.add(new Script02(mainLoop));
		
		new Scripter(this, levelQueue, mainLoop);
		
		mainLoop.add(player, 1);
		mainLoop.add(new Border(), 2);
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
